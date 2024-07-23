package Gaus;/*Gaus.Matrix.printlnMatrix(ROWECHELON);
           System.out.println(Gaus.Matrix.det(ROWECHELON));
           System.out.println(Gaus.Matrix.det(IN));
           for (int i = 0; i < b.length; i++) {
               System.out.println(b[i]);
           }
          /* double[] solution = solve(A, b);

           for (int i = 0; i < solution.length; i++) {
               if (Double.compare(solution[i],0.0) == 0){
                   solution[i] = 0;
               }
               System.out.print(solution[i] + " ");
           }*/

/*
5 3
0 2 -1 -4
1 -1 5 3
2 1 -1 0
3 2 3 -1
3 4 2 -5

ANSWER:
YES
1 -2 0*/

/*          int rankAp = rank(ROWECHELON);
            int rankA = rank(A);
            int n = A[0].length;
            System.out.println(rankA+" "+rankAp+" "+n);
            Gaus.Matrix.printlnMatrix(IN);
            Gaus.Matrix.printlnMatrix(ROWECHELON);
            System.out.println();
            Gaus.Matrix.printlnMatrix(A);
            */

import java.io.*;

public class Mainn {

    public static void main(String[] args) {
        double[][] IN = readMatrix();
        if (IN.length == 1){
            if (IN[0].length == 2){
                if (Double.compare(IN[0][0],0.0) != 0) {
                    System.out.println("YES");
                    System.out.println(IN[0][1] / IN[0][0]);
                }else {
                    if (Double.compare(IN[0][1],0.0) != 0){
                        System.out.println("NO");
                    }else {
                        System.out.println("INF");
                    }
                }
            }
        }else {
            double[][] ROWECHELON = rowEchelonForm(IN);

            double[] b = getArrayb(ROWECHELON);
            double[][] A = getMatrixA(ROWECHELON);

            int rankAp = rank(ROWECHELON);
            int rankA = rank(A);
            int n = A[0].length;
            if ((rankA == rankAp)){ // совместна  if ((rankA == rankAp) && rankA < n){ // INF  && (rankA == n)
                double[] solution = solve(A, b);
                for (int i = 0; i < solution.length; i++) {
                    if (compareWithZero(solution[i]) == 0){
                        solution[i] = 0;
                    }
                    System.out.print(solution[i] + " ");
                }
            }
            if (rankA != rankAp){ // несовместна
                System.out.println("NO");
            }
        }
    } // end of main

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

    static double[] solve(double[][] A, double[] b){ // for square matrix only  A and b is from row echelon form
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
                    System.out.println("INF");
                    return new double[0];
                }else {
                    System.out.println("NO"); // hear
                    return new double[0];
                }
            }
        }
    }

    static double[] notSquare(double[][] A, double[] b) { // рангиравны но матрица не квадратная
        //if ((rankA == rankAp)){ // совместна
        // if ((rankA == rankAp) && rankA < n){ // INF  && (rankA == n)
        int r = rank(A);
        int m = A.length;
        int n = A[0].length;
        if (r == n) {
            double[][] squareMatrix = new double[r][n];
            double[] Rb = new double[r];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < n; j++) {
                    squareMatrix[i][j] = A[i][j];
                }
                Rb[i] = b[i];
            }
            return solve(squareMatrix, Rb);
        }
        else {
            System.out.println("INF");
            return new double[0];
        }
    }

    static int compareWithZero(double num){
        if (Math.abs(num) < Math.pow(10,-14)){
            return 0;
        }else {
            return 1;
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

    static double[] withoutFirstElement(double[] Row){
        int n = Row.length;
        double[] res = new double[n - 1];
            for (int j = 0; j < n - 1; j++) {
                res[j] = Row[j + 1];
            }
        return res;
    }

    static double[][] gausMethod(double[] firstRow, double[] secondRow){
        int l = firstRow.length;
        if (l != secondRow.length){
            return null;
        }else {
            double[][] rows = new double[2][l];
            if (compareWithZero(secondRow[0]) == 0) {
                /*if (compareWithZero(firstRow[0]) ==0){
                    if (l == 1){
                        rows[0][0] = 0;
                        rows[1][0] = 0;
                    }else {
                        rows[0][0] = 0;
                        rows[1][0] = 0;
                        double[][] res = gausMethod(withoutFirstElement(firstRow),withoutFirstElement(secondRow));
                        for (int i = 0; i < 2; i++){
                            for (int j = 1; j < l; j++){
                                rows[i][j] = res[i][j - 1];
                            }
                        }
                    }
                }else {*/
                    for (int i = 0; i < l; i++) {
                        rows[0][i] = firstRow[i];
                        rows[1][i] = secondRow[i];
                    //}
                }
            } else {
                if (compareWithZero(firstRow[0]) == 0) {
                    for (int i = 0; i < l; i++) {
                        rows[0][i] = secondRow[i];
                        rows[1][i] = firstRow[i];
                    }
                } else {
                    for (int i = 0; i < l; i++) {
                        rows[0][i] = firstRow[i];
                        rows[1][i] = addArrays(secondRow,multiplyArrayOnNum(firstRow,(-(secondRow[0]/firstRow[0]))))[i];
                    }
                }
            }
            return rows;
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

    static double[][] rowEchelonForm(double[][] matrix){ // не достаточно теста
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] resMatrix = new double[m][n];
        if (m == 2){
            return gausMethod(matrix[0],matrix[1]);
        }else {
            for (int i = 1; i < m; i++) {
                double[][] res = null;
                if (compareWithZero(matrix[0][0]) == 0){
                    matrix = shuffleRows(matrix,0,m - 1);
                    if (i != m - 1) {
                        res = gausMethod(matrix[0], matrix[i]);
                    }
                }else {
                    res = gausMethod(matrix[0], matrix[i]);
                }
                for (int j = 0; j < n; j++) {
                    resMatrix[0][j] = res[0][j];
                    resMatrix[i][j] = res[1][j];
                }
            }
            double[][] rec = rowEchelonForm(minor(resMatrix, 0, 0));
            double[][] res2Matrix = new double[m][n];
            for (int i = 0; i < resMatrix.length; i++) {
                for (int j = 0; j < resMatrix[0].length; j++) {
                    if ((j == 0) || (i == 0)) {
                        res2Matrix[i][j] = resMatrix[i][j];
                    } else {
                        res2Matrix[i][j] = rec[i - 1][j - 1];
                    }
                }
            }
            return res2Matrix;
        }
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
