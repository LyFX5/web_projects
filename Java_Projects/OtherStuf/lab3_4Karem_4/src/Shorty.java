public abstract class Shorty {

    private String name;
    private Eyes eyes;

    private boolean seeTrap;

    public Shorty(String name, Colour eyeColour) {
        this.name = name;
        this.eyes = new Eyes(eyeColour);
    }

    public String getName() {
        return name;
    }

    public Colour getEyesColour() {
        return eyes.getColour();
    }

    public void speak(String word) {
        System.out.println(word);
    }

    public void getIntoTruble(){

        BeTruble trap = new BeTruble() { // anonym
            @Override
            public void makeTruble() {
                seeTrap = true;
                System.out.println(name + " get into trouble");
            }
        };
        trap.makeTruble();
    }

    public void walk() throws CantRunException{

        if (seeTrap){
            throw new CantRunException("I cannot walk fast");
        }

        System.out.println("I am walking!");
    }

    public String see(Object ob){
        return ob.toString();
}

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Integer.parseInt(name);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Shorty shorty = (Shorty)obj;
        boolean check = false;
        if( (this.getClass() == shorty.getClass()) && (this.name.equals(shorty.name))){ check = true; }
        return check;
    }

    private class Eyes{ // non-static inner class

        private Colour colour;
        private Eyes(Colour colour){
            this.colour = colour;
        }

        public Colour getColour() {
            return this.colour;
        }

    }

}
