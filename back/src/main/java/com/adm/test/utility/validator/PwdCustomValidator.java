package com.adm.test.utility.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PwdCustomValidator implements ConstraintValidator<ValidPassword, String> {

    private final PasswordValidator passayValidator;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        PasswordData passwordData = new PasswordData(password);
        RuleResult result = passayValidator.validate(passwordData);

        if (!result.isValid()) {
            for (RuleResultDetail ruleResultDetail : result.getDetails()) {
                context.buildConstraintViolationWithTemplate(ruleResultDetail.toString())
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
            }
            return false;
        }
        return true;
    }
}
