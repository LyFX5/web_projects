import ru.ifmo.se.pokemon.*;

public class Confusion extends SpecialMove {
    public Confusion() {
        super(Type.PSYCHIC, 50, 1);
    }


    @Override
    protected void applyOppEffects(Pokemon p)
    {
        if (Math.random() > 0.1)
        {
            p.confuse();


        }

    }
    @Override
    protected String describe()
    {
        return "гипнотизирует цель";
    }
}
