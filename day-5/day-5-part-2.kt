import java.io.File
import java.io.InputStream
import java.awt.geom.Line2D

fun main(){
    val inputList = getInputList("input.txt")
    val intersections = mapOverlaps(inputList)

    println("Total Intersections: $intersections")
}

fun getInputList(file:String): List<String> {
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<String>()

    // Add values to list
    inputStream.bufferedReader().forEachLine { 
        inputList += it.replace(" -> ",",")
    }

    return inputList
}

fun mapOverlaps(inputList: List<String>): Int {
    // Create a direction list
    val vectorList = inputList.map { it.split(",").map { it.toInt() } }
    // Flatten list
    val flatVectorList: List<Int> = vectorList.flatten()
    // Get max value 
    val maxValue = flatVectorList.maxOrNull() ?: 0
    // Create Grid
    var grid = MutableList<MutableList<Int>>(maxValue + 1) { MutableList(maxValue + 1) { 0 } }
    // Intersection count
    var intersections = 0

    // Loop through each coordinate list
    for( coor in vectorList ){
        var (x1, y1, x2, y2) = coor
        var xRangeList = if(x1 < x2) (x1..x2).toList() else (x1 downTo x2).toList()
        var yRangeList = if(y1 < y2) (y1..y2).toList() else (y1 downTo y2).toList()

        if(x1 == x2){
            for(y in yRangeList){
                grid[y][x1] ++
            }
        }else if(y1 == y2){
            for(x in xRangeList){
                grid[y1][x] ++
            }
        }else{
            for((index, y) in yRangeList.withIndex()){
                grid[y][xRangeList[index]] ++
            }
        }
    }
    
    for(line in grid){
        for(value in line){
            if(value > 1){
                intersections ++
            }
        }
    }

    return intersections
}