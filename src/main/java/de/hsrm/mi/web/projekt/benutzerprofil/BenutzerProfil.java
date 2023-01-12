package de.hsrm.mi.web.projekt.benutzerprofil;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.gebot.Gebot;
import de.hsrm.mi.web.projekt.projektuser.ProjektUser;
import de.hsrm.mi.web.projekt.validierung.Bunt;

@Entity
public class BenutzerProfil implements Serializable{
    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Size(min = 3, max = 60)
    @NotNull @NotBlank
    private String name;

    @DateTimeFormat(iso = ISO.DATE)
    @PastOrPresent @NotNull
    private LocalDate geburtsdatum;
    
    @NotNull
    private String adresse;

    @OneToMany(mappedBy="anbieter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Angebot> angebote;

    @OneToMany(mappedBy="gebieter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Gebot> gebote;

    @OneToOne(mappedBy = "benutzerProfil", cascade = CascadeType.REMOVE)
    private ProjektUser projektUser;

    private double lat;

    private double lon;
    
    @Email
    private String email;
    
    @Bunt(message = "{profil.buntfehler}")
    private String lieblingsfarbe;
    
    @NotNull
    private String interessen;


    public BenutzerProfil() {
        this.name = "";
        this.geburtsdatum = LocalDate.of(1,1,1);
        this.adresse = "";
        this.email = null;
        this.lieblingsfarbe = "";
        this.interessen = "";
        this.angebote = new ArrayList<Angebot>();
        this.gebote = new ArrayList<Gebot>();
    }
    
    public BenutzerProfil(String name, LocalDate geburtsdatum, String adresse, String email, String lieblingsfarbe,
            String interessen) {
        this.name = name;
        this.geburtsdatum = geburtsdatum;
        this.adresse = adresse;
        this.email = email;
        this.lieblingsfarbe = lieblingsfarbe;
        this.interessen = interessen;
    }

    public List<String> getInteressenListe() 
    {
        String interessenListe = this.getInteressen();
        if (interessenListe == null) {
            return Collections.emptyList();
        }
        List<String> interessenList = Arrays.asList(getInteressen().split("\\s*,\\s*"));
        return interessenList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }
    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLieblingsfarbe() {
        return lieblingsfarbe;
    }
    public void setLieblingsfarbe(String lieblingsfarbe) {
        this.lieblingsfarbe = lieblingsfarbe;
    }
    public String getInteressen() {
        if(this.interessen.equals("")) {
            return null;
        }
        return interessen;
    }
    public void setInteressen(String interessen) {
        this.interessen = interessen;
    }  
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geburtsdatum == null) ? 0 : geburtsdatum.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BenutzerProfil other = (BenutzerProfil) obj;
        if (geburtsdatum == null) {
            if (other.geburtsdatum != null)
                return false;
        } else if (!geburtsdatum.equals(other.geburtsdatum))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BenutzerProfil [adresse=" + adresse + ", email=" + email + ", geburtsdatum=" + geburtsdatum + ", id="
                + id + ", interessen=" + interessen + ", lat=" + lat + ", lieblingsfarbe=" + lieblingsfarbe + ", lon="
                + lon + ", name=" + name + ", version=" + version + "]";
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public List<Angebot> getAngebote() {
        return this.angebote;
    }

    public void addAngebote(Angebot angebot) {
        this.angebote.add(angebot);
    }

    public void delAngebote(Angebot angebot) {
        this.angebote.remove(angebot);
    }

    public void setAngebote(List<Angebot> angebote) {
        this.angebote = angebote;
    }

    public List<Gebot> getGebote() {
        return gebote;
    }

    public void setGebote(List<Gebot> gebote) {
        this.gebote = gebote;
    }

    public void addGebote(Gebot gebot) {
        this.gebote.add(gebot);
    }

    public void delGebote(Gebot gebot) {
        this.gebote.remove(gebot);
    }

    public ProjektUser getProjektUser() {
        return projektUser;
    }

    public void setProjektUser(ProjektUser projektUser) {
        this.projektUser = projektUser;
    }

    
}
