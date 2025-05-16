package representationSolution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import peuple.Personne;
import territoire.Ville;

/**
 * Cette classe permet de generer un fichier HTML representant le meilleur chemin genere
 * pour visiter un ensemble de villes et de personnes, a partir d'une ville de depart specifique.
 */
public class FichierHTMLGenerateur {
	
	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private FichierHTMLGenerateur() {
		
	}
	
	/**
     * Genere un fichier HTML contenant le chemin optimal.
     *
     * @param selection Liste des personnes a visiter.
     * @param villesAvisiter Liste des villes a visiter.
     * @param nomFichier Nom du fichier dans lequel ecrire la representation.
     */
	public static void genererFichierHTML(ArrayList<Personne> selection, ArrayList<Ville> villesAvisiter, String nomFichier) {
		try {
            // Créer le fichier HTML
            File htmlFile = new File(nomFichier);
            if (!htmlFile.exists()) {
                htmlFile.createNewFile();
            }

            // Écrire le contenu dans le fichier HTML
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile))) {
                writer.write("<html>\n");
                writer.write("<head><title>Liste des Personnes</title></head>\n");
                writer.write("<body>\n");
                writer.write("<h1>Liste des Personnes</h1>\n");

                for (Personne pers : selection) {
                    writer.write("<p>" + pers.toString() + "</p>\n"); // Utilise la méthode toString de Personne
                }
                // Ajouter la liste des villes
                writer.write("<h1>Itineraire des Villes</h1>\n");
                if (!villesAvisiter.isEmpty()) {
                    writer.write("<p>");
                    for (int i = 0; i < villesAvisiter.size(); i++) {
                        writer.write(villesAvisiter.get(i).getNom());
                        if (i < villesAvisiter.size() - 1) {
                            writer.write(" -> ");
                        }
                    }
                    writer.write("</p>\n");
                } else {
                    writer.write("<p>Aucune ville a visiter.</p>\n");
                }

                writer.write("</body>\n");
                writer.write("</html>\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Erreur lors de la création du fichier HTML.");
        }
	}
}
