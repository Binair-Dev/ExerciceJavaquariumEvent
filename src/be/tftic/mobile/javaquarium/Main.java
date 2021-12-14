package be.tftic.mobile.javaquarium;

import be.tftic.mobile.javaquarium.enums.Sexe;
import be.tftic.mobile.javaquarium.interfaces.IAlgue;
import be.tftic.mobile.javaquarium.interfaces.IEtreVivant;
import be.tftic.mobile.javaquarium.interfaces.IPoisson;
import be.tftic.mobile.javaquarium.models.*;

import java.security.SecureRandom;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Aquarium aquarium = new Aquarium(30, 25);
        aquarium.setAjouterEtreVivantEventListener((poisson) -> listen(poisson));

        aquarium.ajouterPoisson(new Carpe(10, 4, "Antoine", Sexe.MALE));
        aquarium.ajouterPoisson(new Carpe(10, 8, "Fabio", Sexe.MALE));
        aquarium.ajouterPoisson(new Carpe(9, 8, "Francesco", Sexe.MALE));
        aquarium.ajouterPoisson(new Carpe(16, 2, "Thomas", Sexe.FEMELLE));
        aquarium.ajouterPoisson(new Carpe(8, 2, "William", Sexe.FEMELLE));

        aquarium.ajouterPoisson(new Bar(12, 17, "Balthazar", Sexe.FEMELLE));
        aquarium.ajouterPoisson(new Bar(9, 13, "Della", Sexe.FEMELLE));
        aquarium.ajouterPoisson(new Bar(7, 11, "Daisy", Sexe.FEMELLE));
        aquarium.ajouterPoisson(new Bar(7, 7, "Donald", Sexe.MALE));
        aquarium.ajouterPoisson(new Bar(11, 1, "Riri", Sexe.MALE));
        aquarium.ajouterPoisson(new Bar(10, 2, "Zaza", Sexe.MALE));
        aquarium.ajouterPoisson(new Bar(12, 1, "Doris", Sexe.MALE));

        aquarium.ajouterPoisson(new PoissonClown(16, 1, "Nemo", Sexe.MALE));
        aquarium.ajouterPoisson(new PoissonClown(24, 5, "Chuck Norris", Sexe.MALE));
        aquarium.ajouterPoisson(new PoissonClown(12, 3, "Tibaut", Sexe.FEMELLE));
        aquarium.ajouterPoisson(new PoissonClown(15, 4, "Maud", Sexe.FEMELLE));
        aquarium.ajouterPoisson(new PoissonClown(19, 10, "Brian", Sexe.MALE));

        SecureRandom rng = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int pdv = rng.nextInt(5, 11);
            int age = rng.nextInt(1, 7);
            aquarium.ajouterAlgue(new Algue(pdv, age));
        }

        // Lancer la simulation
        Scanner sc = new Scanner(System.in);

        int jour = 1;
        while (aquarium.getNbPoisson() > 0) {
            System.out.println("Journée " + jour);
            System.out.println("***********");
            afficherResumeAquarium(aquarium);

            aquarium.simulerTour();
            System.out.println();
            sc.nextLine();
            jour++;
        }
    }

    private static void listen(IEtreVivant etreVivant) {
        if(etreVivant instanceof IPoisson) {
            ((Poisson)etreVivant).setNaissancePoissonEventListener((pere, mere, enfant) -> System.out.println(pere.getName() + " et " + mere.getName() + " donnent naissance a " + enfant.getName() + "!"));
            ((Poisson)etreVivant).setPoissonMeurtEventListener((raison) -> System.out.println(((Poisson)etreVivant).getName() + " vient de mourrir " + raison + "!"));
        } else if(etreVivant instanceof IAlgue) {
            ((Algue)etreVivant).setPoissonMeurtEventListener((raison) -> System.out.println("Une algue vient de mourrir " + raison + "!"));
            ((Algue)etreVivant).setAlgueMultiplicationEventListener(() -> System.out.println("Une algue vient de se multiplier"));
        }
    }

    private static void afficherResumeAquarium(Aquarium aquarium) {
        System.out.println("Liste des poissons");
        for (IEtreVivant etreVivant : aquarium.getOccupants()) {
            if(etreVivant instanceof IPoisson fish) {
                String description = " - %s %s (Age: %d | Pdv: %d)" ;
                System.out.printf((description) + "%n", fish.getEspece(), fish.getName(), fish.getAge(), fish.getPdv());
            }
        }
        System.out.println();
        System.out.println("Nombre d'habitant de l'aquarium");
        System.out.println(" - Nombre de poisson : " + aquarium.getNbPoisson());
        System.out.println(" - Nombre de algue : " + aquarium.getNbAlgue());
        System.out.println();
    }
}
