package model.levels.cave;

import java.awt.Graphics2D;
import java.awt.Toolkit;

import model.GroundRenderer;


public class CaveGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(Toolkit.getDefaultToolkit().getImage("./images/cave/ground.jpg"), null, null);
	}
}
