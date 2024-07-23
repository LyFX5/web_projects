public class Main {
    public static void main(String[] args){

        Town zmeevka = new Town("Zmeevka",1000, "Gvozdik");

        Worker[] workers = new Worker[10];
        for( int i = 0; i < 10; i ++){
            workers[i] = new Worker(String.valueOf(i));
        }
        Car shurupchikCar = new Car(8,3.9f,10,"greenCar3.3",99.9);

        System.out.println("Num of fruits = " + FruitQuantity.fruitQuantityInTown);

        for(int i = 0;i < 10; i++){
            workers[i].eat(Fruit.APPLE);
        }

        System.out.println("Num of fruits = " + FruitQuantity.fruitQuantityInTown);

        for(int i = 0;i < 10; i++){
            workers[i].work();
        }

        System.out.println("Num of fruits = " + FruitQuantity.fruitQuantityInTown);

        shurupchikCar.peekUpPears();
        shurupchikCar.getCoordinate();

        System.out.println("Num of fruits = " + FruitQuantity.fruitQuantityInTown);

        workers[5].setName("Shurupchik");

        workers[5].speak();

        try {
            workers[5].drive(shurupchikCar,3.2f,6);
        }
        catch (NoLicenseException nL){
            nL.getMessage();
        }


        Worker Bublik = new Worker("Bublik");
        Car bublikCar = new Car(4,shurupchikCar.getCoordinate(),15,"rower2000",80);

        System.out.println("Surupchk's car " + shurupchikCar.getEngineModel() + " is at " + shurupchikCar.getCoordinate());
        System.out.println("Bublik's car " + bublikCar.getEngineModel() + " is at " + bublikCar.getCoordinate());

        Bublik.eat(Fruit.PEAR);
        Bublik.work();
        Bublik.work();
        bublikCar.peekUpPears();
        Bublik.eat(Fruit.PEAR);

        System.out.println("Num of fruits = " + FruitQuantity.fruitQuantityInTown);

        workers[5].eat(Fruit.APPLE);

        workers[5].work();

        System.out.println("Num of fruits = " + FruitQuantity.fruitQuantityInTown);

        System.out.println("Surupchk's car is at " + shurupchikCar.getCoordinate());

        zmeevka.gossipRise();

        zmeevka.getBraveMan();
    }
}
