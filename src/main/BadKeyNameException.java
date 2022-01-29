package main;

public class BadKeyNameException extends Exception
{
	private static final long serialVersionUID = 1L;

	public BadKeyNameException()
	{
		super("This is not a valid key !");
	}
	
	public BadKeyNameException(String msg)
	{
		super(msg);
	}
	
}
