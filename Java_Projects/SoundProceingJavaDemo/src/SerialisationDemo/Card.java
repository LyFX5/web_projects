package SerialisationDemo;

public class Card {
    String question;
    String answer;
    public Card(String q,String a){
        question = q;
        answer = a;
    }
    public String getQuestion(){
        return question;
    }
    public String getAnswer(){
        return answer;
    }
}
