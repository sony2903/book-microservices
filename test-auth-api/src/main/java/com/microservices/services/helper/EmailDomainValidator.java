package com.microservices.services.helper;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailDomainValidator implements ConstraintValidator<ValidEmailDomain, String> {
    private String[] allowedDomains;

    @Override
    public void initialize(ValidEmailDomain constraintAnnotation) {
        this.allowedDomains = constraintAnnotation.allowedDomains();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        
        String domain = email.substring(email.lastIndexOf("@") + 1);
        return Arrays.asList(allowedDomains).contains(domain);
    }
}
