
package javafxapplication2;


public class users_Admin {
     String login;
     String nom;
     String prenom;
     String mail;
     String genre;

    public users_Admin(String login, String nom, String prenom, String mail, String genre) {
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.genre = genre;
    }

    public String getLogin() {
        return login;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getGenre() {
        return genre;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    

    
}
