package be.technobel.fbrassine.labobackend.models.form;

import be.technobel.fbrassine.labobackend.models.entity.*;
import be.technobel.fbrassine.labobackend.validation.constraint.EmailNotTaken;
import be.technobel.fbrassine.labobackend.validation.constraint.PasswordConfirmed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@PasswordConfirmed
public class RegisterRequestForm {

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
    @NotNull
    @Pattern(regexp = "^0\\d{8,9}$", message="Not valid phone number")
    private String phoneNumber;
    @NotBlank(message = "Enter an adress")
    private String adress;

    public RegisterRequest toEntity(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setConfirm(confirm);
        request.setName(name);
        request.setFirstName(firstName);
        request.setPhoneNumber(phoneNumber);
        request.setAdress(adress);
        request.setLogin(firstName.toLowerCase().charAt(0)+name.toLowerCase());
        return request;
    }
}
