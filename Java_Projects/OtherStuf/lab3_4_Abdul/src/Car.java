public class Car extends ObjectOfTown implements Move{

    public Car(int numOfWeels, float coordinate, float engineMass, String engineModel, double enginePower){
        this.numOfWeels = numOfWeels;
        this.coordinate = coordinate;
        this.engine = new Engine(engineMass,engineModel,enginePower);
    }

    private float coordinate;

    private int numOfWeels;

    private Engine engine;

    public String getEngineModel(){
        return engine.getModel();
    }

    public float getCoordinate() {
        return coordinate;
    }

    @Override
    public void move(float spead) {
        this.coordinate += spead;
        System.out.println("Car's cordinate is " + this.coordinate);
    }

    public void peekUpPears(){
        FruitQuantity.fruitQuantityInTown += 5;
    }

    private class Engine{ // non-static inner class
        float mass;
        String model;
        double power;

        private Engine(float mass, String model, double power){
            this.mass = mass;
            this.model = model;
            this.power = power;
        }

        public String getModel() {
            return model;
        }
    }

}
