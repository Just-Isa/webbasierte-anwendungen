package de.hsrm.mi.web.projekt.validierung;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BuntValidator.class)
@Documented
public @interface Bunt {
    String message() default "Die Farbe ${validatedValue} enthält zwei gleiche R/G/B-Anteile und ist daher nicht bunt genug";

    Class<? extends Payload>[] payload() default { };
    
    Class<?>[] groups() default { };
}
