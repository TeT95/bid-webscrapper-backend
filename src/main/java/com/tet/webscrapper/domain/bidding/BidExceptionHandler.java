package com.tet.webscrapper.domain.bidding;

import com.tet.webscrapper.exceptions.BidNotFoundException;
import com.tet.webscrapper.exceptions.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BidExceptionHandler {

    @ExceptionHandler(BidNotFoundException.class)
    public ResponseEntity<ErrorResponse> BidNotFoundHandler(BidNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
