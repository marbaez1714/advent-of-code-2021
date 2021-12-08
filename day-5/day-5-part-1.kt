import java.io.File
import java.io.InputStream
import java.awt.geom.Line2D

fun main(){
    val inputList = getInputList("input.txt")
    val intersections = mapOverlaps(inputList)

    println("Horizonal and Vertical Intersections: $intersections")
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
    // Grab only horizontal and vertical lines
    val filteredVectorList = vectorList.filter { it[0] == it[2] || it[1] == it[3] }
    // Flatten list
    val flatFilteredVectorList: List<Int> = filteredVectorList.flatten()
    // Get max value 
    val maxValue = flatFilteredVectorList.maxOrNull() ?: 0
    // Create Grid
    var grid = MutableList<MutableList<Int>>(maxValue + 1) { MutableList(maxValue + 1) { 0 } }
    // Intersection count
    var intersections = 0

    for( coor in filteredVectorList ){
        var (x1, y1, x2, y2) = coor
        var xRangeList = if(x1 < x2) (x1..x2).toList() else (x1 downTo x2).toList()
        var yRangeList = if(y1 < y2) (y1..y2).toList() else (y1 downTo y2).toList()

        if(x1 == x2){
            for(y in yRangeList){
                grid[y][x1] ++
            }
        }

        if(y1 == y2){
            for(x in xRangeList){
                grid[y1][x] ++
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