import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class SwordsDance extends StatusMove {
    public SwordsDance(){
        super(Type.NORMAL,0,1);
    }


    @Override
    protected void applySelfEffects(Pokemon p)
    {
        p.setMod(Stat.ATTACK,2);

    }
    @Override
    protected String describe()
    {
        return "поднимает боевой дух";
    }

}
