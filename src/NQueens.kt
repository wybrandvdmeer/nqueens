import java.lang.System.exit
import kotlin.math.pow

fun main() {
    val nQueens = NQueens(5);
    nQueens.algo();
}

class NQueens(val size: Int) {

    fun algo() {
        for(y in 0 until size) {
            var fieldNo = toFieldNo(0, y);
            var bitmask : Long = queen(fieldNo);

            var notCompleted = false;
            for(attempt in 0 until size - 1) {
                fieldNo = nextQueen(bitmask, fieldNo);
                if(fieldNo == -1) {
                    notCompleted = true;
                    break;
                }
                bitmask = bitmask or queen(fieldNo);
            }

            if(!notCompleted) {
                System.out.printf("Found combination.\n")
            }
        }
    }
    fun nextQueen(bitmask : Long, fieldNo : Int) : Int {
        val x = fieldNo%size;

        for(nextY in 0 until size) {
            val nxtFieldNo = toFieldNo(x + 1, nextY);

            if(nxtFieldNo >= size * size) {
                return -1;
            }

            if(!capture(bitmask, nxtFieldNo)) {
                return nxtFieldNo;
            }
        }
        return -1;
    }

    fun toFieldNo(x : Int, y : Int) : Int {
        return y * size + x;
    }

    fun queen(fieldNo: Int): Long {
        return bishop(fieldNo) or rook(fieldNo);
    }

    fun bishop(fieldNo: Int): Long {
        val y = fieldNo/size;

        var bitmask : Long = 0;

        for(idx in 0 until size) {
            // Forward diagonal.
            var bit = fieldNo + (idx - y) * (size+1);
            if(bit >= idx * size && bit < idx * size + size) {
                bitmask = bitmask or 2.0.pow(bit).toLong();
            }

            // Backward diagonal.
            bit = fieldNo + (idx - y)  * (size-1);
            if(bit >= idx * size && bit < idx * size + size) {
                bitmask = bitmask or 2.0.pow(bit).toLong();
            }
        }
        return bitmask;
    }

    fun rook(fieldNo: Int): Long {
        val x = fieldNo%size;
        val y = fieldNo/size;

        var bitmask : Long = 0;
        var shiftValue : Long = 0;

        for(idx in 0 until size) {
            shiftValue += 2.0.pow(idx).toLong();
        }

        bitmask = bitmask or (shiftValue shl (y*size));

        for(idx in 0 until size) {
            bitmask = bitmask or 2.0.pow(x + idx * size).toLong();
        }

        return bitmask;
    }

    fun capture(bitmask : Long, fieldNo: Int) : Boolean {
        return (bitmask and (1L shl fieldNo)) shr fieldNo == 1L;
    }

    fun print(bitmask: Long) {
        System.out.printf("\n");
        var y = 0;
        for(idx in 0 until size * size) {
            val dot : Boolean = capture(bitmask, idx);
            if(y != idx/size) {
                System.out.printf("\n");
                y = idx/size;
            }
            System.out.printf("%c ", if(dot) 'x' else '.');
        }
        System.out.printf("\n");
    }
}
