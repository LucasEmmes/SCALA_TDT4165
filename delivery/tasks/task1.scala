object Task1 {
    def main(args: Array[String]) {

        // A
        var numbers:Array[Int] = new Array[Int](50);
        for ( i <- 0 to numbers.length - 1) {
            numbers(i) = i+1;
        }
        println(numbers.mkString(" "));
    
        // B
        def sum(list:Array[Int]): Int = {
            var sum = 0;
            for (i <- 0 to list.length-1) {
                sum += list(i);
            }
            return sum;
        }
        println(sum(numbers));
    
        // C
        def sum_recursive(list:Array[Int], index:Int):Int = {
            if (index < list.length) {
                return list(index) + sum_recursive(list, index+1)
            } else {
                return 0
            }
        }
        println(sum_recursive(numbers,0));
    
        // D
        def fibonacci(n:Int):BigInt = {
            // Shorter version but we're afraid of getting deduction for """"unreasonable indentation""""
            // return if (n<2) n else fib(n-1) + fib(n-2);
            
            if (n < 2) {
                return n;
            } else {
                return fib(n-1) + fib(n-2);
            }
        }
        println(fibonacci(15));

    }

}