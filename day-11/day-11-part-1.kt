import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val numberOfSteps = 100
    val totalFlashes = countFlashes(inputList, numberOfSteps)
    println("Total number of flashes after $numberOfSteps steps: $totalFlashes")
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
    var canIncrement = true
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
        if(canIncrement){
            when(currentEnergy){
                9 -> {
                    currentEnergy = 0
                    totalFlashes ++
                    isFlashing = true
                    canIncrement = false
                }
                else -> currentEnergy ++
            }
        }
    }

    fun triggerFlash() {
        isFlashing = false
    }

    fun resetCanIncrement() {
        canIncrement = true
    }
}

fun countFlashes(inputList: List<List<Int>>, totalSteps: Int): Int{
    val bottomRow = inputList.size - 1
    val rightCol = inputList.first().size - 1
    val octopusMap = mutableListOf<MutableList<DumboOct>>()
    var sumOfFlashes = 0


    // create oct list and increment list
    for(row in 0..bottomRow){
        octopusMap += mutableListOf<DumboOct>()

        for(col in 0..rightCol){
            val octInitalEnergy = inputList[row][col]
            octopusMap[row] += DumboOct(octInitalEnergy, Pair(row, col))
        }
    }

    repeat(totalSteps){
        val incrementList = mutableListOf<Pair<Int, Int>>()
        var stepInProgress = true

        for(row in 0..bottomRow){
            for(col in 0..rightCol){
                incrementList += Pair(row, col)
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

        for(row in 0..bottomRow){
            for(col in 0..rightCol){
                octopusMap[row][col].resetCanIncrement()
            }
        }
    }

    // Gather sums
    octopusMap.forEach{ row ->  
        sumOfFlashes += row.map { it.totalFlashes }.sum()
    }

    return sumOfFlashes
}