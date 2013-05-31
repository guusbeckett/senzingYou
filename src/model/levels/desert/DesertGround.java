package model.levels.desert;

import java.awt.Graphics2D;

import model.GroundRenderer;
import model.MediaProvider;


public class DesertGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(MediaProvider.getInstance().getImage("desert/ground.jpg"), null, null);
	}
}
