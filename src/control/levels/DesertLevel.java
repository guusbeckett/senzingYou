package control.levels;

import java.awt.Toolkit;
import java.util.Random;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.desert.Camel;
import model.entities.desert.Scorpion;
import model.entities.desert.Snake;
import model.entities.desert.Vulture;
import control.Climate;
import control.Hardware;

public class DesertLevel extends PunchLevel
{

	public DesertLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.WARM);
		game.setBackground(Toolkit.getDefaultToolkit().getImage(
				"./images/desert/background.jpg"));
		game.setGround(Toolkit.getDefaultToolkit().getImage(
				"./images/desert/ground.jpg"));
	}
	
	public void update(double time)
	{
		super.update(time);
	}

	@Override
	public Entity getRandomEntity()
	{
		Random r = new Random();
		if(r.nextBoolean())
			return new Camel();
		else
			return new Vulture();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		Random r = new Random();
		if(r.nextBoolean())
			return new Snake(getGame().getCamera().getUsers());
		else
			return new Scorpion(getGame().getCamera().getUsers());
	}
}
