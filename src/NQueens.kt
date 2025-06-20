import kotlin.math.pow
import kotlin.streams.toList

fun main() {
    val nQueens = NQueens(8)
    val list = nQueens.nQueens()
    require(list.size == 92)
}

class NQueens(val size: Int) {
    val combinations : MutableList<List<Int>> = mutableListOf()

    fun nQueens() : List<List<String>> {
        combinations.clear()

        for(y in 0 until size) {
            val foundQueens = mutableListOf<Int>()
            val fieldNo = toFieldNo(0, y)
            nQueens(foundQueens, queen(fieldNo), fieldNo)
        }

        /* Convert to neetcode requirement. */
        val result = mutableListOf<List<String>>()

        combinations.forEach{
            q ->
            run {
                result.add((q.stream().map { field ->
                    val dotString = List(size) { "." }.joinToString("")
                    dotString.substring(0, field/size) + "Q" + dotString.substring(field/size + 1).reversed()
                }.toList()))
            }
        }

        return result.toList()
    }

    fun nQueens(foundQueens: MutableList<Int>, bitmask: Long, fieldNo : Int) {
        foundQueens.add(fieldNo)
        val x = fieldNo%size + 1
        if(x >= size) {
            combinations.add(foundQueens.toList())
            foundQueens.remove(fieldNo)
            return
        }

        for(y in 0 until size) {
            val nxtFieldNo = toFieldNo(x, y)
            if(!capture(bitmask, nxtFieldNo)) {
                nQueens(foundQueens, bitmask or queen(nxtFieldNo), nxtFieldNo)
            }
        }
        foundQueens.remove(fieldNo)
    }

    fun toFieldNo(x : Int, y : Int) : Int {
        return y * size + x
    }

    fun queen(fieldNo: Int): Long {
        return bishop(fieldNo) or rook(fieldNo)
    }

    fun bishop(fieldNo: Int): Long {
        val y = fieldNo/size

        var bitmask : Long = 0

        for(idx in 0 until size) {
            // Forward diagonal.
            var bit = fieldNo + (idx - y) * (size+1)
            if(bit >= idx * size && bit < idx * size + size) {
                bitmask = bitmask or (1L shl bit)
            }

            // Backward diagonal.
            bit = fieldNo + (idx - y)  * (size-1)
            if(bit >= idx * size && bit < idx * size + size) {
                bitmask = bitmask or (1L shl bit)
            }
        }
        return bitmask
    }

    fun rook(fieldNo: Int): Long {
        val x = fieldNo%size
        val y = fieldNo/size

        var bitmask : Long = 0
        var shiftValue : Long = 0

        for(idx in 0 until size) {
            shiftValue += 2.0.pow(idx).toLong()
        }

        bitmask = bitmask or (shiftValue shl (y*size))

        for(idx in 0 until size) {
            bitmask = bitmask or (1L shl x + idx * size)
        }

        return bitmask
    }

    fun capture(bitmask : Long, fieldNo: Int) : Boolean {
        return (bitmask and (1L shl fieldNo)) ushr fieldNo == 1L
    }

    fun print(bitmask: Long) {
        System.out.printf("\n")
        var y = 0
        for(idx in 0 until size * size) {
            val dot : Boolean = capture(bitmask, idx)
            if(y != idx/size) {
                System.out.printf("\n")
                y = idx/size
            }
            System.out.printf("%c ", if(dot) 'x' else '.')
        }
        System.out.printf("\n")
    }
}
