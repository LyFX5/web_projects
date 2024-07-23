import ru.ifmo.se.pokemon.*;

public class DragonRush extends PhysicalMove {
    public DragonRush(){
        super(Type.DRAGON,100,0.75);
    }

    @Override
    protected void applyOppEffects(Pokemon p)
    {
        if (Math.random() > 0.2)
        {
            Effect.flinch(p);
        }

    }
    @Override
    protected String describe()
    {
            return "подавляет противника";
    }
}
