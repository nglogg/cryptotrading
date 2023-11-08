package com.cryptotrading.validator;

import com.cryptotrading.model.CryptoType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CryptoTypeValidator implements ConstraintValidator<CryptoTypeSubset, String> {
    private CryptoType[] subset;

    @Override
    public void initialize(CryptoTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset)
                .contains(CryptoType.of(value));
    }
}
