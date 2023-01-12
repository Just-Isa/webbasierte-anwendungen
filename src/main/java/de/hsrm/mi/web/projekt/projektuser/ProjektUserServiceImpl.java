package de.hsrm.mi.web.projekt.projektuser;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilServiceImpl;


@Service
public class ProjektUserServiceImpl implements ProjektUserService{

    @Autowired private PasswordEncoder encoder;
    @Autowired private BenutzerprofilServiceImpl bpService;

    private ProjektUserRepository userRepo;

    public ProjektUserServiceImpl(ProjektUserRepository repo) {
        this.userRepo = repo;
    }

    @Override
    public ProjektUser neuenBenutzerAnlegen(String username, String klartextpasswort, String rolle) {
        if (this.userRepo.findById(username).isPresent()) {
            throw new ProjektUserServiceException("Username schon vergeben");
        }
        if (rolle.equals("") || rolle == null) {
            rolle = "USER";
        }
        ProjektUser newUser = new ProjektUser(username, encoder.encode(klartextpasswort), rolle);  
        this.userRepo.save(newUser);
        
        BenutzerProfil newProfil = new BenutzerProfil();

        newProfil.setName(username);
        newProfil.setProjektUser(newUser);
        newUser.setBenutzerProfil(newProfil);

        this.bpService.speichereBenutzerProfil(newProfil);
        this.userRepo.save(newUser);

        return newUser;
    }

    @Override
    public ProjektUser findeBenutzer(String username) {
        Optional<ProjektUser> user = this.userRepo.findById(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ProjektUserServiceException("User not found!");
        }
    }
}
