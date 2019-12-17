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
            print("pc=${pc} instr:${opcode.instr} modes:${opcode.param1Mode},${opcode.param2Mode},${opcode.param3Mode} ")
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
                }
                3 -> { // readInput
                    val r1: Int = inp.removeAt(0)
                    writeMem(pc+1, r1, opcode.param1Mode)
//                    writeImediateMode(mem[pc+1], a1)
                    pc += 2
                }
                4 -> { // writeOutput
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    output.add(r1)
                    pc += 2
                }
                5 -> { // jump-if-true
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    val r2 = readMem(pc + 2, opcode.param2Mode)
                    print("jump-if-nz  $r1 addr: $r2")
                    if (r1 !=0) {
                        pc = r2
                    } else {
                        pc += 3
                    }
                }
                6 -> { // jump-if-false
                    val r1 = readMem(pc + 1, opcode.param1Mode)
                    val r2 = readMem(pc + 2, opcode.param2Mode)
                    print("jump-if-z  $r1 addr: $r2")
                    if (r1 == 0) {
                        pc = r2
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

    private fun writeImediateMode(addr: Int, value: Int) {
        mem[addr] = value
    }

    private fun readMem(addr: Int, paramMode: Byte): Int =
        when (paramMode.toInt()) {
            0 -> readPositionMode(addr)
            1 -> readImediateMode(addr)
            else -> throw Exception("Unknown parameter mode(readMem) ${paramMode.toInt()}")
        }

    private fun writeMem(addr: Int, value: Int, paramMode: Byte): Unit =
        when(paramMode.toInt()) {
            0 -> writePositionMode(addr, value)
            1 -> writeImediateMode(addr, value)
            else -> throw Exception("Unknown parameter mode(writeMem ${paramMode.toInt()}")
        }

    fun readPositionMode(addr: Int) = mem[mem[addr]]
    fun readImediateMode(addr: Int) = mem[addr]
    fun writePositionMode(addr: Int, value: Int) {
        mem[mem[addr]] = value
    }

    fun dumpMem() : List<Int> = mem.toList()
}


