package model;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Score implements Serializable, Comparable<Score>
{
	private String songName;
	private int score;
	private ImageIcon capture;
	
	public Score(String songName, int score, ImageIcon capture)
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
	
	public ImageIcon getCapture()
	{
		return capture;
	}


	@Override
	public int compareTo(Score o)
	{
		return getScore() - o.getScore();
	}
	
}
