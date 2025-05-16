package filtrage;

import java.util.ArrayList;
import java.util.Set;
import peuple.*;
import territoire.*;

/**
 * Cette classe contient des methodes pour trier des listes de personnes selon divers criteres 
 * tels que la discipline, la fonction, la region, la population, etc.
 * @version 1.0
 */
public class Trie {
	
	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private Trie() {
		
	}
		
    /**
     * Affiche les personnes par ordre de leur type : Etudiant, MCF, Chercheur.
     * @param personnes Liste des personnes a trier.
     */
    public static void afficheParOrdre(ArrayList<Personne> personnes) {
        Etudiant[] etudiants = new Etudiant[personnes.size()];
        MCF[] mcfs = new MCF[personnes.size()];
        Chercheur[] chercheurs = new Chercheur[personnes.size()];
        int iEtud = 0;
        int iMCF = 0;
        int iCher = 0;
        for (Personne p: personnes) {
            if (p instanceof Etudiant) {
                etudiants[iEtud] = (Etudiant) p;
                iEtud ++;
            } else if (p instanceof MCF) {
                mcfs[iMCF] = (MCF) p;
                iMCF ++;
            } else {
                chercheurs[iCher] = (Chercheur) p;
                iCher ++;
            }
        }
        System.out.println("\nVoici la base de données courante :\n");
        
        System.out.println("Les Etudiants :");
        for(int k = 0; k<iEtud; k++) {
            System.out.println(etudiants[k].toString());
        }
        System.out.println("\nLes MCF :");
        for(int k = 0; k<iMCF; k++) {
            System.out.println(mcfs[k].toString());
        }
        System.out.println("\nLes Chercheurs :");
        for(int k = 0; k<iCher; k++) {
            System.out.println(chercheurs[k].toString());
        }
        
    }
    
    /**
     * Clone une liste de personnes.
     * @param personnes Liste des personnes a cloner.
     * @return Une nouvelle liste contenant les mêmes personnes que la liste d'origine.
     */
    public static ArrayList<Personne> monClone(ArrayList<Personne> personnes){
        ArrayList<Personne> clonePers = new ArrayList<>();
        for (Personne p: personnes) {
            clonePers.add(p);
        }
        return clonePers;
    }

    /**
     * Trie les personnes en fonction du type de discipline et renvoie les personnes ayant 
     * toutes les disciplines spécifiées.
     * @param Personnes Liste des personnes a trier.
     * @param disciplines Liste des disciplines a verifier.
     * @return Liste des personnes ayant toutes les disciplines données.
     */
    public static ArrayList<Personne> TrieDisciplineET(ArrayList<Personne> Personnes, ArrayList<Discipline> disciplines){
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            boolean bool = true;
            Set<Discipline> D = pers.getDisciplines();
            for(Discipline dis : disciplines){
                if (!D.contains(dis)){
                    bool=false;
                    break;
                }
            }
            if (bool){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction du type de discipline et renvoie les personnes ayant 
     * au moins une discipline parmi celles spécifiées.
     * @param Personnes Liste des personnes a trier.
     * @param disciplines Liste des disciplines a verifier.
     * @return Liste des personnes ayant au moins une discipline donnée.
     */
    public static ArrayList<Personne> TrieDisciplineOU(ArrayList<Personne> Personnes, ArrayList<Discipline> disciplines){
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            boolean bool = false;
            Set<Discipline> D = pers.getDisciplines();
            for(Discipline dis : disciplines){
                if (D.contains(dis)){
                    bool=true;
                    break;
                }
            }
            if (bool){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur fonction et renvoie celles ayant au moins une des fonctions spécifiées.
     * @param Personnes Liste des personnes a trier.
     * @param fonctions Liste des fonctions à vérifier.
     * @return Liste des personnes ayant au moins une des fonctions spécifiées.
     */
    public static ArrayList<Personne> TrieFonction(ArrayList<Personne> Personnes, ArrayList<String> fonctions){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            ArrayList<String> F = pers.getFonctions();
            for(String fct : fonctions){
                if (F.contains(fct)){
                    AVoir.add(pers);
                    break;
                }
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur région et renvoie celles appartenant aux régions spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param Regions Liste des régions à vérifier.
     * @return Liste des personnes appartenant aux régions spécifiées.
     */
    public static ArrayList<Personne> TrieRegion(ArrayList<Personne> Personnes, ArrayList<Region> Regions){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if (Regions.contains(pers.getVille().getRegion())){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur département et renvoie celles appartenant aux départements spécifiés.
     * @param Personnes Liste des personnes à trier.
     * @param Codedepartements Liste des codes départements à vérifier.
     * @return Liste des personnes appartenant aux départements spécifiés.
     */
    public static ArrayList<Personne> TrieDepartement(ArrayList<Personne> Personnes, ArrayList<String> Codedepartements){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if (Codedepartements.contains(pers.getVille().getDepartement().toLowerCase())){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur ville et renvoie celles appartenant aux villes spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param Villes Liste des villes à vérifier.
     * @return Liste des personnes appartenant aux villes spécifiées.
     */
    public static ArrayList<Personne> TrieVille(ArrayList<Personne> Personnes, ArrayList<String> Villes){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if (Villes.contains(pers.getVille().getNom().toLowerCase())){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de la population de leur région et renvoie celles dont la population de la région 
     * est comprise entre les deux valeurs spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param Population1 Limite inférieure de la population.
     * @param Population2 Limite supérieure de la population.
     * @return Liste des personnes dont la région a une population comprise entre Population1 et Population2.
     */
    public static ArrayList<Personne> TriePopulationRegion(ArrayList<Personne> Personnes, double Population1, double Population2){ 
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            double Population = pers.getVille().getRegion().getPopulationEstimee2024();
            if(Population >= Population1 && Population <= Population2){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de la population de leur ville et renvoie celles dont la population de la ville 
     * est comprise entre les deux valeurs spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param Population1 Limite inférieure de la population.
     * @param Population2 Limite supérieure de la population.
     * @return Liste des personnes dont la ville a une population comprise entre Population1 et Population2.
     */
    public static ArrayList<Personne> TriePopulationVille(ArrayList<Personne> Personnes, double Population1, double Population2){ 
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            double Population = pers.getVille().getPopulation();
            if(Population >= Population1 && Population <= Population2){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de la superficie de leur région et renvoie celles dont la superficie de la région 
     * est comprise entre les deux valeurs spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param Superficie1 Limite inférieure de la superficie.
     * @param Superficie2 Limite supérieure de la superficie.
     * @return Liste des personnes dont la région a une superficie comprise entre Superficie1 et Superficie2.
     */
    public static ArrayList<Personne> TrieSuperficieRegion(ArrayList<Personne> Personnes, double Superficie1, double Superficie2){ 
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            double Superficie = pers.getVille().getRegion().getSuperficie();
            if(Superficie >= Superficie1 && Superficie <= Superficie2){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de la superficie de leur ville et renvoie celles dont la superficie de la ville 
     * est comprise entre les deux valeurs spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param Superficie1 Limite inférieure de la superficie.
     * @param Superficie2 Limite supérieure de la superficie.
     * @return Liste des personnes dont la ville a une superficie comprise entre Superficie1 et Superficie2.
     */
    public static ArrayList<Personne> TrieSuperficieVille(ArrayList<Personne> Personnes, double Superficie1, double Superficie2){ 
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            double Superficie = pers.getVille().getSuperficie();
            if(Superficie >= Superficie1 && Superficie <= Superficie2){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur ville chef-lieu et renvoie celles dont la ville est un chef-lieu
     * dans la liste des villes chef-lieu spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param VilleChefLieu Liste des villes chef-lieu à vérifier.
     * @return Liste des personnes dont la ville est un chef-lieu.
     */
    public static ArrayList<Personne> TrieRegionVilleChefLieu(ArrayList<Personne> Personnes, ArrayList<Ville> VilleChefLieu){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if (VilleChefLieu.contains(pers.getVille().getRegion().getChefLieu())){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur âge et renvoie celles dont l'âge est compris entre les deux valeurs spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param age1 Limite inférieure de l'âge.
     * @param age2 Limite supérieure de l'âge.
     * @return Liste des personnes dont l'âge est compris entre age1 et age2.
     */
    public static ArrayList<Personne> TrieAge(ArrayList<Personne> Personnes, double age1, double age2){ 
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            int age = pers.getAge();
            if(age >= age1 && age <= age2){
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur nom et prénom et renvoie celles dont le nom et prénom correspondent
     * à ceux spécifiés dans la liste NomPrenom.
     * @param Personnes Liste des personnes à trier.
     * @param NomPrenom Liste des noms et prénoms à vérifier.
     * @return Liste des personnes dont le nom et prénom correspondent.
     */
    public static ArrayList<Personne> TrieNomPrenom(ArrayList<Personne> Personnes, ArrayList<String> NomPrenom){
        ArrayList<Personne> AVoir= new ArrayList<>();
        for(Personne pers : Personnes){
            String nomprenom = pers.getNomPrenom().toLowerCase();
            if(NomPrenom.contains(nomprenom)){
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de leur ID et renvoie celles dont l'ID correspond à ceux spécifiés dans la liste IDs.
     * @param Personnes Liste des personnes à trier.
     * @param IDs Liste des IDs à vérifier.
     * @return Liste des personnes dont l'ID correspond.
     */
    public static ArrayList<Personne> TrieID(ArrayList<Personne> Personnes, ArrayList<Integer> IDs){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(IDs.contains(pers.getID())){
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction du sujet de thèse et renvoie celles dont le sujet de thèse correspond à ceux
     * spécifiés dans la liste de sujets.
     * @param Personnes Liste des personnes à trier.
     * @param sujets Liste des sujets de thèse à vérifier.
     * @return Liste des personnes dont le sujet de thèse correspond.
     */
    public static ArrayList<Personne> TrieSujetdeThese(ArrayList<Personne> Personnes, ArrayList<String> sujets){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Etudiant etudiant){ 
                if(sujets.contains(etudiant.getSujetDeThese().toLowerCase())){
                    AVoir.add(pers);
                }
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction des mots clés de leur sujet de thèse (au moins un des mots clés).
     * @param Personnes Liste des personnes à trier.
     * @param MotCle Liste des mots clés à vérifier.
     * @return Liste des personnes dont le sujet de thèse contient au moins un des mots clés.
     */
    public static ArrayList<Personne> TrieSujetdeTheseMotclOU(ArrayList<Personne> Personnes, ArrayList<String> MotCle){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Etudiant etudiant){
                if(etudiant.contientMotCleOUThese(MotCle)){
                    AVoir.add(pers);
                }
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction des mots clés de leur sujet de thèse (tous les mots clés doivent être présents).
     * @param Personnes Liste des personnes à trier.
     * @param MotCle Liste des mots clés à vérifier.
     * @return Liste des personnes dont le sujet de thèse contient tous les mots clés.
     */
    public static ArrayList<Personne> TrieSujetdeTheseMotclET(ArrayList<Personne> Personnes, ArrayList<String> MotCle){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Etudiant etudiant){ 
                if(etudiant.contientMotCleETThese(MotCle)){
                    AVoir.add(pers);
                }
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de l'année de leur thèse et renvoie celles dont l'année de thèse est comprise
     * entre les années spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param annee1 Première année à vérifier.
     * @param annee2 Deuxième année à vérifier.
     * @param annee3 Troisième année à vérifier.
     * @return Liste des personnes dont l'année de thèse est comprise entre annee1, annee2 et annee3.
     */
    public static ArrayList<Personne> TrieAnneedeThese(ArrayList<Personne> Personnes, int annee1, int annee2, int annee3){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Etudiant etudiant){ 
                int AnneeDeThese = etudiant.getAnneeDeThese();
                if(AnneeDeThese == annee1 || AnneeDeThese == annee2 || AnneeDeThese == annee3){
                    AVoir.add(pers);
                }
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction du nombre d'étudiants pour chaque chercheur et renvoie celles dont le nombre
     * d'étudiants est compris entre les deux valeurs spécifiées.
     * @param Personnes Liste des personnes à trier.
     * @param nbretudiantmin Limite inférieure du nombre d'étudiants.
     * @param nbretudiantmax Limite supérieure du nombre d'étudiants.
     * @return Liste des personnes dont le nombre d'étudiants est compris entre nbretudiantmin et nbretudiantmax.
     */
    public static ArrayList<Personne> TrieNbrEtudiant(ArrayList<Personne> Personnes, double nbretudiantmin, double nbretudiantmax){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Chercheur chercheur){ 
                int nbretudiant = chercheur.getNbrEtudiant();
                if(nbretudiantmax == -1 && nbretudiant >= nbretudiantmin){
                    AVoir.add(pers);
                }
                else if(nbretudiantmin == -1 && nbretudiant <= nbretudiantmax){
                    AVoir.add(pers);
                }
                else if(nbretudiantmax >= nbretudiantmin && nbretudiant <= nbretudiantmax){
                    AVoir.add(pers);
                }
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction des étudiants du chercheur et renvoie celles ayant au moins un étudiant (OU) 
     * ou tous les étudiants (ET) selon le critère.
     * @param Personnes Liste des personnes à trier.
     * @param Etudiants Liste des étudiants à vérifier.
     * @return Liste des personnes dont les étudiants correspondent au critère (OU/ET).
     */
    public static ArrayList<Personne> TrieTitulaireEtudiantOU(ArrayList<Personne> Personnes, ArrayList<Personne> Etudiants){
        ArrayList<Personne> AVoir = new ArrayList<>();
        ArrayList<Etudiant> E = convertionEtudiants(Etudiants);
        for(Personne pers : Personnes){
            if(pers instanceof Titulaire titulaire && titulaire.ContientEtudiantOU(E)){ 
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction des étudiants du chercheur et renvoie celles ayant tous les étudiants (ET) 
     * correspondant au critère.
     * @param Personnes Liste des personnes à trier.
     * @param Etudiants Liste des étudiants à vérifier.
     * @return Liste des personnes dont tous les étudiants correspondent au critère.
     */
    public static ArrayList<Personne> TrieTitulaireEtudiantET(ArrayList<Personne> Personnes, ArrayList<Personne> Etudiants){
        ArrayList<Personne> AVoir = new ArrayList<>();
        ArrayList<Etudiant> E = convertionEtudiants(Etudiants);
        for(Personne pers : Personnes){
            if(pers instanceof Titulaire titulaire && titulaire.ContientEtudiantET(E)){
                AVoir.add(pers);
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction du numéro de bureau et renvoie celles ayant un numéro de bureau parmi ceux spécifiés.
     * @param Personnes Liste des personnes à trier.
     * @param NumBureau Liste des numéros de bureau à vérifier.
     * @return Liste des personnes ayant un numéro de bureau parmi ceux spécifiés.
     */
    public static ArrayList<Personne> TrieNumBureau(ArrayList<Personne> Personnes, ArrayList<Integer> NumBureau){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Titulaire titulaire){ 
                if (NumBureau.contains(titulaire.getNumBureau())){ 
                    AVoir.add(pers);
                }
            }
        }
        return AVoir;
    }

    /**
     * Trie les personnes en fonction de l'encadrant des etudiants et renvoie celles ayant un encadrant parmi ceux spécifiés.
     * @param Personnes Liste des personnes à trier.
     * @param Encadrants Liste des encadrants à vérifier.
     * @return Liste des personnes ayant un encadrant parmi ceux spécifiés.
     */
    public static ArrayList<Personne> TrieEtudiantEncadrant(ArrayList<Personne> Personnes, ArrayList<Personne> Encadrants){
        ArrayList<Personne> AVoir = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Etudiant etudiant){
                if (Encadrants.contains(etudiant.getEncadrant())){ 
                    AVoir.add(pers);
                }
            }
        }
        return AVoir;
    }
    
    // Conversion de type 
    
    /**
     * Convertit une liste de personnes en une liste d'étudiants.
     * @param Personnes Liste des personnes à convertir.
     * @return Liste des étudiants extraits de la liste de personnes.
     */
    public static ArrayList<Etudiant> convertionEtudiants(ArrayList<Personne> Personnes){
        ArrayList<Etudiant> resulat = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Etudiant etudiant){
                resulat.add(etudiant);
            }
        }
        return resulat;
    }

    /**
     * Convertit une liste de personnes en une liste de titulaires.
     * @param Personnes Liste des personnes à convertir.
     * @return Liste des titulaires extraits de la liste de personnes.
     */
    public static ArrayList<Titulaire> convertionTitulaires(ArrayList<Personne> Personnes){
        ArrayList<Titulaire> resulat = new ArrayList<>();
        for(Personne pers : Personnes){
            if(pers instanceof Titulaire titulaire){
                resulat.add(titulaire);
            }
        } 
        return resulat;
    }

    /**
     * Convertit une liste de personnes en une liste de titulaires ayant des étudiants.
     * @param personnes Liste des personnes à convertir.
     * @return Liste des titulaires ayant des étudiants.
     */
    public static ArrayList<Titulaire> conversionTitulaireAvecEtudiant(ArrayList<Personne> personnes){
        ArrayList<Titulaire> titulaires = convertionTitulaires(personnes);
        ArrayList<Titulaire> t = new ArrayList<>();
        for(Titulaire ti : titulaires){
            if(ti instanceof MCF m && m.getEtudiant() != null){
                t.add(ti);
            }
            else if(ti instanceof Chercheur c && c.getNbrEtudiant() > 0){
                t.add(ti);
            }
        }
        return t;
    }
    
    /**
     * Trie les personnes en fonction de leur ville et les classe dans une liste par ville.
     * @param personnesSelection Liste des personnes à trier.
     * @param villesSelection Liste des villes dans lesquelles trier les personnes.
     * @return Liste des listes de personnes, classées par ville.
     */
    public static ArrayList<ArrayList<Personne>> personnesParVille(ArrayList<Personne> personnesSelection, ArrayList<Ville> villesSelection){
        ArrayList<ArrayList<Personne>> listeFinale = new ArrayList<>();
        for (int i = 0; i < villesSelection.size(); i++){
            listeFinale.add(new ArrayList<>());
        }
        for (Personne p: personnesSelection){
            int i = villesSelection.indexOf(p.getVille()); // Sous l'hypothese que la ville de personne est forcement dans la liste des villes du chemin
            if (i != -1){
                listeFinale.get(i).add(p);
            }
        }
        return listeFinale;
    }
}