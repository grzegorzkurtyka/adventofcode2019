package intcomputer

data class Opcode(private val v: Int) {
    val repr: String
        get() = v.toString().padStart(5, '0')
    val instr: Int
        get() = listOf<Char>(repr[3], repr[4]).joinToString("").toInt()
    val param1Mode = (repr[2] - '0').toByte()
    val param2Mode = (repr[1] - '0').toByte()
    val param3Mode = (repr[0] - '0').toByte()
    val mnem
        get() = v
}
