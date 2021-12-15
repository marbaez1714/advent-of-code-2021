import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val lowestPointsList = findLowestPoints(inputList)
    val lowestPointsRiskList = lowestPointsList.map { it + 1 }

    println("Sum of Risk: ${lowestPointsRiskList.sum()}")
}

fun getInputList(file:String): List<List<Int>>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<List<Int>>()
    // Create list of input and outputs 
    inputStream.bufferedReader().forEachLine { line -> 
        inputList.add(line.toList().map { Character.getNumericValue(it) } )
    } 
    // Return list
    return inputList
}

fun findLowestPoints(mapList: List<List<Int>>): List<Int> {

    val lowestPointsList = mutableListOf<Int>()

    for((rowIndex, row) in mapList.withIndex()){

        val aboveIndex = rowIndex - 1
        val belowIndex = rowIndex + 1

        for((colIndex, col) in row.withIndex()){
            val leftIndex = colIndex - 1
            val rightIndex = colIndex + 1

            val topCompare = mapList.getOrElse(aboveIndex, {listOf()}).getOrElse(colIndex, { 10 })
            val bottomCompare = mapList.getOrElse(belowIndex, {listOf()}).getOrElse(colIndex, { 10 })
            val leftCompare = row.getOrElse(leftIndex, { 10 })
            val rightCompare = row.getOrElse(rightIndex, { 10 })

            val compareList = listOf(topCompare, bottomCompare, leftCompare, rightCompare).sorted()
            
            if(compareList.first() > col) lowestPointsList.add(col)
        }
    }

    return lowestPointsList
}