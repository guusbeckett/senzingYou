package model.levels.cave;

import java.awt.Graphics2D;

import model.GroundRenderer;
import model.MediaProvider;


public class CaveGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(MediaProvider.getInstance().getImage("cave/ground.jpg"), null, null);
	}
}
