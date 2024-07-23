public abstract class Clothes {

    private Colour colour;

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Clothes(Colour colour){
        this.colour = colour;
    }

    @Override
    public String toString() {
        return colour.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Integer.parseInt(colour.toString());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Clothes clothes = (Clothes) obj;
        boolean check = false;
        if ((this.getClass() == clothes.getClass()) && (this.colour.toString() == clothes.colour.toString())) {
            check = true;
        }
        return check;
    }
}
