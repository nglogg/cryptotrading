package com.cryptotrading.converter;

import com.cryptotrading.model.CryptoType;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
@Component
public class CryptoTypeConverter implements Converter<String, CryptoType> {
    @Override
    public CryptoType convert(String value) {
        return CryptoType.of(value);
    }
}