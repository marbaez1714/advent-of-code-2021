import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val windowList = getWindowList(inputList)
    val increaseCount = getIncreaseCount(windowList)
    println("Increase Count: $increaseCount")
}

fun getInputList(file:String): List<Int>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<Int>()

    // Add values to list
    inputStream.bufferedReader().forEachLine { inputList.add(it.toInt()) }

    // Return list
    return inputList
}

fun getWindowList(inputList:List<Int>): List<Int>{
    // Initialize a mutable list of the windows and index
    var windowList = mutableListOf<Int>()
    var windowIndex: Int = 0

    // Loop through each value in the inputList to create windows
    while( windowIndex < inputList.size - 2){
        var windowValue = inputList[windowIndex] + inputList[windowIndex + 1] + inputList[windowIndex + 2]
        windowList.add(windowValue)
        windowIndex += 1
    }

    // return the windowList
    return windowList
}

fun getIncreaseCount(windowList:List<Int>): Int{
    // Initalize previousDepth and increaseCount
    var previousDepth: Int = windowList[0]
    var increaseCount: Int = 0

    // Loop through each value in the list
    for( (index,value) in windowList.withIndex() ){
        // If it is the first index, set the value and skip
        if(index == 0){
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