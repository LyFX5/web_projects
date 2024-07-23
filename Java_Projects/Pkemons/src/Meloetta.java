import ru.ifmo.se.pokemon.*;

public class Meloetta extends Pokemon {
    public Meloetta(String name, int level) {
        super(name, level);
        setStats(100,77,77,128,128,90);
        setType(Type.NORMAL, Type.PSYCHIC);
        setMove(new Confusion(),new HyperVoice(),new Thunder(),new Thunderbolt());
    }
}