import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalculateExpresions {

    public static void main(String[] args){
        String str = "";
        System.out.println("Введите арифметическое вырожение." + "\n" + "Все символы должны быть разделены пробелом.");
        try {
            str = new BufferedReader(new InputStreamReader(System.in)).readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(doubleFromExpression(str));
    }

    public static double doubleFromExpression(String expresiion){
        return numPars(expressionPars(chaingeSimbols(expresiion)));
    }

    public static String chaingeSimbols(String expression){
        String[] strs = expression.split(" ");
        for (int i = 0; i < strs.length; i++){
            if (strs[i].equals("×") || strs[i].equals("*")){
                strs[i] = "x";
            }
            if (strs[i].equals("+")){
                strs[i] = "p";
            }
            if (strs[i].equals("-")){
                if (i == 0){
                    strs[i] = "( - 1 ) x";
                }else {
                    strs[i] = "p ( - 1 ) x";
                }
            }
        }
        return glue(strs);
    }

    public static String expressionPars(String expression){
        if (expression.contains("(")){
            String[] strs = expression.split(" ");
            int first = 0;
            int next = 0;
            boolean nextIsclose = false;
            for (int i = 0; i < strs.length; i++){
                if (strs[i].equals("(")){
                    first = i;
                    break;
                }
            }
            for (int i = first + 1; i < strs.length; i++){
                nextIsclose = strs[i].equals(")");
                if (nextIsclose || strs[i].equals("(")){
                    next = i;
                    break;
                }
            }
            if (nextIsclose){
                String[] inBrackets = new String[next - first - 1];
                for (int i = (first + 1); i < next; i++){
                    inBrackets[i - first - 1] = strs[i];
                }
                String in = String.valueOf(numPars(glue(inBrackets)));

                String[] beforeBrackets = new String[first];
                for (int i = 0; i < beforeBrackets.length; i++){
                    beforeBrackets[i] = strs[i];
                }
                String before = glue(beforeBrackets);

                String[] afterBrackets = new String[strs.length - next - 1];
                for (int i = 0; i < afterBrackets.length; i++){
                    afterBrackets[i] = strs[i + next + 1];
                }
                String after = glue(afterBrackets);

                return expressionPars(before + " " + in + " " + after);
            }else {
                String[] beforeNext = new String[next];
                for (int i = 0; i < beforeNext.length; i++){
                    beforeNext[i] = strs[i];
                }
                String before = glue(beforeNext);

                String[] afterNext = new String[strs.length - next];
                for (int i = 0; i < afterNext.length; i++){
                    afterNext[i] = strs[i + next];
                }
                String after = expressionPars(glue(afterNext));

                return expressionPars(before + after); // почему не нужно добавлять разделитель " " ???
            }
        }else {
            return expression;
        }
    }

    public static double numPars(String str){ // must be without brackets
        String[] els = clinFirst(str.split(" "));
        switch (els.length){
            case 1:
                double d = 0;
                try{
                    d = Double.parseDouble(els[0]);
                }catch (NumberFormatException nfex){
                    System.out.println("Exception, "+ "Формат не соблюден: " + nfex.toString());
                }
                return d;
            case 2 : // in this case first must be "-" and second must be number
                return - numPars(els[1]);
            default:
                if ((els.length == 3) && els[1].equals("/")){ // in this case it mast be a fraction
                    return numPars(els[0]) / numPars(els[2]);
                }else {
                    String[] slogaemie = glue(els).split("p");
                    if (slogaemie.length < 2){
                        String[] mnojiteli = glue(els).split("x");
                        if (mnojiteli.length < 2){
                            System.out.println("Exception, "+ "Формат не соблюден:");
                            return 0;
                        }else {
                            double m = 1;
                            for (int i = 0; i < mnojiteli.length; i++) {
                                m *= numPars(mnojiteli[i]);
                            }
                            return m;
                        }
                    }else {
                        double n = 0;
                        for (int i = 0; i < slogaemie.length; i++) {
                            n += numPars(slogaemie[i]);
                        }
                        return n;
                    }
                }
        }
    }

    public static String[] clinFirst(String[] strings){
        String[] strings1 = new String[strings.length - 1];
        if (strings[0].equals("") || strings[0].equals(" ")){
            for (int i = 0; i < strings1.length; i++){
                strings1[i] = strings[i + 1];
            }
            return strings1;
        }else {
            return strings;
        }
    }

    public static String glue(String[] strings){
        String str = "";
        for (int i = 0; i < strings.length; i++){
            if (i == strings.length - 1){
                str += strings[i];
            }else {
                str += strings[i] + " ";
            }
        }
        return str;
    }
}
