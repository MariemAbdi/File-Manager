
package javafxapplication2;


public class nb_files {
    String login;
     String prenom;
     String nom;
     int nbfiles; 
     
     public nb_files(String login, String nom, String prenom, int nbfiles) {
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.nbfiles = nbfiles;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbfiles(int nbfiles) {
        this.nbfiles = nbfiles;
    }

    public String getLogin() {
        return login;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getNbfiles() {
        return nbfiles;
    }
}
