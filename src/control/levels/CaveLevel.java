package control.levels;

import java.awt.Toolkit;

import control.Hardware;
import control.Temperature;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.cave.Droplet;
import model.entities.cave.Rock;

public class CaveLevel extends DodgeLevel
{

	public CaveLevel(Game game)
	{
		super(game);

		game.setBackground(Toolkit.getDefaultToolkit().getImage("./images/cave/background.png"));
		game.setGround(Toolkit.getDefaultToolkit().getImage("./images/cave/ground.jpg"));
		Hardware.getInstance().setTemperature(Temperature.COLD);
	}

	public void update(double time)
	{
		super.update(time);
//		if((int)(Math.random() * time) == 1){
//			getGame().getEntities().add(new Droplet());
//		}
//		if((int)(Math.random() * (time * 0.5)) == 1){
//			getGame().getEntities().add(new Rock(getGame().getCamera().getUsers()));
//		}
	}

	@Override
	public Entity getRandomEntity()
	{
		return new Droplet();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new Rock(getGame().getCamera().getUsers());
	}
}
