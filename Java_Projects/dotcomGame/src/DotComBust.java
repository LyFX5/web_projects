import java.util.ArrayList;
public class DotComBust {
        private GameHelper helper = new GameHelper();
        private ArrayList<DotCom> DotComlist = new ArrayList(3);
        private int num_of_gasses = 0;

        public void setupGame(){
            String[] names = {"Yandex.com","Google.com","YouTube.com"};
            DotCom one = new DotCom();
            DotComlist.add(one);
            DotCom two = new DotCom();
            DotComlist.add(two);
            DotCom three = new DotCom();
            DotComlist.add(three);

            for (int i = 0; i < names.length; i++) {
                DotComlist.get(i).setName(names[i]);
                DotComlist.get(i).setPosition(helper.placeDotCom(3));
            }
        }

        void startGame(){
            while (DotComlist.isEmpty() == false){
              String x = helper.getPlaergasse("Do your choice");
              chek(x);
            }
            finishGame();
        }
       private void chek(String gesse){
            num_of_gasses++;
            String res = "past";
            int[] arr = {0,1,2};
            for (int j : arr) {
                DotCom r = DotComlist.get(j);//здесь проблема
                res = r.chekyourself(gesse);
                if (res.equals("you bust it")){
                    DotComlist.remove(r);
                }
                if(res.equals("you hit it")){
                    break;
                }
            }
            System.out.println(res);
        }

        void finishGame(){
            System.out.println("Game over");
            if(num_of_gasses < 25){
                System.out.println("Congratulations, you need only " + num_of_gasses + "attempt");
            }else{
                System.out.println("Come on, why so much.Whole " +num_of_gasses+ "attempt?");
            }
        }

}
