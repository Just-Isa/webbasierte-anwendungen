package de.hsrm.mi.web.projekt.validierung;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BuntValidator 
        implements ConstraintValidator<Bunt, String>{
    protected String farbString;

    @Override
    public void initialize(Bunt annoTationBunt) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        } else if (value.equals("")){
            return true;
        }
        if (!value.startsWith("#")) return false;
        Pattern p = Pattern.compile("[^#abcdefABCDEF1234567890]");
        Matcher hasBadChars = p.matcher(value);
        if (hasBadChars.find()) return false;
        if (value.length() != 4 && value.length() != 7) return false;
        if (value.length() == 4) {
            if (value.charAt(1) == value.charAt(2) || value.charAt(1) == value.charAt(3) || value.charAt(2) == value.charAt(3)) return false;
        }
        if (value.length() == 7) {
            String r = value.substring(1, 3);
            String g = value.substring(3, 5);
            String b = value.substring(5, 7);
            if (r.equalsIgnoreCase(g) || r.equalsIgnoreCase(b) || g.equalsIgnoreCase(b)) return false;
        }
        return true;
    }
    
}
