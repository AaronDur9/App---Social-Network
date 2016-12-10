package abd.p1.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * JPanel that contains a 90x90 ImageIcon 
 */
public class JPanelAvatar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ImageIcon icon;
	private Image rescaledIcon;
	private static final int WIDTH = 90, HEIGHT = 90;
	private static final ImageIcon defaultIcon = new ImageIcon("default.png");
	
	public JPanelAvatar()
	{
		this(JPanelAvatar.defaultIcon);
	}
	
	public JPanelAvatar(ImageIcon icon)
	{
		this.setPreferredSize(new Dimension(JPanelAvatar.WIDTH, JPanelAvatar.HEIGHT));
		this.icon = icon;
		this.rescaledIcon = icon.getImage();
	}
	
	public ImageIcon getIcon()
	{
		return this.icon;
	}
	
	public void setIcon(ImageIcon icon)
	{
		this.icon = icon;
		this.rescaledIcon = icon.getImage();
		this.repaint();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(this.rescaledIcon, 0, 0, this);
	}
}