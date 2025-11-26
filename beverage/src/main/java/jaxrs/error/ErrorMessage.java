package jaxrs.error;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ErrorMessage {
    private ErrorType type;
    private String message;

    // JAXB requires a no-arg constructor
    public ErrorMessage() {
        // keep default for marshalling/unmarshalling
    }

    public ErrorMessage(final ErrorType type, final String message) {
        this.type = type;
        this.message = message == null ? "" : message;
    }

    public ErrorType getType() {
        return type;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorType=" + this.type +
                ", message='" + getMessage() + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorMessage)) return false;
        final ErrorMessage that = (ErrorMessage) o;
        return type == that.type &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, getMessage());
    }
}
