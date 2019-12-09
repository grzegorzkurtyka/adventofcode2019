package day04

fun main(args: Array<String>) {
    val range = 152085..670283
//    val range = 111115..111125
    val listOfPass = range.map { toListDigits(it) }
    val tmp1 =
        listOfPass.filter { noDecreasing(it) }
            .filter { hasDoubles(it) }

    tmp1.forEach { println(it.joinToString("")) }
    println("Count:" + tmp1.count())
}

fun hasDoubles(l: List<Char>): Boolean {
    return (l[0] == l[1]) || (l[1] == l[2]) || (l[2] == l[3]) || (l[3] == l[4]) || (l[4] == l[5])
}

fun toListDigits(n: Int): List<Char> {
    return n.toString().toCharArray().toList()
}

fun noDecreasing(l: List<Char>): Boolean {
    return (l[0] <= l[1]) &&
            (l[1] <= l[2]) &&
            (l[2] <= l[3]) &&
            (l[3] <= l[4]) &&
            (l[4] <= l[5])
}
