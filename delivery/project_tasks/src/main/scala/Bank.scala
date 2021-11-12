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
        // TODO
        // project task 2
        // create a new transaction object and put it in the queue
        // spawn a thread that calls processTransactions
        val transaction = new Transaction(this.transactionsQueue, this.processedTransactions, from, to, amount, this.allowedAttempts)
        transactionsQueue.push(transaction)

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
        processTransactions()
    }


    private def executeTransaction(transaction: Transaction):Unit = {
        val result = transaction.from.withdraw(transaction.amount)
        // println("has withdrawn from fom")
        
        result match {
            case Left(()) => {
                // println("depositing to to")
                val result2 = transaction.to.deposit(transaction.amount)
                // println("deposited to to")

                result2 match {
                    case Left(()) => {
                        transaction.status = TransactionStatus.SUCCESS
                        }

                    case Right(string) => {
                        transaction.status = TransactionStatus.FAILED
                    }
                    case _ => println("shits fack fam (TM)")
                }
            }

            case Right(string) => {
                transaction.status = TransactionStatus.FAILED
            }
            case _ => println("shits fack fam (TM)")
        }
    }

    private def processTransactions(): Unit = {
        //println(this.transactionsQueue.isEmpty)
        // TOO
        // project task 2
        // Function that pops a transaction from the queue
        val transaction: Transaction = transactionsQueue.pop
        if (transaction.status == TransactionStatus.FAILED && transaction.attempt < transaction.allowedAttemps) {
            transaction.attempt += 1
            transaction.status == TransactionStatus.PENDING
        }
        if (transaction.status == TransactionStatus.PENDING) {
            transactionsQueue.push(transaction)
            processTransactions()
        } else {
            if (transaction.status == TransactionStatus.FAILED) {
                println("transaction failed", transaction.amount)
            }
            this.processedTransactions.push(transaction)
        }
        // Finally do the appropriate thing, depending on whether
        // the transaction succeeded or not
    }
                                                

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
