import javax.xml.crypto.Data;
import java.util.Date;

public class Nature implements Talk_about_this {
    private Nature(){}
    public static String setWeather(){
        String[] pog = {"Идет снег","Солнечно","Дует ветер","Идёт дождь"};
        int rand = (int)(Math.random()*3);
        return pog[rand];
    }
    public static Date setTime(){
        Date t = new Date();
        return t;
    }

    @Override
    public String to_be_discussed() {
        return (setWeather() +"\n"+ setTime());
    }
}
