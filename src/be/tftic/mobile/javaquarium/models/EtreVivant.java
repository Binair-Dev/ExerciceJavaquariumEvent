package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.interfaces.IEtreVivant;
import be.tftic.mobile.javaquarium.interfaces.INourriture;

public abstract class EtreVivant implements IEtreVivant, INourriture {

    private EtreVivantMeurtEventListener etreVivantMeurtEventListener;

    @FunctionalInterface
    public interface EtreVivantMeurtEventListener {
        void meurt(String raison);
    }

    public void setPoissonMeurtEventListener(EtreVivantMeurtEventListener etreVivantMeurtEventListener) {
        this.etreVivantMeurtEventListener = etreVivantMeurtEventListener;
    }

    public EtreVivantMeurtEventListener getEtreVivantMeurtEventListener() {
        return etreVivantMeurtEventListener;
    }

    //region Champs
    private int pdv;
    private int age;
    //endregion

    // region Encapsulation
    @Override
    public int getPdv() {
        return pdv;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public boolean estVivant() {
        return getPdv() > 0 && getAge() <= 20;
    }
    //endregion

    //region Constructeur
    public EtreVivant(int pdv, int age) {
        this.pdv = pdv;
        this.age = age;
    }

    public EtreVivant() {
        this.pdv = 10;
        this.age = 0;
    }
    //endregion

    //region Méthodes
    protected void gainPdv(int pdv) {
        if(pdv <= 0) throw new IllegalArgumentException("Les pdv récuperés doivent etre positif !");
        this.pdv += pdv;
    }
    protected void pertePdv(int pdv) {
        if(pdv <= 0) throw new IllegalArgumentException("Les pdv perdus doivent etre positif !");
        this.pdv -= pdv;
        if(getPdv() <= 0 && age < 20 && this.getEtreVivantMeurtEventListener() != null) this.getEtreVivantMeurtEventListener().meurt("de faim");
    }

    @Override
    public void etreManger() {
        subirUneAttaque();
    }

    protected abstract void subirUneAttaque();

    @Override
    public void actionFinJournee() {
        veillir();
    }

    protected void veillir() {
        if(estVivant()) {
            this.age++;
        }
    }
    //endregion
}
