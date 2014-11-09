package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		//TODO Add OS X code here
		return letter + ":/";
	}
	
	public boolean isConnected()
	{
		return (connected = file.canRead());
	}

	public boolean wasJustConnected()
	{
		boolean wasAlreadyConnected = connected;

		connected = file.canRead();

		return (wasAlreadyConnected) ? false : connected;
	}

	public List<File> getSongs(File directory)
	{
		List<File> songs = new ArrayList<File>();

		File[] files = directory.listFiles();
			
		if (files != null)
		{
			for (File file : files)
			{
				if (file.isDirectory())
				{
					songs.addAll(getSongs(file));
				}
	
				else
				{
					String name = file.getName();
					
					int i = name.lastIndexOf('.');
					
					if (i > 0)
					{
						if (name.substring(i+1).compareToIgnoreCase("mp3") == 0)
						{
							songs.add(file);
						}
					}
				}
			}
		}

		return songs;
	}

	public List<File> getAudioFiles()
	{
		return getSongs(file);
	}
}
