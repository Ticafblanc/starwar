package ca.collegelacite.starwars;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

// Classe implantant un descriptif de personnage de film
public class Personnage {
    private String nom = null;         // nom du personnage
    private String descriptif = null;  // description textuelle du personnage
    private String wikiUrl = null;     // URL de la page Wiki du personnage
    private String photo = null;       // image locale

    private static Context contexte = null;

    // Constructeur par défaut
    public Personnage() {
        this.setNom(null);
        this.setDescriptif(null);
        this.setWikiUrl(null);
        this.setPhoto(null);
    }

    // Accesseur de l'attribut nom
    public String getNom() {
        return nom;
    }

    // Mutateur de l'attribut nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Accesseur de l'attribut wikiUrl
    public String getWikiUrl() {
        return wikiUrl;
    }

    // Mutateur de l'attribut wikiUrl
    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    // Accesseur de l'attribut descriptif
    public String getDescriptif() {
        return descriptif;
    }

    // Mutateur de l'attribut descriptif
    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    // Accesseur de l'attribut photo
    public String getPhoto() {
        return photo;
    }

    // Mutateur de l'attribut photo
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // Méthode pour placer l'image dans l'image View passée en paramètre
    public void photoDansImageView(ImageView iv) {
        String uri = "@drawable/" + this.getPhoto().toLowerCase();

        int imageResource = contexte.getResources().getIdentifier(uri, null, contexte.getPackageName());

        Drawable res = contexte.getDrawable(imageResource);
        iv.setImageDrawable(res);
    }

    // Retourne une chaîne décrivant brièvement le personnage
    @Override
    public String toString() {
        return this.getNom();
    }

    // Désérialiser une liste de personnages d'un fichier JSON
    public static ArrayList<Personnage> lireFichier(String nomFichier, Context ctx){
        final ArrayList<Personnage> liste = new ArrayList<>();
        contexte = ctx;

        try {
            // Charger les données dans un ArrayList
            String jsonString  = lireJson(nomFichier, contexte);
            JSONObject json        = new JSONObject(jsonString);
            JSONArray personnages = json.getJSONArray("personnages");

            // Lire chaque personnage du fichier
            for(int i = 0; i < personnages.length(); i++){
                Personnage p = new Personnage();

                p.nom        = personnages.getJSONObject(i).getString("nom");
                p.descriptif = personnages.getJSONObject(i).getString("description");
                p.wikiUrl    = personnages.getJSONObject(i).getString("wiki");
                p.photo      = personnages.getJSONObject(i).getString("photo");

                liste.add(p);
            }
        } catch (JSONException e) {
            // Une erreur s'est produite (on la journalise)
            e.printStackTrace();
        }

        return liste;
    }

    // Retourne une balise lue d'un fichier JSON
    private static String lireJson(String nomFichier, Context contexte) {
        String json = null;

        try {
            InputStream is = contexte.getAssets().open(nomFichier);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            // Une erreur s'est produite (on la journalise)
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
