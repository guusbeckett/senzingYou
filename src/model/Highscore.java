package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

public class Highscore implements Serializable
{
	private List<Score> score;
	
	public Highscore(){
		try
		{
			FileInputStream saveFile = new FileInputStream("saveFile.sav");
		} catch (FileNotFoundException e)
		{
		}
		
		
	}
	
	public List<Score> getScores(){
		return score;
	}
	
	public void add(Score score){
		
	}
	
	public void save(){
		
	}
}

