import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val display = processSignal(inputList)
    var numberOfUniqueNumbers = 0

    println("Count Map: \n")

    for((key, value) in display.countMap){
        when(key){
            1,4,7,8 -> numberOfUniqueNumbers += value
        }
        println("$key: $value")
    }

    println("\nNumber of unique instances: $numberOfUniqueNumbers")

}

fun getInputList(file:String): List<Pair<String, String>>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<Pair<String, String>>()
    // Create list of input and outputs 
    inputStream.bufferedReader().forEachLine { 
        inputList.addAll(it.split(" | ").zipWithNext()) 
    } 
    // Return list
    return inputList
}

class Display(){
    val numberList = mutableListOf<Int>()
    val numberSet = mutableSetOf<Int>().toSortedSet()
    val countMap = numberList.associate { it to 0 }.toSortedMap()

    fun processSignal(signal: String){
        when(signal.length){
            2 -> increment(1)
            3 -> increment(7)
            4 -> increment(4)
            7 -> increment(8)
        }
    }

    fun increment(number: Int){
        val currentCount = countMap.getOrDefault(number, 0)
        countMap += Pair(number, currentCount + 1)
        numberList.add(number)
        numberSet.add(number)
    }

    fun printDisplay(){

        val displayList = numberList.map { getNumberDisplay(it) } 
    
        for(i in 0..6){
            for(number in displayList){
                print("  ${number[i]}")
            }
            print("\n")
        }
    }

    fun getNumberDisplay(number: Int): List<String>{

        val segmentList = mutableListOf<String>(
            " aaaa ", 
            "b    c", 
            "b    c", 
            " dddd ", 
            "e    f", 
            "e    f", 
            " gggg "
        )
    
        val filterMap = mapOf(
            0 to "d",
            1 to "a|b|d|e|g",
            2 to "b|f",
            3 to "b|e",
            4 to "a|e|g",
            5 to "c|e",
            6 to "a|c",
            7 to "b|d|e|g",
            9 to "e"
        )
    
        val filterRegex = filterMap.getOrDefault(number, "a^").toRegex()
        val numberList = segmentList.map { it.replace(filterRegex, " ") } 
    
        return numberList
    }
}



fun processSignal(inputList: List<Pair<String,String>>): Display{

    //  aaaa   top             |     | 1,0 | 2,0 | 3,0 | 4,0 |     |
    // b    c  verticalOne     | 0,1 |     |     |     |     | 5,1 |
    // b    c  verticalTwo     | 0,2 |     |     |     |     | 5,2 |
    //  dddd   middle          |     | 1,3 | 2,3 | 3,3 | 4,3 | 5,3 |
    // e    f  verticalThree   | 0,4 |     |     |     |     | 5,4 |
    // e    f  verticalFour    | 0,5 |     |     |     |     | 5,5 |
    //  gggg   bottom          |     | 1,6 | 2,6 | 3,6 | 4,6 |     |

    val display = Display()

    for((_, output) in inputList){

        val outputList = output.split(" ")

        for(signal in outputList){
            display.processSignal(signal)
        }
    }

    return display
}



