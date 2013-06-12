package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

public class Score implements Serializable, Comparable<Score>
{
	private static final long serialVersionUID = 1L;

	private String songTitle, songArtist;
	private List<Integer> scores;
	private ImageIcon capture;
	
	public Score(String songTitle, String songArtist, List<Integer> scores, ImageIcon capture)
	{
		super();
		this.songTitle = songTitle;
		this.songArtist = songArtist;
		this.scores = scores;
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

	public List<Integer> getScores()
	{
		return scores;
	}
	
	public ImageIcon getCapture()
	{
		return capture;
	}

	@Override
	public int compareTo(Score o)
	{
		return Collections.max(scores) - Collections.max(o.scores);
	}
	
	@Override
	public String toString()
	{
		boolean first = true;
		String str = "";
		
		for (int score : scores)
		{
			if (!first)
			{
				str += ", ";
			}
			
			str += score;
			
//			if (first && scores.size() > 1)
//			{
//				str += " (links)";
//			}
			
			first = false;
		}
		
//		if (scores.size() > 1)
//		{
//			str += " (rechts)";
//		}
		
		return str;
	}
	
}
