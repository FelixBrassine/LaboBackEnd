package be.technobel.fbrassine.labobackend.validation.constraint;

import be.technobel.fbrassine.labobackend.validation.validator.EmailNotTakenValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailNotTakenValidator.class)
public @interface EmailNotTaken {

    String message() default "email already taken";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
