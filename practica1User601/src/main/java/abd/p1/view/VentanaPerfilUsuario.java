package abd.p1.view;

import abd.p1.controller.Controller;
import abd.p1.model.User;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JDialog containing a PanelDatosUsuario. Allows to change the User's password
 * and save other personal data modifications
 */
public class VentanaPerfilUsuario extends JDialog
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JButton btnCambiarContrasena, btnGuardar, btnCancelar;
	private PanelDatosUsuario panelDatosUsuario;
	
	public VentanaPerfilUsuario(Controller con, User user)
	{
		setModal(true);
		this.controller = con;
		this.controller.setVentanaPerfil(this);
		
		this.setLocation(300, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(700, 600);
		this.setTitle("Perfil Personal");
		this.getContentPane().setLayout(null);
		
		this.panelDatosUsuario = new PanelDatosUsuario(con, user, false);
		this.panelDatosUsuario.setLocation(10, 11);
		this.getContentPane().add(this.panelDatosUsuario);
		
		this.btnCambiarContrasena = new JButton("Cambiar contraseña");
		this.btnCambiarContrasena.setBounds(10, 535, 150, 25);
		this.getContentPane().add(this.btnCambiarContrasena);
		
		this.btnGuardar= new JButton("Guardar cambios");
		this.btnGuardar.setBounds(442, 535, 140, 25);
		getContentPane().add(this.btnGuardar);
		
		this.btnCancelar = new JButton("Cancelar");
		this.btnCancelar.setBounds(594, 535, 90, 25);
		getContentPane().add(this.btnCancelar);
	
		addBtnActions();
		
		this.setVisible(true);
	}

	public void onSetName(String name)
	{
		this.panelDatosUsuario.onSetName(name);
	}
	
	public void onCambiarSexo(String valorSexo)
	{
		this.panelDatosUsuario.onCambiarSexo(valorSexo);
	}
	
	public void onCambiarPreferencia(String interest)
	{
		this.panelDatosUsuario.onCambiarPreferencia(interest);
	}
	
	public void onAnadirAficion(String aficion)
	{
		this.panelDatosUsuario.onAnadirAficion(aficion);
	}
	
	public void onEliminarAficion(int index)
	{
		this.panelDatosUsuario.onEliminarAficion(index);
	}
	
	public void onEditarAficion(int index, String aficion)
	{
		this.panelDatosUsuario.onEditarAficion(index, aficion);
	}
	
	public void onSetEdad(int edad)
	{
		this.panelDatosUsuario.onSetEdad(edad);
	}
	
	public void onSetAvatar(ImageIcon icon)
	{
		this.panelDatosUsuario.onSetAvatar(icon);
	}
	
	private void addBtnActions()
	{
		this.btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				controller.recoverLastUser();
				dispose();
			}
		});
		
		this.btnCambiarContrasena.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String password = JOptionPane.showInputDialog(btnCambiarContrasena, "Nueva contraseña");
				
				if(password != null)
				{
					controller.setUserPassword(password);
					JOptionPane.showMessageDialog(btnCambiarContrasena, "La contraseña ha sido cambiada");
				}
			}
		});
		
		this.btnGuardar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				controller.setUserDescription(panelDatosUsuario.getuserDescription());
				
				if(controller.saveUser())
				{
					controller.setInterestinUsersPrincipal("");
					dispose();
				}
				else
					JOptionPane.showMessageDialog(btnGuardar, "Debes rellenar todos los campos obligatorios");
			}
		});
	}
}