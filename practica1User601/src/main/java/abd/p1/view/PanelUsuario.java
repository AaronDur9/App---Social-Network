package abd.p1.view;

import javax.swing.JPanel;
import abd.p1.Utils;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;

/**
 * JPanel that contains an User's avatar, name and age
 */
public class PanelUsuario extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JPanelAvatar panelAvatar;
	private JLabel lblNombre;
	private JLabel lblEdad;
	
	public PanelUsuario()
	{
		this.setLayout(null);
		this.setPreferredSize(new Dimension(349, 110));
		
		this.panelAvatar = new JPanelAvatar();
		this.panelAvatar.setBounds(10, 11, 90, 90);
		add(panelAvatar);
		
		this.lblNombre = new JLabel("Nombre");
		this.lblNombre.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.lblNombre.setBounds(120, 11, 220, 25);
		add(lblNombre);
		
		this.lblEdad = new JLabel("edad");
		this.lblEdad.setBounds(120, 42, 220, 14);
		add(lblEdad);
		
		this.setVisible(true);
	}
	
	public void setAge(Date bDate)
	{
		Date last = new Date();
		
		if(bDate != null)
			this.lblEdad.setText(Utils.getDiffYears(bDate, last) + " años");
		else
			this.lblEdad.setText("Edad desconocida");
	}
	
	public void setName(String name)
	{
		this.lblNombre.setText(name);
	}
	
	public void setAvatar(ImageIcon img)
	{
		this.panelAvatar.setIcon(img);
	}
}