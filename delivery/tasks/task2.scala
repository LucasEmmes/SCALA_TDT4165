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

    var a:Array[Int] = generate50()
    println(sum_array(a))
    println(sum_recursive(a))
  
    println(fib(15))

    var t1 = create_thread(increaseCounter)
    var t2 = create_thread(increaseCounter)
    var t3 = create_thread(printCounter)
    
    t1.start()
    t2.start()
    Thread.sleep(1000)
    t3.start()