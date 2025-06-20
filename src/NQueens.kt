import kotlin.math.pow
import kotlin.streams.toList

fun main() {
    val nQueens = NQueens(8)
    val list = nQueens.nQueens()
}

class NQueens(val size: Int) {

    val combinations : MutableList<List<Int>> = mutableListOf()

    fun nQueens() : List<List<String>> {
        combinations.clear()

        for(y in 0 until size) {
            val foundQueens = mutableListOf<Int>()
            nQueens(foundQueens, queen(toFieldNo(0, y)), toFieldNo(0, y))
        }

        /* Convert to neetcode requirement. */
        val result = mutableListOf<List<String>>()

        combinations.forEach{
            q ->
            run {
                result.add((q.stream().map { field ->
                    "....".substring(0, field/size) + "Q" + "....".substring(field/size + 1).reversed()
                }.toList()))
            }
        }

        return result.toList()
    }

    fun nQueens(foundQueens: MutableList<Int>, bitmask: Long, fieldNo : Int) {
        val x = fieldNo%size + 1
        if(x >= size) {
            foundQueens.add(fieldNo)
            combinations.add(foundQueens)
            return
        }

        for(y in 0 until size) {
            val nxtFieldNo = toFieldNo(x, y)
            if(!capture(bitmask, nxtFieldNo)) {
                foundQueens.add(fieldNo)
                nQueens(foundQueens, bitmask or queen(nxtFieldNo), nxtFieldNo)
            }
        }
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
                bitmask = bitmask or 2.0.pow(bit).toLong()
            }

            // Backward diagonal.
            bit = fieldNo + (idx - y)  * (size-1)
            if(bit >= idx * size && bit < idx * size + size) {
                bitmask = bitmask or 2.0.pow(bit).toLong()
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
            bitmask = bitmask or 2.0.pow(x + idx * size).toLong()
        }

        return bitmask
    }

    fun capture(bitmask : Long, fieldNo: Int) : Boolean {
        return (bitmask and (1L shl fieldNo)) shr fieldNo == 1L
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
