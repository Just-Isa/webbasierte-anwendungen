package de.hsrm.mi.web.projekt.projektuser;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;

@Entity
public class ProjektUser implements Serializable{

    @Id
    @NotBlank(message = "Darf nicht leer sein")
    @Size(min = 3, message = "Mindestens 3 Zeichen lang")
    private String username;

    @NotBlank(message = "Darf nicht leer sein")
    @Size(min = 3, message = "Mindestens 3 Zeichen lang")
    private String password;
    
    private String role;
    
    @OneToOne
    private BenutzerProfil benutzerProfil;
    
    public ProjektUser(String username, String password, String role) {
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public ProjektUser() {
        this.username = "";
        this.password = "";
        this.role = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BenutzerProfil getBenutzerProfil() {
        return benutzerProfil;
    }

    public void setBenutzerProfil(BenutzerProfil benutzerProfil) {
        this.benutzerProfil = benutzerProfil;
    }
}
