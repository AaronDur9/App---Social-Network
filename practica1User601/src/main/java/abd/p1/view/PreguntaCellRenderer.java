package abd.p1.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import abd.p1.model.Pregunta;

public class PreguntaCellRenderer extends PanelPregunta implements ListCellRenderer<Pregunta>
{
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Pregunta> list, Pregunta pregunta,
			int index, boolean isSelected, boolean cellHasFocus)
	{
		this.setEnunciado(pregunta.getEnunciado());
		this.setNumOptions(pregunta.getNumOpciones());
		this.setOpaque(true);
		
		if(isSelected)
			this.setBackground(Color.YELLOW);
		else
			this.setBackground(Color.WHITE);
		
		return this;
	}
}