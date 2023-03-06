package be.technobel.fbrassine.labobackend.exceptions;

import lombok.Getter;

@Getter
public class LocateRoomNotValidException extends RuntimeException {

    private final Class<?> ressourceType;
    private final Object referencedBy;

    public LocateRoomNotValidException(Class<?> ressourceType, Object referencedBy) {
        this(
                ressourceType.getSimpleName() + " could not be affected for the request with id : " + referencedBy,
                null,
                ressourceType,
                referencedBy
        );
    }

    public LocateRoomNotValidException(String message, Class<?> ressourceType, Object referencedBy) {
        this(
                message,
                null,
                ressourceType,
                referencedBy
        );
    }

    public LocateRoomNotValidException(Throwable cause, Class<?> ressourceType, Object referencedBy) {
        this(
                ressourceType.getSimpleName() + " could not be affected for the request with id : " + referencedBy,
                cause,
                ressourceType,
                referencedBy
        );
    }

    public LocateRoomNotValidException(String message, Throwable cause, Class<?> ressourceType, Object referencedBy) {
        super(message, cause);
        this.ressourceType = ressourceType;
        this.referencedBy = referencedBy;
    }
}
