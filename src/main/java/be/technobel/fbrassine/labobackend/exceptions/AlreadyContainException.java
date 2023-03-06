package be.technobel.fbrassine.labobackend.exceptions;

import lombok.Getter;

@Getter
public class AlreadyContainException extends RuntimeException  {
    private final Class<?> ressourceType;
    private final Object referencedBy;

    private final Class<?> ressource2Type;
    private final Object referenced2By;

    public AlreadyContainException(Class<?> ressourceType, Object referencedBy, Class<?> ressource2Type, Object referenced2By) {
        this(
                ressourceType.getSimpleName() + " with id : " + referencedBy + " already exist in " + ressource2Type.getSimpleName() + " with id : " + referenced2By,
                null,
                ressourceType,
                referencedBy,
                ressource2Type,
                referenced2By
        );
    }

    public AlreadyContainException(String message, Throwable cause, Class<?> ressourceType, Object referencedBy, Class<?> ressource2Type, Object referenced2By) {
        super(message, cause);
        this.ressourceType = ressourceType;
        this.referencedBy = referencedBy;
        this.ressource2Type = ressource2Type;
        this.referenced2By = referenced2By;
    }
}
