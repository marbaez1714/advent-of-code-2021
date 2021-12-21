import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val paths = findPaths(inputList)

    println("Number of paths found: ${paths.size}")

}

fun getInputList(file:String): List<List<String>>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<List<String>>()
    // Create list of input and outputs 
    inputStream.bufferedReader().forEachLine { inputList.add(it.split("-"))  } 
    // Return list
    return inputList
}

fun findPaths(caveList: List<List<String>>): Set<String> {

    val formattedCaveList = caveList.map{ if(it[1] == "start" || it[1] == "end") it.reversed() else it }
    val startPairList = formattedCaveList.filter { it[0] == "start" }
    val navigateList = getNavigationPaths(formattedCaveList)

    val allPathsFound = mutableSetOf<String>()

    for(startPair in startPairList){
        val start = "${startPair[0]} ${startPair[1]}"
        val basePathsFound = mutableSetOf<String>()
        val pathsFound = navigate(start, navigateList, basePathsFound)
        allPathsFound.addAll(pathsFound)
    }

    return allPathsFound.toSet()
}

fun isMinor(cave: String): Boolean{
    return cave.lowercase() == cave
}

fun getNavigationPaths(caveList: List<List<String>>): List<List<String>>{
    val (caveMajorPairs, caveMiddlePairs)  = caveList.partition{ it[0] == "start" || it[0] == "end" || !isMinor(it[0]) || !isMinor(it[1]) }
    val allowedCaves = caveMajorPairs.flatten()
    val skipCaves = caveMiddlePairs.flatten().filter{ it !in allowedCaves }

    val navigatePairs = caveList.filter{ it[0] != "start" && it[0] !in skipCaves && it[1] !in skipCaves }

    return navigatePairs
}



fun navigate(
    currentPath: String, 
    possiblePaths: List<List<String>>, 
    pathsFound: MutableSet<String>
    ): MutableSet<String>
    {
        val currentPathList = currentPath.split(" ")
        val minorCavesEntered = currentPathList.filter{ isMinor(it) }
        val newPathsFound = pathsFound.toMutableSet()

        val currentCave = currentPathList.last()

        val connectedPathList = possiblePaths.filter{ it[0] == currentCave || it[1] == currentCave }

        for(path in connectedPathList){
            val nextCave = if(path[0] == currentCave) path[1] else path[0]
            val newPath = currentPath + " " + nextCave

            if(minorCavesEntered.contains(nextCave) || nextCave == "start"){
                continue
            }else if(nextCave != "end"){
                newPathsFound += navigate(newPath, possiblePaths, newPathsFound)
            }else{
                newPathsFound += newPath
            }
        }

        return newPathsFound
}

