package com.example

import java.util.*

class RandList {
    var rand_seed = 0
    var variableIdList: MutableList<Int> = mutableListOf()
    var listBool: MutableList<Int> = mutableListOf()
    var listInt: MutableList<Int> = mutableListOf()
    var listFloat: MutableList<Float> = mutableListOf()
    var operationIdList: MutableList<Int> = mutableListOf() //индексы мат операторов
    var listCondition: MutableList<Int> = mutableListOf()
    var relationalOperationIdList: MutableList<Int> = mutableListOf() //индексы операторов сравнения
    var indexVariableList: MutableList<Int> = mutableListOf()
    var listArraySize: MutableList<Int> = mutableListOf()

    constructor(parameters: ProgramParameters, size: Int) {
        rand_seed = parameters.getRandSeed()
        variableIdList = randListInt(Random(parameters.getRandSeed().toLong()), 0, parameters.getVariablesNum(), size * 10)
        listBool = randListInt(Random(parameters.getRandSeed().toLong()), 0, 2, size * 6)
        listInt = randListInt(Random(parameters.getRandSeed().toLong()), 1, MAX_VALUE, size)
        listFloat = randListFloat(Random(parameters.getRandSeed().toLong()), 1, MAX_VALUE, parameters.getVariablesNum() * 5)
        operationIdList = randListInt(Random(parameters.getRandSeed().toLong()), 0, ARITHMETIC_OPERATIONS.size, 70) //индексы мат операторов
        listCondition = randListInt(Random(parameters.getRandSeed().toLong()), 0, 3, size/*parameters.getPrintfNum() + parameters.getVariablesNum()*/)
        relationalOperationIdList = randListInt(Random(parameters.getRandSeed().toLong()), 0, RELATIONAL_OPERATIONS.size, 70)

        when (parameters.getTask_()) {
            6, 7 -> indexVariableList = randListInt(Random(parameters.getRandSeed().toLong()), 0, IDENTIFIER.size, parameters.getForNum() + 1) // * 3
            9 ->    listArraySize = randListInt(Random(parameters.getRandSeed().toLong()), 3, parameters.getArraySize(), size)
        }
    }

    constructor() { }

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
        return 1
    }

    //возвращает первое число из списка float и удаляет его
    fun randListFloatPop(randList: MutableList<Float>) : Float {
        if (!randList.isEmpty()) {
            val value = randList.first()
            randList.remove(value)
            return value
        }
        return 1F
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