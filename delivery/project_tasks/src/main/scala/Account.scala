import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
        if (this.getBalanceAmount() < amount) {
            return "shit fucked yo"
        }
        if (amount < 0) {
            return "amount < 0 you stupid fack"
        }

        balance.amount -= amount
    }

    def deposit (amount: Double): Either[Unit, String] = this.synchronized {
        if (amount < 0) {
            return "amount < 0 you stupid fack"
        }
        balance.amount += amount
    }

    def getBalanceAmount: Double = {
        return balance.amount
    }

    def transferTo(account: Account, amount: Double) = {
        bank addTransactionToQueue (this, account, amount)
    }


}
