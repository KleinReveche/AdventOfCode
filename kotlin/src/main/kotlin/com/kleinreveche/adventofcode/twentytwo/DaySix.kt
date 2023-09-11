package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Solver
import com.kleinreveche.adventofcode.Utils

/** --- Day 6: Tuning Trouble --- https://adventofcode.com/2022/day/6 */
class DaySix : Solver {
    override fun solve() {
        val startPacketIndex = parseData(4)
        val startMessageIndex = parseData(14)
        println(" --- 2022 Day 6: Tuning Trouble ---\n")

        println("   The characters that were needed to be processed before the first start-of-pocket were $startPacketIndex")
        println("   The characters that were needed to be processed before the first start-of-message were $startMessageIndex\n")
    }

    override fun parseData(input: Int): Any {
        val lines = Utils.readInput("twentytwo", "day06")!!.trim()
        var index = 0

        while (index + input < lines.length) {
            val subString = lines.substring(index, index + input)
            if (subString.toSet().size == input) {
                return index + input
            }
            index++
        }

        return -1
    }

    override fun parseData(): Any {
        return 0
    }
}