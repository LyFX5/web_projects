public class Citizen extends ObjectOfTown{

    public Citizen(String name){
        this.name = name;
    }

    private String name;

    private boolean license;

    public boolean isLicense() {
        return license;
    }

    public void setLicense(boolean license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void speak(String words){
        System.out.println(words);
    }

    public void speak(){
        System.out.println("Hello! I am " + name);
    }

    public void drive(Car car,float spead, int k) throws NoLicenseException{
        if (isLicense()) {
            for (int i = 0; i < k; i++) {
                car.move(spead);
            }
        }
        else {
            throw new NoLicenseException("No License to drive !");
        }
    }

    @Override
    public String toString() {
        return "My name is " + name + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Citizen citizen = (Citizen) o;
        return this.name.equals(citizen.name);
    }
}
