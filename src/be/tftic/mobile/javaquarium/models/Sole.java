package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;
import be.tftic.mobile.javaquarium.utils.StringGeneratorUtils;

public class Sole extends PoissonHerbivore {

    public Sole(int pdv, int age, String name, Sexe sexe) {
        super(pdv, age, name, Espece.SOLE, sexe);
    }

    public Sole(String name, Sexe sexe) {
        super(name, Espece.SOLE, sexe);
    }

    @Override
    protected boolean verrifierCompatibilite(IPoisson partenaire) {
        if(partenaire.getEspece() != this.getEspece()) {
            return false;
        }

        if(partenaire.getSexe() == this.getSexe()) {
            this.changerSexe();
        }
        return true;
    }

    @Override
    protected IPoisson genererBebe() {
        String name = StringGeneratorUtils.getString();
        Sexe sexe = Sexe.getRandomSexe();
        return new Sole(name, sexe);
    }
}
