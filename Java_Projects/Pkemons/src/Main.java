import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {

        Battle bat = new Battle();


        bat.addAlly(new Zweilous("Чёрный",1));

        bat.addAlly(new Meloetta("Мело",1));

        bat.addAlly(new Ninjask("Большой_жук",2));



        bat.addFoe(new Hydreigon("Горыныч",2));

        bat.addFoe(new Nincada("жук",1));

        bat.addFoe(new Deino("дракула",2));


        bat.go();
    }
}
