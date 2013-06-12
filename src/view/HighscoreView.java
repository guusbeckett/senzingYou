package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
		songTitleText = new Text(Color.ORANGE, 18);
		songArtistText = new Text(Color.ORANGE, 14);
		scoreText = new Text(Color.GREEN, 22);
	}

	public void drawScore(Graphics2D g2, Score score, Point2D position)
	{
		AffineTransform ax = AffineTransform.getTranslateInstance(position.getX(), position.getY());

		// Draw the capture
		AffineTransform imageTransform = (AffineTransform)ax.clone();
		Image image = score.getCapture().getImage();
		imageTransform.scale(91.0 / image.getWidth(null), 69.0 / image.getHeight(null));
		g2.drawImage(image, imageTransform, null);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.draw(imageTransform.createTransformedShape(new Rectangle2D.Double(0, 0, 640, 480)));

		// Move to the right
		ax.translate(100, 0);
		
		// Draw all the text
		ax.translate(0, 20);
		scoreText.draw(g2, ax, "" + score.getScore());
		ax.translate(0, 20);
		songTitleText.draw(g2, ax, score.getSongTitle());
		ax.translate(0, 15);
		songArtistText.draw(g2, ax, score.getSongArtist());
	}

	public void draw(Graphics2D g2)
	{
		int index = 0;
		List<Score> scores = highscore.getScores();
		
		for (Score score : scores)
		{
			drawScore(g2, score, new Point2D.Double(100, 10 + index * 80));
			index++;
		}
	}
}
