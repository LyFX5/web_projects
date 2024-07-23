public class Earthling extends Shorty implements GetHungry{

    public Earthling(String name, Colour eyeColour) {
        super(name, eyeColour);
    }

    @Override
    public void getHungry() {
        speak("I am hungry");
    }

    @Override
    public String see(Object ob){
        Food food = (Food) ob;
        if(food.getColour().equals(Colour.RED) && food.getSize() == 1){
            return "Lunar raspberry";
        }
        else {
            return ob.toString();
        }
    }
}
