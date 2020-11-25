package FFSSM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.time.LocalDate;
import java.util.List;


public class FFSSMJUnitTest {
    Plongeur plongeur1, plongeur2;
    Club club;
    Embauche embauche1, embauche2;   
    LocalDate date1, date2, date3;
    Licence licence1, licence2;
    Moniteur moniteur; 
    Plongee plongee1;
    Site lieu;
    
    public FFSSMJUnitTest() {
    }
    
    @BeforeEach
    public void setUp() {
        moniteur = new Moniteur("numeroINSEE", "nom", "prenom", "adresse",  "telephone", date1, 3, 001);
        date1 = LocalDate.of(2019, 1, 1);
        date2 = LocalDate.of(2020, 3, 3);
        date3 = LocalDate.of(2020, 3, 5);
        licence1 = new Licence(plongeur1, "001", date1, 1, club);
        licence2 = new Licence(plongeur2, "002", date2, 2, club);
        plongee1 = new Plongee(lieu, moniteur, date3, 300, 60);
        club = new Club(moniteur, "Club", "0606060606");
        plongeur1 = new Plongeur("numeroINSEE", "nom", "prenom", "adresse", "telephone", date1, 1);
        embauche1 = new Embauche(date1, moniteur, club);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    // Ce test vérifie qu'une plongée n'est pas considérée comme conforme
    // si un plongeur a une licence expirée.
    public void plongeeNonConforme () {
        plongee1.ajouteParticipant(licence1);
        plongee1.ajouteParticipant(licence2);
        club.organisePlongee(plongee1);
        assertFalse(club.plongeesNonConformes().isEmpty(), "La plongée non conforme figure bien dans la liste.");
    }
    
    
    @Test
    // Ce  test vérifie qu'un moniteur ayant une embauche en cours ne
    // peut pas être embauché
    public void uneSeuleEmbauche() {
        try {
            moniteur.nouvelleEmbauche(club, date1);
            moniteur.nouvelleEmbauche(club, date2);
            fail("Cet appel doit lever une exception"); 
        } catch (IllegalArgumentException e) {
        }
    }
       
    @Test
    // Ce test vérifie ici qu'une licence accordée le 1/1/2019
    // n'est plus valide le 3/3/2020.
    public void validiteLicence() {
        Licence licence = new Licence(plongeur1, "001", date1, 3, club);
        assertFalse(licence.estValide(date2), "Une licence expirée n'est pas reconnue.");
    }
    
    @Test
    // Ce test crée une plongée non conforme et vérifie si elle est détectée
    // en tant que telle
    public void testPlongeeNonConformeClub(){
        plongee1.ajouteParticipant(licence1);
        plongee1.ajouteParticipant(licence2);
        
    }
    
    @Test
    // Ce test vérifie qu'une nouvelle licence est bien ajoutée au plongeur.
    public void nouvelleLicencePlongeur() {
        plongeur1.ajouteLicence("003", date2, club);
        HashSet<Licence> listeLicences = plongeur1.licences();
        assertFalse(listeLicences.isEmpty(), "La nouvelle licence n'a pas été ajoutée au plongeur.");
    }
    
    @Test
    // On teste Embauche.
    public void finEmbauche() {
        embauche1.terminer(date2);
        assertTrue(embauche1.estTerminee(), "L'embauche n'a pas été terminée.");
    }
}
