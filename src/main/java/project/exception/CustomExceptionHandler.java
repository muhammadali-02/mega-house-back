package project.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        if (ex.getMessage().equals("Access is denied") || ex.getMessage().equals("Доступ запрещен"))
            return new ResponseEntity<>(errorDetails, HttpStatus.EXPECTATION_FAILED);
        else return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(new Date(), ex.getLocalizedMessage(), "Record Not Found");
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(RecordNotFoundException.class)
//    public final ResponseEntity<Object> handleUserBadRequestException(RecordNotFoundException ex) {
//        List<String> details = new ArrayList<>();
//        details.add("BAD_REQUEST");
//        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), details);
//        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
//    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorDetails error = new ErrorDetails(new Date(), "Validation Failed", details.toString());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There was an error processing the request body.")
    public void handleMessageNotReadableException(HttpServletRequest request, Throwable exception) {
        System.out.println(exception.getMessage());
    }
//
//    @Override
//    @ExceptionHandler(RecordNotFoundException.class)
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
//                                                                  HttpStatus status, WebRequest request){
//        List<String> errorDetails = new ArrayList<>();
//        errorDetails.add(request.getDescription(false));
//        ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), HttpStatus.BAD_REQUEST, errorDetails);
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
}