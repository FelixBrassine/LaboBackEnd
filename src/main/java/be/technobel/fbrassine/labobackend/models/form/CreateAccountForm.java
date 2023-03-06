package be.technobel.fbrassine.labobackend.models.form;

import be.technobel.fbrassine.labobackend.models.entity.UserRole;
import be.technobel.fbrassine.labobackend.validation.constraint.EmailNotTaken;
import be.technobel.fbrassine.labobackend.validation.constraint.PasswordConfirmed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@PasswordConfirmed
public class CreateAccountForm {

    @NotBlank(message = "Enter an email")
    @Email
    @EmailNotTaken
    private String email;
    @NotBlank(message = "Enter a password")
    @Pattern(regexp = "^^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$",
            message = "password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
    private String password;
    @NotBlank(message = "Enter a confirm password")
    private String confirm;
    @NotBlank(message = "Enter a name")
    private String name;
    @NotBlank(message = "Enter a firstname")
    private String firstName;
    @NotBlank(message = "Enter a login")
    private String Login;
    @NotNull
    @Pattern(regexp = "^0\\d{8,9}$", message="Not valid phone number")
    private String phoneNumber;
    @NotBlank(message = "Enter an adress")
    private String adress;
    @NotBlank(message = "Select Role(s)")
    private Set<UserRole> roles = new LinkedHashSet<>();
    @NotBlank(message = "Enabled ? ")
    private boolean enabled;
}
