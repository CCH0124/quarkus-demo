package org.cch.convert;

import org.cch.definition.ERole;
import org.jboss.logging.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.stream.Stream;

@Converter()
public class ERoleAttributeConverter implements AttributeConverter<ERole, String> {
    private static final Logger LOG = Logger.getLogger(ERoleAttributeConverter.class);
    @Override
    public String convertToDatabaseColumn(ERole attribute) {
        LOG.infof("convertToDatabaseColumn: %s", attribute);
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public ERole convertToEntityAttribute(String role) {
        LOG.infof("convertToEntityAttribute: %s", role);
        if (Objects.isNull(role)) {
            return null;
        }
        return Stream.of(ERole.values()).filter(r -> Objects.equals(r.name(), role)).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
