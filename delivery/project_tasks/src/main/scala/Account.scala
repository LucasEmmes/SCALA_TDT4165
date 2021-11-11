import exceptions._

object uidGen extends App {
    var uidCount = 0L;
    def getUniqueId: Long = this.synchronized {
        val freshuid = uidCount + 1;
        uidCount = freshuid;
        return freshuid; 
    }
}

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val uid = uidGen.getUniqueId;

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
        if (this.getBalanceAmount < amount) {
            return Right("shit fucked yo")
        }
        if (amount < 0) {
            return Right("amount < 0 you stupid fack")
        }

        Left(balance.amount -= amount)
    }

    def deposit (amount: Double): Either[Unit, String] = this.synchronized {
        if (amount < 0) {
            return Right("amount < 0 you stupid fack")
        }
        Left(balance.amount += amount)
    }

    def getBalanceAmount: Double = {
        return balance.amount
    }

    def transferTo(account: Account, amount: Double) = {
        bank addTransactionToQueue (this, account, amount)
    }


}
