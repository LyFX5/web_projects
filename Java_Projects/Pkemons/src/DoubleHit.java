import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class DoubleHit extends PhysicalMove {
    public DoubleHit(){
        super(Type.NORMAL,35,0.9,1,2);

    }

    @Override
    protected String describe()
    {
        return "наносит двойной удар";
    }


}
