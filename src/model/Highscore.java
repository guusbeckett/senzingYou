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
	private List<Score> scores;
	private final String fileName = "saveFile.sav";
	
	public Highscore(){
		this.scores = new ArrayList<Score>();
		load();
	}
	
	public List<Score> getScores(){
		return scores;
	}
	
	public void add(Score s){
		scores.add(s);
		Collections.sort(scores);
		
		//Saving the file
		save();
	   
	}
	
	public void load(){
		//Reading the score
		try
		{
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream restore = new ObjectInputStream(file);
					
			scores = (List<Score>) (restore.readObject());
			restore.close();
			file.close();
					
		}catch (Exception e)
		{}
	}
	
	public void save(){
		 try
			{
		    	FileOutputStream file = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(file);
				oos.writeObject(scores);
				oos.flush();
				oos.close();
				file.close();
			} catch (Exception e)
			{}
		    
	}
}

