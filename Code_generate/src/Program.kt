package com.example

class Program {
    internal val variable_uns_int: MutableSet<String> = mutableSetOf()
    internal val variable_size_t: MutableSet<String> = mutableSetOf()
    internal val variable_float: MutableSet<String> = mutableSetOf()
    internal val variable_int: MutableSet<String> = mutableSetOf()
    internal val variable_bool: MutableSet<String> = mutableSetOf()

    var counter_variables = 0
    var counter_terms = 0
    var counter_if = 0
    var counter_else = 0
    var counter_while = 0
    var counter_do_while = 0
    var counter_for = 0
    var counter_switch = 0
    var counter_case = 0
    var counter_printf = 0

    internal val program_: MutableList<String> = mutableListOf()


    internal constructor() {}
/*
    internal constructor(prog: Program) {
        counter_variables = prog.counter_variables
        counter_terms = prog.counter_terms
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
    }
*/
    internal fun getVariableUnsInt(): MutableSet<String> {
        return variable_uns_int
    }

    internal fun setVariableUnsInt(variable: MutableList<String>) {
        variable_uns_int.addAll(variable)
    }

    internal fun getVariableUnsIntIndex(index: Int): String {
        return variable_uns_int.elementAt(index)
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

    internal fun getCounterTerm(): Int {
        return counter_terms
    }

    internal fun incrementCounterTerm() {
        counter_terms++
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

    internal fun getCounterCase(): Int {
        return counter_case
    }

    internal fun incrementCounterCase() {
        counter_case++
    }

    internal fun getCounterSwitch(): Int {
        return counter_switch
    }

    internal fun incrementCounterSwitch() {
        counter_switch++
    }

    internal fun getProgram(): MutableList<String> {
        return program_
    }

    internal fun coutProgram() {
        println("program data:")
        println("counter_variables: $counter_variables")
        println("counter_while: $counter_while")
        println("counter_printf: $counter_printf")
        println("___")
    }

}