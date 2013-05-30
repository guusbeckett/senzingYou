package control.levels;

import java.awt.Toolkit;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.sky.Bird;
import model.entities.sky.Cloud;
import control.Climate;
import control.Hardware;

public class SkyLevel extends PunchLevel
{

	public SkyLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.WARM);
		game.setBackground(Toolkit.getDefaultToolkit().getImage(
				"./images/sky/background.jpg"));
		game.setGround(Toolkit.getDefaultToolkit().getImage(
				"./images/sky/ground.jpg"));
	}

	public void update(double time)
	{
		super.update(time);
	}
	
	@Override
	public Entity getRandomEntity()
	{
		return new Cloud();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new Bird(getGame().getCamera().getUsers());
	}
}
