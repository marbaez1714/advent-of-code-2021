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

    var winningPossibilityList: MutableList<List<Int>> = mutableListOf()

    for(i in 0..4){
        val indexSkip = i * 5
        winningPossibilityList.add(listOf(indexSkip, indexSkip+1, indexSkip+2, indexSkip+3, indexSkip+4))
        winningPossibilityList.add(listOf(i, i+5, i+10, i+15, i+20))
    }

    var formattedBoardList = boardStringList.map { it -> it.replace("\n"," ").replace("  "," ").trim() }

    var boardList = formattedBoardList.map{ it -> it.split(" ").toMutableList() }
    var uncalledBoardList = formattedBoardList.toMutableList().map{ it -> it.split(" ").toMutableList() }

    var matchIndexList: MutableList<MutableList<Int>> = MutableList(boardStringList.size){ _ -> mutableListOf() }
    var drawnValueList = mutableListOf<String>()

    var lastDraw: Int = 0
    var winnerIndex: Int = 0
    var winnerUnmatchedSum: Int = 0

    draw@ for(draw in drawList){
        drawnValueList.add(draw)

        for((boardIndex,board) in boardList.withIndex()){
            
            if(board.contains(draw)){

                matchIndexList[boardIndex].add(board.indexOf(draw))
                uncalledBoardList[boardIndex].remove(draw)

                for(possibility in winningPossibilityList){
                    if(matchIndexList[boardIndex].containsAll(possibility)){

                        lastDraw = draw.toInt()
                        winnerIndex = boardIndex
                        break@draw
                    }
                }
            }
        }
    }

    winnerUnmatchedSum = uncalledBoardList[winnerIndex].map{ it.toInt() }.sum()

    return Pair(lastDraw, winnerUnmatchedSum)
}