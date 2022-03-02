
package javafxapplication2;


public class users {
    String login;
     String pass;
     String nom;
     String prenom;
     String mail;
    
public users(String login, String pass, String nom, String prenom, String mail) {
        this.login = login;
        this.pass = pass;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }

  
    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }

    public String getpass() {
        return pass;
    }

    public void setpass(String pass) {
        this.pass = pass;
    }

    public String getnom() {
        return nom;
    }

    public void setnom(String nom) {
        this.nom = nom;
    }

    public String getprenom() {
        return prenom;
    }

    public void setprenom(String prenom) {
        this.prenom = prenom;
    }

    public String getmail() {
        return mail;
    }

    public void setmail(String mail) {
        this.mail = mail;
    }

}
