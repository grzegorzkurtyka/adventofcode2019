package day03

import java.io.File

typealias Point = Pair<Int, Int>

fun main(args: Array<String>) {
//    day02.part1("input/day02A.txt")
    part1("input/day03_test.txt")


//    val tmp1 = listOf<wireDirection>(wireDirection('R', 75), wireDirection('D', 30), wireDirection('R', 83))
//    tmp1.mapTo()<Pair<Int, Int>, Pair<Int, Int>>(Pair(0,0), (startPoint: Pair<Int, Int>) -> {})


//    part1("input/day03A.txt")
}

fun part1(pathName: String) {
    val wires = File(pathName).readLines().map { s -> s.split(',') }
    val tmp1 = wires.map { wire -> wire.map { s -> wireDirection(s[0], s.substring(1).toInt()) } }
    val tmp2 = tmp1.map { listWires -> val w = Wire(); w.fromWireDirections(listWires) ; w.getSegments()}
    println(wires)
//    println(tmp1)
    println(tmp2)
//    val points = f.split(',').map { s -> s.trim().toInt() }
}


class Wire {

    private val segments = mutableListOf<Pair<Point, Point>>()

    public fun fromWireDirections(wires: List<wireDirection>) {
        var current: Point = Pair(0, 0)
        var pointTo: Point = Pair(0, 0)
        wires.forEach { it ->
            run {
                print("current = $current d=${it.d} l=${it.l} ")
                pointTo = when (it.d) {
                    'U' -> Pair(current.first, current.second + it.l)
                    'D' -> Pair(current.first, current.second - it.l)
                    'L' -> Pair(current.first - it.l, current.second)
                    'R' -> Pair(current.first + it.l, current.second)
                    else -> throw Exception("Unknow direction $it.d")
                }
                println("pointTo = $pointTo")
                segments.add(Pair(current, pointTo))
                current = pointTo
            }
        }
    }

    public fun getSegments() = segments.toList()
}

data class wireDirection(val d: Char, val l: Int)
