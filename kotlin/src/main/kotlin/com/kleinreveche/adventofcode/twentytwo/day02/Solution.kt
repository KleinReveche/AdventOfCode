package com.kleinreveche.adventofcode.twentytwo.day02

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year

@Problem(year = Year.TwentyTwo, day = "Day02", name = "Rock Paper Scissors")
class Solution(private val input: String) : ISolution {

    private val moves = parseData()
    private val scores = mutableListOf<Int>()
    private val secondScores = mutableListOf<Int>()

    override fun partOne(): Any {
        moves.forEach { it ->
            it.forEach {
                scores.add(calculateScore(it))
                secondScores.add(calculateScoreWithAltStrategy(it))
            }
        }
        return "Your score is ${scores.sum()}"
    }

    override fun partTwo(): Any {
        return "After the new instructions, your new score is ${secondScores.sum()}"
    }

    data class DayTwoMoves(val opponent: Char, val player: Char)

    private fun parseData(): MutableList<List<DayTwoMoves>> {
        val moves = mutableListOf<List<DayTwoMoves>>()
        var currentMovesList = mutableListOf<DayTwoMoves>()
        val lines = input.trim().lines()

        lines.forEach { line ->
            if (line.isBlank()) {
                moves.add(currentMovesList.toList())
                currentMovesList = mutableListOf()
            } else {
                val (opponent, own) = line.split(" ")
                val movesList = DayTwoMoves(opponent.trim().toCharArray()[0], own.trim().toCharArray()[0])
                currentMovesList.add(movesList)
            }

        }

        if (currentMovesList.isNotEmpty()) {
            moves.add(currentMovesList.toList())
        }

        return moves
    }


    private fun calculateScore(moves: DayTwoMoves): Int {
        val playerChoice = moves.player
        val opponentChoice = moves.opponent

        // Part 1
        val playerWinScore = when (playerChoice) {
            'X' -> if (opponentChoice == 'C') 6 else if (opponentChoice == 'A') 3 else 0
            'Y' -> if (opponentChoice == 'A') 6 else if (opponentChoice == 'B') 3 else 0
            'Z' -> if (opponentChoice == 'B') 6 else if (opponentChoice == 'C') 3 else 0
            else -> throw IllegalArgumentException("Wrong Input, Check Hard-Coded Data")
        }
        val playScore = when (playerChoice) {
            'X' -> 1
            'Y' -> 2
            'Z' -> 3
            else -> throw IllegalArgumentException("Wrong Input, Check Hard-Coded Data")
        }

        return playerWinScore + playScore
    }

    private fun calculateScoreWithAltStrategy(moves: DayTwoMoves): Int {
        val strategy = moves.player
        val opponentChoice = moves.opponent

        val playerMove = when (strategy) {
            'X' -> if (opponentChoice == 'A') 'Z' else if (opponentChoice == 'B') 'X' else 'Y'
            'Y' -> if (opponentChoice == 'A') 'X' else if (opponentChoice == 'B') 'Y' else 'Z'
            'Z' -> if (opponentChoice == 'A') 'Y' else if (opponentChoice == 'B') 'Z' else 'X'
            else -> throw IllegalArgumentException("Wrong Input, Check Hard-Coded Data")
        }

        return calculateScore(DayTwoMoves(moves.opponent, playerMove))
    }
}