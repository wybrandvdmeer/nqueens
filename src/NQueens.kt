import kotlin.math.pow

fun main() {
    val nQueens = NQueens();
    nQueens.queen(4, 4);
}

class NQueens {

    fun queen(fieldNo: Int, n: Int): Long {
        return bishop(fieldNo, n) or rook(fieldNo, n);
    }

    fun bishop(fieldNo: Int, n: Int): Long {
        val y = fieldNo/n;

        var bitmask : Long = 0;

        for(idx in 0..n) {
            if(idx < y) {
                val yy = fieldNo - (y - idx)  * (n+1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(yy).toLong();
                }
            } else {
                val yy = fieldNo + (idx - y)  * (n+1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(yy).toLong();
                }
            }
        }

        for(idx in 0..n) {
            if(idx < y) {
                val yy = fieldNo - (y - idx)  * (n-1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(yy).toLong();
                }
            } else {
                val yy = fieldNo + (idx - y)  * (n-1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask = bitmask or 2.0.pow(yy).toLong();
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


        for(idx in 0..n) {
            shiftValue += 2.0.pow(idx).toLong();
        }

        bitmask = bitmask or (shiftValue shl (y*n));

        for(idx in 0..n) {
            bitmask = bitmask or 2.0.pow(x + idx * n).toLong();
        }

        print(bitmask, n);

        return bitmask;
    }

    fun print(bitmask: Long, size: Int) {
        System.out.printf("\n");
        var y = 0;
        for(idx in 0..size * size) {
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