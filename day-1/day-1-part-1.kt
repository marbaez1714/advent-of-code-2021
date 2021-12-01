import java.io.File
import java.io.InputStream

fun getInputList(file:String): List<Int>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<Int>()

    // Add values to list
    inputStream.bufferedReader().forEachLine { inputList.add(it.toInt()) }

    // Return list
    return inputList
}

fun getIncreaseCount(inputList:List<Int>): Int{
    // Initalize previousDepth and increaseCount
    var previousDepth: Int = 0
    var increaseCount: Int = 0

    // Loop through each value in the list
    for( (index,value) in inputList.withIndex() ){
        // If it is the first index, set the value and skip
        if(index == 0){
            previousDepth = value
            continue
        }
        // if the value is increased, increment increaseCount
        if(value > previousDepth){
            increaseCount += 1
        }
        // Set the previousDepth 
        previousDepth = value
    }

    // Return increaseCount
    return increaseCount
}

fun main(){
    val inputList = getInputList("input.txt")
    val increaseCount = getIncreaseCount(inputList)
    println("Increase Count: $increaseCount")
}