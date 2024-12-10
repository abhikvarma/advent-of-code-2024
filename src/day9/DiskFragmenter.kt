package day9

import java.io.File
import java.util.PriorityQueue

class DiskFragmenter(file: File) {
    private val disk: List<Long?> = file.readText().windowed(2, 2, true)
        .withIndex()
        .flatMap { (idx, value) ->
            List(value.first().digitToInt()) { _ -> idx.toLong() } +
            List(value.getOrElse(1) {_ -> '0'}.digitToInt()) { null }
        }

    private data class Block(val start: Int, val length: Int, val fileId: Long? = null) {
        fun checksum(index: Int = start): Long = (0..<length).sumOf {
            (index + it) * (fileId ?: 0L)
        }
    }

    private fun findAllBlocks(): List<Block> = buildList {
        var blockStart = -1
        var previousValue: Long? = -1L
        disk.withIndex().forEach { (idx, value) ->
            if (previousValue == -1L) {
                blockStart = idx
                previousValue = value
            } else if (previousValue != value) {
                add(Block(blockStart, idx - blockStart, previousValue))
                blockStart = idx
                previousValue = value
            }
        }
        if (blockStart != -1) add(Block(blockStart, disk.size - blockStart, previousValue))
    }

    private fun MutableMap<Int, PriorityQueue<Int>>.findSpace(block: Block): Int = (block.length..9).mapNotNull { trySize ->
        if (this[trySize]?.isNotEmpty() == true) trySize to this.getValue(trySize).first()
        else null
    }.sortedBy { it.second }.filter { it.second < block.start }.firstNotNullOfOrNull { (blockSize, startAt) ->
        this[blockSize]?.poll()
        if (blockSize != block.length) {
            val diff = blockSize - block.length
            computeIfAbsent(diff) {_ -> PriorityQueue()}.add(startAt + block.length)
        }
        startAt
    } ?: block.start

    fun getChecksum(): Long {
        val emptyBlocks = disk.indices.filter { disk[it] == null }.toMutableList()

        return disk.withIndex().reversed().sumOf { (idx, value) ->
            if (value != null) {
                value * (emptyBlocks.removeFirstOrNull() ?: idx)
            } else {
                emptyBlocks.removeLastOrNull()
                0
            }
        }
    }

    fun getChecksum2(): Long {
        val blocks = findAllBlocks()
        val freeSpace: MutableMap<Int, PriorityQueue<Int>> = blocks
            .filter { it.fileId == null }
            .groupBy({ it.length }, { it.start })
            .mapValues { (_, v) -> PriorityQueue(v) }
            .toMutableMap()

        return blocks.filterNot { it.fileId == null }.reversed().sumOf { it.checksum(freeSpace.findSpace(it)) }
    }
}