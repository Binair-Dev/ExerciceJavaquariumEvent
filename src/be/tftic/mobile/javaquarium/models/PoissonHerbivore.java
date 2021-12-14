package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.INourriture;
import be.tftic.mobile.javaquarium.interfaces.INourritureVegetal;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;

public abstract class PoissonHerbivore extends Poisson{

    private PoissonMangeAlgueEventListener poissonMangeAlgueEventListener;

    @FunctionalInterface
    public interface PoissonMangeAlgueEventListener {
        void manger();
    }

    public void setPoissonMangeAlgueEventListener(PoissonMangeAlgueEventListener poissonMangeAlgueEventListener) {
        this.poissonMangeAlgueEventListener = poissonMangeAlgueEventListener;
    }

    public PoissonHerbivore(int pdv, int age, String name, Espece espece, Sexe sexe) {
        super(pdv, age, name, espece, sexe);
        this.setPoissonMangeAlgueEventListener(() -> System.out.println(this.getName() + " mange une algue !"));
    }

    public PoissonHerbivore(String name, Espece espece, Sexe sexe) {
        super(name, espece, sexe);
        this.setPoissonMangeAlgueEventListener(() -> System.out.println(this.getName() + " mange une algue !"));
    }

    @Override
    public boolean manger(INourriture nourriture) {
        if(!(nourriture instanceof INourritureVegetal)) {
            return false;
        }

        poissonMangeAlgueEventListener.manger();
        nourriture.etreManger();
        this.gainPdv(3);
        return true;
    }
}
