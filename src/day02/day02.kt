package day02

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
    part1("input/day02A.txt")
    part2("input/day02A.txt")
}

fun part1(pathName: String) {
    val f = File(pathName).readText()
    val mem = f.split(',').map { s -> s.trim().toLong() }
    val processor = IntegerComputer(mem, null)
    processor.storeInMem(1, 12).storeInMem(2, 2)
    processor.processProgram()
    println("OUTPUT=${processor.readImediateMode(0)}")
}

fun part2(pathName: String) {
    val f = File(pathName).readText()
    val mem = f.split(',').map { s -> s.trim().toLong() }

    var output = emptyList<Long>()

    for (noun in 0..100) {
        for (verb in 0..100) {
            val processor = IntegerComputer(mem, null)
            processor.storeInMem(1, noun.toLong())
                .storeInMem(2, verb.toLong())
            processor.processProgram()
            val output: Long = processor.readImediateMode(0)
            if (output.equals(19690720))
                println("OUTPUT=$output noun=$noun, verb=$verb")
        }
    }
//    println("OUTPUT=$output")
}






