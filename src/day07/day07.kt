package day07

import intcomputer.IntegerComputer
import java.io.File

fun main(args: Array<String>) {
//    val pathName = "input/day07_test.txt"
//
    val pathName = "input/day07.txt"
    val f = File(pathName).readText()
    val mem = f.split(',').map { s -> s.trim().toLong() }

//    val processor = IntegerComputer(mem)
//    var output: List<Int> = mutableListOf()

//    println()

    val l = (0..4).toList()
    val sequences = permute(l)
    val combinations: MutableList<Pair<String, Long>> = mutableListOf<Pair<String, Long>>()

//

    for (sequence in sequences) {

//        val processors: List<IntegerComputer> = sequence.map { IntegerComputer(mem) }
//
//        val o = processors.reversed().foldRight(0, { processor: IntegerComputer, acc: Int ->
//            processor.processProgram(listOf(acc)).get(0)
//        })
//
//        val s = sequence.joinToString(",")


        var output= listOf<Long>()

//        val o = output[0]
//        println("$s $o")
//
        val processor1 = IntegerComputer(mem)
        output = processor1.processProgram(listOf<Long>(sequence[0].toLong(), 0))
//        println("OUTPUT=${output}")

        val processor2 = IntegerComputer(mem)
        output = processor2.processProgram(listOf(sequence[1].toLong(), output[0]))
//        println("OUTPUT=${output}")

        val processor3 = IntegerComputer(mem)
        output = processor3.processProgram(listOf(sequence[2].toLong(), output[0]))
//        println("OUTPUT=${output}")

        val processor4 = IntegerComputer(mem)
        output = processor4.processProgram(listOf(sequence[3].toLong(), output[0]))
//        println("OUTPUT=${output}")

        val processor5 = IntegerComputer(mem)
        output = processor5.processProgram(listOf(sequence[4].toLong(), output[0]))
//        println("sequence = ${sequence} OUTPUT=${output}")
//
        val s = sequence.joinToString(",")
        val o = output[0]
        println("$s $o")

        combinations.add(Pair(s, o))
    }


    combinations.sortBy { it.second }

    for (i in combinations)
        println(i)
}

fun permute(list: List<Int>): List<List<Int>> {
    if (list.size == 1) return listOf(list)
    val perms = mutableListOf<List<Int>>()
    val sub = list[0]
    for (perm: List<Int> in permute(list.drop(1)))
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
}
