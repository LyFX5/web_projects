import java.io.*;

public class GausMethod {
    public static void main(String[] args){
        double[][] matrix = readMatrix();
        double[][] Ab = foo(matrix);
        double[][] A = getMatrixA(Ab);
        double[] b = getArrayb(Ab);
        double[] solution = solve(A,b);
        for (int i = 0; i < solution.length; i++){
            System.out.print(solution[i]+" ");
        }
    }
    static double[][] readMatrix(){ // ultimate
        System.out.println();
        BufferedReader bure = new BufferedReader(new InputStreamReader(System.in));
        int m = 0; // equation num
        int n = 0; // variable num
        String str = "";
        try {
            str = bure.readLine();
        }catch (IOException io){
            io.printStackTrace();
        }
        String[] strs = str.split(" ");
        m = Integer.parseInt(strs[0]);
        n = Integer.parseInt(strs[1]) + 1;
        double[][] matrix = new double[m][n];

        for (int i = 0; i < m; i++){
            try {
                strs = bure.readLine().split(" ");
            }catch (IOException io){io.printStackTrace();}
            for (int j = 0; j < n; j++){
                matrix[i][j] = Double.parseDouble(strs[j]);
            }
        }
        return matrix;
    }

    static double[][] foo(double[][] in) {
        if((in.length < 2) || (in[0]).length < 2) {
            return in;
        }else {
            double[][] Ab = sortRows(in);
            for (int i = 1; i < Ab.length; i++) {
                if (Ab[i][0] != 0) {
                    double[][] res = gaus(Ab[0], Ab[i]);
                    for (int j = 0; j < Ab[i].length; j++) {
                        Ab[i][j] = res[1][j];
                    }
                }
            }
            double[][] min = minor(Ab, 0, 0);
            double[][] res = new double[Ab.length][Ab[0].length];
            for (int i = 0; i < Ab.length; i++) {
                for (int j = 0; j < Ab[i].length; j++) {
                    if ((j == 0) || (i == 0)) {
                        res[i][j] = Ab[i][j];
                    }
                }
            }
            double[][] Ab2 = foo(min);
            for (int i = 1; i < Ab.length; i++) {
                for (int j = 1; j < Ab[i].length; j++) {
                    res[i][j] = Ab2[i - 1][j - 1];
                }
            }
            return res;
        }
    }

    static double[][] withoutLastRow(double[][] in){
        if (in.length < 2){
            return in;
        }else {
            double[][] out = new double[in.length - 1][in[0].length];
            for (int i = 0; i < out.length; i++) {
                for (int j = 0; j < out[i].length; j++) {
                    out[i][j] = in[i][j];
                }
            }
            return out;
        }
    }

    static double[] withoutLastElem(double[] in){
        if (in.length < 2){
            return in;
        }else {
            double[] out = new double[in.length - 1];
            for (int i = 0; i < out.length; i++){
                out[i] = in[i];
            }
            return out;
        }
    }

    static double[] solve(double[][] A, double[] b){ // for square matrix only.  A and b is from row echelon form
        int m = A.length; // == b.length == var num
        if (m != A[0].length){
            return notSquare(A,b); // A and b is from row echelon form
        }else {
            if (compareWithZero(A[m-1][m-1]) != 0) {
                double[] solutionsArray = new double[m];
                for (int i = m - 1; i >= 0; i--) {
                    double sum = 0;
                    for (int j = m - 1; j > i; j--) {
                        sum += solutionsArray[j] * A[i][j];
                    }
                    solutionsArray[i] = (b[i] - sum) / A[i][i];
                }
                System.out.println("YES");
                return solutionsArray;
            }else {
                if(compareWithZero(b[b.length - 1]) == 0){
                    double[][] Anew = withoutLastRow(A);
                    double[] bnew = withoutLastElem(b);
                    return solve(Anew,bnew);
                }else {
                    System.out.println("NO");
                    return new double[0];
                }
            }
        }
    }

    static double[][] AandbToAb(double[][] A,double[] b){
        double[][] Ab = new double[A.length][A[0].length + 1];
        for (int i = 0; i < Ab.length; i++){
            for (int j = 0; j < Ab[i].length; j++){
                if (j == Ab[0].length - 1){
                    Ab[i][j] = b[i];
                }else {
                    Ab[i][j] = A[i][j];
                }
            }
        }
        return Ab;
    }

    static double[] notSquare(double[][] A, double[] b) {
        double[][] Ab = AandbToAb(A,b);
        int rA = rank(A);
        int rAb = rank(Ab);
        int n = A[0].length;
        if (rA == rAb){
            if (rA == n) {
                double[][] squareMatrix = new double[rA][n];
                double[] Rb = new double[rA];
                for (int i = 0; i < rA; i++) {
                    for (int j = 0; j < n; j++) {
                        squareMatrix[i][j] = A[i][j];
                    }
                    Rb[i] = b[i];
                }
                return solve(squareMatrix, Rb);
            }else {
                System.out.println("INF");
                return new double[0];
            }
        }else {
            System.out.println("NO");
            return new double[0];
        }
    }

    static int rank(double[][] matrix){
        int r = 0;
        for (int i = 0; i < matrix.length; i++){
            double sum = 0;
            for (int j = 0; j < matrix[i].length; j++){
                sum += compareWithZero(matrix[i][j]);
            }
            if (sum != 0){
                r++;
            }
        }
        return r;
    }

    static double[] getArrayb(double[][] matrix){ // input is rowEchelonForm matrix A and b
        double[][] res1 = transMatrix(matrix);
        double[] b = new double[res1[res1.length - 1].length];
        for (int i = 0; i < b.length; i++){
            b[i] = res1[res1.length - 1][i];
        }
        // we get b that is array of right parts
        return b;
    }

    static double[][] transMatrix(double[][] matrix){
        double[][] transMatrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix[0].length; i++){
            for (int j = 0; j < matrix.length; j++){
                transMatrix[i][j] = matrix[j][i];
            }
        }
        return transMatrix;
    }

    static double[][] getMatrixA(double[][] matrix){
        double[][] A = new double[matrix.length][matrix[0].length - 1];
        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A[i].length; j++){
                A[i][j] = matrix[i][j];
            }
        }
        // we get A tat is matrix of coefs
        return A;
    }

    static double[][] sortRows(double[][] in){
        double[][] out = new double[in.length][in[0].length];
        for (int i = 0; i < in.length; i++){
            for (int j = 0; j < in[i].length; j++){
                out[i][j] = in[i][j];
            }
        }
        int m = out.length;
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < m - 1; i++) {
                if (compareWithZero(out[i][0]) == 0) {
                    out = shuffleRows(out, i, i + 1);
                }
            }
        }
        return out;
    }

    static double[] multiplyArrayOnNum(double[] array, double num){
        double[] newArray = new double[array.length];
        for (int i = 0; i < array.length; i++){
            newArray[i] = array[i] * num;
        }
        return newArray;
    }

    static double[] addArrays(double[] x,double[] y){
        if (x.length == y.length) {
            double[] res = new double[x.length];
            for (int i = 0; i < res.length; i++){
                res[i] = x[i] + y[i];
            }
            return res;
        }else {
            System.out.println("разные длины");
            return null;
        }
    }

    static double[][] shuffleRows(double[][] matrix, int first, int second){
        double[][] resMatrix = new double[matrix.length][matrix[0].length];
        double[] firstRow = new double[matrix[first].length];
        double[] secondRow = new double[matrix[second].length];
        for (int j = 0; j < firstRow.length; j++){
            firstRow[j] = matrix[first][j];
            secondRow[j] = matrix[second][j];
        }
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                if (i == first){
                    resMatrix[i][j] = secondRow[j];
                }else {
                    if (i == second){
                        resMatrix[i][j] = firstRow[j];
                    }else {
                        resMatrix[i][j] = matrix[i][j];
                    }
                }
            }
        }
        return resMatrix;
    }

    static int compareWithZero(double num){
        if (Math.abs(num) < Math.pow(10,-14)){
            return 0;
        }else {
            return 1;
        }
    }

    static double[][] gaus(double[] firstRow, double[] secondRow){ // не равны нулю
        int l = firstRow.length; // должно быть равно и второму
        double[][] res = new double[2][l];
        double[] sec = addArrays(secondRow,multiplyArrayOnNum(firstRow,(-(secondRow[0]/firstRow[0]))));
        for (int i = 0; i < l; i++){
            res[0][i] = firstRow[i];
            res[1][i] = sec[i];
        }
        return res;
    }

    static double[][] minor(double[][] matrix, int m, int n){
        if ((m == -1) && (n == -1)){
            return matrix;
        }else {

            if ((matrix.length == 0) || (matrix[m].length == 0)) {
                return matrix;
            } else {
                double[][] matrixN = new double[matrix.length][matrix[m].length];

                for (int i = 0; i < matrixN.length; i++) {
                    for (int j = 0; j < matrixN[i].length; j++) {
                        matrixN[i][j] = matrix[i][j];
                    }
                }

                double[][] minor = new double[matrixN.length - 1][matrixN[m].length - 1];

                if (m < matrixN.length - 1) {
                    for (int i = 0; i < matrixN.length - m - 1; i++) {
                        for (int j = 0; j < matrixN[i].length; j++) {
                            matrixN[i + m][j] = matrixN[i + m + 1][j];
                        }
                    }
                }
                if (n < matrixN[0].length - 1) {
                    for (int i = 0; i < matrixN.length; i++) {
                        for (int j = 0; j < matrixN[i].length - n - 1; j++) {
                            matrixN[i][j + n] = matrixN[i][j + n + 1];
                        }
                    }
                }
                for (int i = 0; i < minor.length; i++) {
                    for (int j = 0; j < minor[i].length; j++) {
                        minor[i][j] = matrixN[i][j];
                    }
                }
                return minor;
            }
        }
    } // end of minor

}
