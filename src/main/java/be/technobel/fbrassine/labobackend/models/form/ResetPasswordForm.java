package be.technobel.fbrassine.labobackend.models.form;

import be.technobel.fbrassine.labobackend.validation.constraint.PasswordConfirmed;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordConfirmed
public class ResetPasswordForm {
    @NotBlank
    private String password;
    @NotBlank(message = "Enter a confirm password")
    private String confirm;
}
