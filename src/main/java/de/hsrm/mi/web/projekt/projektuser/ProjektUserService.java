package de.hsrm.mi.web.projekt.projektuser;

public interface ProjektUserService {
    ProjektUser neuenBenutzerAnlegen(String username, String klartextpasswort, String rolle);
    ProjektUser findeBenutzer(String username);
}
