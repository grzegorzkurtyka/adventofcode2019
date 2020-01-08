package day17

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
    val pathName = "input/day17.txt"
    val f = File(pathName).readText()

    val mem = f.split(',').map { s -> s.trim().toLong() }

    val processor = IntegerComputer(mem, 4000)
    val output = processor.processProgram().map { it.toInt() }
    println(output)
    val w = output.indexOf(10)
    val h = output.size / w

    val lines = output.chunked(w + 1).map { it.filter { c -> c != 10 }.toIntArray() }

    println("w=$w h=$h")

    val board = Board(lines)

    board.printBoard()

    val pathPoints = board.pathPoints()
    val intersections = board.intersections()
    println(pathPoints)
    println(intersections)
    val result = intersections.map { it -> it.first * it.second }.sum()
    println(result)
}

class Board(private val board: List<IntArray>) {
    private val w = board.size - 1
    private val h = board[0].size - 1

    fun charAt(x: Int, y: Int): Int {
        val py = (y % w + w) % w
        val px = (x % h + h) % h
        return board[py][px]
    }

    fun printBoard(): Unit {
        for (line in board) {
            for (c in line) {
                print(c.toChar())
            }
            println()
        }
    }

    fun pathPoints(): List<Pair<Int, Int>> {
        val points: MutableList<Pair<Int, Int>> = mutableListOf()
        for (y in 0 until w)
            for (x in 0 until h) {
                if (isScaffold(x, y))
                    points.add(Pair(x, y))
            }
        return points
    }

    fun intersections(): List<Pair<Int, Int>> {
//        val points: MutableList<Pair<Int, Int>> = mutableListOf()
//        for (y in 0 until w)
//            for (x in 0 until h) {
//                if (isIntersection(x, y))
//                    points.add(Pair(x, y))
//            }
        val points = pathPoints().filter { it -> isIntersection(it.first, it.second) }
        return points.toList()
    }

    fun isScaffold(x: Int, y: Int) = charAt(x, y) != 46 // '.' = 46

    fun isIntersection(x: Int, y: Int) =
        isScaffold(x - 1, y) && isScaffold(x, y - 1) && isScaffold(x + 1, y) && isScaffold(x, y + 1)
}
