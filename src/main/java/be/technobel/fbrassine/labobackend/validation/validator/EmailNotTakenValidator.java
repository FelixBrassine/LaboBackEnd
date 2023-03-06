package be.technobel.fbrassine.labobackend.validation.validator;

import be.technobel.fbrassine.labobackend.service.UserService;
import be.technobel.fbrassine.labobackend.validation.constraint.EmailNotTaken;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class EmailNotTakenValidator implements ConstraintValidator<EmailNotTaken, String> {

    private final UserService userService;

    public EmailNotTakenValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.checkEmailNotTaken( value );
    }
}
