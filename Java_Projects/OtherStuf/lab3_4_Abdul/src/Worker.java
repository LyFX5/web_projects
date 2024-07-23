public class Worker extends Citizen implements Work{

    private float power;

    public float getPower() {
        return power;
    }

    public Worker(String name) {
        super(name);
    }

    @Override
    public void work() throws NoPowerException{
        if(power > 1){
            System.out.println("I am " + getName() + " I am working");
            FruitQuantity.fruitQuantityInTown += 1;
            power -= 1;
        }
        else {
            throw new NoPowerException("I am hungry !!");
        }
    }

    @Override
    public void eat(Fruit fruit) {
        switch (fruit){
            case PEAR:
                power += 3;
                FruitQuantity.fruitQuantityInTown -= 3;
            case APPLE:
                power += 2;
                FruitQuantity.fruitQuantityInTown -= 2;
        }
    }

}
