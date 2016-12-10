package abd.p1.view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import abd.p1.controller.Controller;
import abd.p1.model.User;
import javax.swing.DefaultListModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JTextField;

/**
 * JDialog containing another User's personal information
 */
public class VentanaPerfilOtroUsuario extends JDialog
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private PanelDatosUsuario panelDatosUsuario;
	private JLabel lblValordistancia;
	private JButton btnEnviar;
	private DefaultListModel<String> listAficionesModel;
	private JList<String> listHobbies;
	private JLabel lblPorcentaje;
	private JTextPane textPaneChat;
	private JTextField textFieldEnviarMensaje;
	
	public VentanaPerfilOtroUsuario(Controller con, User user, Integer distance)
	{
		this.controller = con;
		this.controller.setVentanaPerfilotroUsuario(this);

		this.setModal(true);
		this.setLocation(300, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(720, 660);
		this.setTitle("Perfil Usuario");
		this.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 692, 568);
		getContentPane().add(tabbedPane);
		
		JPanel panelPerfil = new JPanel();
		tabbedPane.addTab("Perfil", null, panelPerfil, null);
		panelPerfil.setLayout(null);
		
		JPanel panelCompatibilidad = new JPanel();
		tabbedPane.addTab("Compatibilidad", null, panelCompatibilidad, null);
		panelCompatibilidad.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tu nivel de compatibilidad es de:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 11, 639, 27);
		panelCompatibilidad.add(lblNewLabel);
		
		this.lblPorcentaje = new JLabel("");
		this.lblPorcentaje.setFont(new Font("Tahoma", Font.BOLD, 20));
		this.lblPorcentaje.setBounds(237, 49, 122, 38);
		panelCompatibilidad.add(this.lblPorcentaje);
		
		JLabel lblNewLabel_1 = new JLabel("Intereses comunes:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 134, 196, 27);
		panelCompatibilidad.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 181, 621, 327);
		panelCompatibilidad.add(scrollPane);
		
		this.listHobbies = new JList<String>();
		
		scrollPane.setViewportView(this.listHobbies);
		this.listAficionesModel = new DefaultListModel<String>();
		this.listHobbies.setModel(this.listAficionesModel);
		
		JPanel panelChat = new JPanel();
		tabbedPane.addTab("Chat", null, panelChat, null);
		panelChat.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 667, 493);
		panelChat.add(scrollPane_1);
		
		this.textPaneChat = new JTextPane();
		scrollPane_1.setViewportView(textPaneChat);
		
		this.textPaneChat.setEditable(false);
		this.textPaneChat.setContentType("text/html");
		
		JLabel lblEnviarMensaje = new JLabel("Enviar mensaje:");
		lblEnviarMensaje.setBounds(10, 515, 104, 14);
		panelChat.add(lblEnviarMensaje);
		
		this.textFieldEnviarMensaje = new JTextField();
		this.textFieldEnviarMensaje.setBounds(124, 512, 553, 20);
		this.textFieldEnviarMensaje.setColumns(10);
		panelChat.add(this.textFieldEnviarMensaje);
		
		this.textFieldEnviarMensaje.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!textFieldEnviarMensaje.getText().equals("")) 
					controller.sendMessage(user, textFieldEnviarMensaje.getText());
				
				textFieldEnviarMensaje.setText("");
			}
		});
		
		this.panelDatosUsuario = new PanelDatosUsuario(con, user, true);
		this.panelDatosUsuario.setLocation(10, 11);
		panelPerfil.add(this.panelDatosUsuario);
		
		JLabel lblDistancia = new JLabel("Distancia: ");
		lblDistancia.setBounds(10, 596, 66, 16);
		getContentPane().add(lblDistancia);
		
		this.btnEnviar = new JButton("Enviar petición de amistad");
		this.btnEnviar.setBounds(504, 591, 198, 26);
		getContentPane().add(this.btnEnviar);
		
		this.controller.userHasFriend(user);
			
		this.btnEnviar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				controller.requestFriendship(user);
			}
		});
		
		this.lblValordistancia = new JLabel("");
		this.lblValordistancia.setBounds(73, 596, 91, 16);
		getContentPane().add(this.lblValordistancia);
	
		if(distance != null)
			this.lblValordistancia.setText(distance.toString() + "m");
		
		this.controller.setCompatibility(user);
		this.controller.setCommonHobbies(user);
		this.controller.setMensajesChat(user);
		
		this.setVisible(true);
	}
	
	public void onSetCompatibilidad(Double compatibilidad)
	{
		this.lblPorcentaje.setText(compatibilidad.intValue() +" %");
	}
	
	public void setEnviarFalse() {
		
		this.btnEnviar.setEnabled(false);
	}
	
	/**
	 * Add a message to the Chat window
	 * @param htmlMsgText html text with the message
	 */
	public void addMessage(String htmlMsgText)
	{
		try
		{
			HTMLDocument doc = (HTMLDocument)this.textPaneChat.getDocument();
			HTMLEditorKit ek = (HTMLEditorKit)this.textPaneChat.getEditorKit();
			
			ek.insertHTML(doc, doc.getLength(), htmlMsgText, 0, 0, null);
		}
		catch (BadLocationException | IOException e)
		{
		}
	}
	
	public void onSetHobbiesComunes(List <String> hob)
	{
		this.listAficionesModel.removeAllElements();
		
		for(String h: hob)
			this.listAficionesModel.addElement(h);
	}
}