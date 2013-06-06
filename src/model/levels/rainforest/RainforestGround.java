package model.levels.rainforest;

import java.awt.Graphics2D;

import model.GroundRenderer;
import model.MediaProvider;


public class RainforestGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(MediaProvider.getInstance().getImage("rainforest/ground.jpg"), null, null);
	}

	@Override
	public void update(double time)
	{
		// TODO Auto-generated method stub
		
	}
}
