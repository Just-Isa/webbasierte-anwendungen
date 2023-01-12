package de.hsrm.mi.web.projekt.gebot;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilServiceImpl;
import de.hsrm.mi.web.projekt.messaging.BackendInfoServiceImpl;
import de.hsrm.mi.web.projekt.messaging.BackendOperation;

@Service
public class GebotServiceImpl implements GebotService {

    @Autowired private GebotRepository gebotRepo;
    @Autowired private BenutzerprofilServiceImpl benutzerService;
    @Autowired private BackendInfoServiceImpl backendInfoService;

    @Override
    public List<Gebot> findeAlleGebote() {
        return this.gebotRepo.findAll();
    }

    @Override
    public List<Gebot> findeAlleGeboteFuerAngebot(long angebotid) {
        Optional<Angebot> angebot = this.benutzerService.findeAngebotMitId(angebotid);
        if(angebot.isPresent()){
            return angebot.get().getGebote();
        }else {
            return Collections.emptyList();
        }
    }

    @Override
    public Gebot bieteFuerAngebot(long benutzerprofilid, long angebotid, long betrag) {
        Gebot neuGebot = new Gebot();
        neuGebot.setBetrag(betrag);
        Optional<BenutzerProfil> profil = this.benutzerService.holeBenutzerProfilMitId(benutzerprofilid);
        Optional<Angebot> angebot = this.benutzerService.findeAngebotMitId(angebotid); 
        Optional<Gebot> altesGebot = this.gebotRepo.findByAngebotIdAndBieterId(angebotid, benutzerprofilid);
        if (altesGebot.isPresent()) {
            altesGebot.get().setBetrag(betrag);
            altesGebot.get().setGebotZeitpunkt(LocalDateTime.now());
            this.gebotRepo.save(altesGebot.get());
            return altesGebot.get();
        } else {
            if(profil.isPresent()) {
                profil.get().addGebote(neuGebot);
                angebot.get().addGebote(neuGebot);
                neuGebot.setGebieter(profil.get());
                neuGebot.setAngebot(angebot.get());
                this.gebotRepo.save(neuGebot);
                this.backendInfoService.sendInfo("/topic/gebot/angebotid", BackendOperation.UPDATE, neuGebot.getId());
                return neuGebot;
            } else {
                return null;
            }
        }
    }

    @Override
    public void loescheGebot(long gebotid) {
        Gebot gebot = this.gebotRepo.getById(gebotid);
        gebot.getGebieter().delGebote(gebot);
        gebot.getAngebot().delGebote(gebot);
        this.gebotRepo.deleteById(gebotid);
    }
    
}
