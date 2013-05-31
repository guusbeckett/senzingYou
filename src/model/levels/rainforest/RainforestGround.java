package model.levels.rainforest;

import java.awt.Graphics2D;
import java.awt.Toolkit;

import model.GroundRenderer;


public class RainforestGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(Toolkit.getDefaultToolkit().getImage("./images/rainforest/ground.jpg"), null, null);
	}
}
