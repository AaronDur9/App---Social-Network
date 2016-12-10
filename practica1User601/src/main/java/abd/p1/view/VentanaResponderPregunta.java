package abd.p1.view;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import abd.p1.controller.Controller;
import abd.p1.model.Opcion;
import javax.swing.JSlider;

/**
 * JDialog that allows the User to select an Option for the selected Question and answer it or invite a friend to answer it
 */
public class VentanaResponderPregunta extends JDialog
{
	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JLabel lblEnunciado;
	private DefaultListModel<String> modelOptions;
	private JList<String> listOptions;
	private JButton btnResponder, btnInvitarAmigo;
	private JSlider slider;
	
	public VentanaResponderPregunta(Controller con)
	{
		this.controller = con;
		this.controller.setVentanaresponderPreg(this);
		
		setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 100);
		this.setSize(440, 380);
		this.setTitle("Responder pregunta");
		this.getContentPane().setLayout(null);
		
		this.lblEnunciado = new JLabel("Enunciado");
		this.lblEnunciado.setBounds(10, 11, 394, 29);
		getContentPane().add(this.lblEnunciado);
		
		this.modelOptions = new DefaultListModel<String>();
		this.listOptions = new JList <String>();
		this.listOptions.setBounds(10, 39, 414, 200);
		this.listOptions.setModel(this.modelOptions);
		getContentPane().add(this.listOptions);
		
		JLabel lblRelevancia = new JLabel("Relevancia");
		lblRelevancia.setBounds(10, 258, 90, 14);
		getContentPane().add(lblRelevancia);
		
		this.btnResponder = new JButton("Responder");
		this.btnResponder.setBounds(99, 317, 113, 23);
		getContentPane().add(this.btnResponder);
		
		this.btnInvitarAmigo = new JButton("Invitar a un amigo");
		this.btnInvitarAmigo.setBounds(235, 317, 169, 23);
		getContentPane().add(this.btnInvitarAmigo);
		
		this.slider = new JSlider();
		this.slider.setBounds(110, 250, 294, 56);
		this.slider.setValue(5);
		this.slider.setMaximum(10);
		this.slider.setMinimum(0);
		this.slider.setPaintTicks(true);
		this.slider.setMinorTickSpacing(1);
		this.slider.setMajorTickSpacing(1);
		this.slider.setPaintLabels(true);
		getContentPane().add(this.slider);
		
		this.controller.fillGapsVentanaResponderPregunta();
		
		addBtnActions();
		
		this.setVisible(true);
	}
	
	public void onSetEnunciado(String enunciado)
	{
		this.lblEnunciado.setText(enunciado);
	}
	
	public void onSetOptionList(List<Opcion> opciones)
	{
		for(Opcion o: opciones)
			this.modelOptions.addElement(o.getNumeroOrden() + ". " + o.getTexto());
	}
	
	private void addBtnActions()
	{
		this.btnResponder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int index = listOptions.getSelectedIndex();
				int relevance = slider.getValue();

				if(index != -1)
				{
					controller.answerQuestion(index + 1, relevance);

					dispose();
				}
				else
					JOptionPane.showMessageDialog(btnResponder, "Debes seleccionar alguna opción");
			}
		});
		
		this.btnInvitarAmigo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableVentanaResponderPregunta(false);
				controller.createVentanaInvitacionPregunta();
				enableVentanaResponderPregunta(true);
			}
		});
	}
	
	private void enableVentanaResponderPregunta(boolean b)
	{
		this.setEnabled(b);
	}
}