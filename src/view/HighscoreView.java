package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import model.Highscore;
import model.Score;

public class HighscoreView
{
	private Highscore highscore;
	private Text songTitleText, songArtistText;
	private Text scoreText;
	
	public HighscoreView(Highscore highscore)
	{
		this.highscore = highscore;
		songTitleText = new Text(Color.ORANGE, 18, false);
		songArtistText = new Text(Color.ORANGE, 14, false);
		scoreText = new Text(Color.ORANGE, 10);
	}
	
	public void drawScore(Graphics2D g2, Score score, Point2D position)
	{
		Image capture = score.getCapture().getImage();
		
		AffineTransform ax = new AffineTransform();
		
		ax.scale(133 / (double)capture.getWidth(null), 100 / (double)capture.getHeight(null));
		ax.translate(position.getX(), position.getY());

		g2.drawImage(capture, ax, null);
		
		songTitleText.draw(g2, position, score.getSongTitle());


	}
	
	public void draw(Graphics2D g2)
	{
		int index = 0;
		List<Score> scores = highscore.getScores();
		
		for (Score score : scores)
		{
			drawScore(g2, score, new Point2D.Double(100, 30 + index * 110));
			index++;
		}
	}
}
