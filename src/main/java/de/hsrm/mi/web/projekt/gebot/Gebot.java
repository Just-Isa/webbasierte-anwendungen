package de.hsrm.mi.web.projekt.gebot;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;

@JsonIdentityInfo (
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property="id"
)
@Entity
public class Gebot implements Serializable{

    @Id @GeneratedValue
    private long id;

    @Version
    private long version;

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private BenutzerProfil gebieter;

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Angebot angebot;

    private long betrag;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime gebotZeitpunkt;

    public Gebot() {
        this.gebotZeitpunkt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((angebot == null) ? 0 : angebot.hashCode());
        result = prime * result + (int) (betrag ^ (betrag >>> 32));
        result = prime * result + ((gebieter == null) ? 0 : gebieter.hashCode());
        result = prime * result + ((gebotZeitpunkt == null) ? 0 : gebotZeitpunkt.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (version ^ (version >>> 32));
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
        Gebot other = (Gebot) obj;
        if (angebot == null) {
            if (other.angebot != null)
                return false;
        }
        if (betrag != other.betrag)
            return false;
        if (gebieter == null) {
            if (other.gebieter != null)
                return false;
        }
        if (gebotZeitpunkt == null) {
            if (other.gebotZeitpunkt != null)
                return false;
        }
        if (id != other.id)
            return false;
        return true;
    }

    public BenutzerProfil getGebieter() {
        return gebieter;
    }

    public void setGebieter(BenutzerProfil gebieter) {
        this.gebieter = gebieter;
    }

    public Angebot getAngebot() {
        return angebot;
    }

    public void setAngebot(Angebot angebot) {
        this.angebot = angebot;
    }

    public long getBetrag() {
        return betrag;
    }

    public void setBetrag(long betrag) {
        this.betrag = betrag;
    }

    public LocalDateTime getGebotZeitpunkt() {
        return gebotZeitpunkt;
    }

    public void setGebotZeitpunkt(LocalDateTime gebotZeitpunkt) {
        this.gebotZeitpunkt = gebotZeitpunkt;
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }
}
