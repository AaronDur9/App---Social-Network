package abd.p1.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import abd.p1.controller.Controller;
import abd.p1.model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;

/**
 * JDialog that contains a PanelUsuarios and allows a User to invite another User to answer a Question
 */
public class VentanaInvitacionPregunta extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private PanelUsuarios panelUsuarios;
	private Controller controller;
	
	public VentanaInvitacionPregunta(Controller con)
	{
		this.controller = con;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(420, 500);
		setLocation(300, 100);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel lblSeleccionaElUsuario = new JLabel("Selecciona el usuario al que deseas invitar:");
		lblSeleccionaElUsuario.setBounds(10, 11, 271, 14);
		getContentPane().add(lblSeleccionaElUsuario);
		
		this.panelUsuarios = new PanelUsuarios(con);
		this.panelUsuarios.setBounds(20, 36, 368, 380);
		getContentPane().add(this.panelUsuarios);
		
		this.btnAceptar = new JButton("Aceptar");
		this.btnAceptar.setBounds(97, 433, 98, 26);
		getContentPane().add(this.btnAceptar);
		
		this.btnCancelar = new JButton("Cancelar");
		this.btnCancelar.setBounds(207, 433, 98, 26);
		getContentPane().add(this.btnCancelar);
		
		this.controller.setInterestingUsers("", this.panelUsuarios);
		
		addBtnActions();
		
		setVisible(true);
	}

	private void addBtnActions()
	{
		this.btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		this.btnAceptar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = panelUsuarios.getSelectedIndex();
				
				if(index != -1)
				{
					controller.invitarUsuario(panelUsuarios.getElementAt(index));
					dispose();
				}
				else
					JOptionPane.showMessageDialog(btnAceptar, "Selecciona algún usuario antes de aceptar");
			}
		});
	}

	public void setUserList(List<User> interestingUsers)
	{
		this.panelUsuarios.onSetUserList(interestingUsers);
	}
}