// Task 2

// a
def create_thread(function: () => Unit): Thread = {
    val t = new Thread {
        override def run {
            return function()
        }
    }
    return t
}


// b & c
private var counter: Int = 0

def increaseCounter(): Unit = this.synchronized {
    val current = counter
    // possible read here
    Thread.sleep(100)
    counter = current + 1
}

def printCounter() = {
    println(counter)
}

var t1 = create_thread(increaseCounter)
var t2 = create_thread(increaseCounter)
var t3 = create_thread(printCounter)

t1.start()
t2.start()
Thread.sleep(1000)
t3.start()

// d
object Lock1 {
    Thread.sleep(1000);
    lazy val break: Int = Lock2.break;
}
object Lock2 {
    Thread.sleep(1000);
    lazy val break: Int = Lock1.break;
}
var l1 = create_thread(() => {Lock1.break})
var l2 = create_thread(() => {Lock2.break})
l1.start()
l2.start()