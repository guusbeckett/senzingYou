package view.ground;

import java.awt.Graphics2D;
import java.awt.Toolkit;

public class DesertGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(Toolkit.getDefaultToolkit().getImage("./images/desert/ground.jpg"), null, null);
	}
}
