import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam() {
        super(Type.NORMAL, 0, 1);
    }


    @Override
    protected void applySelfEffects(Pokemon p)
    {
        p.setMod(Stat.DEFENSE, 1);
    }
    @Override
    protected String describe()
    {

        return "начинает быстро двигаться";
    }
}

