import java.util.ArrayList;
import java.util.Scanner;

public class DeepS {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        String ve [] = scan.nextLine().split(" ");
        int v = Integer.valueOf(ve[0]);
        int e = Integer.valueOf(ve[1]);
        boolean graf = Graf(v,e);
        ArrayList<ArrayList> Pas = new ArrayList();
        ArrayList<Integer> vers = new ArrayList();
        if(graf){
            for(int i = 0; i < e; i++){
                ve = scan.nextLine().split(" ");
                int x = Integer.valueOf(ve[0]);
                int y = Integer.valueOf(ve[1]);
                if (x > v || y > v || x == y) {
                    graf = false;
                    break;
                } else {
                    ArrayList xy = new ArrayList();
                    xy.add(x);
                    xy.add(y);
                    Pas.add(xy);
                    if (!vers.contains(x)){
                        vers.add(x);
                    }
                    if (!vers.contains(y)){
                        vers.add(y);
                    }
                }
            }
        }
        if (graf){
            boolean stuff = true;
            int t = Pas.size();
            while (stuff){
                boolean flag = false;
                int n = Pas.size();
                for(int i = 0; i < n; i++){
                    if (flag) break;
                    else {
                        for (int j = 0; j < Pas.get(i).size(); j++) {
                            if (flag) break;
                            else{
                                for(int k = 0; k < n; k++){
                                    if (flag) break;
                                    else {
                                        ArrayList p = Pas.get(k);
                                        if(!p.equals(Pas.get(i))){
                                            if(p.contains(Pas.get(i).get(j))){
                                                Pas.get(i).addAll(p);
                                                Pas.remove(p);
                                                t = Pas.size();
                                                flag = true;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
                if (n == t){
                    stuff = false;
                }
            }
            int w = v - vers.size();
            System.out.println(Pas.size() + w);
        }else {
            if(e == 0){
                System.out.println(v);
            }else {
                System.out.println(0);
            }
        }

    }

    static boolean Graf(int v, int e){
        boolean graf = true;
        if(e <= 0 || v <= 1){
            graf = false;
        }
        return graf;
    }



}
