package be.technobel.fbrassine.labobackend.validation.constraint;

import be.technobel.fbrassine.labobackend.validation.validator.PasswordConfirmedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConfirmedValidator.class)
public @interface PasswordConfirmed {

    String message() default "different passwords";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
