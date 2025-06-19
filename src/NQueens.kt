import java.lang.System.exit
import kotlin.math.pow

fun main() {
    val size = 8;
    val nQueens = NQueens();
    var bitmask = nQueens.queen(10, size);
    nQueens.print(bitmask, size);
}

class NQueens {

    fun queen(fieldNo: Int, n: Int): Long {
        return bishop(fieldNo, n) or rook(fieldNo, n);
    }

    fun bishop(fieldNo: Int, n: Int): Long {
        val y = fieldNo/n;

        var bitmask : Long = 0;

        for(idx in 0 until n) {
            if(idx < y) {
                var bit = fieldNo - (y - idx) * (n+1);
                if(bit >= idx * n && bit < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(bit).toLong();
                }
                bit = fieldNo - (y - idx)  * (n-1);
                if(bit >= idx * n && bit < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(bit).toLong();
                }
            } else {
                var bit = fieldNo + (idx - y)  * (n+1);
                if(bit >= idx * n && bit < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(bit).toLong();
                }
                bit = fieldNo + (idx - y)  * (n-1);
                if(bit >= idx * n && bit < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(bit).toLong();
                }
            }
        }
        return bitmask;
    }

    fun rook(fieldNo: Int, n: Int): Long {
        val x = (fieldNo%n);
        val y = fieldNo/n;

        var bitmask : Long = 0;
        var shiftValue : Long = 0;

        for(idx in 0 until n) {
            shiftValue += 2.0.pow(idx).toLong();
        }

        bitmask = bitmask or (shiftValue shl (y*n));

        for(idx in 0 until n) {
            bitmask = bitmask or 2.0.pow(x + idx * n).toLong();
        }

        return bitmask;
    }

    fun print(bitmask: Long, size: Int) {
        System.out.printf("\n");
        var y = 0;
        for(idx in 0 until size * size) {
            val value : Long = (bitmask and (1L shl idx)) shr idx;
            if(y != idx/size) {
                System.out.printf("\n");
                y = idx/size;
            }
            System.out.printf("%c ", if(value == 1L) 'x' else '.');
        }
        System.out.printf("\n");
    }
}