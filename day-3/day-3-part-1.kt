import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val (gama,epsilon) = getPowerConsumption(inputList)
    println("Gama = $gama")
    println("Epsilon = $epsilon")
    println("Product = ${gama * epsilon}")
}

fun getInputList(file:String): List<String> {
    // Gather data from input.txt
    val inputStream: InputStream = File(file).inputStream()
    val inputList = mutableListOf<String>()

    // Add values to list
    inputStream.bufferedReader().forEachLine { 
        inputList += it
    }
    return inputList
}

fun getPowerConsumption(inputList: List<String>): Pair<Int,Int>{

    var gammaList: List<Int> = mutableListOf<Int>()
    var epsilonList: List<Int> = mutableListOf<Int>()
    val majorityBoundry: Double = inputList.size / 2.0
    var sumMap: Map<Int,Int> = mapOf<Int,Int>()

    for(rowValue in inputList){
        for(colIndex in rowValue.indices){
            val currentColCount = sumMap.getOrDefault(colIndex,0)
            val colValue = Character.getNumericValue(rowValue[colIndex])
            val newColCount = currentColCount + colValue

            sumMap += Pair(colIndex, newColCount)
        }
    }

    for(value in sumMap.values){
        if(value > majorityBoundry){
            gammaList += 1
            epsilonList += 0
        }else{
            gammaList += 0
            epsilonList += 1        
        }
    }

    println(sumMap)

    val gamaBinary = gammaList.joinToString("").toInt(2)
    val epsilonBinary = epsilonList.joinToString("").toInt(2)

    return Pair(gamaBinary,epsilonBinary)
}