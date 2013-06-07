package model;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Score implements Serializable, Comparable<Score>
{
	private String songTitle, songArtist;
	private int score;
	private ImageIcon capture;
	
	public Score(String songTitle, String songArtist, int score, ImageIcon capture)
	{
		super();
		this.songTitle = songTitle;
		this.songArtist = songArtist;
		this.score = score;
		this.capture = capture;
	}
	
	public String getSongTitle()
	{
		return songTitle;
	}
	
	public String getSongArtist()
	{
		return songArtist;
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
