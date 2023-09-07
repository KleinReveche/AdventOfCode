package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Utils

fun daySix() {
    val startPacketIndex = readDaySixInput(4)
    val startMessageIndex = readDaySixInput(14)
    println(" --- 2022 Day 6: Tuning Trouble ---\n")

    println("   The characters that were needed to be processed before the first start-of-pocket were $startPacketIndex")
    println("   The characters that were needed to be processed before the first start-of-message were $startMessageIndex")
}

private fun readDaySixInput(charNum: Int): Int {
    val input = Utils.readInput("twentytwo", "day06")!!.trim()
    var index = 0

    while (index + charNum < input.length) {
        val subString = input.substring(index, index + charNum)
        if (subString.toSet().size == charNum) {
            return index + charNum
        }
        index++
    }

    return -1
}