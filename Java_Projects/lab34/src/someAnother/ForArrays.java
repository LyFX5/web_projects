package someAnother;

public class ForArrays {

    public static int[] intSorter(int[] intArray){
        for (int j = 0; j < intArray.length; j++) {
            for (int i = 0; i < intArray.length - 1; i++) {
                int[] u = myMin(intArray[i], intArray[i + 1]);
                intArray[i] = u[0];
                intArray[i + 1] = u[1];
            }
        }
        return intArray;
    }

    public static int[] myMin(int one,int two){
        double res = one - two;
        if (res > 0){
            return new int[]{two,one};
        }else {
            return new int[]{one,two};
        }
    }
}
