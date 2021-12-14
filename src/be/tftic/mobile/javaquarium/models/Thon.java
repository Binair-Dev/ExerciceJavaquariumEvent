package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;
import be.tftic.mobile.javaquarium.utils.StringGeneratorUtils;

public class Thon extends PoissonCarnivore {

    public Thon(int pdv, int age, String name, Sexe sexe) {
        super(pdv, age, name, Espece.THON, sexe);
    }

    public Thon(String name, Sexe sexe) {
        super(name, Espece.THON, sexe);
    }

    @Override
    protected IPoisson genererBebe() {
        Sexe sexe = Sexe.getRandomSexe();
        String name = StringGeneratorUtils.getString();
        return new Thon(name, sexe);
    }
}
