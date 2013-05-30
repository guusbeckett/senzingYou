package control.levels;

import java.awt.Toolkit;

<<<<<<< HEAD
import control.Climate;
import control.Hardware;
=======
import view.ground.CaveGround;
>>>>>>> 646ddbac82a3cc267a6ec4332815a0f452587cf4

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
		Hardware.getInstance().setClimate(Climate.MOIST);
		game.setBackground(Toolkit.getDefaultToolkit().getImage("./images/cave/background.png"));
		game.setGroundRenderer(new CaveGround());
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

	@Override
	public int getEntitySpawnRate()
	{
		return 100;
	}

	@Override
	public int getHostileEntitySpawnRate()
	{
		return 300;
	}
}
