import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val stepsToSync = countFlashes(inputList)
    println("Steps until all are flashing: $stepsToSync")
}

fun getInputList(file:String): List<List<Int>>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<List<Int>>()
    // Create list of input and outputs 
    inputStream.bufferedReader().forEachLine { line -> 
        inputList.add( line.toList().map { Character.getNumericValue(it) } )
    } 
    // Return list
    return inputList
}

class DumboOct(initialEnergy: Int, coordinates: Pair<Int, Int>){
    var totalFlashes = 0
    var shouldIncrement = false
    var isFlashing = false
    var currentEnergy = initialEnergy
    var adjacentOctList = mutableListOf<Pair<Int, Int>>()

    init { 
        val (row, col) = coordinates

        val above = row - 1
        val below = row + 1
        val left = col - 1
        val right = col + 1

        if(above >= 0) adjacentOctList += Pair(above, col)
        if(below <= 9) adjacentOctList += Pair(below, col)

        if(left >= 0) adjacentOctList += Pair(row, left)
        if(right <= 9) adjacentOctList += Pair(row, right)

        if(above >= 0 && left >= 0) adjacentOctList += Pair(above, left)
        if(above >= 0 && right <= 9) adjacentOctList += Pair(above, right)
        if(below <= 9 && left >= 0) adjacentOctList += Pair(below, left)
        if(below <= 9 && right <= 9) adjacentOctList += Pair(below, right)
    }

    fun increment() {
        if(shouldIncrement){
            when(currentEnergy){
                9 -> {
                    currentEnergy = 0
                    totalFlashes ++
                    isFlashing = true
                    shouldIncrement = false
                }
                else -> currentEnergy ++
            }
        }
    }

    fun triggerFlash() {
        isFlashing = false
    }

    fun resetShouldIncrement() {
        shouldIncrement = true
    }
}

fun countFlashes(inputList: List<List<Int>>): Int{
    val bottomRow = inputList.size - 1
    val rightCol = inputList.first().size - 1
    val octopusMap = mutableListOf<MutableList<DumboOct>>()
    var numberOfSteps = 0
    var notSynchronized = true


    // create oct list and increment list
    for(row in 0..bottomRow){
        octopusMap += mutableListOf<DumboOct>()

        for(col in 0..rightCol){
            val octInitalEnergy = inputList[row][col]
            octopusMap[row] += DumboOct(octInitalEnergy, Pair(row, col))
        }
    }

    while(notSynchronized){
        val incrementList = mutableListOf<Pair<Int, Int>>()
        var stepInProgress = true
        var totalEnergy = 0
        numberOfSteps++

        for(row in 0..bottomRow){
            for(col in 0..rightCol){
                incrementList += Pair(row, col)
                octopusMap[row][col].resetShouldIncrement()
            }
        }

        while(stepInProgress){
            val flashCheckList = mutableListOf<Pair<Int, Int>>()

            for((row, col) in incrementList){
                octopusMap[row][col].increment()

                if(octopusMap[row][col].isFlashing){
                    octopusMap[row][col].triggerFlash()
                    flashCheckList.addAll(octopusMap[row][col].adjacentOctList)
                }
            }

            if(flashCheckList.size > 0){
                stepInProgress = true
                incrementList.clear()
                incrementList.addAll(flashCheckList)
            }else{
                stepInProgress = false
            }
        }

        for(row in octopusMap){
            totalEnergy += row.map { it.currentEnergy }.sum()
        }        

        notSynchronized = totalEnergy > 0
    }

    return numberOfSteps
}