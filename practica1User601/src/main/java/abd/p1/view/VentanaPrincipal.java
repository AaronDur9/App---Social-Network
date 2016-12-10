package abd.p1.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import abd.p1.controller.Controller;
import abd.p1.model.Pregunta;
import abd.p1.model.User;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.event.HyperlinkEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

/**
 * JDialog, this is the main application window
 */
public class VentanaPrincipal extends JDialog
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JButton btnModificarPerfil, btnResponder, btnVerPerfilSeleccionado, btnPreguntaAleatoria,
		btnMarcarLeidos;;
	private DefaultListModel<Pregunta> modelQuestions;
	private JList<Pregunta> listQuestions;
	private JScrollPane scrollPanePreguntas;
	private JTextPane textPaneAreaMensajes;
	private PanelUsuarios panUsuarios;
	private JScrollPane scrollPane;
	
	public VentanaPrincipal(Controller con)
	{
		this.controller = con;
		this.controller.setVentanaPrincipal(this);
		
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 100);
		this.setResizable(false);
		this.setSize(400, 500);
		this.setTitle("Principal");
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 374, 449);
		this.getContentPane().add(tabbedPane);
		
		JPanel panelUsuarios = new JPanel();
		tabbedPane.addTab("Usuarios", null, panelUsuarios, null);
		panelUsuarios.setLayout(null);
		
		this.panUsuarios = new PanelUsuarios(con);
		this.panUsuarios.setInterestingUsers();
		panelUsuarios.add(this.panUsuarios);
		
		this.btnModificarPerfil = new JButton("Modificar mi perfil");
		this.btnModificarPerfil.setBounds(37, 387, 140, 25);
		panelUsuarios.add(this.btnModificarPerfil);
		
		this.btnVerPerfilSeleccionado = new JButton("Ver Perfil seleccionado");
		this.btnVerPerfilSeleccionado.setBounds(189, 387, 170, 25);
		panelUsuarios.add(this.btnVerPerfilSeleccionado);
		
		JPanel panelPreguntas = new JPanel();
		tabbedPane.addTab("Preguntas", null, panelPreguntas, null);
		panelPreguntas.setLayout(null);
		
		this.modelQuestions = new DefaultListModel<>();
		
		this.scrollPanePreguntas = new JScrollPane();
		this.scrollPanePreguntas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPanePreguntas.setBounds(10, 29, 349, 336);
		panelPreguntas.add(this.scrollPanePreguntas);
		
		this.listQuestions = new JList<>();
		listQuestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listQuestions.setModel(this.modelQuestions);
		this.listQuestions.setCellRenderer(new PreguntaCellRenderer());

		this.scrollPanePreguntas.setViewportView(listQuestions);
		
		this.controller.setTopQuestions();
		
		this.btnResponder = new JButton("Responder");
		
		this.btnResponder.setBounds(65, 376, 99, 23);
		panelPreguntas.add(btnResponder);
		
		this.btnPreguntaAleatoria = new JButton("Pregunta aleatoria");
		this.btnPreguntaAleatoria.setBounds(182, 376, 166, 23);
		panelPreguntas.add(this.btnPreguntaAleatoria);
		
		JLabel lblNewLabel = new JLabel("Mejor valoradas:");
		lblNewLabel.setBounds(10, 6, 106, 14);
		panelPreguntas.add(lblNewLabel);
		
		JPanel panelMsgNoLeidos = new JPanel();
		tabbedPane.addTab("Mensajes no leídos", null, panelMsgNoLeidos, null);
		panelMsgNoLeidos.setLayout(null);
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(12, 12, 345, 359);
		panelMsgNoLeidos.add(scrollPane);
		
		this.textPaneAreaMensajes = new JTextPane();
		this.textPaneAreaMensajes.setContentType("text/html");
		
		this.scrollPane.setViewportView(textPaneAreaMensajes);
		
		this.textPaneAreaMensajes.addHyperlinkListener(new HyperlinkListener()
		{
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
				{
					String dsc = e.getDescription();
					String type = dsc.substring(0, 3);
					String value = dsc.substring(4, dsc.length());
					
					enableVentanaPrincipal(false);
					
					if(type.equals("prg"))
					{
						try
						{
							controller.createVentanaResponderPreguntaLink(Integer.parseInt(value));
						}
						catch(NumberFormatException nfe)
						{
						}
					}
					else
						controller.createVentanaPerfilOtroUsuarioLink(value);

					enableVentanaPrincipal(true);
				}
			}
		});
		this.textPaneAreaMensajes.setEditable(false);
		
		this.btnMarcarLeidos = new JButton("Marcar todos como leídos");
		this.btnMarcarLeidos.setBounds(83, 383, 200, 26);
		panelMsgNoLeidos.add(this.btnMarcarLeidos);
		
		this.controller.setMensajesNoLeidos();
		
		addBtnActions();
		
		this.setVisible(true);
	}

	public void onSetUserList(List <User> listUsers)
	{
		this.panUsuarios.onSetUserList(listUsers);
	}
	
	public void onSetQuestionList(List <Pregunta> Questionlist)
	{
		this.modelQuestions.removeAllElements();
		
		for(Pregunta p: Questionlist)
			this.modelQuestions.addElement(p);
	}
	
	private void addBtnActions()
	{
		this.btnPreguntaAleatoria.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				enableVentanaPrincipal(false);
				controller.createVentanaResponderPreguntaRandom();
				enableVentanaPrincipal(true);
			}
		});
		
		this.btnResponder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int index = listQuestions.getSelectedIndex();
				
				if(index != -1)
				{
					enableVentanaPrincipal(false);
					controller.createVentanaResponderPregunta(modelQuestions.getElementAt(index));
					enableVentanaPrincipal(true);
				
				}
				else
					JOptionPane.showMessageDialog(btnResponder, "Selecciona alguna pregunta que responder");
			
			}
		});
		
		this.btnModificarPerfil.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableVentanaPrincipal(false);
				controller.createVentanaPerfil();
				enableVentanaPrincipal(true);
			}
		});
		
		this.btnVerPerfilSeleccionado.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = panUsuarios.getSelectedIndex();
				
				if(index != -1)
				{
					enableVentanaPrincipal(false);
					controller.createVentanaPerfilOtrousuario(panUsuarios.getElementAt(index));
					enableVentanaPrincipal(true);
				}
				else
					JOptionPane.showMessageDialog(btnVerPerfilSeleccionado, "Selecciona algún usuario para ver su perfil");
			}
		});
		
		this.btnMarcarLeidos.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				controller.marcarMensajesLeidos();
			}
		});
	}
	
	private void enableVentanaPrincipal(boolean v)
	{
		this.setEnabled(v);
	}
	
	public void setInterestingUsersPrincipal(List<User> listUsers)
	{
		if(this.panUsuarios != null)
			this.panUsuarios.onSetUserList(listUsers);
	}
	
	public PanelUsuarios getPanelUsuarios()
	{
		return this.panUsuarios;
	}

	/**
	 * Add a message to the Chat window
	 * @param htmlMsgText html text with the message
	 */
	public void addMessage(String htmlMsgText)
	{
		try
		{
			HTMLDocument doc = (HTMLDocument)this.textPaneAreaMensajes.getDocument();
			HTMLEditorKit ek = (HTMLEditorKit)this.textPaneAreaMensajes.getEditorKit();
			
			ek.insertHTML(doc, doc.getLength(), htmlMsgText, 0, 0, null);
		}
		catch (BadLocationException | IOException e)
		{
		}
	}

	public void onClearMensajesNoLeidos()
	{
		this.textPaneAreaMensajes.setText("");
	}

	public void onMostrarMensajeSinPreguntas()
	{
		JOptionPane.showMessageDialog(btnPreguntaAleatoria, "No existen preguntas para responder");
	}
}