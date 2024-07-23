import ru.ifmo.se.pokemon.Type;

public class Ninjask extends Nincada {
    public Ninjask(String name,int level){
        super(name, level);
        setStats(61,90,45,50,50,160);
        setType(Type.BUG,Type.FLYING);
        addMove(new SwordsDance());
    }
}
