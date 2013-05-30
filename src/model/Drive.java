package model;

import java.io.File;

public class Drive
{
	private boolean connected;
	private char letter;
	private File file;
	
	public Drive(char letter)
	{
		this.letter = letter;
		this.file = new File(getPath());
		this.connected = file.canRead();
	}
	
	public String getPath()
	{
		return letter + ":/";
	}
	
	public boolean wasJustConnected()
	{
		if (connected)
		{
			return false;
		}
		
		return (connected = file.canRead());
	}
	
	public boolean wasJustDisconnected()
	{
		if (!connected)
		{
			return false;
		}
		
		return !(connected = file.canRead());
	}
}
