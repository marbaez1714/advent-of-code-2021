import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val threeLargestBasins = findBasinSizes(inputList).subList(0, 3)
    val threeLargestBasinsProduce = threeLargestBasins.reduce { acc, el -> acc * el }


    println("Three largest basins: $threeLargestBasins")
    println("Three largest basins product: $threeLargestBasinsProduce")
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

fun findBasinSizes(mapList: List<List<Int>>): List<Int> {

    // Pair(row, col)
    val lowestCoordinateList = mutableListOf<Pair<Int,Int>>()
    val basinSizeList = mutableListOf<Int>()

    for((rowIndex, row) in mapList.withIndex()){
        val aboveIndex = rowIndex - 1
        val belowIndex = rowIndex + 1

        for((colIndex, col) in row.withIndex()){
            val leftIndex = colIndex - 1
            val rightIndex = colIndex + 1

            val topCompare = mapList.getOrElse(aboveIndex, {listOf()}).getOrElse(colIndex, { 9 })
            val bottomCompare = mapList.getOrElse(belowIndex, {listOf()}).getOrElse(colIndex, { 9 })
            val leftCompare = row.getOrElse(leftIndex, { 9 })
            val rightCompare = row.getOrElse(rightIndex, { 9 })

            val compareList = listOf(topCompare, bottomCompare, leftCompare, rightCompare).sorted()
            
            if(compareList.first() > col) lowestCoordinateList.add(Pair(rowIndex, colIndex))
        }
    }

    for(coordinate in lowestCoordinateList){
        val riskMapList = mapList.map { row -> row.map { it + 1 }.toMutableList() }.toMutableList()
        val basinMapList = markBasins(riskMapList, coordinate)
        basinSizeList += basinMapList.flatten().count {it == 0}
    }

    return basinSizeList.sortedDescending()
}

fun markBasins(mapList: MutableList<MutableList<Int>>, coordinate: Pair<Int, Int>): MutableList<MutableList<Int>>{
    val (rowIndex, colIndex) = coordinate
    val skipValues = listOf(0, 10)
    var newRiskMapList = mapList.toMutableList()
    
    newRiskMapList[rowIndex][colIndex] = 0 

    val aboveCoor = Pair(rowIndex - 1, colIndex)
    val belowCoor = Pair(rowIndex + 1, colIndex)
    val leftCoor = Pair(rowIndex, colIndex - 1)
    val rightCoor = Pair(rowIndex, colIndex + 1)

    val aboveValue = newRiskMapList.getOrElse(aboveCoor.first, { listOf() }).getOrElse(aboveCoor.second, { 10 })
    val belowValue = newRiskMapList.getOrElse(belowCoor.first, { listOf() }).getOrElse(belowCoor.second, { 10 })
    val leftValue = newRiskMapList.getOrElse(leftCoor.first, { listOf() }).getOrElse(leftCoor.second, { 10 })
    val rightValue = newRiskMapList.getOrElse(rightCoor.first, { listOf() }).getOrElse(rightCoor.second, { 10 })

    if(aboveValue !in skipValues){
        newRiskMapList = markBasins(newRiskMapList, aboveCoor)
    }
    if(belowValue !in skipValues){
        newRiskMapList = markBasins(newRiskMapList, belowCoor)
    }
    if(leftValue !in skipValues){
        newRiskMapList = markBasins(newRiskMapList, leftCoor)
    }
    if(rightValue !in skipValues){
        newRiskMapList = markBasins(newRiskMapList, rightCoor)
    }

    return newRiskMapList
}