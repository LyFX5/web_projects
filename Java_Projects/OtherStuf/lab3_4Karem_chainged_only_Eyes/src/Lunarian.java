public class Lunarian extends Shorty{

    public Lunarian(String name, Colour eyeColour) {
        super(name, eyeColour);
    }

    private Blazer blazer;

    public Blazer getBlazer() {
        return blazer;
    }

    public void setBlazer(Blazer blazer) {
        this.blazer = blazer;
    }

    private Cap cap;

    public Cap getCap() {
        return cap;
    }

    public void setCap(Cap cap) {
        this.cap = cap;
    }

    private Pants pants;

    public Pants getPants() {
        return pants;
    }

    public void setPants(Pants pants) {
        this.pants = pants;
    }

    private Sandals sandals;

    public Sandals getSandals() {
        return sandals;
    }

    public void setSandals(Sandals sandals) {
        this.sandals = sandals;
    }

    private String atribut;

    public String getAtribut() {
        return atribut;
    }

    public void setAtribut(String atribut) {
        this.atribut = atribut;
    }
}
