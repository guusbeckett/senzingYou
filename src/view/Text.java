package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Text
{
	private Font font;
	private Color color;
	private boolean centered;
	
	public Text(Color color, int size, boolean centered)
	{
		this.font = new Font("Arial", Font.BOLD, size);
		this.centered = centered;
		this.color = color;
	}
	
	public Text(Color color, int size)
	{
		this(color, size, false);
	}
	
	public void draw(Graphics2D g2, Point2D position, String text)
	{
		AffineTransform transform = new AffineTransform();
		transform.translate(position.getX(), position.getY());
		draw(g2, transform, text);
	}
	
	public void draw(Graphics2D g2, AffineTransform transform, String text)
	{
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, text);
		Shape outline = gv.getOutline(0, 0);
		
		if (transform != null)
		{
			if (centered)
				transform.translate(-(outline.getBounds().width / 2), 0);
			
			outline = transform.createTransformedShape(outline);
		}
		
		g2.setColor(color);
		g2.fill(outline);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.draw(outline);
	}
}
