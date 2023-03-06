package be.technobel.fbrassine.labobackend.exceptions;

public class RequestStatusException extends RuntimeException {

    public RequestStatusException() {
        super("Cant change status this way");
    }
}
