package abd.p1.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import abd.p1.model.User;

public class UsuarioCellRenderer extends PanelUsuario implements ListCellRenderer<User>
{
	private static final long serialVersionUID = 1L;

	public UsuarioCellRenderer()
	{
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User user,
			int index, boolean isSelected, boolean cellHasFocus)
	{
		this.setName(user.getName());
		this.setAge(user.getBirthDate());
		this.setAvatar(new ImageIcon(user.getAvatar()));
		this.setOpaque(true);
		
		if(isSelected)
			this.setBackground(Color.YELLOW);
		else
			this.setBackground(Color.WHITE);
		
		return this;
	}
}