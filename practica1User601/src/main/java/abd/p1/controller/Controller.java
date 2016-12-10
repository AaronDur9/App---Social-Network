package abd.p1.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import org.hibernate.SessionFactory;
import abd.p1.Coordinate;
import abd.p1.Utils;
import abd.p1.bd.DAO;
import abd.p1.model.AnsweredQuestion;
import abd.p1.model.FriendRequestMessage;
import abd.p1.model.Gender;
import abd.p1.model.GenderInterests;
import abd.p1.model.Message;
import abd.p1.model.Pregunta;
import abd.p1.model.QuestionInviteMessage;
import abd.p1.model.TextMessage;
import abd.p1.model.User;
import abd.p1.view.PanelUsuarios;
import abd.p1.view.VentanaInvitacionPregunta;
import abd.p1.view.VentanaPerfilOtroUsuario;
import abd.p1.view.VentanaPerfilUsuario;
import abd.p1.view.VentanaPrincipal;
import abd.p1.view.VentanaResponderPregunta;

public class Controller
{
	private User user; 
	private DAO dao;
	private boolean loginOk;
	private Pregunta preguntaResponder;
	private VentanaPerfilUsuario ventanaPerfil;
	private VentanaPrincipal ventanaPrincipal;
	private VentanaResponderPregunta ventanaPreg;
	private VentanaPerfilOtroUsuario ventanaOtroUsuario;
	
	/**
	 * Constructor for the Controller
	 * @param sf a SessionFactory
	 */
	public Controller(SessionFactory sf)
	{
		this.loginOk = false;
		this.user = null;
		this.dao = new DAO(sf);
		this.preguntaResponder = null;
		this.ventanaPerfil = null;
		this.ventanaPrincipal = null;
		this.ventanaPreg = null;
		this.ventanaOtroUsuario = null;
	}
	
	public void setVentanaPerfil(VentanaPerfilUsuario ventanaPerfil)
	{
		this.ventanaPerfil = ventanaPerfil;
	}
	
	public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal)
	{
		this.ventanaPrincipal = ventanaPrincipal;
	}
	
	public void setVentanaresponderPreg(VentanaResponderPregunta ventanaPreg)
	{
		this.ventanaPreg = ventanaPreg;
	}

	public void createVentanaInvitacionPregunta()
	{
		new VentanaInvitacionPregunta(this);
	}
	
	/**
	 * Creates another User's window
	 * @param otherUser any User
	 */
	public void createVentanaPerfilOtrousuario(User otherUser)
	{
		Coordinate coordOne = new Coordinate(this.user.getLatitude(), this.user.getLongitude()), 
				coordTwo = new Coordinate(otherUser.getLatitude(), otherUser.getLongitude());
		
		Integer distance = Utils.calculateDistance(coordTwo, coordOne);
		
		new VentanaPerfilOtroUsuario(this, otherUser, distance);
	}
	
	/**
	 * Fills the gaps in VentanaResponderPregunta
	 */
	public void fillGapsVentanaResponderPregunta()
	{
		if(this.preguntaResponder != null)
		{
			this.ventanaPreg.onSetEnunciado(this.preguntaResponder.getEnunciado());
			this.ventanaPreg.onSetOptionList(this.preguntaResponder.getOpciones());
		}
	}
	
	/**
	 * Creates a new VentanaResponderPregunta with a rendom Question
	 */
	public void createVentanaResponderPreguntaRandom()
	{
		this.preguntaResponder = this.dao.getRandomQuestion();
		
		if(this.preguntaResponder != null)
			new VentanaResponderPregunta(this);
		else
			this.ventanaPrincipal.onMostrarMensajeSinPreguntas();
	}
	
	/**
	 * Creates a new VentanaResponderPregunta for p
	 * @param p any Question
	 */
	public void createVentanaResponderPregunta(Pregunta p)
	{
		this.preguntaResponder = p;

		ordenarOpciones();
		
		new VentanaResponderPregunta(this);
	}
	
	private void ordenarOpciones()
	{
         for(int i = 0; i < this.preguntaResponder.getNumOpciones(); i++)
              for(int j = 1; j < this.preguntaResponder.getNumOpciones() - i; j++)
                   if(this.preguntaResponder.getOpcion(j + 1).getNumeroOrden() < this.preguntaResponder.getOpcion(j).getNumeroOrden())
                	   this.preguntaResponder.intercambiarOpciones(j + 1, j);
	}
	
	/**
	 * Creates a new VentanaPerfilUsuario
	 */
	public void createVentanaPerfil()
	{
		new VentanaPerfilUsuario(this, this.user);
	}
	
	/**
	 * @param eMail any eMail
	 * @param password any password
	 * @return true if the eMail exists in the DB and the password is correct
	 */
	public boolean login(String eMail, String password)
	{
		User u = new User(eMail, password);
		
		this.loginOk = ((this.user = this.dao.login(u)) != null) ? true : false;
		
		//Randomly set user's latitude and longitude.
		if(this.loginOk)
		{
			setRandomUserCoordinates();
			saveUser();
		}
		
		return this.loginOk; 
	}
	
	private void setRandomUserCoordinates()
	{
		this.user.setLatitude(Utils.randomDouble(Utils.PHI_MIN_RAD, Utils.PHI_MAX_RAD));
		this.user.setLongitude(Utils.randomDouble(Utils.THETA_MIN_RAD, Utils.THETA_MAX_RAD));
	}

	/**
	 * Get 20 interesting Users from the DB
	 * @param panelUsuarios 
	 * @param interestedIn user's gender interests
	 */
	public void setInterestingUsers(String filtro, PanelUsuarios panelUsuarios)
	{
		if(panelUsuarios != null)
			panelUsuarios.onSetUserList(this.dao.getInterestingUsers(this.user, filtro));
	}
	
	/**
	 * Get 20 interesting Usesrs from the DB for VentanaPrincipal
	 * @param filtro
	 */
	public void setInterestinUsersPrincipal(String filtro)
	{
		if(this.ventanaPrincipal != null)
			this.ventanaPrincipal.setInterestingUsersPrincipal(this.dao.getInterestingUsers(this.user, filtro));
	}
	
	/**
	 * Get 20 friends from the DB
	 * @param name
	 * @param panelUsuarios 
	 */
	public void setInterestingFriends(String filtro, PanelUsuarios panelUsuarios)
	{
		panelUsuarios.onSetUserList(this.dao.getInterestingFriends(this.user, filtro));
	}
	
	public boolean getLoginOk()
	{
		return this.loginOk;
	}
	
	/*
	public User getUser()
	{
		return this.user;
	}
	
	*/
	
	/**
	 * @param eMail any eMail
	 * @return true if the eMail exists in the DB
	 */
	public boolean existsUser(String eMail)
	{
		return this.dao.existsUser(new User(eMail, null));
	}

	/**
	 * Set a new User in the Controller (When creating a new User)
	 * @param eMail any eMail
	 * @param password a password
	 */
	public void setNewUser(String eMail, String password)
	{
		this.user = new User(eMail, password);
		
		setRandomUserCoordinates();
		
		try
		{
			//Set the default avatar to the user
			File avatar = new File("default.png");
			byte[] byteImage = Files.readAllBytes(avatar.toPath());
			
			this.user.setAvatar(byteImage);
		}
		catch (IOException e)
		{
		}
	}

	public void setUserName(String name)
	{
		this.user.setName(name);
		this.ventanaPerfil.onSetName(name);
	}

	public void setUserGender(Gender gender)
	{
		this.user.setGender(gender);
		this.ventanaPerfil.onCambiarSexo(gender.toString().toLowerCase());
	}

	public void setUserInterest(GenderInterests genderInterests)
	{
		this.user.setInterestedIn(genderInterests);
		this.ventanaPerfil.onCambiarPreferencia(genderInterests.toString().toLowerCase());
	}

	public void setUserPassword(String password)
	{
		this.user.setPassword(password);
	}

	/**
	 * @param aficion any hobby
	 * @return true if the hobby was successfully added to the User's hobbies list
	 */
	public boolean addUserHobby(String aficion)
	{
		boolean ok = this.user.addHobby(aficion);
		
		if(ok)
			this.ventanaPerfil.onAnadirAficion(aficion);
		
		return ok;
	}

	/**
	 * @param hobby any hobby
	 * @param index the selected hobby index
	 * @return true if the hobby was successfully removed from the User's hobbies list
	 */
	public boolean removeUserHobby(String hobby, int index)
	{
		boolean ok = this.user.removeHobby(hobby);
		
		if(ok)
			this.ventanaPerfil.onEliminarAficion(index);
		
		return ok;
	}

	/**
	 * @param selectedValue any hobby
	 * @param aficion a new hobby
	 * @param index index of the selectedValue hobby
	 * @return true if the hobby was successfully modified in the User's hobbies list
	 */
	public boolean modifyUserHobby(String selectedValue, String aficion, int index)
	{
		boolean ok = this.user.modifyHobby(selectedValue, aficion);
		
		if(ok)
			this.ventanaPerfil.onEditarAficion(index, aficion);
		
		return ok;
	}

	public void setUserDescription(String text)
	{
		this.user.setDescription(text);
	}

	/**
	 * Saves the Controller's User to the DB
	 * @return
	 */
	public boolean saveUser()
	{
		boolean ok = false;
	
		//Check if all the mandatory field are correctly set
		if(this.user.getLatitude() != null && this.user.getLongitude() != null
				&& this.user.getGender() != Gender.DEFAULT
				&& this.user.getInterestedIn() != GenderInterests.DEFAULT
				&& this.user.getName() != null)
		{
			//Create a new user or update it if it already exists
			if(this.dao.existsUser(this.user))
				this.user = this.dao.updateUser(this.user);
			else
				this.dao.saveUser(this.user);
			
			this.loginOk = true; //In case the User has just been created
			ok = true;
		}
		
		return ok;
	}

	/**
	 * @param selectedDate any Date
	 * @return true if the Date has been correctly set
	 */
	public boolean changeUserBirthDate(Date selectedDate)
	{
		int years = Utils.getDiffYears(selectedDate, new Date());

		if(years >= 18)
		{
			this.user.setBirthDate(selectedDate);
			this.ventanaPerfil.onSetEdad(years);
		}
		
		return years >= 18; 
	}

	/**
	 * Changes the Controlles's User to the last same one from the DB
	 * @return
	 */
	public boolean recoverLastUser()
	{
		this.user = this.dao.recoverUser(this.user.geteMail());
		
		return this.user != null;
	}

	public void setLogin(boolean b)
	{
		this.loginOk = b;
	}

	public void setUserAvatar(ImageIcon icon, byte[] byteImage)
	{
		this.user.setAvatar(byteImage);
		this.ventanaPerfil.onSetAvatar(icon);
	}
	
	/**
	 * Set the top questions in VentanaPrincipal
	 */
	public void setTopQuestions()
	{
		this.ventanaPrincipal.onSetQuestionList(this.dao.getTopQuestions());
	}
	
	/**
	 * Answer a Question in the DB
	 * @param numOpcion the Question's option
	 * @param relevance the answer's relevance
	 */
	public void answerQuestion(int numOpcion, int relevance)
	{
		AnsweredQuestion ans = new AnsweredQuestion();

		ans.setOption(this.preguntaResponder.getOpcion(numOpcion));
		ans.setUser(this.user);
		ans.setRelevance(relevance);
		
		//Si existe ya contestación de este usuario la elimina.
		this.dao.deleteUserAnswer(this.user, this.preguntaResponder.getOpcion(numOpcion));
		this.dao.answerQuestion(ans);
		this.setTopQuestions();
	}

	/**
	 * Sets the compatibility between 2 Users
	 * @param user any User
	 */
	public void setCompatibility(User user)
	{
		double mTotal = this.dao.getMTotal(this.user, user);
		double mAcierto = this.dao.getMAcierto(this.user, user);
		Double compatibilidad;
		
		if(mTotal != 0 && mAcierto != 0)
			compatibilidad = 100 * (mAcierto/mTotal);
		else
			compatibilidad = 0.0;
		
		this.ventanaOtroUsuario.onSetCompatibilidad(compatibilidad);
	}
	
	/**
	 * Sets the common Hobbies between 2 Users
	 * @param user any User
	 */
	public void setCommonHobbies(User user)
	{
		List<String> hobbiesComunes = new ArrayList<String>();
		
		for(String h : this.user.getHobbies())
			if(user.getHobbies().contains(h))
				hobbiesComunes.add(h);
		
		this.ventanaOtroUsuario.onSetHobbiesComunes(hobbiesComunes);
	}

	public void setVentanaPerfilotroUsuario(VentanaPerfilOtroUsuario ventanaPerfilOtroUsuario)
	{
		this.ventanaOtroUsuario = ventanaPerfilOtroUsuario;
	}

	public void userHasFriend(User otroUser)
	{
		 if(this.dao.hasFriend(this.user, otroUser))
			 this.ventanaOtroUsuario.setEnviarFalse();
	}
	
	
	/**
	 * Request a friendship to user
	 * @param user any User
	 */
	public void requestFriendship(User otroUser)
	{
		Message m = new Message(this.user, otroUser,new Timestamp(new Date().getTime()) , false);
		FriendRequestMessage frm = new FriendRequestMessage(m);

		this.user = this.dao.updateUserFriends(this.user, otroUser, frm);
		this.ventanaOtroUsuario.setEnviarFalse();
	}
	
	/**
	 * Add a TextMessage to the DB
	 * @param otroUser target User in the TextMessage
	 * @param text the text in TextMessage
	 */
	public void sendMessage(User otroUser, String text)
	{
		Message m = new Message(this.user, otroUser,new Timestamp(new Date().getTime()) , false);
		TextMessage txtMessage = new TextMessage(m, text);
		
		this.dao.addTextMessage(txtMessage);
		this.ventanaOtroUsuario.addMessage(Utils.generateTextMessageHTML(txtMessage));
	}

	/**
	 * Add a QuestionInviteMessage to the DB
	 * @param otroUser the User to invite
	 */
	public void invitarUsuario(User otroUser)
	{
		Message m = new Message(this.user, otroUser, new Timestamp(new Date().getTime()), false);
		QuestionInviteMessage questionMessage = new QuestionInviteMessage(m, this.preguntaResponder);
		
		this.dao.addMessageInvite(questionMessage);
	}
	
	/**
	 * Update the chat messages in VentanaOtroUsuario
	 * @param otroUser any User
	 */
	public void setMensajesChat(User otroUser)
	{
		List<TextMessage> listaMensajes = this.dao.getMensajeschat(this.user,otroUser);
		
		for(TextMessage tm: listaMensajes)
			this.ventanaOtroUsuario.addMessage(Utils.generateTextMessageHTML(tm));
	}

	/**
	 * Recover and set all the unread Messages in VentanaPrincipal
	 */
	public void setMensajesNoLeidos()
	{
		List <Message> m = this.dao.getMensajesNoLeidos(this.user);
		
		for(Message msg : m)
		{
			try
			{
				this.ventanaPrincipal.addMessage(Utils.generateTextMessageHTML((TextMessage)msg));
			}
			catch(Exception e)
			{
				try
				{
					this.ventanaPrincipal.addMessage(Utils.generateFriendRequestMessageHTML((FriendRequestMessage)msg));
				}
				catch (Exception e2)
				{
					try
					{
						this.ventanaPrincipal.addMessage(Utils.generateQuestionInviteMessageHTML((QuestionInviteMessage)msg));
					}
					catch (Exception e3)
					{
					}
				}
			}
		}
	}

	/**
	 * Mark all unread Messages as read for the Controller0's User
	 */
	public void marcarMensajesLeidos()
	{
		this.dao.marcarMensajesLeidos(this.user.geteMail());
		this.ventanaPrincipal.onClearMensajesNoLeidos();
	}

	/**
	 * Create a new VentanaPerfilOtroUsuario after pressing a User link in VentanaPrincipal
	 * @param eMail a User eMail
	 */
	public void createVentanaPerfilOtroUsuarioLink(String eMail)
	{
		User other = this.dao.recoverUser(eMail);
		
		if(other != null)
			createVentanaPerfilOtrousuario(other);
	}

	/**
	 * Create a new VentanaResponderPregunta after pressing a Question link in VentanaPrincipal
	 * @param value
	 */
	public void createVentanaResponderPreguntaLink(Integer value)
	{
		createVentanaResponderPregunta(this.dao.getQuestionById(value));
	}
}