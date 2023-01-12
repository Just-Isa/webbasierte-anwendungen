package de.hsrm.mi.web.projekt.projektuser;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjektUserController {
    @Autowired ProjektUserServiceImpl userService;

    @GetMapping("/registrieren")
    public String showRegistrieren(Model m) {
        m.addAttribute("projektuser", new ProjektUser());
        return "projektuser/registrieren";
    }

    @PostMapping("/registrieren")
    public String saveRegistrieren (@Valid @ModelAttribute("projektuser") ProjektUser user,
                                    BindingResult errors) {
        if (errors.hasErrors()) {
            return "projektuser/registrieren";
        }
        this.userService.neuenBenutzerAnlegen(user.getUsername(), user.getPassword(), user.getRole());
        return "redirect:/benutzerprofil";
    }
}
