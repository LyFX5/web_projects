package Gaus;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Matrix {

    public static void main(String[] args){
                        String[][] A = readStringMatrix();
                        String[][] B = readStringMatrix();
                        String[][] C = matrixStringMultiply(A,B);
                        printStringMatrix(C);
                    } // end of main

                    static String[][] readStringMatrix(){
                        String[][] matrix = null;
                        int m = 0;
                        int n = 0;
                        System.out.println("write Matrix");
                        BufferedReader bure = new BufferedReader(new InputStreamReader(System.in));
                        try{
                            String mn = bure.readLine();
                            String[] mns = mn.split(" ");
                            m = Integer.parseInt(mns[0]);
                            n = Integer.parseInt(mns[1]);
                            matrix = new String[m][n];
                            for (int i = 0; i < m; i++){
                                String string = bure.readLine();
                                String[] strings = string.split(" ");
                                for (int j = 0; j < n; j++){
                                    matrix[i][j] = strings[j];
                                }
            }
        }catch (IOException io){
            io.printStackTrace();
        }
        return matrix;
    }

    public static String[][] matrixStringMultiply(String[][] A, String[][] B){
        int ma = A.length; // строки
        int na = A[0].length; // столбцы
        int mb = B.length; // строки
        int nb = B[0].length; // столбцы

        if (na != mb){
            System.out.println("нельзя умножить");
            return null;
        }else {
            String[][] C = new String[ma][nb];
            for (int i = 0; i < nb; i++){
                for (int j = 0; j < na; j++){
                    C[i][j] = "";
                }
            }
            for (int i = 0; i < ma; i++){
                for (int j = 0; j < nb; j++){
                    for (int k = 0; k < na; k++){
                        C[i][j] += "+" + A[i][k] + B[k][j];
                    }
                }
            }

            return C;
        }
    }

    public static ArrayList<String> readMatrix(){
        System.out.println();
        BufferedReader bure = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> strings = new ArrayList<String>();
        try {
            String string = "";
            while (!(string = bure.readLine()).equals("end")){
                for (String string1 : strings){
                    int d1 = string.split(" ").length;
                    int d2 = string1.split(" ").length;
                    if (d1 != d2){
                        System.out.println("ошибка ввода");
                        System.exit(1);
                    }
                }
                strings.add(string);
            }
        }catch (IOException io){io.printStackTrace();}

        return strings;
    }

    static double[][] minor(double[][] matrix, int m, int n){ // я ахуел пока собрал этот метод
        // на следующий день оказалось что не правильно и через ещё часа 4 получилось

        if ((matrix.length == 0) || (matrix[m].length == 0)){
            return matrix;
        }else {
            double[][] matrixN = new double[matrix.length][matrix[m].length];

            for (int i = 0; i < matrixN.length; i++){
                for (int j = 0; j < matrixN[i].length; j++){
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
                for (int i = 0; i < matrixN.length; i++){
                    for (int j = 0; j < matrixN[i].length - n - 1; j++) {
                        matrixN[i][j + n] = matrixN[i][j + n + 1];
                    }
                }
            }
            for (int i = 0; i < minor.length; i++){
                for (int j = 0; j < minor[i].length; j++){
                    minor[i][j] = matrixN[i][j];
                }
            }
            return minor;
        }
    } // end of minor

    static double[][] oppositeMatrix(double[][] matrix){
        if (matrix.length == matrix[0].length){
            double det = det(matrix);
            if (Double.compare(det,0.0) != 0){ // det != 0
                double opdet = 1/det;
                double[][] algCompl = algCompl(matrix);
                algCompl = multiplyOnNum(algCompl,opdet);
                double[][] opposite = transMatrix(algCompl);
                return opposite;
            }else {
                System.out.println("det == 0");
                return null;
            }
        }else {
            System.out.println("not square");
            return null;
        }
    }

    static double[][] multiplyMatrixOnVector(double[][] matrix, double[] vector){
        double[][] vec = new double[vector.length][1];
        for (int i = 0; i < vec.length; i++){
            vec[i][0] = vector[i];
        }
        double[][] res = matrixMultiply(matrix,vec);
        return res;
    }

    static double innerProduct(double[] first,double[] second){
        int m = first.length;
        if (m != second.length){
            return 0;
        }else {
            double[] arres = new double[m];
            for (int i = 0; i < first.length; i++) {
                arres[i] = first[i] * second[i];
            }
            return sumOfArray(arres);
        }
    }

    static double sumOfArray(double[] arry){
        double sum = 0;
        for (int i = 0; i < arry.length; i++){
            sum += arry[i];
        }
        return sum;
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

    static double[][] shuffleColumns(double[][] matrix, int first, int second){
        double[][] A = transMatrix(matrix);
        double[][] B = shuffleRows(A,first,second);
        return transMatrix(B);
    }

    static double[][] readMatrix2(){ // ultimate
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

    static double det(double[][] matrix){ // определитель
        if (matrix.length == 1){
            return matrix[0][0];
        }else {
            double d = 0;
            for (int i = 0; i < matrix.length; i++) {
                d = d + matrix[0][i] * Math.pow((-1),(i)) * det(minor(matrix, 0, i));
            }
            return d;
        }
    }

    static double[][] multiplyOnNum(double[][] matrix, double num){
        double[][] newMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                newMatrix[i][j] = matrix[i][j] * num;
            }
        }
        return newMatrix;
    }

    static double[][] transMatrix(double[][] matrix){
        double[][] transMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                transMatrix[i][j] = matrix[j][i];
            }
        }
        return transMatrix;
    }

    static double[][] algCompl(double[][] matrix){ // algebraic complement
        double[][] algCompl = new double[matrix.length][matrix.length];
        for (int i = 0; i < algCompl.length; i++){
            for (int j = 0; j < algCompl.length; j++){
                algCompl[i][j] = Math.pow((-1),(i + j)) * det(minor(matrix,i,j));
            }
        }
        return algCompl;
    }

    static double[][] matrixMultiply(double[][] A, double[][] B){
        int ma = A.length; // строки
        int na = A[0].length; // столбцы
        int mb = B.length; // строки
        int nb = B[0].length; // столбцы

        if (na != mb){
            System.out.println("нельзя умножить");
            return null;
        }else {
            double[][] C = new double[ma][nb];
            for (int i = 0; i < ma; i++){
                for (int j = 0; j < nb; j++){
                    for (int k = 0; k < na; k++){
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }

            return C;
        }
    }

    static void printStringMatrix(String[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    static void printlnMatrix(double[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    static void printMatrix(double[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + "  ");
            }
        }
    }
}
