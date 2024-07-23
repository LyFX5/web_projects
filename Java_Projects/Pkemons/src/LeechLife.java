import ru.ifmo.se.pokemon.*;

public class LeechLife extends PhysicalMove {

    private double damageDealt;

    public LeechLife(){
        super(Type.BUG,80,1);
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage)
    {
        damageDealt = damage;
        super.applyOppDamage(p, damage);
    }

    @Override
    protected void applySelfEffects(Pokemon p)
    {
        p.setMod(Stat.HP, (int) -damageDealt/2);
    }

    @Override
    protected String describe()
    {
        return "высасывает кровь противника";
    }
}

