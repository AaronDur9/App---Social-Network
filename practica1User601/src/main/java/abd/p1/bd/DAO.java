package abd.p1.bd;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import abd.p1.model.AnsweredQuestion;
import abd.p1.model.FriendRequestMessage;
import abd.p1.model.GenderInterests;
import abd.p1.model.Message;
import abd.p1.model.Opcion;
import abd.p1.model.Pregunta;
import abd.p1.model.QuestionInviteMessage;
import abd.p1.model.TextMessage;
import abd.p1.model.User;

public class DAO
{
	private SessionFactory sf;
	
	public DAO(SessionFactory sf)
	{
		this.sf = sf;
	}
	
	/**
	 * @param u any User
	 * @return u completely instantiated from the DB or null if they don't exist
	 */
	public User login(User u)
	{
		boolean ok = false;
		Session session = this.sf.openSession();
		User tmp = session.get(User.class, u.geteMail());
		
		if(tmp != null)
			ok = u.getPassword().equals(tmp.getPassword());
		
		session.close();
		
		return ok ? tmp : null;
	}
	
	/**
	 * @param u any user
	 * @return true if u exists in the DB
	 */
	public boolean existsUser(User u)
	{
		Session session = this.sf.openSession();
		User tmp = session.get(User.class, u.geteMail());
		
		session.close();
		
		return tmp == null ? false : true;
	}

	/**
	 * Saves a User to the DB
	 * @param user any user
	 */
	public void saveUser(User user)
	{
		Session session = this.sf.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(user);
		tr.commit();
		session.close();
	}

	/**
	 * @param user any User
	 * @return a new User who is the same as user, but updated in the DB
	 */
	public User updateUser(User user)
	{
		Session session = this.sf.openSession();
		Transaction tr = session.beginTransaction();
		
		User u = (User)session.merge(user);
		tr.commit();
		session.close();
		return u;
	}
	
	/**
	 * @param user1 any User
	 * @param user2 any Uset
	 * @return a new User who is the same as user1, but updated in the DB
	 */
	public User updateUserFriends(User user1, User user2, FriendRequestMessage frm)
	{
		User u1,u2;
		Session session = this.sf.openSession();
		Transaction tr = session.beginTransaction();
		
		u1 = (User) session.merge(user1);
		u2 = (User) session.merge(user2);
		
		u1.addFriend(u2);
		u2.addFriend(u1);
		
		session.save(frm);
		
		tr.commit();
		session.close();
		
		return u1;
	}
	
	/**
	 * Add a QuestionInviteMessage to the DB
	 * @param m a QuestionInviteMessage
	 */
	public void addMessageInvite(QuestionInviteMessage m)
	{
		Session session = this.sf.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(m);
		
		tr.commit();
		session.close();
	}

	/**
	 * Add a TextMessage to the DB
	 * @param txtMessage a TextMessage
	 */
	public void addTextMessage(TextMessage txtMessage)
	{
		Session session = this.sf.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(txtMessage);
		
		tr.commit();
		session.close();
	}
	
	/**
	 * @param user any User
	 * @param otroUser any User
	 * @return all the TextMessages sent between user and otroUser ordered by timestamp
	 */
	public List<TextMessage> getMensajeschat(User user, User otroUser)
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery(" Select txtM From TextMessage AS txtM"
								+ " WHERE (txtM.source = :correoU AND txtM.target = :correoOtroU)"
								+ " OR (txtM.source= :correoOtroU AND txtM.target = :correoU)"
								+ " ORDER BY txtM.msgTimestamp");
				
		query.setString("correoU",user.geteMail());
		query.setString("correoOtroU",otroUser.geteMail());
		
		@SuppressWarnings("unchecked")
		List<TextMessage> listM =  query.list();
		
		session.close();
		
		return listM;
	}
	
	/**
	 * @param user any User
	 * @return all the Messages not yet read by user
	 */
	public List<Message> getMensajesNoLeidos(User user)
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery("From Message AS msg"
				+ " WHERE msg.target = :t AND msg.isRead = false"
				+ " ORDER BY msg.msgTimestamp");
				
		query.setString("t",user.geteMail());
		
		@SuppressWarnings("unchecked")
		List<Message> listM = query.list();
		
		session.close();
		
		return listM;
	}
	
	/**
	 * @param mail any User's eMail
	 * @return the User with the mail eMail from the DB, or null if the mail doesn't exist
	 */
	public User recoverUser(String mail)
	{
		Session session = this.sf.openSession();
		User tmp = session.get(User.class, mail);
		
		session.close();
		
		return tmp;
	}
	
	/**
	 * @param user any User
	 * @param userName another User's name
	 * @return 20 Users that are in user's friend list ordered by descending distance to user
	 */
	public List<User> getInterestingFriends(User user, String filtro)
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery("Select usu From User AS u , User as usu"
				+ " WHERE u.eMail = :correo AND usu.name LIKE :f AND usu MEMBER OF u.friends"
				+ " ORDER BY ((:phiOne - usu.latitude) * (:phiOne - usu.latitude) +"
				+ " (:thetaOne - usu.longitude) * (:thetaOne - usu.longitude)) DESC");
		
		query.setString("correo",user.geteMail());
		query.setString("f", "%"+ filtro + "%");
		query.setDouble("phiOne", user.getLatitude());
		query.setDouble("thetaOne", user.getLongitude());
		query.setMaxResults(20);

		@SuppressWarnings("unchecked")
		List<User> userF =  query.list();
		
		session.close();
		
		return userF;
	}
	
	/**
	 * @param user any User
	 * @param otroUser any User
	 * @return true if otroUser is friend of user
	 */
	public boolean hasFriend(User user, User otroUser)
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery("Select usu From User AS u, User as usu"
				+ " WHERE u.eMail = :correo AND usu.eMail = :correoAmigo AND usu MEMBER OF u.friends ");
				
		query.setString("correo",user.geteMail());
		query.setString("correoAmigo",otroUser.geteMail());
		 
		User userF =  (User) query.uniqueResult();
			
		session.close();
		
		return userF != null;
	}
	
	/**
	 * @param user any User
	 * @param interestedIn user's gender interests
	 * @return 20 Users with the gender that user is interested in, ordered by ascending distance to user
	 */
	public List<User> getInterestingUsers(User user, String filtro)
	{
		Session session = this.sf.openSession();
		Query query;
		
		if(user.getInterestedIn() != GenderInterests.BOTH)
		{
			query = session.createQuery("From User AS u WHERE u.eMail <> :correo " + "AND u.gender = :interestedIn AND u.name LIKE :f"
					+ " ORDER BY ((:phiOne - u.latitude) * (:phiOne - u.latitude) + (:thetaOne - u.longitude) * (:thetaOne - u.longitude))");

			query.setString("interestedIn", interestToGender(user.getInterestedIn()));
		}
		else
			query = session.createQuery("From User AS u WHERE u.eMail <> :correo " + "AND u.name LIKE :f"
					+ " ORDER BY ((:phiOne - u.latitude) * (:phiOne - u.latitude) + (:thetaOne - u.longitude) * (:thetaOne - u.longitude))");
		
		query.setString("correo",user.geteMail());
		query.setString("f", "%"+ filtro + "%");
		query.setDouble("phiOne", user.getLatitude());
		query.setDouble("thetaOne", user.getLongitude());
		query.setMaxResults(20);

		@SuppressWarnings("unchecked")
		List<User> userF =  query.list();
		
		session.close();
		
		return userF;
	}
	
	/**
	 * @return 20 top Questions ordered by descending average relevance
	 */
	public List<Pregunta> getTopQuestions()
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery("SELECT DISTINCT op.preguntaMadre From AnsweredQuestion AS ans"
				+ " inner join ans.option AS op right outer join op.preguntaMadre AS pregunta2_"
				+ " group by pregunta2_"
				+ " order by avg(ans.relevance) DESC");
		
		query.setMaxResults(20);
	    @SuppressWarnings("unchecked")
		List<Pregunta> questionL =  (List<Pregunta>) query.list();
		
		session.close();
		
		return questionL;
	}
	
	/**
	 * @param interest any GenderInterests
	 * @return interest in String form
	 */
	private String interestToGender(GenderInterests interest)
	{
		String gender;
	
		switch(interest)
		{
			case MEN:
				gender = "MALE";
				break;
			case WOMEN:
				gender = "FEMALE";
				break;
			default:
				gender = "BOTH";
		}
			
		return gender;
	}
	
	/**
	 * @param u1 any User
	 * @param u2 any User
	 * @return the sum of the relevance of all AnsweredQuestion that both u1 and u2 have answered 
	 */
	public Double getMTotal(User u1, User u2)
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery("SELECT sum(ans.relevance) + sum(ans2.relevance)"
				+ " From AnsweredQuestion AS ans,AnsweredQuestion AS ans2"
				+ " WHERE ans.user = :user1 AND ans2.user = :user2"
				+ " AND ans.option.preguntaMadre = ans2.option.preguntaMadre");
		
		query.setString("user1", u1.geteMail());
		query.setString("user2", u2.geteMail());
		
	    Long total =  (Long) query.uniqueResult();
	    Double mTotal;
	    
	    if(total == null)
			mTotal = 0.0;
	    else
	    	mTotal = total.doubleValue();
		
		session.close();
		
		return mTotal;
	}

	/**
	 * @param u1 any User
	 * @param u2 any User
	 * @return the sum of the relevance of all AnsweredQuestion that both u1 and u2 have answered with the same option
	 */
	public Double getMAcierto(User u1, User u2)
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery("SELECT sum(ans.relevance) + sum(ans2.relevance)"
				+ " From AnsweredQuestion AS ans,AnsweredQuestion AS ans2 "
				+ " WHERE ans.user = :user1 AND ans2.user = :user2"
				+ " AND ans.option.preguntaMadre = ans2.option.preguntaMadre"
				+ " and ans.option = ans2.option");
		
		query.setString("user1", u1.geteMail());
		query.setString("user2", u2.geteMail());
		
	    Long acierto =  (Long) query.uniqueResult();
	    Double mAcierto;
	    
	    if(acierto == null)
			mAcierto = 0.0;
	    else
	    	mAcierto = acierto.doubleValue();
	    
		session.close();
		
		return mAcierto;
	}
	
	/**
	 * @param id a Question's id
	 * @return the Question with the given id
	 */
	public Pregunta getQuestionById(Integer id)
	{
		Session session = this.sf.openSession();
		Pregunta tmp = session.get(Pregunta.class, id);
		
		session.close();
		
		return tmp;
	}

	/**
	 * Save an AnsweredQuestion to the DB
	 * @param ans any AnsweredQuestion
	 */
	public void answerQuestion(AnsweredQuestion ans)
	{
		Session session = this.sf.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(ans);
		tr.commit();
		session.close();
	}

	/**
	 * Deletes an AnsweredQuestion with option from the DB if user has answered it 
	 * @param user any User
	 * @param option any Option
	 */
	public void deleteUserAnswer(User user, Opcion option)
	{
		Session session = this.sf.openSession();
		/*Query query = session.createQuery("From AnsweredQuestion AS ans"
				+ " WHERE ans.option = :idOpcion AND ans.user = :userMail");*/
		
		Query query = session.createQuery("SELECT ans FROM AnsweredQuestion AS ans,Opcion AS op"
				+ " WHERE op.preguntaMadre = :pm AND ans.option = op AND ans.user = :u");
		
		query.setInteger("pm", option.getPreguntaMadre().getId());
		query.setString("u", user.geteMail());
		
		AnsweredQuestion ans = (AnsweredQuestion) query.uniqueResult();
		
		if(ans != null)
		{
			Transaction tr = session.beginTransaction();
		
			session.delete(ans);
			tr.commit();
		}
		
		session.close();
	}

	/**
	 * @return a random Question from the DB
	 */
	public Pregunta getRandomQuestion()
	{
		Session session = this.sf.openSession();
		Query query = session.createQuery("FROM Pregunta"
				+ " ORDER BY RAND()");

		query.setMaxResults(1);
		
		Pregunta p = (Pregunta)query.uniqueResult();
		
		session.close();
		
		return p;
	}

	/**
	 * Mark all unread messages for user with eMail as read
	 * @param eMail a User's eMail
	 */
	public void marcarMensajesLeidos(String eMail)
	{
		Session session = this.sf.openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("UPDATE Message m SET m.isRead = true"
				+ " WHERE m.target = :t");

		query.setString("t", eMail);
		
		query.executeUpdate();
		t.commit();	
		session.close();
	}
}