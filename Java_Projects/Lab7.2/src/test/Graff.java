package test;

import java.util.ArrayList;
import java.util.Stack;

public class Graff {

    int[][] matrix = new int[][]{{0,1,0,0,1,1,1},
                                 {1,0,1,1,0,0,0},
                                 {0,1,0,1,0,1,0},
                                 {0,1,1,0,1,1,0},
                                 {1,0,0,1,0,1,1},
                                 {1,0,1,1,1,0,2},
                                 {1,0,0,0,1,2,0}};


    public static void main(String[] args){
        Graff g = new Graff();
        System.out.println(g.eiler(g.matrix));
    }

     ArrayList<Integer> eiler(int[][] matrix){ // если есть две нечётные вершины

        int[][] newMatrix = new int[matrix.length][matrix.length]; // копируем матрицу
         // что бы не изменять аргумент
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        int[] stepeni = new int[matrix.length]; // степени вершин

        for (int i = 0; i < newMatrix.length; i++){ // определяем степени вершин
            int count = 0;
            for (int j = 0; j < newMatrix[i].length; j++){
                count += newMatrix[i][j];
            }
            stepeni[i] = count;
        }

        Stack<Integer> stack = new Stack(); // заводим стекк

        ArrayList<Integer> answer = new ArrayList(); // заводим коллекцию для ответа

         int nechver = 0;

         for (int i = 0; i < stepeni.length; i++){
             if(stepeni[i] % 2 != 0){
                 nechver = stepeni[i];
                 break;
             }
         }

        stack.push(nechver); // кладём в стекк нечётную вершину
        while (!stack.empty()){ // пока стек не пустой делаем следующее
            if (stepeni[stack.peek()] == 0){// если степень головы стека нулевая
                answer.add(stack.pop()); // достаём её из стека и кладём в ответ
            }else { // иначе
                for (int j = 0; j < newMatrix[0].length; j++){ // ищем смежную с ней вершину
                    if (newMatrix[stack.peek()][j] > 0){
                        // переходим к ней и удаляем ребро
                        newMatrix[stack.peek()][j]--;
                        newMatrix[j][stack.peek()]--;
                        // степени этих вершин дикриментируем
                        stepeni[stack.peek()]--;
                        stepeni[j]--;
                        stack.push(j); // кладём в голову стека вершину к которой перешли
                        break;
                    }
                }

            }
        }
        return answer; // возвращаем эйлеров путь
    }
}
