import ru.ifmo.se.pokemon.*;
public class Nincada extends Pokemon {
    public Nincada(String name, int level) {
        super(name, level);
        setStats(31,45,90,30,30,40);
        setType(Type.BUG, Type.GROUND);
        setMove(new LeechLife(),new DoubleTeam(),new Confide());
    }
}