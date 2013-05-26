package control.levels;

import java.io.FileNotFoundException;
import java.util.Collections;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.MenuEntity;
import control.Song;

public class SongMenu extends MenuLevel
{
	private MenuEntity menuEntity;
	
	public SongMenu(Game game, MenuEntity menuEntity)
	{
		super(game);
		this.menuEntity = menuEntity;
		//Add here the entity's 
		Collections.reverse(game.getEntities());	//So that the hand come first!
		
		//Soon will add this to the menu choose!
		String fileName = "evil.mp3";
				try
				{
					game.setSong(new Song("audio/"+fileName));
					game.getSong().play();
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
	}

	@Override
	public Entity getRandomEntity()
	{
		return null;
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return null;
	}
	
	public void update(double time)
	{
		super.update(time);	
		getGame().getEntities().clear();
		menuEntity.goToLevel(getGame());
	}

}
