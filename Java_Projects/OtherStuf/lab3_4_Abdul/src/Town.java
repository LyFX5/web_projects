public class Town {
    String name;
    int population;
    String bravesName;

    public Town(String name, int population, String bravesName){
        this.bravesName = bravesName;
        this.name = name;
        this.population = population;
    }

    private interface BeBrave{
        boolean beBrave();
    }

    public BeBrave getBraveMan(){

        class Brave implements BeBrave{ // local class

            final String name = Town.this.bravesName;

            public Brave(){
                System.out.println("I am " + name + " ! I will kill monster!" );
            }

            @Override
            public boolean beBrave(){
                return true;
            }
        }
        return new Brave();
    }

    public void gossipRise(){
        Gossip babaYaga = new Gossip() { // anonym class
            @Override
            public void gossipTold() {
                System.out.println("Probably there is BabaYaga on the road!");
            }
        };
        Gossip koshey = new Gossip() { // anonym class
            @Override
            public void gossipTold() {
                System.out.println("Probably there is Koshey Bessmertni on the road!");
            }
        };
        Gossip drakon = new Gossip() { // anonym class
            @Override
            public void gossipTold() {
                System.out.println("Probably there is Drakon in the town!");
            }
        };
        babaYaga.gossipTold();
        koshey.gossipTold();
        drakon.gossipTold();
    }
}
