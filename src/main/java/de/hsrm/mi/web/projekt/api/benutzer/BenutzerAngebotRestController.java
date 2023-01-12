package de.hsrm.mi.web.projekt.api.benutzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.api.gebot.GetGebotResponseDTO;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilServiceImpl;
import de.hsrm.mi.web.projekt.gebot.Gebot;


@RestController
@RequestMapping("/api")
public class BenutzerAngebotRestController {

    Logger logger = LoggerFactory.getLogger(BenutzerprofilServiceImpl.class);
    
    @Autowired private BenutzerprofilServiceImpl benutzerService;
    
    @GetMapping(value = "/angebot", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GetAngebotResponseDTO> getAlleAngebote() {
        List<GetAngebotResponseDTO> alleAngeboteAsDto = new ArrayList<GetAngebotResponseDTO>();
        for(Angebot angebot : this.benutzerService.alleAngebote()) {
            alleAngeboteAsDto.add(GetAngebotResponseDTO.from(angebot));
        }
        logger.warn("HIER WAR ICH! = {}", alleAngeboteAsDto);
        return alleAngeboteAsDto;
    }

    @GetMapping(value="/angebot/{id}")
    public GetAngebotResponseDTO getAngebotPerId(@PathVariable("id") long id) {
        return GetAngebotResponseDTO.from(this.benutzerService.findeAngebotMitId(id).get());
    }

    @GetMapping(value="/angebot/{id}/gebot")
    public List<GetGebotResponseDTO> getAllGebotePerAngebotId(@PathVariable("id") long id) {
        List <GetGebotResponseDTO> allGeboteMitAngebotId = new ArrayList<GetGebotResponseDTO>();
        Optional<Angebot> angebot = this.benutzerService.findeAngebotMitId(id);
        if(angebot.isPresent()) {
            for(Gebot gebot : angebot.get().getGebote()) {
                allGeboteMitAngebotId.add(GetGebotResponseDTO.from(gebot));
            }
        }
        return null;

    }

    @GetMapping(value="/benutzer/{id}/angebot")
    public List<GetAngebotResponseDTO> getAllAngebotePerBenutzerId(@PathVariable("id") long id) {
        List<GetAngebotResponseDTO> angeboteFromId = new ArrayList<GetAngebotResponseDTO>();
        for(Angebot a : this.benutzerService.holeBenutzerProfilMitId(id).get().getAngebote()) {
            angeboteFromId.add(GetAngebotResponseDTO.from(a));
        }
        return angeboteFromId;
    }
    
    
    

}
