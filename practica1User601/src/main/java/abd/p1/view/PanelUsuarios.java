package abd.p1.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import abd.p1.controller.Controller;
import abd.p1.model.User;

/**
 * Jpanel that contains multiple PanelUsuario
 */
public class PanelUsuarios extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JCheckBox chckbxFiltroNombre, chckbxMostrarSoloAmigos;
	private JTextField textFieldFiltroNombre;
	private DefaultListModel<User> modelUsers;
	private JList<User> listUsers;
	private JScrollPane scrollPaneUsers;
	
	public PanelUsuarios(Controller con)
	{
		this.controller = con;
		
		setLayout(null);
		setSize(369, 380);
		this.chckbxFiltroNombre = new JCheckBox("Filtrar por nombre:");
		this.chckbxFiltroNombre.setBounds(8, 322, 140, 23);
		add(this.chckbxFiltroNombre);
		
		this.chckbxMostrarSoloAmigos = new JCheckBox("Mostrar solo amigos");
		this.chckbxMostrarSoloAmigos.setBounds(8, 349, 156, 23);
		add(this.chckbxMostrarSoloAmigos);
		
		this.textFieldFiltroNombre = new JTextField();
		this.textFieldFiltroNombre.setBounds(163, 323, 206, 20);
		this.textFieldFiltroNombre.setColumns(10);
		this.textFieldFiltroNombre.setEnabled(false);
		add(this.textFieldFiltroNombre);
		
		this.modelUsers = new DefaultListModel<>();
		
		this.scrollPaneUsers = new JScrollPane();
		this.scrollPaneUsers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPaneUsers.setBounds(0, 0, 369, 314);
		add(this.scrollPaneUsers);
		
		this.listUsers = new JList<>();
		this.listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listUsers.setModel(this.modelUsers);
		this.listUsers.setCellRenderer(new UsuarioCellRenderer());
		
		this.scrollPaneUsers.setViewportView(listUsers);
		
		addBtnActions();
	}

	public void setInterestingUsers()
	{
		this.controller.setInterestingUsers(this.textFieldFiltroNombre.getText(), this);
	}
	
	public void onSetUserList(List <User> listUsers)
	{
		modelUsers.removeAllElements();
		
		for(User u: listUsers)
			this.modelUsers.addElement(u);
	}
	
	private void addBtnActions()
	{
		this.textFieldFiltroNombre.getDocument().addDocumentListener(new DocumentListener()
		{
			  public void changedUpdate(DocumentEvent e)
			  {
				  changed();
			  }
			  
			  public void removeUpdate(DocumentEvent e)
			  {
				  changed();
			  }
			  
			  public void insertUpdate(DocumentEvent e)
			  {
				  changed();
			  }

			  public void changed() 
			  {
				  if(chckbxFiltroNombre.isSelected() && !chckbxMostrarSoloAmigos.isSelected())
					  controller.setInterestingUsers(textFieldFiltroNombre.getText(), getPanelUsu());
				  else
					  if(chckbxMostrarSoloAmigos.isSelected() && chckbxFiltroNombre.isSelected())
					  controller.setInterestingFriends(textFieldFiltroNombre.getText(), getPanelUsu());
			  }
			 
		});
		
		this.chckbxFiltroNombre.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setUsers();
			}
		});
		
		this.chckbxMostrarSoloAmigos.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setUsers();
			}
		});
	}

	private void setUsers()
	{
		if(this.chckbxFiltroNombre.isSelected() && !this.chckbxMostrarSoloAmigos.isSelected())
		{
			textFieldFiltroNombre.setEnabled(true);
			controller.setInterestingUsers("", this);
		}
		else
			if(!chckbxFiltroNombre.isSelected() && chckbxMostrarSoloAmigos.isSelected())
			{
				textFieldFiltroNombre.setEnabled(false);
				controller.setInterestingFriends("", this);
			}
			else
				if(chckbxFiltroNombre.isSelected() && chckbxMostrarSoloAmigos.isSelected())
				{
					textFieldFiltroNombre.setEnabled(true);
					controller.setInterestingFriends("", this);
				}
				else
				{
					textFieldFiltroNombre.setEnabled(false);
					controller.setInterestingUsers("", this);
				}
	}
	
	public int getSelectedIndex()
	{
		return this.listUsers.getSelectedIndex();
	}

	public User getElementAt(int index)
	{
		return this.modelUsers.getElementAt(index);
	}
	
	private PanelUsuarios getPanelUsu()
	{
		return this;
	}
}