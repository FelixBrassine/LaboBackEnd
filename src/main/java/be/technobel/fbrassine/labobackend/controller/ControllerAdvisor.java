package be.technobel.fbrassine.labobackend.controller;

import be.technobel.fbrassine.labobackend.exceptions.AlreadyContainException;
import be.technobel.fbrassine.labobackend.exceptions.LocateRoomNotValidException;
import be.technobel.fbrassine.labobackend.models.dto.ErrorDTO;
import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest req){

        ErrorDTO errorDTO = ErrorDTO.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                .headers( headers )
                .body( errorDTO );

    }

    @ExceptionHandler(AlreadyContainException.class)
    public ResponseEntity<ErrorDTO> handleAlreadyContainException(AlreadyContainException ex, HttpServletRequest req){

        ErrorDTO errorDTO = ErrorDTO.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                .headers( headers )
                .body( errorDTO );

    }
    @ExceptionHandler(LocateRoomNotValidException.class)
    public ResponseEntity<ErrorDTO> handleLocateRoomNotValidFound(LocateRoomNotValidException ex, HttpServletRequest req){

        ErrorDTO errorDTO = ErrorDTO.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                .headers( headers )
                .body( errorDTO );

    }

}
