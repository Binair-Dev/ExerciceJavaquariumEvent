package be.tftic.mobile.javaquarium.interfaces;

import be.tftic.mobile.javaquarium.enums.Espece;
import be.tftic.mobile.javaquarium.enums.Sexe;

public interface IPoisson extends  IEtreVivant {
    String getName();
    Sexe getSexe();
    Espece getEspece();

    boolean apteACopuler();
    boolean aFaim();

    void copuler();

    boolean manger(INourriture nourriture);
    IPoisson seReproduire(IPoisson partenaire);
}
