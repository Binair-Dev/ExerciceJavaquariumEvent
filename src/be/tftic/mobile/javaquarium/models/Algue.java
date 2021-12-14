package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.interfaces.IAlgue;
import be.tftic.mobile.javaquarium.interfaces.INourritureVegetal;

public class Algue extends EtreVivant implements IAlgue, INourritureVegetal {

    private AlgueMultiplicationEventListener algueMultiplicationEventListener;

    @FunctionalInterface
    public interface AlgueMultiplicationEventListener {
        void multiplication();
    }

    public void setAlgueMultiplicationEventListener(AlgueMultiplicationEventListener algueMultiplicationEventListener) {
        this.algueMultiplicationEventListener = algueMultiplicationEventListener;
    }

    private int PDV_MULTI = 10;

    //region Constructeur
    public Algue(int pdv, int age) {
        super(pdv, age);
    }

    public Algue(int pdv) {
        super(pdv, 0);
    }

    public Algue() {
        super();
    }
    //endregion

    //region Methodes
    @Override
    protected void subirUneAttaque() {
        this.pertePdv(2);
        if(!estVivant() && this.getEtreVivantMeurtEventListener() != null) this.getEtreVivantMeurtEventListener().meurt("après s'être faite mangée par un poisson");
    }

    @Override
    public IAlgue seMultiplier() {
        if (this.getPdv() >= PDV_MULTI) {
            int pdvBebe = this.getPdv() / 2;
            this.pertePdv(pdvBebe);
            if(algueMultiplicationEventListener != null) algueMultiplicationEventListener.multiplication();
            return new Algue(pdvBebe);
        }
        return null;
    }

    @Override
    public void actionFinJournee() {
        super.actionFinJournee();

        if(this.estVivant()) {
            gainPdv(1);
        } else {
            if(this.getEtreVivantMeurtEventListener() != null) this.getEtreVivantMeurtEventListener().meurt("de vieillesse");
        }
    }
    //endregion
}
