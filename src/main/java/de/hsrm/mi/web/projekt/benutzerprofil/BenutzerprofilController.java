package de.hsrm.mi.web.projekt.benutzerprofil;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.messaging.BackendInfoServiceImpl;
import de.hsrm.mi.web.projekt.messaging.BackendOperation;
import de.hsrm.mi.web.projekt.projektuser.ProjektUser;
import de.hsrm.mi.web.projekt.projektuser.ProjektUserServiceImpl;

@Controller
@SessionAttributes(names = {"profil"})
@RequestMapping("benutzerprofil")
public class BenutzerprofilController {
    @Autowired private BenutzerprofilServiceImpl benutzerprofilService;
    @Autowired private BackendInfoServiceImpl backendInfoService;
    @Autowired private ProjektUserServiceImpl projektUserService;
    
    Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);

    @ModelAttribute("profil")
    
    public void initProfil(Model m, Principal principal) {
        if (principal != null) {
            logger.error("principal = {}", principal);
            ProjektUser user = this.projektUserService.findeBenutzer(principal.getName());
            BenutzerProfil profil = user.getBenutzerProfil();
            m.addAttribute("profil", profil);
        } else {
            m.addAttribute("profil", new BenutzerProfil());
        }
    }

    @GetMapping()
    public String showBenutzerprofil(Model m)
    {
        logger.info("profil IM CONTROLLER AAAA = {}", m);
        return "benutzerprofil/profilansicht";
    }

    @GetMapping("/bearbeiten")
    public String showBenutzerEditor(Model m) 
    {
        logger.info("profil = {}", m);
        return ("benutzerprofil/profileditor");
    }

    @PostMapping("/bearbeiten")
    public String bearbeiten(Model m,
                            @Valid @ModelAttribute("profil") BenutzerProfil profil,
                            BindingResult profilErrors) 
    {
        if (profilErrors.hasErrors()) {
            logger.info("errors = {}",profilErrors.getAllErrors().toArray());
            return "benutzerprofil/profileditor";
        }

        BenutzerProfil savedProfil = this.benutzerprofilService.speichereBenutzerProfil(profil);
        m.addAttribute("profil", savedProfil);
        return "redirect:/benutzerprofil";
    }

    @GetMapping("/clearsession")
    public String clearSession(SessionStatus status) 
    {
        status.setComplete();
        return "redirect:/benutzerprofil";
    }

    @GetMapping("/liste")
    public String showListBenutzer(Model m, 
                                @RequestParam Optional<String> op,
                                @RequestParam Optional<Long> id) 
    {
        if(op.isPresent()) {
            switch(op.get()) {
                case "loeschen":
                    this.benutzerprofilService.loescheBenutzerProfilMitId(id.get());
                    return "redirect:/benutzerprofil/liste";
                case "bearbeiten":
                    Optional<BenutzerProfil> benutzerProfil =  this.benutzerprofilService.holeBenutzerProfilMitId(id.get());
                    if(benutzerProfil.isPresent()) {
                        logger.info("profil = {}", benutzerProfil.get());
                        m.addAttribute("profil", benutzerProfil.get());
                        return "redirect:/benutzerprofil/bearbeiten";
                    }
                    break;
                default:
                    break;
            }
        }
        List<BenutzerProfil> benutzerListe = this.benutzerprofilService.alleBenutzerProfile();
        m.addAttribute("nutzer", benutzerListe);
        return "benutzerprofil/profilliste";
    }

    @GetMapping("/angebot")
    public String showAngebote(Model m) {
        m.addAttribute("angebot", new Angebot());
        
        return "benutzerprofil/angebotsformular";
    }

    @PostMapping("/angebot")
    public String saveAngebot(Model m, 
                            @ModelAttribute("profil") BenutzerProfil profil,
                            @ModelAttribute("angebot") Angebot angebot) {
        this.benutzerprofilService.fuegeAngebotHinzu(profil.getId(), angebot);
        m.addAttribute("profil",this.benutzerprofilService.holeBenutzerProfilMitId(profil.getId()).get());
        if (benutzerprofilService.findeAngebotMitId(angebot.getId()).isPresent()) {
            this.backendInfoService.sendInfo("angebot", BackendOperation.CREATE, angebot.getId());
        } else {
            this.backendInfoService.sendInfo("angebot", BackendOperation.CREATE, 0);
        }
        return "redirect:/benutzerprofil";
    }

    @GetMapping("/angebot/{id}/del")
    public String delAngebot(Model m, 
                            @ModelAttribute("profil") BenutzerProfil profil,
                            @PathVariable("id") long id) {

        this.benutzerprofilService.loescheAngebot(id);
        m.addAttribute("profil",this.benutzerprofilService.holeBenutzerProfilMitId(profil.getId()).get());
        this.backendInfoService.sendInfo("angebot", BackendOperation.DELETE, id);
        return "redirect:/benutzerprofil";
    }
}
