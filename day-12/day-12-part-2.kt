import java.io.File
import java.io.InputStream
import java.util.Collections

fun main() {
    val cavePairList = getCavePairList("input.txt")
    val pathsFound = findNavPaths(cavePairList)
    println(pathsFound)
}

fun getCavePairList(file: String): List<List<String>> {
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<List<String>>()
    // Create list of input and outputs 
    inputStream.bufferedReader().forEachLine { inputList.add(it.split("-")) }
    // Return list
    return inputList
}

class Cave(val name: String) {
    var isStart = false
    var isEnd = false
    var isMinor = false

    init {
        isStart = name == "start"
        isEnd = name == "end"
        isMinor = name.lowercase() == name && !isStart && !isEnd
    }
}

class CavePair(pair: List<String>) {
    var start = Cave(" ")
    var end = Cave(" ")
    var connectionType = "middle"

    init {
        start = Cave(pair.first())
        end = Cave(pair.last())
        connectionType = if (start.isStart) "start" else if (start.isEnd) "end" else "middle"
    }

    fun getOther(cave: Cave): Cave {
        return if (start.name == cave.name) end else start
    }

    fun isConnected(cave: Cave): Boolean{
        return start.name == cave.name || end.name == cave.name
    }

}

fun findNavPaths(rawCaveList: List<List<String>>): Long {
    val formatttedRawCaveList =
        rawCaveList.map { pair -> if (pair[1] == "start" || pair[1] == "end") pair.reversed() else pair }
    val caveList = formatttedRawCaveList.map { pair -> CavePair(pair) }

    val (startPairList, navigatePairList) = caveList.partition { cave -> cave.connectionType == "start" }
    var totalPathsFound = 0L


    for (pair in startPairList) {
        val initialPath = listOf(pair.start, pair.end)
        totalPathsFound += navigatePath(initialPath, navigatePairList)
    }

    return totalPathsFound
}


fun navigatePath(currentPath: List<Cave>, possiblePaths: List<CavePair>, currentCount: Long = 0): Long {

    val currentCave = currentPath.last()
    val minorCavesVisited = currentPath.filter { cave -> cave.isMinor }.map { cave -> cave.name }
    val hasVisitedMinorCaveTwice = minorCavesVisited.size != minorCavesVisited.distinct().size
    val connectedPaths = possiblePaths.filter { path -> path.isConnected(currentCave) }
    var newCount = currentCount

    for(pair in connectedPaths){
        val nextCave = pair.getOther(currentCave)
        val newPath = currentPath + nextCave

        if(nextCave.isStart){
            continue
        }else if(hasVisitedMinorCaveTwice && minorCavesVisited.contains(nextCave.name)){
            continue
        }else if(nextCave.isEnd){
            newCount ++
        }else{
            newCount = navigatePath(newPath, possiblePaths, newCount)
        }
    }

    return newCount
}


