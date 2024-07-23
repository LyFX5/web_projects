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

    public void save(){}

    public static class Cap extends Clothes{
        public Cap(Colour colour) {
            super(colour);
        } // static inner class

        @Override
        public void save(){
            System.out.println("I am very hungry! I will eat you if I see you!");
        }
    }

    public static class Blazer extends Clothes{
        public Blazer(Colour colour) {
            super(colour);
        } // static inner class

        @Override
        public void save(){
            System.out.println("I am Koshey Bessmertni! I will eat you!");
        }
    }

    public static class Sandals extends Clothes{
        public Sandals(Colour colour) {
            super(colour);
        } // static inner class

        @Override
        public void save(){
            System.out.println("I am Drakon! I eat malishka every day but I prefer malish!");
        }
    }

    public static class Pants extends Clothes{
        public Pants(Colour colour) {
            super(colour);
        } // static inner class

        @Override
        public void save(){
            System.out.println("I am Drakon! I eat malishka every day but I prefer malish!");
        }
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
