import java.io.File
import java.io.InputStream
import java.util.Collections

fun main(){
    val inputList = getInputList("input.txt")
    val numberOfDays = 80
    val totalLanterns = lanternBirthChart(inputList, numberOfDays)
    println("Inital number of fish: ${inputList.size}")
    println("Number of days: $numberOfDays")
    println("After total number of birthed: $totalLanterns ")
}

fun getInputList(file:String): List<Int> {
    // Gather data from input.txt
    val inputString: String = File(file).readText()
    // Turn input into list of ints
    val inputList: List<Int> = inputString.split(",").map { it.toInt() }
    return inputList
}

fun lanternBirthChart(inputList: List<Int>, totalDays: Int): Long {
    var totalLanterns: Long = 0 
    val setOfLanternAges: Set<Int> = inputList.toSortedSet()
    val ageCountMap: Map<Int, Int> = inputList.groupingBy { it }.eachCount().toSortedMap()
    
    val createdByAge: MutableMap<Int, Long> = mutableMapOf()
    val totalCreatedByAge: MutableMap<Int, Long> = mutableMapOf()

    for(lanternAge in setOfLanternAges){
        val daysLeft = totalDays - lanternAge
        val lanternsCreatedByAge = findNumberCreated(1, daysLeft)
        val totalLanternsCreated = ageCountMap.getOrDefault(lanternAge, 0) * lanternsCreatedByAge
        totalLanterns += totalLanternsCreated
        createdByAge.put(lanternAge, lanternsCreatedByAge)
        totalCreatedByAge.put(lanternAge, totalLanternsCreated)
    }

    return totalLanterns
}

fun findNumberCreated(currentCount: Int, daysLeft: Int): Long{
    if(daysLeft <= 0){
        return currentCount.toLong()
    }
    val currentTimeToBirth = daysLeft - 7
    val newTimeToBirth = daysLeft - 9
    val lanternsCreated = findNumberCreated(currentCount, currentTimeToBirth)
    val newLanternsCreated = findNumberCreated(1, newTimeToBirth)

    return lanternsCreated + newLanternsCreated
}
