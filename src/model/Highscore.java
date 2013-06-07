package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscore
{
	private List<Score> scores;
	private String fileName;
	public Highscore(){
		fileName = "saveFile.sav";
		
		this.scores = new ArrayList<Score>();
		//Reading the score
		try
		{
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream restore = new ObjectInputStream(file);
			
			scores = (List<Score>) (restore.readObject());
			restore.close();
			file.close();
			
		} catch (Exception e)
		{}
		
		
	}
	
	public List<Score> getScores(){
		return scores;
	}
	
	public void add(Score s){
		scores.add(s);
		Collections.sort(scores);
		
		//Saving the file
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
	
	public void save(){
		
	}
}

