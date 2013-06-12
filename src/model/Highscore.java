package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscore
{
	private Score lastScore;
	private List<Score> scores;
	private final String fileName = "saveFile.sav";

	public Highscore()
	{
		this.lastScore = null;
		this.scores = new ArrayList<Score>();
		load();
	}
	
	public Score getLastScore()
	{
		return lastScore;
	}

	public List<Score> getScores()
	{
		return scores;
	}

	public void add(Score s)
	{
		lastScore = s;
		scores.add(s);
		Collections.sort(scores);

		// Saving the file
		save();

	}

	@SuppressWarnings("unchecked")
	public void load()
	{
		// Reading the score
		try
		{
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream restore = new ObjectInputStream(file);

			Object o = restore.readObject();
			
			if (o instanceof List<?>)
				scores = (List<Score>) o;

			restore.close();
			file.close();

		} catch (Exception e)
		{
		}
	}

	public void save()
	{
		try
		{
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(file);
			oos.writeObject(scores);
			oos.flush();
			oos.close();
			file.close();
		} catch (Exception e)
		{
		}

	}
}
