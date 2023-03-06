package be.technobel.fbrassine.labobackend.validation.validator;

import be.technobel.fbrassine.labobackend.models.form.RegisterRequestForm;
import be.technobel.fbrassine.labobackend.validation.constraint.PasswordConfirmed;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, RegisterRequestForm> {
    @Override
    public boolean isValid(RegisterRequestForm value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirm());
    }
}
