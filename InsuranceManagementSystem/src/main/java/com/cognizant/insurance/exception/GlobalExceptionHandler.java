package com.cognizant.insurance.exception;
 
import java.util.HashMap;
import java.util.Map;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.cognizant.insurance.exception.AllException.AgentDetailNotFound;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.exception.AllException.UserNotExist;



@ControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Map<String,Object>> handleNoHandlerFoundException(NoHandlerFoundException ex){
		Map<String,Object> error=new HashMap<>();
		error.put("status",String.valueOf(404)+" (Rrsource Not Found)");
		error.put("message","The URL you are trying to access doesnt exist");
	//error.put("path",ex.getRequestURL()+" this path doesnt exist");
	
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler( UserNotExist.class)
	public ResponseEntity<String> handleUserNotExist(UserNotExist ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> 
		errors.put(error.getField(), error.getDefaultMessage()) );
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String,String>> handleTypeMismatch(
			MethodArgumentTypeMismatchException ex){
		Map<String,String> error=new HashMap<>();
		error.put("error","Invalid path variable");
		error.put("message","Failed to convert "+ex.getValue()+" to the required type "+ex.getName());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(CustomerDetailNotFound.class)
	public ResponseEntity<Map<String,String>> handleInstructorDetailNotFound(CustomerDetailNotFound ex){
		Map<String, String> error = new HashMap<>();
		error.put("error","Invalid Details");
		error.put("message", ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(AgentDetailNotFound.class)
	public ResponseEntity<Map<String,String>> handleStudentDetailNotFound(AgentDetailNotFound ex){
		Map<String, String> error = new HashMap<>();
		error.put("error","Invalid Details");
		error.put("message", ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	
	
/*	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(){
		return new ResponseEntity<>("something went wrong",HttpStatus.OK);
	}
	*/
	
}
