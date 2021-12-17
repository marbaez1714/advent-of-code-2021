import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val middleIncompleteScore = getMiddleIncompleteScore(inputList)
    println("Middle score: $middleIncompleteScore")

}

fun getInputList(file:String): List<String>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<String>()
    // Create list of input and outputs 
    inputStream.bufferedReader().forEachLine { line -> 
        inputList.add( line )
    } 
    // Return list
    return inputList
}

class CodeLine(val line: String){
    val bracketPairList = listOf("()", "[]", "{}", "<>")
    val errorPointsMap = mapOf(')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L)
    val incompletePointMap = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
    val illegalScoreList = mutableListOf<Long>()
    var charactersToCompleteScore: Long = 0
    var reducedLine = ""
    var error = ""

    init {
        var filteredLine = line
        var lookForPair = true
        val leftBrackets = bracketPairList.map { it.first() }

        while(lookForPair){
            for(bracketPair in bracketPairList){
                if(filteredLine.contains(bracketPair)){
                    filteredLine = filteredLine.replace(bracketPair, "")
                }
            }
            for(bracketPair in bracketPairList){
                lookForPair = filteredLine.contains(bracketPair)
                if(lookForPair) break
            }
        }

        for(bracketPair in bracketPairList){
            val leftBracket = bracketPair.first()
            val rightBracket = bracketPair.last()
            val containsLeftBrackets = filteredLine.contains(leftBracket)
            val containsRightBrackets = filteredLine.contains(rightBracket)

            if(containsLeftBrackets && containsRightBrackets){
                error = "error"
                break   
            }else{
                error = "incomplete"
            }
        }

        if(error == "error"){
            filteredLine.toList().forEach { 
                if(it !in leftBrackets) illegalScoreList.add(errorPointsMap.getOrDefault(it, 0)) 
            }
        }
            
        if(error == "incomplete"){
            var newScore = 0L

            filteredLine.toList().reversed().forEach { 
                newScore = (5L * newScore) +  incompletePointMap.getOrDefault(it, 0) 
            }
            
            charactersToCompleteScore = newScore
        }

        reducedLine = filteredLine
    }
}

fun getMiddleIncompleteScore (inputList: List<String>): Long {
    val incompleteScoreList = mutableListOf<Long>()

    for(line in inputList){
        val codeLine = CodeLine(line)
        if(codeLine.error == "incomplete") incompleteScoreList.add(codeLine.charactersToCompleteScore) 
    }
    
    return incompleteScoreList.sorted()[incompleteScoreList.size / 2]
}