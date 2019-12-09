package day02

import java.io.File
import java.util.concurrent.Flow

fun main(args: Array<String>) {
    part1("input/day02A.txt")
    part2("input/day02A.txt")
}

fun part1(pathName: String) {
    val f = File(pathName).readText()
    val mem = f.split(',').map { s -> s.trim().toInt() }
    val processor = IntegerComputer(mem)
    processor.storeInMem(1, 12).storeInMem(2, 2)
    processor.processProgram()
    println("OUTPUT=${processor.readImediateMode(0)}")
}

fun part2(pathName: String) {
    val f = File(pathName).readText()
    val mem = f.split(',').map { s -> s.trim().toInt() }

    var output = emptyList<Int>()

    for (noun in 0..100) {
        for (verb in 0..100) {
            val processor = IntegerComputer(mem)
            processor.storeInMem(1, noun)
                .storeInMem(2, verb)
            processor.processProgram()
            val output: Int = processor.readImediateMode(0)
            if (output == 19690720)
                println("OUTPUT=$output noun=$noun, verb=$verb")
        }
    }
//    println("OUTPUT=$output")
}

data class Opcode(private val v: Int) {
    val repr: String
        get() = v.toString().padStart(5, '0')
    val instr: Int
        get() = listOf<Char>(repr[3], repr[4]).joinToString("").toInt()
    val param1Mode = (repr[2] - '0').toByte()
    val param2Mode = (repr[1] - '0').toByte()
    val param3Mode = (repr[0] - '0').toByte()
}

class IntegerComputer(private val input: List<Int>) {

    private val mem = input.toMutableList()

    fun storeInMem(address: Int, value: Int): IntegerComputer {
        mem[address] = value
        return this;
    }

    fun processProgram(input: List<Int> = emptyList()): List<Int> {
        val inp = input.toMutableList()
        var pc = 0
        var halt = false
        var opcode: Opcode;
        val output: MutableList<Int> = mutableListOf<Int>()
        while (!halt) {
            opcode = Opcode(mem[pc])
            println("pc=${pc} instr=${opcode.instr}")
            when (opcode.instr) {
                1 -> { // add
                    val a1: Int = readMem(pc + 1, opcode.param1Mode)
                    val a2: Int = readMem(pc + 2, opcode.param2Mode)
                    val r = a1 + a2
                    writePositionMode(pc + 3, r)
                    pc += 4
                }
                2 -> { // multiply
                    val a1: Int = readMem(pc + 1, opcode.param1Mode)
                    val a2: Int = readMem(pc + 2, opcode.param2Mode)
                    val r = a1 * a2
                    writePositionMode(pc + 3, r)
                    pc += 4
                } // readInput
                3 -> {
                    val a1: Int = inp.removeAt(0)
                    writeImediateMode(mem[pc+1], a1)
                    pc += 2
                } // writeOutput
                4 -> {
                    val a1 = readImediateMode(mem[pc + 1])
                    output.add(a1)
                    pc += 2
                }
                5 -> { // jump-if-true
                    if (mem[pc + 1] != 0) {
                        pc = mem[pc + 2]
                    }
                    pc += 3
                }
                6 -> { // jump-if-false
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    if (r1 == 0) {
                        pc = readMem(pc + 2, opcode.param2Mode)
                    }
                    pc += 3
                }
                7 -> {
                    if (readMem(pc + 1, opcode.param1Mode) < readMem(pc + 2, opcode.param2Mode)) {
                        writeMem(pc + 3, 1, opcode.param3Mode)
                    } else {
                        writeMem(pc + 3, 0, opcode.param3Mode)
                    }
                    pc += 4
                }
                8 -> { // equals
                    if (mem[pc + 1] == mem[pc + 2]) {
                        writeMem(pc + 3, 1, opcode.param3Mode)
                    } else {
                        writeMem(pc + 3, 0, opcode.param3Mode)
                    }
                    pc += 4
                }
                99 -> {
                    halt = true
                }
                else -> {
                    throw Exception("Illegal instruction: ${opcode.instr} pc=$pc v=${mem[pc]}")
                }
            }
        }
        return output.toList()
    }

    private fun writeImediateMode(addr: Int, value: Int) {
        mem[addr] = value
    }

    private fun readMem(addr: Int, paramMode: Byte): Int =
        if (paramMode.compareTo(0) == 0) readPositionMode(addr) else readImediateMode(addr)

    private fun writeMem(addr: Int, value: Int, paramMode: Byte): Unit =
        if (paramMode.compareTo(0) == 0) writePositionMode(addr, value) else writeImediateMode(addr, value)

    fun readPositionMode(addr: Int) = mem[mem[addr]]
    fun readImediateMode(addr: Int) = mem[addr]
    fun writePositionMode(addr: Int, value: Int) {
        mem[mem[addr]] = value
    }

    fun dumpMem() : List<Int> = mem.toList()
}


