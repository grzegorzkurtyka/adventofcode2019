package day09

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
    val pathName = "input/day09.txt"
//    val pathName = "input/day09.txt"
    val f = File(pathName).readText()

//    val f = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"
//    val f = "1102,34915192,34915192,7,4,7,99,0"
//    val f = "104,1125899906842624,99"

    val mem = f.split(',').map { s -> s.trim().toLong() }

    val processor = IntegerComputer(mem, 4000)
    val output = processor.processProgram(listOf(2))
    println(processor.dumpMem())
    println(output)
}
