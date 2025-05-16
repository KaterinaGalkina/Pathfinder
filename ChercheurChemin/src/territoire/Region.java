package territoire;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration Region representant les differentes regions de France. 
 * Chaque region a un chef-lieu, une superficie en km carres, une estimation de population pour 2024, 
 * et une liste de codes de departements qui sont dans cette region.
 */
public enum Region {
	// Liste de tous les regions de France
	
	/**
     * Auvergne-Rhone-Alpes.
     */
    AUVERGNE_RHONE_ALPES(new Ville("Lyon", "69000", 516092, 47.87, "69"), 
        69711, 
        8235923, 
        List.of("01", "03", "07", "15", "26", "38", "42", "43", "63", "69", "73", "74")),

    /**
     * Bourgogne-Franche-Comte.
     */
    BOURGOGNE_FRANCHE_COMTE(new Ville("Dijon", "21000", 158002, 40.41, "21"), 
        47784, 
        2800194, 
        List.of("21", "25", "39", "58", "70", "71", "89", "90")),

    /**
     * Bretagne.
     */
    BRETAGNE(new Ville("Rennes", "35000", 222485, 50.39, "35"), 
        27208, 
        3453023, 
        List.of("22", "29", "35", "56")),

    /**
     * Centre-Val-de-Loire.
     */
    CENTRE_VAL_DE_LOIRE(new Ville("Orleans", "45000", 116238, 27.48, "45"), 
        39151, 
        2573295, 
        List.of("18", "28", "36", "37", "41", "45")),

    /**
     * Corse.
     */
    CORSE(new Ville("Ajaccio", "20000", 71000, 82.03, "2A"), 
        8680, 
        355528, 
        List.of("2A", "2B")),

    /**
     * Grand Est.
     */
    GRAND_EST(new Ville("Strasbourg", "67000", 284677, 78.26, "67"), 
        57441, 
        5568711, 
        List.of("08", "10", "51", "52", "54", "55", "57", "67", "68", "88")),

    /**
     * Hauts-de-France.
     */
    HAUTS_DE_FRANCE(new Ville("Lille", "59000", 232787, 34.8, "59"), 
        31806, 
        5983823, 
        List.of("02", "59", "60", "62", "80")),
    
    /**
     * Ile-de-France.
     */
    ILE_DE_FRANCE(new Ville("Paris", "75000", 2148327, 105.4, "75"),    
        12012, 
        12419961, 
        List.of("75", "77", "78", "91", "92", "93", "94", "95")),
    
    /**
     * Normandie.
     */
    NORMANDIE(new Ville("Rouen", "76000", 111553, 21.38, "76"), 
        29907, 
        3327077, 
        List.of("14", "27", "50", "61", "76")),
    
    /**
     * Nouvelle-Aquitaine.
     */
    NOUVELLE_AQUITAINE(new Ville("Bordeaux", "33000", 257068, 49.36, "33"), 
        84036, 
        6154772, 
        List.of("16", "17", "19", "23", "24", "33", "40", "47", "64", "79", "86", "87")),
    
    /**
     * Occitanie.
     */
    OCCITANIE(new Ville("Toulouse", "31000", 493465, 118.3, "31"), 
        72724, 
        6154729, 
        List.of("09", "11", "12", "30", "31", "32", "34", "46", "48", "65", "66", "81", "82")),
    
    /**
     * Pays-de-la-Loire.
     */
    PAYS_DE_LA_LOIRE(new Ville("Nantes", "44000", 314138, 65.19, "44"), 
        32082, 
        3926389, 
        List.of("44", "49", "53", "72", "85")),
    
    /**
     * Provence-Alpes-Cote-d'Azur.
     */
    PROVENCE_ALPES_COTE_D_AZUR(new Ville("Marseille", "13000", 870018, 240.62, "13"), 
        31400, 
        5198011, 
        List.of("04", "05", "06", "13", "83", "84"));

    private final Ville chefLieu;
    private final int superficie;
    private final int populationEstimee2024;
    private final List<String> departements;

    // Le constructuer de l'enumeration
    Region(Ville chefLieu, int superficie, int populationEstimee2024, List<String> departements) {
        this.chefLieu = chefLieu;
        this.superficie = superficie;
        this.populationEstimee2024 = populationEstimee2024;
        this.departements = departements;
    }

    /**
     * Retourne le chef-lieu de la region.
     * 
     * @return Le chef-lieu sous forme d'objet {@link Ville}.
     */
    public Ville getChefLieu() {
        return this.chefLieu;
    }

    /**
     * Retourne la superficie totale de la region en kilometres carres.
     * 
     * @return La superficie de la region.
     */
    public int getSuperficie() {
        return this.superficie;
    }

    /**
     * Retourne la population estimee pour l'annee 2024.
     * 
     * @return La population estimee de la region.
     */
    public int getPopulationEstimee2024() {
        return this.populationEstimee2024;
    }

    /**
     * Retourne la liste des codes departementaux de la region.
     * 
     * @return Une liste de chaines de caracteres representant les codes departementaux.
     */
    public List<String> getDepartements() {
        return this.departements;
    }

    /**
     * Trouve la region correspondant a une ville donnee.
     * 
     * @param ville La ville pour laquelle il faut trouver la region.
     * @return La region de type {@link Region} contenant la ville passee entre parametres ou {@code null} si la region n'a pas ete trouvee.
     */
    public static Region trouverRegion(Ville ville) {
        for (Region region : Region.values()) {
            if (region.getDepartements().contains(ville.getDepartement())) {
                return region;
            }
        }
        return null; 
    }
    
    /**
     * Retourne une liste contenant toutes les regions sous forme d'une liste.
     * 
     * @return Une {@code ArrayList<Region>} contenant toutes les regions.
     */
    public static ArrayList<Region> transformerEnListe(){
    	ArrayList<Region> regions = new ArrayList<>();
    	for (Region region : Region.values()) {
    		regions.add(region);
        }
    	return regions;
    }
    
    /**
     * Retourne une representation sous forme de chaine de caracteres de la region,
     * incluant tous les informations disponibles sur ce region.
     * 
     * @return Une chaine de caracteres representant la region.
     */
    @Override
    public String toString() {
    	return("Nom = " + this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase() + //
                "\n\n\tChef-lieu = " + this.chefLieu.getNom() + ", " + //
                "\tSuperficie = " + this.superficie + "km2, " + //
                "\tPopulation = " + this.populationEstimee2024 + ", " + //
                "\n\tDépartements = " + this.departements + " \n\n");
    }
    
    /**
     * Affiche dans la console une representation de toutes les regions avec tous les informations.
     */
    public static void afficherRegions() {
    	for(Region reg: Region.values()) {
    		System.out.println(reg.toString());
    	}
    }
}
