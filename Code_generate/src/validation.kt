package com.example

fun validateTask(task: Int, randSeed: String) {
    if (task !in 1 until NUMBER_TASKS) {
        throw IncorrectTaskDataError("Incorrect task number $task: should be between 1 and ${NUMBER_TASKS - 1}")
    }
    if (randSeed.length <= 8 || !randSeed.contains(TEMPLATE)) {
        throw IncorrectTaskDataError("Incorrect random seed")
    }
}

fun validateTaskParameters(parameters: GenerationParameters): Boolean {
    return with(parameters) {
        when (task) {
            1 -> (statementsNum != null && argumentsNum != null && printfNum != null && redefinitionVar != null && operations != null)
            2 -> (argumentsNum != null && ifNum != null && nestingLevel != null)
            3 -> (argumentsNum != null && switchNum != null && caseNum != null && nestingLevel != null)
            4 -> (argumentsNum != null && whileNum != null && nestingLevel != null)
            5 -> (argumentsNum != null && doWhileNum != null && nestingLevel != null)
            6 -> (argumentsNum != null && forNum != null && nestingLevel != null)
            7 -> ((argumentsNum != null && nestingLevel != null && (ifNum != null || (switchNum != null && caseNum != null) || whileNum != null || doWhileNum != null || forNum != null)))
            8 -> (statementsNum != null && argumentsNum != null && printfNum != null)
            9 -> (arrayNum != null && arraySize != null && statementsNum != null && printfNum != null)
            10 -> (argumentsNum != null && printfNum != null)
            else -> false
        }
    }

}

fun validateParameters(parameters: GenerationParameters) {
    validateTask(parameters.task, parameters.randSeed)
    if (!validateTaskParameters(parameters)) {
        throw IncorrectTaskDataError("Incorrect parameters for task ${parameters.task}")
    }
}