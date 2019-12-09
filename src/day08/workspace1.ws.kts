package day08

val layers = listOf<List<Int>>(listOf(0, 1, 1, 2, 2, 2), listOf(0, 0, 2))

data class Pixes(val v0: Int, val v1: Int, val v2: Int)

//val counts = layers.map { layer ->
//    Pixes(
//        layer.filter { it == 0 }.count(),
//        layer.filter { it == 1 }.count(),
//        layer.filter { it == 1 }.count()
//    )
//}

val counts = layers.map { layer -> layer.groupByTo(mutableMapOf(), {k -> k}) }
//val tmp1 = counts.mapTo ( mutableMapOf<Int, Int>(), {it -> Pair(it[0], it[1].count())} )
val tmp1 =counts.sortedBy { it[0]?.count() }.get(0)
for(c in tmp1)
    println(c)
println(tmp1[1]?.count())
println(tmp1[2]?.count())
