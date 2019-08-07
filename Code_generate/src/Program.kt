//package com.example

class Program {
    internal val variable_uns_int: MutableSet<String> = mutableSetOf()
    internal val variable_size_t: MutableSet<String> = mutableSetOf()
    internal val variable_float: MutableSet<String> = mutableSetOf()
    internal val variable_int: MutableSet<String> = mutableSetOf()
    internal val variable_bool: MutableSet<String> = mutableSetOf()

    internal var counter_variables: Int = 0
    internal var counter_if: Int = 0
    internal var counter_else: Int = 0
    internal var counter_while: Int = 0
    internal var counter_do_while: Int = 0
    internal var counter_for: Int = 0
    internal var counter_printf: Int = 0

/*
    var counter_variables = 0
    var counter_if = 0
    var counter_else = 0
    var counter_while = 0
    var counter_do_while = 0
    var counter_for = 0
    var counter_printf = 0
*/

    internal val program_: MutableList<String> = mutableListOf()
    internal var parameters: ProgramParameters = ProgramParameters()


    internal constructor() {}
//    constructor() { }

    internal constructor(param: ProgramParameters) {
/*
        parameters.task = param.getTask_()
        parameters.rand_seed = param.getRandSeed()
        parameters.variables_num = param.getVariablesNum()
        parameters.statements_num = param.getStatementsNum()
        parameters.arguments_num = param.getArgumentsNum()
        parameters.printf_num = param.getPrintfNum()
        parameters.redefiniton_var = param.getRedefinitionVar()
        parameters.operation_index = param.getOperationIndex()
        parameters.OPERATIONS_TYPE.addAll(param.getOperationType())
        parameters.nesting_level = param.getNestingLevel()
        parameters.size_ = param.getSize()
*/
        parameters = param
    }
/*
    internal constructor(program: Program) {
        counterVariables = program.counterVariables
        counterIf = program.counterIf
        counterElse = program.counterElse
        counterWhile = program.counterWhile
        counterDoWhile = program.counterDoWhile
        counterFor = program.counterFor
        counterPrintf = program.counterPrintf
    }
*/

    internal constructor(prog: Program) {
        counter_variables = prog.counter_variables
        counter_if = prog.counter_if
        counter_else = prog.counter_else
        counter_while = prog.counter_while
        counter_do_while = prog.counter_do_while
        counter_for = prog.counter_for
        counter_printf = prog.counter_printf

        variable_uns_int.addAll(prog.variable_uns_int)
        variable_size_t.addAll(prog.variable_size_t)
        variable_float.addAll(prog.variable_float)
        variable_int.addAll(prog.variable_int)
        variable_bool.addAll(prog.variable_bool)
        program_.addAll(prog.program_)
        parameters = prog.parameters

/*
        variable_uns_int.addAll(prog.getVariableUnsInt())
        variable_size_t.addAll(prog.getVariableSize_t())
        variable_float.addAll(prog.getVariableFloat())
        variable_int.addAll(prog.getVariableInt())
        variable_bool.addAll(prog.getVariableBool())
        program_.addAll(prog.getProgram())
        parameters = prog.getParameters_()
*/
    }

    internal fun getParameters_(): ProgramParameters {
        return parameters
    }

    /*fun setParameters_(param: ProgramParameters) {
        parameters = param
    }*/

    internal fun getVariableUnsInt(): MutableSet<String> {
        return variable_uns_int
    }

    internal fun setVariableUnsInt(variable: MutableList<String>) {
        variable_uns_int.addAll(variable)
    }

    internal fun getVariableSize_t(): MutableSet<String> {
        return variable_size_t
    }

    internal fun setVariableSize_t(variable: MutableList<String>) {
        variable_size_t.addAll(variable)
    }

    internal fun getVariableFloat(): MutableSet<String> {
        return variable_float
    }

    internal fun setVariableFloat(variable: MutableList<String>) {
        variable_float.addAll(variable)
    }

    internal fun getVariableFloatIndex(index: Int): String {
        return variable_float.elementAt(index)
    }

    internal fun getVariableInt(): MutableSet<String> {
        return variable_int
    }

    internal fun setVariableInt(variable: MutableList<String>) {
        variable_int.addAll(variable)
    }

    internal fun getVariableIntIndex(index: Int): String {
        return variable_int.elementAt(index)
    }

    internal fun getVariableBool(): MutableSet<String> {
        return variable_bool
    }

    internal fun setVariableBool(variable: MutableList<String>) {
        variable_bool.addAll(variable)
    }

    internal fun getVariableBoolIndex(index: Int): String {
        return variable_bool.elementAt(index)
    }

    internal fun getCounterVariables(): Int {
//        return counter_variables
        return variable_uns_int.size + variable_bool.size + variable_float.size + variable_int.size + variable_size_t.size
    }

    internal fun setCounterVariables(number: Int) {
        counter_variables = number
    }

    internal fun incrementCounterVariables() {
        counter_variables++
    }

    internal fun getCounterIf(): Int {
        return counter_if
    }

    internal fun setCounterIf(number: Int) {
        counter_if = number
    }

    internal fun incrementCounterIf() {
        counter_if++
    }

    internal fun getCounterElse(): Int {
        return counter_else
    }

    internal fun setCounterElse(number: Int) {
        counter_else = number
    }

    internal fun incrementCounterElse() {
        counter_else++
    }

    internal fun getCounterWhile(): Int {
        return counter_while
    }

    internal fun incrementCounterWhile() {
        counter_while++
    }

    internal fun getCounterDoWhile(): Int {
        return counter_do_while
    }

    internal fun incrementCounterDoWhile() {
        counter_do_while++
    }

    internal fun getCounterFor(): Int {
        return counter_for
    }

    internal fun incrementCounterFor() {
        counter_for++
    }

    internal fun getCounterPrintf(): Int {
        return counter_printf
    }

    internal fun incrementCounterPrintf() {
        counter_printf++
    }

    internal fun getProgram(): MutableList<String> {
        return program_
    }
/*
    internal fun addProgram(prog: Program) {
        counter_variables += prog.counter_variables
        counter_if += prog.counter_if
        counter_else += prog.counter_else
        counter_while += prog.counter_while
        counter_do_while += prog.counter_do_while
        counter_for += prog.counter_for
        counter_printf += prog.counter_printf

        variable_uns_int.addAll(prog.variable_uns_int)
        variable_size_t.addAll(prog.variable_size_t)
        variable_float.addAll(prog.variable_float)
        variable_int.addAll(prog.variable_int)
        variable_bool.addAll(prog.variable_bool)
        //program_.addAll(prog.program_)
        parameters = prog.parameters
/*
        counter_variables += prog.getCounterVariables()
        counter_if += prog.getCounterIf()
        counter_else += prog.getCounterElse()
        counter_while += prog.getCounterWhile()
        counter_do_while += prog.getCounterDoWhile()
        counter_for += prog.getCounterFor()
        counter_printf += prog.getCounterPrintf()

        variable_uns_int.addAll(prog.getVariableUnsInt())
        variable_size_t.addAll(prog.getVariableSize_t())
        variable_float.addAll(prog.getVariableFloat())
        variable_int.addAll(prog.getVariableInt())
        variable_bool.addAll(prog.getVariableBool())
        program_.addAll(prog.getProgram())
        parameters = prog.getParameters_()
*/

/*        counter_variables = counter_variables + prog.getCounterVariables()
        counter_if = counter_if + prog.getCounterIf()
        counter_else = counter_else + prog.getCounterElse()
        counter_while = counter_while + prog.getCounterWhile()
        counter_do_while = counter_do_while + prog.getCounterDoWhile()
        counter_for = counter_for + prog.getCounterFor()
        counter_printf = counter_printf + prog.getCounterPrintf()
*/
    }
*/
    internal fun coutProgram() {
        println("program data:")
        println("counter_variables: $counter_variables")
        println("counter_while: $counter_while")
        println("counter_printf: $counter_printf")
        println("___")
    }

}