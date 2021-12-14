package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.INourriture;
import be.tftic.mobile.javaquarium.interfaces.INourritureAnimal;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;

public abstract class PoissonCarnivore extends Poisson {

    private PoissonMangePoissonEventListener poissonMangePoissonEventListener;

    @FunctionalInterface
    public interface PoissonMangePoissonEventListener {
        void manger(IPoisson repas);
    }

    public void setPoissonMangePoissonEventListener(PoissonMangePoissonEventListener poissonMangePoissonEventListener) {
        this.poissonMangePoissonEventListener = poissonMangePoissonEventListener;
    }

    public PoissonCarnivore(int pdv, int age, String name, Espece espece, Sexe sexe) {
        super(pdv, age, name, espece, sexe);
        this.setPoissonMangePoissonEventListener((food) -> System.out.println(this.getName() + "  mange " + food.getName() + " !"));
    }

    public PoissonCarnivore(String name, Espece espece, Sexe sexe) {
        super(name, espece, sexe);
        this.setPoissonMangePoissonEventListener((food) -> System.out.println(this.getName() + "  mange " + food.getName() + " !"));
    }

    @Override
    public boolean manger(INourriture nourriture) {
        if(!(nourriture instanceof INourritureAnimal)) {
            return false;
        }

        if(nourriture instanceof IPoisson) {
            IPoisson repas = (IPoisson) nourriture;

            if(repas.getEspece() == this.getEspece()) {
                return false;
            }
            this.poissonMangePoissonEventListener.manger(repas);
        }

        nourriture.etreManger();
        this.gainPdv(5);
        return true;
    }
}
