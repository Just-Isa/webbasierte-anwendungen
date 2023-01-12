package de.hsrm.mi.web.projekt.api.gebot;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.gebot.Gebot;
import de.hsrm.mi.web.projekt.gebot.GebotServiceImpl;

@RestController
@RequestMapping("/api")
public class GebotRestController {
    Logger logger = LoggerFactory.getLogger(GebotRestController.class);
    
    @Autowired GebotServiceImpl gebotService;

    @GetMapping(value="/gebot", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<GetGebotResponseDTO> getGebotDtos() {
        List<GetGebotResponseDTO> gebotDto = new ArrayList<GetGebotResponseDTO>();
        for(Gebot gebot : this.gebotService.findeAlleGebote()) {
            gebotDto.add(GetGebotResponseDTO.from(gebot));
        }
        return gebotDto;
    }

    @PostMapping(value="/gebot", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public long newGebotPerDto(@RequestBody AddGebotRequestDTO newGebot) {
        logger.warn("newGebot = {}", newGebot);
        Gebot gebot = this.gebotService.bieteFuerAngebot(newGebot.benutzerprofilid(), newGebot.angebotid(), newGebot.angebotid());
        return gebot.getId();
    }

    @DeleteMapping("/gebot/{id}")
    public void deleteGebot(@PathVariable("id") long id) {
        this.gebotService.loescheGebot(id);
    }
}
