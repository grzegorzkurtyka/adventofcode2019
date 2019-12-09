package day01

import java.io.File

fun main(args: Array<String>) {
    val f = File("input/day01A.txt").useLines { it.toList() }.map { s -> s.toInt() }
//    val f = File("input/day01_test.txt").useLines { it.toList() }
    val i1 = f.map { mass -> fuelForModule(mass) }
    val i2 = i1.map { mass -> mass + fuelForFuel(mass) }
    val sum = i2.sum()
    println("SUM=$sum")
}

fun fuelForModule(mass: Int) =
    (Math.floorDiv(mass, 3) - 2)

fun fuelForFuel(massOfFuel: Int): Int {
    var f = 0;
    var tmp: Int = fuelForModule(massOfFuel);
    while (tmp > 0) {
        f += tmp
        tmp = fuelForModule(tmp)
    }
    return f
}
