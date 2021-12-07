import java.io.File
import java.io.InputStream

fun main(){
    val (drawList, boardList) = getInputList("input.txt")
    val (lastDraw, unmatchedSum) = bingo(drawList,boardList)
    println("Last Draw = $lastDraw")
    println("Unmatched Sum = $unmatchedSum")
    println("Product = ${lastDraw * unmatchedSum}")
}

fun getInputList(file:String): Pair< List<String>, List<String> > {
    // Gather data from input.txt
    val inputText: String = File(file).readText()
    // Split input into list
    val splitInput: List<String> = inputText.split("\n\n")
    // Grab draw list from first row of inputs
    val drawList: List<String> = splitInput[0].split(",")
    // Grab sublist of ever element except the first
    val boardList = splitInput.subList(1, splitInput.size)

    // Return Pair of lists
    return Pair(drawList, boardList)
}

fun bingo(drawList: List<String>, boardStringList: List<String>): Pair<Int,Int>{

    //  0  1  2  3  4
    //  5  6  7  8  9
    // 10 11 12 13 14
    // 15 16 17 18 19
    // 20 21 22 23 24

    var winningPatterList: MutableList<List<Int>> = mutableListOf()

    for(i in 0..4){
        val indexSkip = i * 5
        winningPatterList.add(listOf(indexSkip, indexSkip+1, indexSkip+2, indexSkip+3, indexSkip+4))
        winningPatterList.add(listOf(i, i+5, i+10, i+15, i+20))
    }

    var formattedBoardList = boardStringList.map { it -> it.replace("\n"," ").replace("  "," ").trim() }
    
    val boardIntList = formattedBoardList.map { list -> list.split(" ").map { it.toInt() } }
    var matchIndexList = MutableList<MutableList<Int>>(boardIntList.size){ _ -> mutableListOf() }
    val drawIntList = drawList.map{ it.toInt() }

    var lastWinningDraw = 0
    var lastWinningDrawIndex = 0
    var winnerIndexList = mutableListOf<Int>()

    for((drawIndex, draw) in drawIntList.withIndex()){

        for((boardIndex, board) in boardIntList.withIndex()){

            if(board.contains(draw)){

                matchIndexList[boardIndex].add(board.indexOf(draw))

                for(pattern in winningPatterList){

                    if(matchIndexList[boardIndex].containsAll(pattern)){
                        
                        if(boardIndex !in winnerIndexList){
                            lastWinningDraw = draw
                            lastWinningDrawIndex = drawIndex
                            winnerIndexList.add(boardIndex)
                        }
                    }
                }                
            }
        }
    }
    
    val drawSubList: List<Int> = drawIntList.subList(0, lastWinningDrawIndex + 1)
    var winnerList: MutableList<Int> = boardIntList[winnerIndexList.last()].toMutableList<Int>()
    winnerList.removeAll(drawSubList)

    var unmatchedSum = winnerList.sum()

    return Pair(lastWinningDraw, unmatchedSum)
}