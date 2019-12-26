package intcomputer

class IntegerComputer(private val input: List<Long>, private val memSize: Int? = null) {

    private var relativeBase: Int = 0

    private val mem: MutableList<Long> = if (memSize != null) {
        val tmp = arrayOfNulls<Long>(memSize)
        tmp.fill(0)
        for ((a, i) in input.withIndex()) {
            tmp[a] = i
        }
        tmp.map { it!!.toLong() }.toMutableList()
    } else {
        input.toMutableList()
    }

    fun storeInMem(address: Int, value: Long): IntegerComputer {
        mem[address] = value
        return this;
    }

    fun processProgram(input: List<Long> = emptyList()): List<Long> {
        val inp = input.toMutableList<Long>()
        var pc = 0
        var halt = false
        var opcode: Opcode;
        val output: MutableList<Long> = mutableListOf<Long>()
        while (!halt) {
            opcode = Opcode(mem[pc].toInt())
            print("pc=${pc} mnem=${opcode.mnem} instr:${opcode.instr} modes:${opcode.param1Mode},${opcode.param2Mode},${opcode.param3Mode} ")
            when (opcode.instr) {
                1 -> { // add
                    val a1: Long = readMem(pc + 1, opcode.param1Mode)
                    val a2: Long = readMem(pc + 2, opcode.param2Mode)
                    val r = a1 + a2
                    print("add  $a1 + $a2 = $r")
                    writeMem(pc + 3, r, opcode.param3Mode)
                    pc += 4
                }
                2 -> { // multiply
                    val a1: Long = readMem(pc + 1, opcode.param1Mode)
                    val a2: Long = readMem(pc + 2, opcode.param2Mode)
                    val r: Long = a1 * a2
                    print("multiply  $a1 * $a2 = $r")
                    writeMem(pc + 3, r, opcode.param3Mode)
                    pc += 4
                }
                3 -> { // readInput
                    val r1: Long = inp.removeAt(0)
                    writeMem(pc + 1, r1, opcode.param1Mode)
                    print("readInput  $r1")
                    pc += 2
                }
                4 -> { // writeOutput
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    print("writeOutput $r1")
                    output.add(r1)
                    pc += 2
                }
                5 -> { // jump-if-true
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    val r2 = readMem(pc + 2, opcode.param2Mode)
                    print("jump-if-nz  $r1 addr: $r2")
                    if (!r1.equals(0.toLong())) {
                        pc = r2.toInt()
                    } else {
                        pc += 3
                    }
                }
                6 -> { // jump-if-false
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    val r2 = readMem(pc + 2, opcode.param2Mode)
                    print("jump-if-z  $r1 addr: $r2")
                    if (r1.equals(0.toLong())) {
                        pc = r2.toInt()
                    } else {
                        pc += 3
                    }
                }
                7 -> { // less than
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    val r2 = readMem(pc + 2, opcode.param2Mode)
                    print("less-than  $r1 < $r2")
                    if (r1 < r2) {
                        writeMem(pc + 3, 1, opcode.param3Mode)
                    } else {
                        writeMem(pc + 3, 0, opcode.param3Mode)
                    }
                    pc += 4
                }
                8 -> { // equals
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    val r2 = readMem(pc + 2, opcode.param2Mode)
                    print("equals  $r1 == $r2")

                    if (r1 == r2) {
                        writeMem(pc + 3, 1, opcode.param3Mode)
                    } else {
                        writeMem(pc + 3, 0, opcode.param3Mode)
                    }
                    pc += 4
                }
                9 -> {
                    print("relativeBase $relativeBase")
                    val r1 = readMem(pc + 1, opcode.param1Mode).toInt()
                    relativeBase += r1
                    print(" to $relativeBase")
                    pc += 2
                }
                99 -> {
                    halt = true
                }
                else -> {
                    throw Exception("Illegal instruction: ${opcode.instr} pc=$pc v=${mem[pc]}")
                }
            }
            println()
        }
        println()
        return output.toList()
    }

    private fun writeImediateMode(addr: Int, value: Long) {
        mem[addr] = value
    }

    private fun readMem(addr: Int, paramMode: Byte): Long =
        when (paramMode.toInt()) {
            0 -> readPositionMode(addr)
            1 -> readImediateMode(addr)
            2 -> readRelativeMode(addr)
            else -> throw Exception("Unknown parameter mode(readMem) ${paramMode.toInt()}")
        }

    private fun readRelativeMode(addr: Int): Long {
        return mem[readImediateMode(addr).toInt() + relativeBase]
    }

    private fun writeMem(addr: Int, value: Long, paramMode: Byte): Unit =
        when (paramMode.toInt()) {
            0 -> writePositionMode(addr, value)
            1 -> writeImediateMode(addr, value)
            2 -> writeRelativeMode(addr, value)
            else -> throw Exception("Unknown parameter mode(writeMem ${paramMode.toInt()}")
        }

    private fun writeRelativeMode(addr: Int, value: Long) {
        val dest = readImediateMode(addr) + relativeBase
        mem[dest.toInt()] = value
    }

    fun readPositionMode(addr: Int) = mem[mem[addr].toInt()]
    fun readImediateMode(addr: Int) = mem[addr]
    fun writePositionMode(addr: Int, value: Long) {
        mem[mem[addr].toInt()] = value
    }

    fun dumpMem(): List<Long> = mem.toList()
}
