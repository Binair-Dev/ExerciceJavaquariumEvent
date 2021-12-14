package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.INourriture;
import be.tftic.mobile.javaquarium.interfaces.INourritureAnimal;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;

public abstract class Poisson extends EtreVivant implements IPoisson, INourritureAnimal {

    private NaissancePoissonEventListener naissancePoissonEventListener;

    @FunctionalInterface
    public interface NaissancePoissonEventListener {
        void naissance(IPoisson pere, IPoisson mere, IPoisson enfant);
    }

    public void setNaissancePoissonEventListener(NaissancePoissonEventListener naissancePoissonEventListener) {
        this.naissancePoissonEventListener = naissancePoissonEventListener;
    }

    //region Champs
    private String name;
    private Espece espece;
    private Sexe sexe;
    private boolean copuler;
    //endregion

    //region Encapsulation
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Espece getEspece() {
        return espece;
    }

    @Override
    public Sexe getSexe() {
        return sexe;
    }

    @Override
    public boolean aFaim() {
        return this.getPdv() <= 5;
    }

    protected boolean aCopuler() {
        return copuler;
    }
    //endregion

    //region Constructeur
    public Poisson(int pdv, int age, String name, Espece espece, Sexe sexe) {
        super(pdv, age);
        this.name = name;
        this.espece = espece;
        this.sexe = sexe;
        this.copuler = false;
    }

    public Poisson(String name, Espece espece, Sexe sexe) {
        this.name = name;
        this.espece = espece;
        this.sexe = sexe;
        this.copuler = false;
    }
    //endregion

    //region Methodes
    protected void changerSexe() {
        this.sexe = (this.sexe == Sexe.FEMELLE) ? Sexe.MALE : Sexe.FEMELLE;
    }

    public abstract boolean manger(INourriture nourriture);

    @Override
    protected void subirUneAttaque() {
        this.pertePdv(4);
        if(!estVivant() && this.getEtreVivantMeurtEventListener() != null) this.getEtreVivantMeurtEventListener().meurt("d'une attaque");
    }

    @Override
    public IPoisson seReproduire(IPoisson partenaire) {
        if(this == partenaire || partenaire == null)
            return null;

        if(this.aFaim() || partenaire.aFaim() || this.apteACopuler() || partenaire.apteACopuler())
            return null;

        if(!verrifierCompatibilite(partenaire))
            return null;

        this.copuler();
        partenaire.copuler();

        IPoisson bebe = genererBebe();
        if(naissancePoissonEventListener != null) naissancePoissonEventListener.naissance(this, partenaire, bebe);
        return bebe;
    }

    @Override
    public boolean apteACopuler() {
        return copuler && this.getAge() >= 3;
    }

    @Override
    public void copuler() {
        this.copuler = true;
    }

    protected boolean verrifierCompatibilite(IPoisson partenaire) {
        return this.getEspece() == partenaire.getEspece()
                && this.getSexe() != partenaire.getSexe();
    }

    protected abstract IPoisson genererBebe();

    @Override
    public void actionFinJournee() {
        super.actionFinJournee();
        if(this.estVivant()) {
            this.copuler = false;
            this.pertePdv(1);
        } else {
            if(this.getEtreVivantMeurtEventListener() != null)
                this.getEtreVivantMeurtEventListener().meurt("de vieillesse");
        }
    }
    //endregion
}
