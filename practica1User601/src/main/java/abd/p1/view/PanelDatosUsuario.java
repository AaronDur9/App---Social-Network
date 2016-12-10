package abd.p1.view;

import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import abd.p1.Utils;
import abd.p1.controller.Controller;
import abd.p1.model.Gender;
import abd.p1.model.GenderInterests;
import abd.p1.model.User;

/**
 * JPanel that contains all the personal information for a User
 */
public class PanelDatosUsuario extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JButton btnCambiarAvatar, btnCambiarNombre, btnAnadirAficion, btnEliminarAfcSelec,
		btnEditarAfcSelec, btnCambiarSexo, btnCambiarPreferencia;
	private JTextArea txtrUsrDsc;
	private JLabel lblNombreUsuario, lblEdad, lblValorSexo, lblValorBusca;
	private JScrollPane scrollPaneDescripcion, scrollPaneAficiones;
	private JList<String> listAficiones;
	private DefaultListModel<String> listAficionesModel;
	private JButton btnCambiarFechaNac;
	private JPanelAvatar panelAvatar;
	private Controller controller;
	
	public PanelDatosUsuario(Controller con, User user, boolean viewOnly)
	{
		this.controller = con;
		
		setLayout(null);
		setBackground(SystemColor.activeCaption);
		setSize(674, 513);
		
		this.btnCambiarAvatar = new JButton("Cambiar avatar");
		this.btnCambiarAvatar.setBounds(452, 116, 210, 25);
		add(this.btnCambiarAvatar);
		
		this.btnCambiarNombre = new JButton("Cambiar nombre");
		this.btnCambiarNombre.setBounds(452, 13, 210, 25);
		add(this.btnCambiarNombre);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(10, 121, 75, 14);
		add(lblDescripcion);
		
		this.scrollPaneDescripcion = new JScrollPane();
		this.scrollPaneDescripcion.setBounds(10, 146, 654, 150);
		add(this.scrollPaneDescripcion);
		
		this.txtrUsrDsc = new JTextArea();
		this.txtrUsrDsc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.scrollPaneDescripcion.setViewportView(txtrUsrDsc);
		
		this.btnAnadirAficion = new JButton("Añadir afición");
		this.btnAnadirAficion.setBounds(452, 305, 210, 25);
		add(this.btnAnadirAficion);
		
		this.btnEliminarAfcSelec = new JButton("Eliminar afición seleccionada");
		this.btnEliminarAfcSelec.setBounds(452, 341, 210, 25);
		add(this.btnEliminarAfcSelec);
		
		this.btnEditarAfcSelec = new JButton("Editar afición seleccionada");
		this.btnEditarAfcSelec.setBounds(452, 379, 210, 25);
		add(this.btnEditarAfcSelec);
		
		this.btnCambiarSexo = new JButton("Cambiar sexo");
		this.btnCambiarSexo.setBounds(452, 439, 210, 25);
		add(this.btnCambiarSexo);
		
		this.btnCambiarPreferencia = new JButton("Cambiar preferencia");
		this.btnCambiarPreferencia.setBounds(452, 477, 210, 25);
		add(this.btnCambiarPreferencia);
		
		JLabel lblAficiones = new JLabel("Aficiones:");
		lblAficiones.setBounds(10, 307, 60, 14);
		add(lblAficiones);
		
		JLabel lblBusca = new JLabel("Busca:");
		lblBusca.setBounds(10, 482, 50, 14);
		add(lblBusca);
		
		JLabel lblGenero = new JLabel("Sexo:");
		lblGenero.setBounds(10, 446, 46, 14);
		add(lblGenero);
		
		this.panelAvatar = new JPanelAvatar();
		this.panelAvatar.setBounds(10, 11, 90, 90);
		add(this.panelAvatar);
		
		this.lblNombreUsuario = new JLabel("Nombre Usuario");
		this.lblNombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.lblNombreUsuario.setBounds(110, 16, 200, 17);
		add(this.lblNombreUsuario);
		
		this.lblEdad = new JLabel("edad");
		this.lblEdad.setBounds(110, 44, 107, 14);
		add(this.lblEdad);
		
		this.lblValorSexo = new JLabel("Género");
		this.lblValorSexo.setBounds(67, 446, 83, 16);
		add(this.lblValorSexo);
		
		this.lblValorBusca = new JLabel("Interesado en");
		this.lblValorBusca.setBounds(67, 482, 83, 16);
		add(this.lblValorBusca);
		
		this.scrollPaneAficiones = new JScrollPane();
		this.scrollPaneAficiones.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPaneAficiones.setBounds(10, 332, 432, 103);
		add(this.scrollPaneAficiones);
		
		this.listAficionesModel = new DefaultListModel<String>();
		
		this.listAficiones = new JList<String>();
		this.scrollPaneAficiones.setViewportView(this.listAficiones);
		this.listAficiones.setModel(this.listAficionesModel);
		
		this.btnCambiarFechaNac = new JButton("Cambiar fecha nacimiento");
		this.btnCambiarFechaNac.setBounds(452, 78, 210, 26);
		add(this.btnCambiarFechaNac);
		
		fillUserData(user);
		addBtnActions();
		
		if(viewOnly)
			setViewOnly();
	}
	
	private void addBtnActions()
	{
		this.btnCambiarNombre.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String name = JOptionPane.showInputDialog ("Nombre");
				
				if(name != null)
				{
					if(!name.equals(""))
						controller.setUserName(name);
					else
						JOptionPane.showMessageDialog(btnCambiarNombre, "Nombre inválido");
				}
			}
		});
		
		this.btnCambiarSexo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String[] gender = new String[] {Gender.MALE.toString(), Gender.FEMALE.toString()};
				int answer = JOptionPane.showOptionDialog(btnCambiarSexo, "Cambiar género","Género",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, gender, gender[0]);
				
				if(answer != -1)
					controller.setUserGender(Gender.values()[answer + 1]);
			}
		});
		
		this.btnCambiarPreferencia.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String[] interested = new String[] {GenderInterests.MEN.toString(), GenderInterests.WOMEN.toString(), GenderInterests.BOTH.toString()};
				int answer = JOptionPane.showOptionDialog(btnCambiarPreferencia, "Cambiar preferencia","Interesado en...",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, interested, interested[0]);
				
				if(answer != -1)
					controller.setUserInterest(GenderInterests.values()[answer + 1]);
			}
		});
		
		this.btnAnadirAficion.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String aficion = JOptionPane.showInputDialog ("Añadir afición");
				
				if(aficion != null)
				{
					if(!aficion.equals(""))
					{
						if(!controller.addUserHobby(aficion))
							JOptionPane.showMessageDialog(btnAnadirAficion, "Afición existente");
					}
					else
						JOptionPane.showMessageDialog(btnAnadirAficion, "Afición inválida");
				}
			}
		});
		
		this.btnEliminarAfcSelec.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = listAficiones.getSelectedIndex();
				
				if(index != -1)
				{
					if(!controller.removeUserHobby(listAficiones.getSelectedValue(), index))
						JOptionPane.showMessageDialog(btnEliminarAfcSelec, "Error al eliminar afición");
				}
				else
					JOptionPane.showMessageDialog(btnEliminarAfcSelec, "Selecciona una afición antes de eliminar");
			}
		});
		
		this.btnEditarAfcSelec.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = listAficiones.getSelectedIndex();
				
				if(index != -1)
				{
					String aficion = JOptionPane.showInputDialog (btnEditarAfcSelec, "Editar afición");
					
					if(aficion != null)
					{
						if(!aficion.equals(""))
						{
							if(!controller.modifyUserHobby(listAficiones.getSelectedValue(), aficion, index))
								JOptionPane.showMessageDialog(btnEditarAfcSelec, "Error al editar afición");
						}
						else
							JOptionPane.showMessageDialog(btnEditarAfcSelec, "Afición inválida");
					}
				}
				else
					JOptionPane.showMessageDialog(btnEditarAfcSelec, "Selecciona una afición antes de editar");
			}
		});
		
		this.btnCambiarFechaNac.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableVentanaPerfilUsuario(false);
				
				VentanaFechaNacimiento vfn = new VentanaFechaNacimiento();
				
				if(vfn.hasSelectedDate())
				{
					Date selectedDate = vfn.getSelectedDate();
				
					if(selectedDate != null && !controller.changeUserBirthDate(selectedDate))
						JOptionPane.showMessageDialog(btnCambiarFechaNac, "Debes tener al menos 18 años");
				}
				
				vfn.dispose();
				enableVentanaPerfilUsuario(true);
			}
		});
		
		this.btnCambiarAvatar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogTitle("Elegir imagen de avatar");
				
				int returnVal = fc.showDialog(btnCambiarAvatar, "Seleccionar");
				File file = null;
				
				if(returnVal ==  JFileChooser.APPROVE_OPTION)
				{
					file = fc.getSelectedFile();
				
					try
					{
						Image image = ImageIO.read(file);
						ImageIcon icon = new ImageIcon(image);
						byte[] byteImage = Files.readAllBytes(file.toPath());
						
						controller.setUserAvatar(icon, byteImage);
					}
					catch (Exception e1)
					{
						JOptionPane.showMessageDialog(btnCambiarAvatar, "Archivo no válido");
					}
				}
			}
		});
	}

	private void onSetDescription(String description)
	{
		this.txtrUsrDsc.setText(description);
	}

	public void onSetName(String name)
	{
		this.lblNombreUsuario.setText(name);
	}
	
	public void onCambiarSexo(String valorSexo)
	{
		this.lblValorSexo.setText(valorSexo);
	}
	
	public void onCambiarPreferencia(String interest)
	{
		this.lblValorBusca.setText(interest);
	}
	
	public void onAnadirAficion(String aficion)
	{
		this.listAficionesModel.addElement(aficion);
	}
	
	public void onEliminarAficion(int index)
	{
		this.listAficionesModel.removeElementAt(index);
	}
	
	public void onEditarAficion(int index, String aficion)
	{
		this.listAficionesModel.remove(index);
		this.listAficionesModel.add(index, aficion);
	}
	
	public void onSetEdad(int edad)
	{
		this.lblEdad.setText(edad + " años");
	}
	
	public void onSetAvatar(ImageIcon icon)
	{
		this.panelAvatar.setIcon(icon);
	}
	
	private void fillUserData(User user)
	{
		if(user != null)
		{
			if(user.getAvatar() != null)
			{
				ImageIcon ii = new ImageIcon(user.getAvatar());
				
				this.panelAvatar.setIcon(ii);
			}
			
			if(user.getName() != null)
				onSetName(user.getName());
			
			if(user.getBirthDate() != null)
			{
				Date now = new Date();
				int years = Utils.getDiffYears(user.getBirthDate(), now);
				
				onSetEdad(years);
			}
			
			if(user.getDescription() != null)
				onSetDescription(user.getDescription());
			
			if(user.getHobbies() != null)
				for(String af : user.getHobbies())
					onAnadirAficion(af);
			
			if(user.getGender() != Gender.DEFAULT)
				onCambiarSexo(user.getGender().toString().toLowerCase());
			
			if(user.getInterestedIn() != GenderInterests.DEFAULT)
				onCambiarPreferencia(user.getInterestedIn().toString().toLowerCase());
		}
	}
	
	private void enableVentanaPerfilUsuario(boolean v)
	{
		this.setEnabled(v);
	}

	public String getuserDescription()
	{
		return this.txtrUsrDsc.getText();
	}
	
	public void setViewOnly()
	{
		this.btnCambiarNombre.setVisible(false);
		this.btnCambiarFechaNac.setVisible(false);
		this.btnCambiarAvatar.setVisible(false);
		this.txtrUsrDsc.setEditable(false);
		this.btnAnadirAficion.setVisible(false);
		this.btnEliminarAfcSelec.setVisible(false);
		this.btnEditarAfcSelec.setVisible(false);
		this.btnCambiarSexo.setVisible(false);
		this.btnCambiarPreferencia.setVisible(false);
	}
}