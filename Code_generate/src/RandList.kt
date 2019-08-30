package com.example

import java.util.*

class RandList {
    var variableIdList: MutableList<Int> = mutableListOf()
    var listBool: MutableList<Int> = mutableListOf()
    var listInt: MutableList<Int> = mutableListOf()
    var listFloat: MutableList<Float> = mutableListOf()
    var operationIdList: MutableList<Int> = mutableListOf() //индексы мат операторов
    var listCondition: MutableList<Int> = mutableListOf()
    var relationalOperationIdList: MutableList<Int> = mutableListOf() //индексы операторов сравнения

    constructor() { }

    constructor(randSeed: Int, size_: Int, variablesNum: Int, operationNum: Int) {
        val size = size_
        variableIdList = randListInt(Random(randSeed.toLong()), 0, variablesNum, size)
        listBool = randListInt(Random(randSeed.toLong()), 0, 2, size)
        listInt = randListInt(Random(randSeed.toLong()), 1, MAX_VALUE, size)
        operationIdList = randListInt(Random(randSeed.toLong()), 0, operationNum, size) //индексы мат операторов
    }

    constructor(randSeed: Int, size_: Int, variablesNum: Int) {
        val size = size_
        variableIdList = randListInt(Random(randSeed.toLong()), 0, variablesNum, size * 10) // * 3
        listBool = randListInt(Random(randSeed.toLong()), 0, 2, size * 6)
        listInt = randListInt(Random(randSeed.toLong()), 1, MAX_VALUE, size)
        listFloat = randListFloat(Random(randSeed.toLong()), 1, MAX_VALUE, variablesNum * 5)
        operationIdList = randListInt(Random(randSeed.toLong()), 0, ARITHMETIC_OPERATIONS.size, 70) //индексы мат операторов
        listCondition = randListInt(Random(randSeed.toLong()), 0, 3, size/*parameters.getPrintfNum() + parameters.getVariablesNum()*/)
        relationalOperationIdList = randListInt(Random(randSeed.toLong()), 0, RELATIONAL_OPERATIONS.size, 70)
    }

    //генерирует число в диапазоне [from; to] с зерном randSeed
    fun rand(from: Int, to: Int, randSeed: Int): Int {
        val random = Random(randSeed.toLong())
        if (from == to)
            return from
        return random.nextInt(to - from) + from
    }

    //генерирует последовательность int в диапазоне [from; to] с зерном randSeed
    fun randListInt(random: Random, from: Int, to: Int, size: Int): MutableList<Int> = MutableList(size) {
        random.nextInt(to - from) + from
    }

    //генерирует последовательность float в диапазоне [from; to] с зерном randSeed
    fun randListFloat(random: Random, from: Int, to: Int, size: Int): MutableList<Float> = MutableList(size) {
        (random.nextInt(to - from) + from + (random.nextInt(to - from) + from) * 0.1).toFloat()
    }

    //возвращает первое число из списка int и удаляет его Int
    fun randListIntPop(randList: MutableList<Int>) : Int {
        if (!randList.isEmpty()) {
            val index = randList.first()
            randList.remove(index)
            return index
        }
        return 0
    }

    //возвращает первое число из списка float и удаляет его
    fun randListFloatPop(randList: MutableList<Float>) : Float {
        if (!randList.isEmpty()) {
            val value = randList.first()
            randList.remove(value)
            return value
        }
        return 0F
    }

    //возвращает первое число из списка и удаляет его Boolean
    fun randListBoolPop(randListBool: MutableList<Int>) : Boolean {
        if (!randListBool.isEmpty()) {
            val flag = randListBool.first()
            randListBool.remove(randListBool.first())
            if (flag == 1)
                return true
            return false
        }
        return true
    }

}