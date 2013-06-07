package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.List;

import model.Highscore;
import model.Score;

public class HighscoreView
{
	private Highscore highscore;
	private Text songNameText;
	private Text scoreText;
	
	public HighscoreView(Highscore highscore)
	{
		this.highscore = highscore;
		songNameText = new Text(Color.ORANGE, 12);
		scoreText = new Text(Color.ORANGE, 10);
	}
	
	public void drawScore(Graphics2D g2, Score score, Point2D position)
	{
		Image capture = score.getCapture();
		String songName = score.getSongName();

		songNameText.draw(g2, position, songName);
	}
	
	public void draw(Graphics2D g2)
	{
		int index = 0;
		List<Score> scores = highscore.getScores();
		
		for (Score score : scores)
		{
			drawScore(g2, score, new Point2D.Double(100, 100 + index * 30));
			index++;
		}
	}
}
