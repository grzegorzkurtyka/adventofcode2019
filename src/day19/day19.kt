package day19

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
    val pathName = "input/day19.txt"
    val f = File(pathName).readText()

    val mem = f.split(',').map { s -> s.trim().toLong() }

    val grid = Grid(50, 50)
    var out: List<Long>
    val out1 = mutableListOf<Int>()

    for(y in 0 until 100)
        for(x in 0 until 100) {
            val processor = IntegerComputer(mem, 4000)
            processor.addInput(listOf<Long>(x.toLong(), y.toLong()))
            out = processor.processProgram()
            out1.add(out[0].toInt())
        }


    println(out1.chunked(100))
    out1.chunked(100).map { it -> it.joinToString("") }.forEach { println(it) }

    println(out1.filter { it == 1 }.size)
//    val lines = output.chunked(w + 1).map { it.filter { c -> c != 10 }.toIntArray() }

}

class Grid(private val sizeX: Int, private val sizeY: Int) {

    private val mem = arrayOfNulls<Int>(sizeX * sizeY)
//        .fill(0)
        .map { 0 }.toMutableList()

    fun setAt(x: Int, y: Int, v: Int) =
        mem.set(y * this.sizeY + x, v)

    fun getAt(x: Int, y: Int) = mem.get(y * this.sizeY + x)

}
