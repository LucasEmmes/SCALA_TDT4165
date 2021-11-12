class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    def create_thread(function: () => Unit): Thread = {
        val t = new Thread {
            override def run {
                return function()
            }
        }
        return t
    }

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
        val transaction = new Transaction(this.transactionsQueue, this.processedTransactions, from, to, amount, this.allowedAttempts)
        transactionsQueue.push(transaction)
        runTransaction(transaction, to, from)
        
        val t = create_thread(processTransactions)
        t.start()
    }

    private def runTransaction(transaction: Transaction, to:Account, from:Account):Unit = {
        val t = create_thread(() => {
            if (from.uid < to.uid) {
                from.synchronized { to.synchronized{
                    executeTransaction(transaction)
                }}
            } else {
                to.synchronized { from.synchronized{
                    executeTransaction(transaction)
                }}
            }
        })
        t.start()
    }

    private def executeTransaction(transaction: Transaction):Unit = {
        transaction.attempt += 1
        val result = transaction.from.withdraw(transaction.amount)
        val result2 = transaction.to.deposit(transaction.amount)
        
        (result, result2) match {
            case (Left(()), Left(())) => transaction.status = TransactionStatus.SUCCESS
            case (Left(()), Right(string2)) => {
                transaction.from.deposit(transaction.amount)
                transaction.status = TransactionStatus.FAILED
            }
            case (Right(string1), Left(())) => {
                transaction.to.withdraw(transaction.amount)
                transaction.status = TransactionStatus.FAILED
            }
            case ((Right(string1), Right(string2))) => {
                transaction.status = TransactionStatus.FAILED
            }
            case _ => println("shits facked fam (TM)")
                
        }
    }

    private def processTransactions(): Unit = {
        println(transactionsQueue.iterator.toList.size) //MUY IMPORTANTE!! (REMOVE this for failed test)
        val transaction: Transaction = transactionsQueue.pop
        if (transaction.status == TransactionStatus.FAILED && transaction.attempt < transaction.allowedAttemps) {
            
            transaction.status == TransactionStatus.PENDING
            runTransaction(transaction, transaction.to, transaction.from)
        }
        if (transaction.status == TransactionStatus.PENDING) {
            transactionsQueue.push(transaction)
            
            processTransactions()
        } else {
            this.processedTransactions.push(transaction)
        }
    }
                                                

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
