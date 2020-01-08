package day13

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
    val pathName = "input/day13.txt"
//    val pathName = "input/day09.txt"
    val f = File(pathName).readText()

    val mem = f.split(',').map { s -> s.trim().toLong() }

    val processor = IntegerComputer(mem, 4000)
//    processor.storeInMem(0, 2)
    val output = processor.processProgram()
    val tiles = output.chunked(3)
    println(tiles)
    val blocks = tiles.filter { it.get(2).toInt() == 2 }
    println(blocks.size)
}
