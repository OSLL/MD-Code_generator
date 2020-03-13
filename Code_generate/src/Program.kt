package com.example

class Program {
    internal val variable_uns_int: MutableSet<String> = mutableSetOf()
    internal val variable_size_t: MutableSet<String> = mutableSetOf()
    internal val variable_float: MutableSet<String> = mutableSetOf()
    internal val variable_int: MutableSet<String> = mutableSetOf()
    internal val variable_bool: MutableSet<String> = mutableSetOf()
    internal val pointer_variable: MutableSet<String> = mutableSetOf()
    internal val array_variable: MutableSet<String> = mutableSetOf()

    var counter_terms = 0
    var counter_if = 0
    var counter_else = 0
    var counter_while = 0
    var counter_do_while = 0
    var counter_for = 0
    var counter_switch = 0
    var counter_case = 0
    var counter_printf = 0
    var arithmetic_conversion_count = 0
    var arithmetic_conversion_correctness = true

    internal val program_: MutableList<String> = mutableListOf()

    internal constructor() {}

    internal fun getVariableUnsInt(): MutableSet<String> {
        return variable_uns_int
    }

    internal fun getVariableUnsIntIndex(index: Int): String {
        return variable_uns_int.elementAt(index)
    }

    internal fun getVariableFloat(): MutableSet<String> {
        return variable_float
    }

    internal fun getVariableFloatIndex(index: Int): String {
        return variable_float.elementAt(index)
    }

    internal fun getVariableInt(): MutableSet<String> {
        return variable_int
    }

    internal fun getVariableIntIndex(index: Int): String {
        return variable_int.elementAt(index)
    }

    internal fun getVariableBool(): MutableSet<String> {
        return variable_bool
    }

    internal fun getVariableBoolIndex(index: Int): String {
        return variable_bool.elementAt(index)
    }

    fun getPointerVariable(): MutableSet <String> {
        return pointer_variable
    }

    fun getPointerVariableIndex(index: Int): String {
        return pointer_variable.elementAt(index)
    }

    fun getArrayVariable(): MutableSet <String> {
        return array_variable
    }

    fun getArrayVariableIndex(index: Int): String {
        return array_variable.elementAt(index)
    }

    internal fun getCounterVariables(): Int {
        return variable_uns_int.size + variable_bool.size + variable_float.size + variable_int.size + variable_size_t.size + pointer_variable.size
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

    internal fun incrementCounterIf() {
        counter_if++
    }

    internal fun getCounterElse(): Int {
        return counter_else
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
}