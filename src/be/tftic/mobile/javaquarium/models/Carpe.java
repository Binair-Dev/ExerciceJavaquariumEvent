package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;
import be.tftic.mobile.javaquarium.utils.StringGeneratorUtils;

public class Carpe extends PoissonHerbivore {

    public Carpe(int pdv, int age, String name, Sexe sexe) {
        super(pdv, age, name, Espece.CARPE, sexe);
    }

    public Carpe(String name, Sexe sexe) {
        super(name, Espece.CARPE, sexe);
    }

    @Override
    protected IPoisson genererBebe() {
        String name = StringGeneratorUtils.getString();
        Sexe sexe = Sexe.getRandomSexe();
        return new Carpe(name, sexe);
    }
}
