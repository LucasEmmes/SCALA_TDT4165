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


// b
// What is this phenomenon called? Give one example of a situation where it can be
// problematic.

// This is called non-determinism.

// Let's take the bank example. We have two threads.

// Thread 1 will read your current balance and then remove 2$

// Thread 2 will read your current balance and then add 3$

// All in all these two threads equals to four operations.

// t1: read balance
// t1: write balance - 2
// t2: read balance
// t2: write balance + 3

// Leaving you with a balance of +1$

// Since the order of operations is nondeterministisc we don't know which order they come in.

// In a worst case senario they could end up in this order:

// t1: read balance
// t2: read balance
// t2: write balance + 3
// t1: write balance - 2

// This would leave you with a balance of -2 instead of +1 which would be bad.

// c
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

// 2d) what is a deadlock?
// A lock is when an object only can be accessed by one thead at a time.
// When a thread is acessing the object no other thread is able to access it. We say that the thread has aquired the lock.
// When a thread want to access an object that is locked (accessed by an other thread) it will stall until this lock is released.

// When two threads are waiting for the other to release its locks we reach a deadlock.

// How to prevent deadlocks?
// (Don't do concurrency)
// Threre are probably several ways, but one way is to establish a total order when acquiring locks.
// This way threads will try to aquire the same locks first.
// Meaning that if a thread has to wait for a lock to be released, 
// it cannot have aquired a lock the blocking thread is waiting for.