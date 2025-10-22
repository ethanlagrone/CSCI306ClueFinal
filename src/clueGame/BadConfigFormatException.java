package clueGame;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException(){
		System.out.println("Bad Format Exception");
	}
	
	public BadConfigFormatException(String message){
		super(message);
	}
}
