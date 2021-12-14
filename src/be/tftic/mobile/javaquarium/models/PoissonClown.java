package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;
import be.tftic.mobile.javaquarium.utils.StringGeneratorUtils;

public class PoissonClown extends  PoissonCarnivore {

    public PoissonClown(int pdv, int age, String name, Sexe sexe) {
        super(pdv, age, name, Espece.POISSON_CLOWN, sexe);
    }

    public PoissonClown(String name, Sexe sexe) {
        super(name, Espece.POISSON_CLOWN, sexe);
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
        Sexe sexe = Sexe.getRandomSexe();
        String name = StringGeneratorUtils.getString();
        return new PoissonClown(name, sexe);
    }
}
