public abstract class Monster extends ObjectOfTown{

    public void takeFood(){}

    public static class BabaYaga extends Monster{ // static inner class

        @Override
        public void takeFood(){
            System.out.println("I am very hungry! I will eat you if I see you!");
        }
    }

    public static class Koshey extends Monster{ // static inner class
        @Override
        public void takeFood(){
            System.out.println("I am Koshey Bessmertni! I will eat you!");
        }
    }

    public static class Drakon extends Monster{ // static inner class
        @Override
        public void takeFood(){
            System.out.println("I am Drakon! I eat malishka every day but I prefer malish!");
        }
    }
}
