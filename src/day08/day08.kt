package day08

import java.io.File

fun main(args: Array<String>) {
    val pathName = "input/day08.txt"
    val contents = File(pathName).readText().trimEnd()
    val pixels = contents.toCharArray().map { it.toInt() - 48 }

    val part1 = part1(pixels)
    println("part1 = ${part1.first} * ${part1.second} = ${part1.first * part1.second}")

    val part2 = part2(pixels)
}

fun part2(pixels: List<Int>): Unit {

}

fun part1(pixels: List<Int>): Pair<Int, Int> {

    val layers = pixels.chunked(6 * 25)

//    val counts = layers.map { layer -> { layer.groupByTo(mutableMapOf(), {it}) } }
    val counts = layers
        .map { layer -> layer.groupByTo(mutableMapOf(), { k -> k }) }

//    println("pixCount=${pixels.size}")

    for (l in layers)
        println(l)
    for (c in counts)
        println(c)

    val tmp1 = counts.sortedBy { it[0]?.count() }.get(0)
    for (c in tmp1)
        println(c)
    println(tmp1[1]?.count())
    println(tmp1[2]?.count())

    val count1 = tmp1.getOrElse(1, { mutableListOf<Int>()}).count()
    val count2 = tmp1.getOrElse(2, { mutableListOf<Int>()}).count()

    return Pair<Int, Int>(count1, count2)
}
