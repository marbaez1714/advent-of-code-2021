import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val (target, fuelCost) = alignCrabs(inputList)

    println("Target position: $target")
    println("Lowest fuel cost to target: $fuelCost")

}

fun getInputList(file:String): List<Int> {
    // Gather data from input.txt
    val inputString: String = File(file).readText()
    // Turn input into list of ints
    val inputList: List<Int> = inputString.split(",").map { it.toInt() }
    return inputList
}

fun alignCrabs(inputList: List<Int>): Pair<Int, Long> {
    val crabPositionsSet = inputList.toSortedSet()
    val firstPosition = crabPositionsSet.first()
    val lastPosition = crabPositionsSet.last()
    val crabPositionInstances: Map<Int, Int> = inputList.groupingBy { it }.eachCount().toSortedMap()
    var lowestFuelCost: Long? = null
    var lowestTargetPosition = 0

    target@ for(targetPosition in firstPosition .. lastPosition){
        var fuelCostByPosition = mutableMapOf<Int, Long>()
        var totalFuelCostByPosition = mutableMapOf<Int, Long>()
        var totalFuelCostToTarget: Long = 0

        for(position in crabPositionsSet){
            val fuelCost = Math.abs(position - targetPosition).toLong()
            val numberOfInstances = crabPositionInstances.getOrDefault(position, 1)
            val totalPositionFuelCost = fuelCost * numberOfInstances.toLong()
            val newFuelCost = totalFuelCostToTarget + totalPositionFuelCost

            if( lowestFuelCost != null && newFuelCost > lowestFuelCost){
                continue@target
            }

            totalFuelCostToTarget = newFuelCost
            fuelCostByPosition.put(position, fuelCost)
            totalFuelCostByPosition.put(position, totalPositionFuelCost)
        }

        if(lowestFuelCost == null || totalFuelCostToTarget < lowestFuelCost){
            lowestFuelCost = totalFuelCostToTarget
            lowestTargetPosition = targetPosition
        }
    }

    return Pair(lowestTargetPosition, lowestFuelCost ?: 0)
}