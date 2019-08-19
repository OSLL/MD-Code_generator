package com.example

import java.util.*

class RandList {
    var randList: MutableList<Int> = mutableListOf()
    var numList: MutableList<Int> = mutableListOf()
    var listBool: MutableList<Int> = mutableListOf()
    var listInt: MutableList<Int> = mutableListOf()
    var listFloat: MutableList<Float> = mutableListOf()
    var operationIdList: MutableList<Int> = mutableListOf() //индексы операторов
    var listCondition: MutableList<Int> = mutableListOf()

    constructor() { }

    constructor(randSeed: Int, size_: Int, variablesNum: Int) {
        val size = size_
        randList = randListInt(Random(randSeed.toLong()), 0, variablesNum, size)
    }

    constructor(randSeed: Int, size_: Int, variablesNum: Int, argumentsNum: Int) {
        val size = size_
        randList = randListInt(Random(randSeed.toLong()), 0, variablesNum, size)
        numList = randListInt(Random(randSeed.toLong()), 1, MAX_VALUE, variablesNum * argumentsNum)
        listBool = randListInt(Random(randSeed.toLong()), 0, 2, size)
        listInt = randListInt(Random(randSeed.toLong()), 0, variablesNum, variablesNum * 10)
        listFloat = randListFloat(Random(randSeed.toLong()), 0, 9, variablesNum * 5)
        operationIdList = randListInt(Random(randSeed.toLong()), 0, OPERATIONS.size, 70) //индексы операторов
        listCondition = randListInt(Random(randSeed.toLong()), 0, 3, size/*parameters.getPrintfNum() + parameters.getVariablesNum()*/)
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