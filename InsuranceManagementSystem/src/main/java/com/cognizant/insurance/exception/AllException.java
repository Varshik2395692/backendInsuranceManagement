package com.cognizant.insurance.exception;

public class AllException {
public static class CustomerDetailNotFound extends RuntimeException{
	public CustomerDetailNotFound(String msg){
		super(msg);
	}
}
public static class AgentDetailNotFound extends RuntimeException{
	public AgentDetailNotFound(String msg){
		super(msg);
	}
}


public static class UserNotExist extends RuntimeException{
	public UserNotExist(String msg){
		 super(msg);
	 }
}

public static class PolicyDetailNotFound extends RuntimeException{
	public PolicyDetailNotFound(String msg){
		 super(msg);
	 }
}

public static class ClaimAmountError extends RuntimeException{
	public ClaimAmountError(String msg){
		 super(msg);
	 }
}

}
