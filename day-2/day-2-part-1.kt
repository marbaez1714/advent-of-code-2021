import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val (horizontal, depth) = getPosition(inputList)
    println("Horizontal Distance = $horizontal")
    println("Depth = $depth")
    println("Product = ${horizontal * depth}")
}

fun getInputList(file:String): List<String> {
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<String>()

    // Add values to list
    inputStream.bufferedReader().forEachLine { 
        inputList += it
    }
    return inputList
}

fun getPosition(inputList: List<String>): Pair<Int,Int> {
    // Initalize position values
    var horizontal: Int = 0
    var depth: Int = 0

    // Loop through list and increment values based on direction
    for(input in inputList){
        val (direction: String, value: String) = input.split(" ")
        val valueInt: Int = value.toInt()
        when(direction){
            "forward" -> horizontal += valueInt
            "down" -> depth += valueInt
            "up" -> depth -= valueInt
        }
    }
    
    return Pair(horizontal,depth)
}