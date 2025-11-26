package jaxrs.error;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public final class ErrorMessage {

    @XmlElement(required = true)
    private ErrorType errorType;

    private String message;

    // JAXB requires a no-arg constructor
    public ErrorMessage() {
        // intentionally empty
    }

    public ErrorMessage(final ErrorType errorType, final String message) {
        this.errorType = Objects.requireNonNull(errorType, "errorType must not be null");
        this.message = message == null ? "" : message;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(final ErrorType errorType) {
        this.errorType = Objects.requireNonNull(errorType, "errorType must not be null");
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message == null ? "" : message;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorType=" + this.errorType +
                ", message='" + this.message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorMessage)) return false;
        ErrorMessage that = (ErrorMessage) o;
        return errorType == that.errorType &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorType, message);
    }
}
