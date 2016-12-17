package Exceptions;


/**
 * @Author DavidCohen55
 */
public class LoginException extends Exception{
	private static final long serialVersionUID = 1L;
	public String exception;
    public LoginException(String str) {
    	exception=str;
    }
    public String toString(){ 
       return exception ;
    }
}