package model;

import java.awt.Image;

public class Score
{
	private String songName;
	private int score;
	private Image capture;
	
	public Score(String songName, int score, Image capture)
	{
		super();
		this.songName = songName;
		this.score = score;
		this.capture = capture;
	}
	
	
	public String getSongName()
	{
		return songName;
	}
	
	public void setSongName(String songName)
	{
		this.songName = songName;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public Image getCapture()
	{
		return capture;
	}
	
	public void setCapture(Image capture)
	{
		this.capture = capture;
	}
	
}
