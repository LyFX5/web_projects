public abstract class Food {

    private Colour colour;

    public Colour getColour() {
        return colour;
    }

    private int size;

    public int getSize() {
        return size;
    }

    public Food(Colour colour, int size){
        this.colour = colour;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Food"+ "  " + this.colour.toString() + "  " + String.valueOf(this.size);
    }

    @Override
    public int hashCode() {
        return this.size + Integer.valueOf(this.colour.toString());
    }

    @Override
    public boolean equals(Object obj) {
        Food food = (Food) obj;
        boolean check = false;
        if( (this.getClass() == food.getClass()) && (this.colour.toString() == food.colour.toString()) && (this.size == food.size)){ check = true; }
        return check;
    }
}
