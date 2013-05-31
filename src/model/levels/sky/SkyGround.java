package model.levels.sky;

import java.awt.Graphics2D;

import model.GroundRenderer;
import model.MediaProvider;


public class SkyGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(MediaProvider.getInstance().getImage("sky/ground.jpg"), null, null);
	}
}
