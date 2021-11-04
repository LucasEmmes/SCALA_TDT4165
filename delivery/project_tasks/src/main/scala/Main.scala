import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent._


object Main extends App {


        def generate50():Array[Int] = {
        
        var numbers:Array[Int] = new Array[Int](50)
        
        for (i <- 0 to (numbers.length - 1)) {
            numbers(i) = i+1
            // println(numbers(i))
        }
        return numbers
    }

    def sum_array(a:Array[Int]): Int = {
        var total = 0;
        for (i <- 0 to (a.length-1)) {
            total += a(i)
        }
        return total
    }

    def sum_recursive(a:Array[Int], index:Int = 0): Int = {
        if (index < a.length) {
            return a(index) + sum_recursive(a, index+1)
        } else {
            return 0
        }
    }

    def fib(n:Int): BigInt = {

        if (n <= 0) {
            return 0
        }
        if (n == 1) {
            return 1
        }
        

        var a:BigInt = fib(n-1)
        var b:BigInt = fib(n-2)

        return a + b
    }

    def create_thread(function: () => Unit): Thread = {

        val t = new Thread {
            override def run {
                return function()
            }
        }
        return t
    }

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


    def thread(body: => Unit): Thread = {
        val t = new Thread {
            override def run() = body
        }
        t.start
        t
    }



    var t1 = create_thread(increaseCounter)
    var t2 = create_thread(increaseCounter)
    var t3 = create_thread(printCounter)
    
    t1.start()
    t2.start()
    Thread.sleep(1000)
    t3.start()


}