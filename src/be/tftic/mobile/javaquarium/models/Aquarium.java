package be.tftic.mobile.javaquarium.models;

import be.tftic.mobile.javaquarium.interfaces.IAlgue;
import be.tftic.mobile.javaquarium.interfaces.IEtreVivant;
import be.tftic.mobile.javaquarium.interfaces.INourriture;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aquarium {

    private AjouterEtreVivantEventListener ajouterEtreVivantEventListener;

    @FunctionalInterface
    public interface AjouterEtreVivantEventListener {
        void listen(IEtreVivant enfant);
    }

    public void setAjouterEtreVivantEventListener(AjouterEtreVivantEventListener ajouterEtreVivantEventListener) {
        this.ajouterEtreVivantEventListener = ajouterEtreVivantEventListener;
    }
    //region Champs
    private List<IEtreVivant> occupants;
    private int maxAlgue, maxPoisson;
    //endregion

    //region Encapsulation
    public int getMaxPoisson() {
        return maxPoisson;
    }

    public int getMaxAlgue() {
        return maxAlgue;
    }

    public List<IEtreVivant> getOccupants() {
        return Collections.unmodifiableList(this.occupants);
    }
    //endregion


    public Aquarium(int maxAlgue, int maxPoisson) {
        this.maxAlgue = maxAlgue;
        this.maxPoisson = maxPoisson;
        this.occupants = new ArrayList<>();
    }

    //region Méthodes
    public int getNbPoisson() {
        int count = 0;
        for(IEtreVivant etreVivant : occupants) {
            if(etreVivant instanceof IPoisson) {
                count++;
            }
        }
        return count;
    }
    public int getNbAlgue() {
        int count = 0;
        for(IEtreVivant etreVivant : occupants) {
            if(etreVivant instanceof IAlgue) {
                count++;
            }
        }
        return count;
    }

    public void ajouterPoisson(IPoisson poisson) {
        if(poisson == null || !poisson.estVivant() || getNbPoisson() >= getMaxPoisson()) {
            return;
        }
        if(ajouterEtreVivantEventListener != null) ajouterEtreVivantEventListener.listen(poisson);
        this.occupants.add(poisson);
    }
    public void ajouterAlgue(IAlgue algue) {
        if(algue == null || !algue.estVivant() || getNbAlgue() >= getMaxAlgue()) {
            return;
        }
        if(ajouterEtreVivantEventListener != null) ajouterEtreVivantEventListener.listen(algue);
        this.occupants.add(algue);
    }

    private void netoyageAquarium() {
        this.occupants.removeIf(occ -> !occ.estVivant());
    }

    public void simulerTour() {
        // Food time !
        for(IEtreVivant etreVivant : occupants) {
            if(etreVivant instanceof IPoisson) {
                IPoisson poisson = (IPoisson) etreVivant;
                if(poisson.estVivant() && poisson.aFaim()) {
                    int nbEssai = 3;
                    boolean aMange = false;
                    for (int i = 0; !aMange && i < nbEssai; i++) {
                        aMange = poisson.manger(chercherNourriture());
                    }
                }
            }
        }
        netoyageAquarium();

        // Reproduction / Multiplication
        List<IEtreVivant> bebes = new ArrayList<>();
        int nbBebePoisson = 0;
        int nbBebeAlgue = 0;
        for(IEtreVivant etreVivant : occupants) {
            IEtreVivant bebe = null;

            // ↓ Utilisation du instanceof avec pattern (-> Création de la variable)
            if(etreVivant instanceof IPoisson fish && (getNbPoisson() + nbBebePoisson < getMaxPoisson())) {
                bebe = fish.seReproduire(chercherPartenaire());
                nbBebePoisson++;
            }
            else if (etreVivant instanceof IAlgue algue && (getNbAlgue() + nbBebeAlgue < getMaxAlgue())) {
                bebe = algue.seMultiplier();
                nbBebeAlgue++;
            }

            if(bebe != null) {
                bebes.add(bebe);
            }
        }
        occupants.addAll(bebes);

        // Fin de journée
        for(IEtreVivant etreVivant : occupants) {
            etreVivant.actionFinJournee();
        }
        netoyageAquarium();
    }

    private INourriture chercherNourriture() {
        SecureRandom rng = new SecureRandom();
        int cible = rng.nextInt(occupants.size());

        if(occupants.get(cible) instanceof INourriture) {
            return (INourriture) occupants.get(cible);
        }
        return null;
    }

    private IPoisson chercherPartenaire() {
        if(getNbPoisson() <= 1) {
            return null;
        }

        // Introduction au Stream (Equivalent à LINQ en C#)
        List<IEtreVivant> poissons = occupants.stream().filter(o -> o instanceof IPoisson).toList();
        int cible = (new SecureRandom()).nextInt(0, poissons.size());

        return (IPoisson) poissons.get(cible);
    }
    //endregion
}
