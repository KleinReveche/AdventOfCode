package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Solver
import com.kleinreveche.adventofcode.Utils

/** --- Day 7: No Space Left On Device --- */
class DaySeven : Solver {
    override fun solve() {
        val rootDirectory: Directory = parseData()
        val unusedSpace = 70_000_000 - rootDirectory.size
        val neededSpace = 30_000_000 - unusedSpace

        val totalDirSize = rootDirectory.find { it.size <= 100_000 }.sumOf { it.size }
        val totalDirSizeToBeDeleted = rootDirectory.find { it.size >= neededSpace }.minBy { it.size }.size

        println(" --- 2022 Day 7: No Space Left On Device ---\n")
        println("   The sum of directories with a total size of at most 100,000 was $totalDirSize")
        println("   The sum of the smallest directory that would free up enough space for the update was $totalDirSizeToBeDeleted\n")

    }

    override fun parseData(): Directory {
        val lines = Utils.readInput("twentytwo", "day07")!!.trim().lines()
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
        val size: Int
            get() = sizeOfFiles + subDirs.values.sumOf { it.size }

        fun addFile(size: Int) {
            sizeOfFiles += size
        }

        fun traverse(dir: String): Directory = subDirs.getOrPut(dir) { Directory(dir) }

        fun find(predicate: (Directory) -> Boolean): List<Directory> =
            subDirs.values.filter(predicate) + subDirs.values.flatMap { it.find(predicate) }
    }

    override fun parseData(input: Int): Any {
        return 0
    }
}