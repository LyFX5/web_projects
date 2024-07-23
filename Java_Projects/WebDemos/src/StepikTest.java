import java.util.Scanner;
import java.lang.Math;

public class StepikTest {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        float g = (float) n*8 + 1;
        int k1 = (int)((Math.pow(g,0.5) - 1)/2);
        g = (float) n*16 + 1;
        int k2 = (int)((Math.pow(g,0.5) - 1)/2);
        int k = 0;
        for (k = k1; k <= k2; k++){
            if(k*(k+1) % 2 == 0){
                break;
            }
        }
        if (k != 0){
            System.out.println(k);
            int i = 1;
            while (i <= k){
                if (i != k*(k+1)/2 - n) {
                    System.out.print(i);
                    System.out.print(" ");

                }
                i ++;
            }
        }else{
            System.out.println("zerro k");
        }
    }
}
