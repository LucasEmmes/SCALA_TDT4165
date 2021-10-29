object Task1 {
    def main(args: Array[String]) {
        
        var numbers:Array[String] = new Array[String](50)
        
        for ( i <- 0 to (numbers.length - 1)) {
            numbers[i] = i+1
        }
        
        println(numbers)
    }


})