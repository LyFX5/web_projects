public class Lunarian extends Shorty{

    public void peekUpFood(Food food) throws NotRipeException{

        if( ! food.isRipe()){
            throw new NotRipeException("Food is not ripe!");
        }
        System.out.println("I get a " + food.toString());
    }

    public Lunarian(String name, Colour eyeColour) {
        super(name, eyeColour);
    }

    private Clothes.Blazer blazer;

    public Clothes.Blazer getBlazer() {
        return blazer;
    }

    public void setBlazer(Clothes.Blazer blazer) {
        this.blazer = blazer;
    }

    private Clothes.Cap cap;

    public Clothes.Cap getCap() {
        return cap;
    }

    public void setCap(Clothes.Cap cap) {
        this.cap = cap;
    }

    private Clothes.Pants pants;

    public Clothes.Pants getPants() {
        return pants;
    }

    public void setPants(Clothes.Pants pants) {
        this.pants = pants;
    }

    private Clothes.Sandals sandals;

    public Clothes.Sandals getSandals() {
        return sandals;
    }

    public void setSandals(Clothes.Sandals sandals) {
        this.sandals = sandals;
    }

    private String atribut;

    public String getAtribut() {
        return atribut;
    }

    public void setAtribut(String atribut) {
        this.atribut = atribut;
    }

    public WaterTheBeds water_the_beds(){

        class Water implements WaterTheBeds{ // local class

            public Water(){
                waterTheBeds();
            }

            @Override
            public void waterTheBeds() {
                System.out.println("Beds are watered!");
            }
        }

        return new Water();
    }
}
