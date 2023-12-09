package com.kleinreveche.adventofcode.twentytwo.day06

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year

@Problem(year = Year.TwentyTwo, day = "Day06", name = "Tuning Trouble")
class Solution(private val input: String) : ISolution {
    override fun partOne(): Any {
        val startPacketIndex = parseData(4)
        return "The characters that were needed to be processed before the first start-of-pocket were $startPacketIndex"
    }

    override fun partTwo(): Any {
        val startMessageIndex = parseData(14)
        return "The characters that were needed to be processed before the first start-of-message were $startMessageIndex"
    }

    private fun parseData(input: Int): Any {
        val lines = this.input.trim()
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
}