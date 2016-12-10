package abd.p1;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import abd.p1.model.FriendRequestMessage;
import abd.p1.model.QuestionInviteMessage;
import abd.p1.model.TextMessage;

public final class Utils
{
	public static final int EARTH_RADIUS = 6371000;
	
	public static final double PHI_MIN_DEG = 40.0,
			PHI_MAX_DEG = 41.2,
			THETA_MIN_DEG = 3.0,
			THETA_MAX_DEG = 4.5;
	
	public static final double PHI_MIN_RAD = Math.toRadians(PHI_MIN_DEG),
			PHI_MAX_RAD = Math.toRadians(PHI_MAX_DEG),
			THETA_MIN_RAD = Math.toRadians(THETA_MIN_DEG),
			THETA_MAX_RAD = Math.toRadians(THETA_MAX_DEG);
	
	/**
	 * @param first the beginning date
	 * @param last the ending date
	 * @return the amount of years between first and last
	 */
	public static int getDiffYears(Date first, Date last)
	{
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    
	    if(a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE)))
	        diff--;

	    return diff;
	}

	private static Calendar getCalendar(Date date)
	{
	    Calendar cal = Calendar.getInstance(Locale.FRANCE);
	    
	    cal.setTime(date);
	    
	    return cal;
	}

	/**
	 * @param min minimum value
	 * @param max maximum value
	 * @return Double between min and max inclusive.
	 */
	public static Double randomDouble(double min, double max)
	{
		return min + (max - min) * new Random().nextDouble();
	}

	/**
	 * @param coordOne any coordinate
	 * @param coordTwo any coordinate
	 * @return the distance in meters from coordOne to coordTwo
	 */
	public static Integer calculateDistance(Coordinate coordOne, Coordinate coordTwo)
	{
		Integer distance = null;
		
		if((coordOne != null) && (coordTwo != null))
		{
			Double cOneLat = coordOne.getLatitude(), cOneLon = coordOne.getLongitude(),
					cTwoLat = coordTwo.getLatitude(), cTwoLon = coordTwo.getLongitude();
			
			if(cOneLat != null && cOneLon != null && cTwoLat != null && cTwoLon != null)
			{
				double phiIncrement = cOneLat - cTwoLat, thetaIncrement = cOneLon - cTwoLon;
				
				double a = (Math.sin(phiIncrement / 2) * Math.sin(phiIncrement / 2))
						+ Math.cos(cOneLat) * Math.cos(cTwoLat)
						* (Math.sin(thetaIncrement / 2) * Math.sin(thetaIncrement / 2));
				
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				
				distance = (int)(Utils.EARTH_RADIUS * c);
			}
		}
		
		return distance;
	}

	/**
	 * @param tm a TextMessage
	 * @return HTML code for a TextMessage
	 */
	public static String generateTextMessageHTML(TextMessage tm)
	{
		return "<span style=\"color:red\">" + "[" + tm.getMsgTimestamp().toString() + "] " + tm.getSource().getName()
				+ " escribió: <br>" + "</span>"  + tm.getMsgText(); 
	}

	/**
	 * @param frm a FriendRequestMessage
	 * @return HTML code for a FriendRequestMessage
	 */
	public static String generateFriendRequestMessageHTML(FriendRequestMessage frm)
	{
		return "<span style=\"color:red\">" + "[" + frm.getMsgTimestamp().toString() + "] Solicitud de amistad de:" + "</span><br>"
				+ " <a href=\"usr_" + frm.getSource().geteMail() + "\">" + frm.getSource().getName() + "</a>"; 
	}

	/**
	 * @param qim a QuestionInviteMessage
	 * @return HTML code for a QuestionInviteMessage
	 */
	public static String generateQuestionInviteMessageHTML(QuestionInviteMessage qim)
	{
		return "<span style=\"color:red\">" + "[" + qim.getMsgTimestamp().toString() + "] " + qim.getSource().getName()
				+ " te ha invitado a responder la pregunta: <br>" + "</span>" + " <a href=\"prg_" + qim.getQuestionInvite().getId() + "\">"
				+ qim.getQuestionInvite().getEnunciado() + "</a>";
	}
}