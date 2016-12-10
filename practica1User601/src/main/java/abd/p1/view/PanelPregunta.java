package abd.p1.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;

/**
 * JPanel that contains a Question and the amount of Answers it has
 */
public class PanelPregunta extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel lblEnunciadoPregunta;
	private JLabel lblNumOpciones;
	
	public PanelPregunta()
	{
		this.setLayout(null);
		this.setPreferredSize(new Dimension(480, 81));
		
		this.lblEnunciadoPregunta = new JLabel("enunciado");
		this.lblEnunciadoPregunta.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.lblEnunciadoPregunta.setBounds(10, 6, 460, 35);
		add(this.lblEnunciadoPregunta);
		
		this.lblNumOpciones = new JLabel("num opciones");
		this.lblNumOpciones.setBounds(10, 40, 93, 14);
		add(this.lblNumOpciones);
		
		this.setVisible(true);
	}
	
	public void setEnunciado(String enunciado)
	{
		this.lblEnunciadoPregunta.setText(enunciado);
	}
	
	public void setNumOptions(int numOpciones)
	{
		this.lblNumOpciones.setText(numOpciones + " opciones");
	}
}