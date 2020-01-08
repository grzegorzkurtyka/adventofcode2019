package day25

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
    val pathName = "input/day25.txt"
    val f = File(pathName).readText()

    val mem = f.split(',').map { s -> s.trim().toLong() }
    val processor = IntegerComputer(mem, 8000)
//    processor.enableDebug()
    var tmpInput:List<Long>

    while(!processor.isHalted()) {
        processor.processProgram()
        println(processor.getOutput().map { it.toChar() }.joinToString(""))
        if(processor.awaitsInput()) {
            tmpInput = readLine()!!.map { it.toLong() }
            processor.addInput(tmpInput.plus(10))
        }
    }
}
