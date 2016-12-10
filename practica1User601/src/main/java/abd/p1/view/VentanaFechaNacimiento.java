package abd.p1.view;

import javax.swing.JDialog;
import com.toedter.calendar.JCalendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;

/**
 * JDialog that alows the User to select a Date from a Calendar
 */
public class VentanaFechaNacimiento extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JButton btnSeleccionar;
	private boolean dateSelected;
	
	public VentanaFechaNacimiento()
	{
		this.dateSelected = false;
		
		this.setSize(240, 260);
		this.setLocation(500, 300);
		this.setModal(true);
		getContentPane().setLayout(null);
		
		this.calendar = new JCalendar();
		this.calendar.setBounds(10, 11, 198, 153);
		getContentPane().add(this.calendar);
		
		this.btnSeleccionar = new JButton("Seleccionar");
		this.btnSeleccionar.setBounds(54, 175, 122, 26);
		getContentPane().add(this.btnSeleccionar);
		
		addBtnActions();
		
		this.setVisible(true);
	}

	public Date getSelectedDate()
	{
		return this.calendar.getDate();
	}
	
	private void addBtnActions()
	{
		this.btnSeleccionar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dateSelected = true;
				setVisibility(false);
			}
		});
	}
	
	private void setVisibility(boolean b)
	{
		this.setVisible(b);
	}

	public boolean hasSelectedDate()
	{
		return this.dateSelected;
	}
}