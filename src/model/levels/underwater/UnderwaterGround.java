package model.levels.underwater;

import java.awt.Graphics2D;

import model.GroundRenderer;
import model.MediaProvider;


public class UnderwaterGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(MediaProvider.getInstance().getImage("underwater/ground.jpg"), null, null);
	}
}
