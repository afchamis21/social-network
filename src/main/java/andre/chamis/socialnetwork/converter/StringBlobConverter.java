package andre.chamis.socialnetwork.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.charset.StandardCharsets;

@Converter
public class StringBlobConverter implements AttributeConverter<String, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(String string) {
        if (string == null) {
            return new byte[0];
        }

        return string.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String convertToEntityAttribute(byte[] bytes) {
        if (bytes == null || bytes.length == 0){
            return "";
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
