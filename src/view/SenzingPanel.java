package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Camera;
import model.Game;
import model.MediaProvider;
import model.Song;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.levels.Level;

public class SenzingPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Game game;
	private Image rewardImage, losingImage;

	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000 / 30, this).start();
		rewardImage = MediaProvider.getInstance().getImage("reward.png");
		losingImage = MediaProvider.getInstance().getImage("losingpoint.png");
	}

	private void drawText(Graphics2D g2, String text, Color color, int size, Point2D p2)
	{
		AffineTransform transform = new AffineTransform();
		transform.translate(p2.getX(), p2.getY());
		drawText(g2, text, color, size, transform);
	}

	private void drawText(Graphics2D g2, String text, Color color, int size, AffineTransform transform)
	{
		Font font = new Font("Arial", Font.BOLD, size);
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, text);
		Shape outline = gv.getOutline(0, 0);
		if (transform != null)
		{
			outline = transform.createTransformedShape(outline);
		}
		g2.setColor(color);
		g2.fill(outline);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(outline);
	}
	
	private void drawImageInCenter(Graphics2D g2, Image image)
	{
		double width = image.getWidth(null);
		double height = image.getHeight(null);

		AffineTransform ax = new AffineTransform();
		
		ax.translate(Camera.VIEW_WIDTH / 2 - 320/2, Camera.VIEW_HEIGHT / 2 - 240/2);
		ax.scale(320 / width, 240 / height);
		
		g2.drawImage(image, ax, null);
	}

	private void drawEntities(Graphics2D g2, int step)
	{
		for (Entity entity : game.getEntities())
		{
			AffineTransform ax = new AffineTransform();

			ax.rotate(entity.getRotation(), entity.getRotationPoint().getX() + entity.getPosition().getX(), entity.getRotationPoint().getY() + entity.getPosition().getY());

			if (entity.getImage() != null)
			{
				if ((step == 0 && !(entity instanceof HostileEntity)) || (step == 1 && (entity instanceof HostileEntity) && ((HostileEntity) entity).isAlive()))
				{
					ax.translate(entity.getPosition().getX(), entity.getPosition().getY());
					if (entity.isMirrored())
						ax.translate(entity.getDimensions().getWidth(), 0);
					ax.scale(entity.getDimensions().getWidth() / entity.getImage().getWidth(null) * ((entity.isMirrored()) ? -1 : 1), entity.getDimensions().getHeight() / entity.getImage().getHeight(null));
					g2.drawImage(entity.getImage(), ax, null);
				}

				else if (step == 1 && entity instanceof HostileEntity)
				{
					HostileEntity hostile = (HostileEntity) entity;

					if (!hostile.isAlive())
					{
						Image image = (hostile.getReward() <= 0) ? losingImage : rewardImage;

						ax.translate(hostile.getDeadLocation().getX(), hostile.getDeadLocation().getY());
						ax.scale(entity.getDimensions().getWidth() / image.getWidth(null), entity.getDimensions().getHeight() / image.getHeight(null));
						g2.drawImage(image, ax, null);

						ax.translate(0, (image.getWidth(null) / 1.5));
						drawText(g2, "" + hostile.getReward(), Color.BLUE, 250, ax);
					}
				}
			}
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        


		double _w = getWidth();
		double _h = getHeight();
		double _x = Camera.VIEW_WIDTH;
		double _y = Camera.VIEW_HEIGHT;
		double _s = _h / _y;
		double _b = (_w / _s - _x) / 2;
		
		g2.scale(_s, _s);
		g2.translate(_b, 0);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		BufferedImage[] images = game.getCamera().getImageBackgroundAndForeground();

		// Draw background
		Level level = game.getLevel();

		if (level == null)
		{
			drawImageInCenter(g2, MediaProvider.getInstance().getImage("usbConnect.png"));
		}
		else
		{
			g2.drawImage(level.getBackground(), 0, 0, null);
		}

		// Draw camera background image
		g2.drawImage(images[0], null, 0, 0);

		// Draw the background entities
		drawEntities(g2, 0);

		// Draw camera foreground image
		g2.drawImage(images[1], null, 0, 0);
		
		// Draw description of level
		if (level != null)
		{
			if (level.isDescriptionImageVisible())
			{
				drawImageInCenter(g2, level.getDescriptionImage());
			}
		}

		// Draw the foreground entities
		drawEntities(g2, 1);

		// Draw all the scores
		if (!game.getCamera().getUsers().isEmpty())
		{
			Color[] colors = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE, Color.YELLOW, Color.LIGHT_GRAY };
			ArrayList<User> copyUsers = new ArrayList<User>();
			copyUsers.addAll(game.getCamera().getUsers());
			Collections.sort(copyUsers);
			int x = 300;
			int scoreWidth = (Camera.VIEW_WIDTH - x) / copyUsers.size();

			for (User u : copyUsers)
			{
				if (u.isVisible())
				{
					drawText(g2, "" + u.getScore(), colors[(u.getId() - 1) % colors.length], 25, new Point2D.Double(x, 25));
				}
				x += scoreWidth;
			}
		}

		// Draw the countdown
		Song song = game.getSong();

		if (song != null)
		{
			int time = (int) song.getTime();
			int length = (int) song.getLength();
			drawText(g2, String.format("%02d:%02d / %02d:%02d", time / 60, time % 60, length / 60, length % 60), Color.ORANGE, 25, new Point2D.Double(8, 25));
		}

		// Draw sideboxes
		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle2D.Double(-_b, 0, _b, _y));
		g2.fill(new Rectangle2D.Double(_x, 0, _b, _y));
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
