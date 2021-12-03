import java.io.File
import java.io.InputStream

fun main(){
    val inputList = getInputList("input.txt")
    val (oxygen, co2) = getPowerConsumption(inputList)
    println("Oxygen = $oxygen")
    println("co2 = $co2")
    println("Product = ${oxygen * co2}")
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

    var oxygenList: List<String> = inputList.toMutableList()
    var co2List: List<String> = inputList.toMutableList()

    var oxyColIndex = 0
    var co2ColIndex = 0

    while(oxygenList.size > 1){
        var oneCount = 0
        var zeroCount = 0

        for(rowValue in oxygenList){
            when(rowValue[oxyColIndex]){
                '1' -> oneCount ++
                '0' -> zeroCount ++
            }
        }

        if(oneCount >= zeroCount){
            oxygenList = oxygenList.filter{ it[oxyColIndex] == '1' } 
        }else{
            oxygenList = oxygenList.filter{ it[oxyColIndex] == '0' }
        }

        oxyColIndex ++
    }

    while(co2List.size > 1){
        var oneCount = 0
        var zeroCount = 0

        for(rowValue in co2List){
            when(rowValue[co2ColIndex]){
                '1' -> oneCount ++
                '0' -> zeroCount ++
            }
        }

        if(oneCount < zeroCount){
            co2List = co2List.filter{ it[co2ColIndex] == '1' } 
        }else{
            co2List = co2List.filter{ it[co2ColIndex] == '0' }
        }

        co2ColIndex ++
    }

    val oxygenValue = oxygenList.joinToString().toInt(2)
    val co2Value = co2List.joinToString().toInt(2)

    return Pair(oxygenValue, co2Value)
}