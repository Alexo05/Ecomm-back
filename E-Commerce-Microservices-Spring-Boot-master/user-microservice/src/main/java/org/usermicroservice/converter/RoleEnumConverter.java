package org.usermicroservice.converter;

import org.springframework.stereotype.Component;
import org.usermicroservice.enums.ERole;
import org.springframework.core.convert.converter.Converter;
@Component
public class RoleEnumConverter implements Converter<String,ERole>{

    @Override
    public ERole convert(String source) {
        if (source != null) {
            try {
                return ERole.valueOf(source);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        throw new IllegalArgumentException("Source string is null");
    }
}
