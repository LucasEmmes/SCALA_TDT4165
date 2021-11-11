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
        val t = create_thread(this.processTransactions)
        t.start()
    }

    private def processTransactions(): Unit = {
        // TOO
        // project task 2
        // Function that pops a transaction from the queue
        val transaction: Transaction = transactionsQueue.pop
        if (transaction.status == TransactionStatus.PENDING) {
            transactionsQueue.push(transaction)
            processTransactions()
        } else {
            // and spawns a thread to execute the transaction.
            val t = create_thread(() => {

                val result = transaction.from.withdraw(transaction.amount)

                result match {
                    case Left => {

                        val deposit = transaction.to.deposit(transaction.amount)

                        deposit match {
                            case Left => transaction.status = TransactionStatus.SUCCESS

                            case Right(string) => {
                                println(string)
                                transaction.status = TransactionStatus.FAILED
                                }
                        }
                    }

                    case Right(string) => {
                        println(string)
                        transaction.status = TransactionStatus.FAILED
                        }
                }

            })
        }
        this.processedTransactions.push(transaction)
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
