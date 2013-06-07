package model;

import java.awt.Image;
import java.io.Serializable;

public class Score implements Serializable
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
	
	public int getScore()
	{
		return score;
	}
	
	public Image getCapture()
	{
		return capture;
	}
	
}
