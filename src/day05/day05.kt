package day05

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
//    val pathName = "input/day05_test.txt"
    val pathName = "input/day05.txt"
    val f = File(pathName).readText()
    val mem = f.split(',').map { s -> s.trim().toLong() }
    val processor = IntegerComputer(mem, null)
//    processor.storeInMem(1, 12).storeInMem(2, 2)
    var output: List<Long> = mutableListOf()


    output = processor.processProgram(listOf<Long>(5))
    println("OUTPUT=${output}")
    println("MEM=${processor.dumpMem()}")

//    output = processor.processProgram(listOf(8))
//    println("OUTPUT=${output}")
//    println("MEM=${processor.dumpMem()}")
//
//    output = processor.processProgram(listOf(9))
//    println("OUTPUT=${output}")
//    println("MEM=${processor.dumpMem()}")
}
