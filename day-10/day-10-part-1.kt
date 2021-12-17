import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")

    val firstErrorCharacterPoints = getFirstCharacterScore(inputList)

    println("Score for error lines:")
    println("Points for first characters: $firstErrorCharacterPoints")
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
    val pointsMap = mapOf(')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L)
    var firstIllegalCharacterScore: Long = 0
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

        val illegalCharacterList = filteredLine.toList().filter { it !in leftBrackets }

        if(illegalCharacterList.size > 0){
            firstIllegalCharacterScore = pointsMap.getOrDefault(illegalCharacterList.first(), 0)
        }
    }
}

fun getFirstCharacterScore (inputList: List<String>): Long {
    val errorCodeLineList = mutableListOf<CodeLine>()
    val incompleteCodeLineList = mutableListOf<CodeLine>()
    var errorFirstCharacterScore = 0L

    for(line in inputList){
        val codeLine = CodeLine(line)
        when(codeLine.error){
            "error" -> errorCodeLineList.add(codeLine)
            "incomplete" -> incompleteCodeLineList.add(codeLine)
        }
    }

    for(codeLine in errorCodeLineList){
        errorFirstCharacterScore += codeLine.firstIllegalCharacterScore
    }
    
    return errorFirstCharacterScore
}