package ktbf

import java.util.*

class Interpreter(dataSize: Int) {
    val data: Array<Byte> = Array<Byte>(dataSize, {0x0})
    var dataPointer: Int = 0
    val scanner: Scanner = Scanner(System.`in`)

    constructor() : this(30000)

    fun interpret(program: String) {
        var pc: Int = 0
        while(pc < program.length) {
            when(program[pc]) {
                '>' -> dataPointer++
                '<' -> dataPointer--
                '+' -> data[dataPointer]++
                '-' -> data[dataPointer]--
                '.' -> println(data[dataPointer].toChar()) // .toChar()?
                ',' -> {
                    var foundChar: Boolean = false
                    while(!foundChar){
                        val input: String = scanner.next()
                        if(input.length == 1){
                            data[dataPointer] = input[0].toByte()
                            foundChar = true
                        } else{
                            println("Please enter a char!")
                        }
                    }
                }
                '[' -> {
                    if(data[dataPointer] == 0.toByte()){
                        var loopsPassed: Int = 1
                        pc++
                        while(loopsPassed != 0){
                            if(program[pc] == '['){
                                loopsPassed++
                            } else if(program[pc] == ']'){
                                loopsPassed--
                            }
                            pc++
                        }
                        pc -= 3
                    }
                }
                ']' -> {
                    if(data[dataPointer] != 0.toByte()){
                        var loopsPassed: Int = 1
                        pc--
                        while(loopsPassed != 0){
                            if(program[pc] == ']'){
                                loopsPassed++
                            } else if(program[pc] == '['){
                                loopsPassed--
                            }
                            pc--
                        }
                        pc++
                    }
                }
            }
            pc++
        }
    }
}

fun main(args: Array<String>) {
    val interpreter: Interpreter = Interpreter()
    interpreter.interpret(">,[>,]<[.<]")
    //interpreter.interpret("++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.")
}