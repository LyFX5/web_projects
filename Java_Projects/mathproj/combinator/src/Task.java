import java.io.*;

public class Task { // input form is: "k n"
    public static void main(String[] args){
        int[] input = readTask();
        int k = input[0];
        int n = input[1];
        int[][] res = countOfcombinations(k,n);
        printMatrix(res);
    }

    static int[][] countOfcombinations(int k, int n) {
        int[][] combinations = new int[combnum(n, k)][k]; // сочетания по k из n
        //todo
        return combinations;
    }

    static int combnum(int n, int k){
        if ((k == n) || k == 0) {
            return 1;
        }else {
            return (combnum((n - 1),(k - 1)) + combnum((n - 1),(k)));
        }
    }

    static int[] readTask(){
        int[] input = new int[2];
        String[] inputs = new String[2];
        try {
            inputs = new BufferedReader(new InputStreamReader(System.in)).readLine().split(" ");
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        input[0] = Integer.parseInt(inputs[0]);
        input[1] = Integer.parseInt(inputs[1]);
        return input;
    }

    static void printMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println();
        }
    }
}
