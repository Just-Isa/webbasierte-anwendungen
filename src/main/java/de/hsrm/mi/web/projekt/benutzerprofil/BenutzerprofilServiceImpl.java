package de.hsrm.mi.web.projekt.benutzerprofil;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.angebot.AngebotRepository;
import de.hsrm.mi.web.projekt.geo.AdressInfo;
import de.hsrm.mi.web.projekt.geo.GeoServiceImpl;

@Service
public class BenutzerprofilServiceImpl implements BenutzerprofilService
{
    private BenutzerprofilRepository benutzerRepo;
    private AngebotRepository angebotRepository;
    private GeoServiceImpl geoService;
    Logger logger = LoggerFactory.getLogger(BenutzerprofilServiceImpl.class);

    public BenutzerprofilServiceImpl(BenutzerprofilRepository bR, GeoServiceImpl gS, AngebotRepository aR)
    {
        this.benutzerRepo = bR;
        this.geoService = gS;
        this.angebotRepository = aR;
    }

    @Override
    public BenutzerProfil speichereBenutzerProfil(BenutzerProfil bp) {
        
        logger.info("Benutzer gespeichert. Info: " + bp.toString());
        List<AdressInfo> where = this.geoService.findeAdressInfo(bp.getAdresse());
        if(where.isEmpty()) {
            bp.setLat(0);
            bp.setLon(0);
        }else {
            bp.setLat(where.get(0).lat());
            bp.setLon(where.get(0).lon());
        }
        BenutzerProfil savedBp = this.benutzerRepo.save(bp);
        return savedBp;
    }

    @Override
    public Optional<BenutzerProfil> holeBenutzerProfilMitId(Long id) {
        Optional<BenutzerProfil> benutzerProfilOpt = this.benutzerRepo.findById(id) ;
        if(benutzerProfilOpt.isPresent()) {
            logger.warn("Benutzer mit id "+ id +" wurde gefunden. Benutzerinfo: " +benutzerProfilOpt.get().toString());
        } else {
            logger.warn("Benutzer mit der id " + id + " wurde nicht gefunden.");
        }
        return benutzerProfilOpt;
    }

    @Override
    public List<BenutzerProfil> alleBenutzerProfile() {
        logger.info("Alle Benutzerprofile werden geholt.");
        return this.benutzerRepo.findAll(Sort.by("name"));
    }

    @Override
    public void loescheBenutzerProfilMitId(Long loesch) {
        logger.info("Benutzer mit der id " + loesch + " wurde geloescht.");
        this.benutzerRepo.deleteById(loesch);
    }

    @Override
    public void fuegeAngebotHinzu(long id, Angebot angebot) {

        Optional<BenutzerProfil> currPro = this.holeBenutzerProfilMitId(id);
        List<AdressInfo> where = this.geoService.findeAdressInfo(angebot.getAbholort());
        if(currPro.isEmpty()) {
            angebot.setLat(0);
            angebot.setLon(0);
            logger.warn("Benutzerprofil nicht present!");
        }else {
            angebot.setLat(where.get(0).lat());
            angebot.setLon(where.get(0).lon());
            currPro.get().addAngebote(angebot);
            angebot.setAnbieter(currPro.get());
            this.benutzerRepo.save(currPro.get());
        }        
    }

    @Override
    public void loescheAngebot(long id) {
        Angebot angebot = this.angebotRepository.getById(id);
        BenutzerProfil benutzer = angebot.getAnbieter();
        benutzer.delAngebote(angebot);
        this.angebotRepository.deleteById(id);        
    }

    @Override
    public List<Angebot> alleAngebote() {
        return this.angebotRepository.findAll();
    }

    @Override
    public Optional<Angebot> findeAngebotMitId(long angebotid) {
        return this.angebotRepository.findById(angebotid);
    }    
}
