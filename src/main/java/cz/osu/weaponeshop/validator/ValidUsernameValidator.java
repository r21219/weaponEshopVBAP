package cz.osu.weaponeshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername,String> {
    @Override
    public void initialize(ValidUsername constraint) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return false;
        }
        return username.length() >= 4 && username.length() <= 16 && username.matches("^[a-zA-Z0-9]+$");
    }
}
