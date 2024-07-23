public abstract class ObjectOfTown {

    private double size;
    private String colour;

    public double getSize() {
        return size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "My size is " + String.valueOf(size) + " my colour is "+ colour + ".";
    }

    @Override
    public int hashCode() {
        int result = Integer.parseInt(colour)*(int)size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        ObjectOfTown other = (ObjectOfTown) obj;
        boolean check = false;
        if( (this.getClass() == other.getClass()) && (this.colour.equals(other.colour)) && (this.size == other.size)){ check = true; }
        return check;
    }

}
