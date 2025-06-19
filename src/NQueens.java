import java.util.List;

public class NQueens {
    public static void main(String[] args) {
        int n = 6;
        int bitmask = queen(15, n);

        print(bitmask, n);
    }

    private static int queen(int fieldNo, int n) {
        return bishop(fieldNo, n) | rook(fieldNo, n);
    }

    private static int bishop(int fieldNo, int n) {
        int x = (fieldNo%n);
        int y = fieldNo/n;

        int bitmask = 0;

        for(int idx=0; idx < n; idx++) {
            if(idx < y) {
                int yy = fieldNo - (y - idx)  * (n+1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask |= ((int) Math.pow(2, yy));
                }
            } else {
                int yy = fieldNo + (idx - y)  * (n+1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask |= ((int) Math.pow(2, yy));
                }
            }
        }

        for(int idx=0; idx < n; idx++) {
            if(idx < y) {
                int yy = fieldNo - (y - idx)  * (n-1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask |= ((int) Math.pow(2, yy));
                }
            } else {
                int yy = fieldNo + (idx - y)  * (n-1);
                if(yy >= idx * n && yy < idx * n + n) {
                    bitmask |= ((int) Math.pow(2, yy));
                }
            }
        }

        return bitmask;
    }

    private static int rook(int fieldNo, int n) {
        int x = (fieldNo%n);
        int y = fieldNo/n;

        int bitmask = 0;

        int shiftValue = 0;
        for(int idx=0; idx < n; idx++) {
            shiftValue += (int)Math.pow(2, idx);
        }

        bitmask |= (shiftValue << (y*n));

        for(int idx=0; idx < n; idx++) {
            bitmask |= ((int)Math.pow(2, x + idx * n));
        }

        print(bitmask, n);

        return bitmask;
    }

    private static void print(int bitmask, int size) {
        System.out.printf("\n");
        int y = 0;
        for(int idx=0; idx < size * size; idx++) {
            int value = (bitmask & (1 << idx)) >> idx;
            if(y != idx/size) {
                System.out.printf("\n");
                y = idx/size;
            }
            System.out.printf("%c ", value == 1 ? 'x' : '.');
        }
        System.out.printf("\n");
    }
}