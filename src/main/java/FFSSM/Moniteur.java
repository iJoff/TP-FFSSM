/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.*;

public class Moniteur extends Plongeur {

    public int numeroDiplome;
    
    public ArrayList<Embauche> listeEmbauches = new ArrayList<>();

    public Moniteur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int niveau, int numeroDiplome) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance, niveau);
        this.numeroDiplome = numeroDiplome;
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur sous la forme d'un Optional
     */
    
    public Optional<Club> employeurActuel() {
        Optional<Club> employeur;
        if (!listeEmbauches.get(listeEmbauches.size() - 1).estTerminee()) {
            Club c = listeEmbauches.get(listeEmbauches.size() - 1).getEmployeur();
            employeur = Optional.ofNullable(c);
        }
        else {
            Club c = null;
            employeur = Optional.ofNullable(c);
        }
        return employeur;
    }
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {   
        Embauche emb = new Embauche(debutNouvelle, this, employeur);
        listeEmbauches.add(emb);
    }

    public List<Embauche> emplois() {
        return listeEmbauches;
    }
}
