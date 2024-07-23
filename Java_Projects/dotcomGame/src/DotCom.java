import java.util.ArrayList;
public class DotCom{
   private String name;
    public void setName(String n) {
        name = n;
    }
    private ArrayList<String> position;

    public void setPosition(ArrayList<String> y) {
        position = y;
    }

    public String chekyourself(String userinput){
        String result = "past";
        int x = position.indexOf(userinput);
        if (x >= 0) {
            position.remove(x);
            result = "you hit it";
            if (position.isEmpty()) {
                result = "you bust it";
                System.out.println("ouch you bust" + name + "  : (  ");
            }
            else {result = "you hit it";}
        }
        return result;
    }
}