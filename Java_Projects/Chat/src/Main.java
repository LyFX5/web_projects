import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        /*
        Database.setUP();
        Server server = new Server();
        server.setUP(args);
        */

        int[] a1 = new int[] {21, 23, 24, 40, 75, 76, 78, 77, 900, 2100, 2200, 2300, 2400, 2500};
        int[] a2 = new int[] {10, 11, 41, 50, 65, 86, 98, 101, 190, 1100, 1200, 3000, 5000};
        int[] a4 = new int[a1.length + a2.length];

        for (int p=0; p < a1.length; p++){
            a4[p] = a1[p];
        }
        for (int p=0; p < a2.length; p++){
            a4[p+a1.length] = a2[p];
        }

        for (int p=0; p < a4.length; p++){
            System.out.print(a4[p] + " ");
        }
        SortUnsorted(a4,0,a4.length-1);
        System.out.println();
        for (int p=0; p < a4.length; p++){
            System.out.print(a4[p] + " ");
        }
    }

    static void Merge(int[] A,int p,int q,int r){
        int[] b = new int[A.length];

        for (int i = p; i <= r; i++) {
            b[i] = A[i];
        }

        int i = p, j = q + 1;
        for (int k = p; k <= r; k++) {

            if (i > q) {
                A[k] = b[j];
                j++;
            } else if (j > r) {
                A[k] = b[i];
                i++;
            } else if (b[j] < b[i]) {
                A[k] = b[j];
                j++;
            } else {
                A[k] = b[i];
                i++;
            }
        }
    }

    static void SortUnsorted(int[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;
        SortUnsorted(a, lo, mid);
        SortUnsorted(a, mid + 1, hi);

        Merge(a,lo,mid,hi);
    }
}

//10 11 21 23 24 40 41 50 65 75 76 77 78 86 98 101 190 900 1100 1200 2100 2200 2300 2400 2500 3000 5000
//10 11 21 23 24 40 41 50 65 75 76 77 78 86 98 101 190 900 1100 1200 2100 2200 2300 2400 2500 3000 5000
//10 11 21 23 24 40 41 50 65 75 76 77 78 86 98 101 190 900 1100 1200 2100 2200 2300 2400 2500 3000 5000