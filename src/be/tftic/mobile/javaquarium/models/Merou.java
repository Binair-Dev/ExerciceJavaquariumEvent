package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;
import be.tftic.mobile.javaquarium.utils.StringGeneratorUtils;

public class Merou extends PoissonCarnivore {

    public Merou(int pdv, int age, String name, Sexe sexe) {
        super(pdv, age, name, Espece.MEROU, sexe);
    }

    public Merou(String name) {
        super(name, Espece.MEROU, Sexe.MALE);
    }

    @Override
    public void actionFinJournee() {
        super.actionFinJournee();

        if(this.getAge() == 10) {
            this.changerSexe();
        }
    }

    @Override
    public boolean apteACopuler() {
        return aCopuler();
    }

    @Override
    protected IPoisson genererBebe() {
        String name = StringGeneratorUtils.getString();
        return new Merou(name);
    }
}
