package andre.chamis.socialnetwork.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.charset.StandardCharsets;

/**
 * JPA Attribute Converter for converting String to byte[] and vice versa for storing as a BLOB in the database.
 */
@Converter
public class StringBlobConverter implements AttributeConverter<String, byte[]> {
    /**
     * Converts a String to a byte array for database storage.
     *
     * @param string The String to be converted.
     * @return The byte array representation of the String.
     */
    @Override
    public byte[] convertToDatabaseColumn(String string) {
        if (string == null) {
            return new byte[0];
        }

        return string.getBytes(StandardCharsets.UTF_8);
    }


    /**
     * Converts a byte array to a String when retrieved from the database.
     *
     * @param bytes The byte array to be converted.
     * @return The String representation of the byte array.
     */
    @Override
    public String convertToEntityAttribute(byte[] bytes) {
        if (bytes == null || bytes.length == 0){
            return "";
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
