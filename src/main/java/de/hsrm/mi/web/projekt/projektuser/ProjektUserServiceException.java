package de.hsrm.mi.web.projekt.projektuser;

public class ProjektUserServiceException extends RuntimeException{

    public ProjektUserServiceException(String errorMessage) {
        super(errorMessage);
    }

    public ProjektUserServiceException() {
        super("Irgendwas ist schief gelaufen!");
    }
    
}
