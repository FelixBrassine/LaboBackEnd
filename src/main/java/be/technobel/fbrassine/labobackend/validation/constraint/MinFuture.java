package be.technobel.fbrassine.labobackend.validation.constraint;

import be.technobel.fbrassine.labobackend.validation.validator.MinFutureValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinFutureValidator.class)
public @interface MinFuture {

    ChronoUnit unit();
    long amount();

    String message() default "min future validation failed";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
