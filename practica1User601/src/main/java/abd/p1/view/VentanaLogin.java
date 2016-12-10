package abd.p1.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import abd.p1.controller.Controller;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * JDialog for logging into the application
 */
public class VentanaLogin extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
	private Controller controller;
	private JTextField passField;
	private JButton btnLogin;
	private JButton btnNuevoUsuario;
	
	public VentanaLogin(Controller con)
	{
		this.controller = con;

		this.setModal(true);
		this.setLocation(300, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(340, 160);
		this.setTitle("Login");
		this.getContentPane().setLayout(null);
		
		JLabel lblDirCorreo = new JLabel("Dirección de Correo:");
		lblDirCorreo.setBounds(10, 11, 120, 20);
		this.getContentPane().add(lblDirCorreo);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(10, 42, 120, 20);
		this.getContentPane().add(lblContrasena);
		
		this.emailField = new JTextField();
		this.emailField.setBounds(140, 11, 170, 20);
		this.emailField.setColumns(10);
		this.getContentPane().add(emailField);
		
		this.btnLogin = new JButton("Iniciar Sesión");
		this.btnLogin.setBounds(40, 83, 130, 25);
		this.getContentPane().add(btnLogin);
		
		this.btnNuevoUsuario = new JButton("Nuevo Usuario");
		this.btnNuevoUsuario.setBounds(180, 83, 130, 25);
		this.getContentPane().add(btnNuevoUsuario);
		
		this.passField = new JTextField();
		this.passField.setBounds(140, 42, 170, 20);
		this.passField.setColumns(10);
		this.getContentPane().add(passField);

		addBtnActions();
		
		this.setVisible(true);
	}
	
	private void addBtnActions()
	{
		this.btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(controller.login(emailField.getText(), passField.getText()))
					dispose();
				else
					JOptionPane.showMessageDialog(btnLogin, "E-Mail y/o contraseña incorrectos",
							"Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		this.btnNuevoUsuario.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!controller.existsUser(emailField.getText()))
				{
					if(!emailField.getText().equals(""))
					{
						controller.setNewUser(emailField.getText(), passField.getText());
						controller.createVentanaPerfil();
						
						if(controller.getLoginOk())
							dispose();
					}
					else
						JOptionPane.showMessageDialog(btnNuevoUsuario, "Correo inválido");
				}
				else
					JOptionPane.showMessageDialog(btnNuevoUsuario, emailField.getText() + " ya está registrado en la base",
							"Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
}