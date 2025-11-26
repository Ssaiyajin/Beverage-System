package jaxrs.error;

import jakarta.xml.bind.annotation.XmlEnum;

/**
 * Represents the category of an API error.
 * Kept minimal to preserve compatibility with existing uses.
 */
@XmlEnum(String.class)
public enum ErrorType {
    INVALID_PARAMETER;

    @Override
    public String toString() {
        return name();
    }

    /**
     * Helper for unmarshalling from textual representation.
     */
    public static ErrorType fromValue(final String v) {
        return ErrorType.valueOf(v);
    }
}
