import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val display = Display(inputList)

    println(display.signalSum)



}

fun getInputList(file:String): List<Pair<String, String>>{
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<Pair<String, String>>()
    // Create list of input and outputs 
    inputStream.bufferedReader().forEachLine { 
        inputList.addAll(it.split(" | ").zipWithNext()) 
    } 
    // Return list
    return inputList
}

class Display(val signalList: List<Pair<String, String>>){

    var signalSum = 0

    //  0000
    // 1    2
    // 1    2 
    //  3333 
    // 4    5
    // 4    5
    //  6666

    init {
        signalList.forEach { signalSum += decodePair(it) } 
    }

    fun decodePair(signalPair: Pair<String, String>): Int{

        val (input, output) = signalPair
        val inputList = input.split(" ").map { it.toList().sorted() }.toMutableList()
        val outputList = output.split(" ").map { it.toList().sorted() }
        var outputString = ""
        val signalValueList = MutableList(10) { listOf<Char>() }

        // Unique Values
        // find 1
        signalValueList[1] = findSignal(inputList, { it.size == 2 })
        
        inputList.removeIf { it == signalValueList[1] }
        // find 4
        signalValueList[4] = findSignal(inputList, { it.size == 4 })
        inputList.removeIf { it == signalValueList[4] }

        // find 7
        signalValueList[7] = findSignal(inputList, { it.size == 3 })
        inputList.removeIf { it == signalValueList[7] } 

        // find 8
        signalValueList[8] = findSignal(inputList, { it.size == 7 })
        inputList.removeIf { it == signalValueList[8] } 

        // Decoded Values
        // find 3 
        signalValueList[3] = findSignal(inputList, { it.size == 5 && signalValueList[1].intersect(it).size == 2 })
        inputList.removeIf { it == signalValueList[3] } 

        // find 2
        signalValueList[2] = findSignal(inputList, { it.size == 5 && signalValueList[4].intersect(it).size == 2 })
        inputList.removeIf { it == signalValueList[2] } 

        // find 5
        signalValueList[5] = findSignal(inputList, { it.size == 5 && signalValueList[4].intersect(it).size == 3 })
        inputList.removeIf { it == signalValueList[5] } 

        // find 9 
        signalValueList[9] = findSignal(inputList, { it.size == 6 && signalValueList[4].intersect(it).size == 4 })
        inputList.removeIf { it == signalValueList[9] } 

        // find 6
        signalValueList[6] = findSignal(inputList, { it.size == 6 && signalValueList[5].intersect(it).size == 5 })
        inputList.removeIf { it == signalValueList[6] } 

        // find 0
        signalValueList[0] = inputList.first()

        for(signal in outputList){
            val signalValue = signalValueList.indexOfFirst { it == signal }
            outputString += "$signalValue"
        }
        
        return outputString.toInt()
    }

    private fun findSignal(inputList: List<List<Char>>, conditions: (input: List<Char>) -> Boolean): List<Char> {
        val signal = inputList.find(conditions)  
        return if(signal == null) listOf<Char>() else signal
    }





}





