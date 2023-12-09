package com.kleinreveche.adventofcode.twentytwo.day07

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year

@Problem(year = Year.TwentyTwo, day = "Day07", name = "No Space Left On Device")
class Solution(private val input: String) : ISolution {
    private val rootDirectory: Directory = parseData()
    private val unusedSpace = 70_000_000 - rootDirectory.size
    private val neededSpace = 30_000_000 - unusedSpace

    override fun partOne(): Any {
        val totalDirSize = rootDirectory.find { it.size <= 100_000 }.sumOf { it.size }
        return "The sum of directories with a total size of at most 100,000 was $totalDirSize"
    }

    override fun partTwo(): Any {
        val totalDirSizeToBeDeleted = rootDirectory.find { it.size >= neededSpace }.minBy { it.size }.size
        return "The sum of the smallest directory that would free up enough space for the update was $totalDirSizeToBeDeleted"
    }

    private fun parseData(): Directory {
        val lines = input.trim().lines()
        val directoryCallStack = ArrayDeque<Directory>().apply { add(Directory("/")) }

        lines.forEach { item ->
            when {
                item == "$ ls" -> {} // Do Nothing
                item.startsWith("dir") -> {} // Do Nothing
                item == "$ cd /" -> directoryCallStack.removeIf { it.name != "/" }
                item == "$ cd .." -> directoryCallStack.removeFirst()
                item.startsWith("$ cd") -> {
                    val name = item.substringAfterLast(" ")
                    directoryCallStack.addFirst(directoryCallStack.first().traverse(name))
                }

                else -> {
                    val size = item.substringBefore(" ").toInt()
                    directoryCallStack.first().addFile(size)
                }
            }
        }

        return directoryCallStack.last()
    }


    class Directory(val name: String) {
        private val subDirs: MutableMap<String, Directory> = mutableMapOf()
        private var sizeOfFiles: Int = 0
        val size: Int get() = sizeOfFiles + subDirs.values.sumOf { it.size }

        fun addFile(size: Int) {
            sizeOfFiles += size
        }

        fun traverse(dir: String): Directory = subDirs.getOrPut(dir) { Directory(dir) }

        fun find(predicate: (Directory) -> Boolean): List<Directory> =
            subDirs.values.filter(predicate) + subDirs.values.flatMap { it.find(predicate) }
    }
}