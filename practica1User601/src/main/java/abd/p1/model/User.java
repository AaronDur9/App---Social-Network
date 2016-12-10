package abd.p1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User
{
	@Id
	private String eMail;
	
	@Column(nullable = false)
	private String password, name;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GenderInterests interestedIn;
	
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	private Double latitude, longitude;
	
	@Lob
	private byte[] avatar;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> hobbies;
	
	@ManyToMany
	private Set<User> friends;
	
	@OneToMany(mappedBy = "user")
	private List<AnsweredQuestion> answeredQuestions;
	
	@OneToMany(mappedBy = "source")
	private List<Message> inbox;
	
	@OneToMany(mappedBy = "target")
	private List<Message> sent;
	
	public User()
	{	
		this.eMail = null;
		this.password = null;
		this.name = null;
		this.description = null;
		this.gender = Gender.DEFAULT;
		this.interestedIn = GenderInterests.DEFAULT;
		this.birthDate = null;
		this.avatar = null;
		this.hobbies = new HashSet<String>();
		this.friends = new HashSet<User>();
		this.inbox = new ArrayList<>();
		this.sent = new ArrayList<>();
	}
	
	public User(String eMail, String password)
	{
		this.eMail = eMail;
		this.password = password;
		this.name = null;
		this.description = null;
		this.gender = Gender.DEFAULT;
		this.interestedIn = GenderInterests.DEFAULT;
		this.birthDate = null;
		this.avatar = null;
		this.hobbies = new HashSet<String>();
		this.friends = new HashSet<User>();
		this.inbox = new ArrayList<>();
		this.sent = new ArrayList<>();
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public GenderInterests getInterestedIn() {
		return interestedIn;
	}

	public void setInterestedIn(GenderInterests interestedIn) {
		this.interestedIn = interestedIn;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public List<Message> getInbox() {
		return inbox;
	}

	public void setInbox(List<Message> inbox) {
		this.inbox = inbox;
	}

	public List<Message> getSent() {
		return sent;
	}

	public void setSent(List<Message> sent) {
		this.sent = sent;
	}

	public Set<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(Set<String> hobbies) {
		this.hobbies = hobbies;
	}

	public Set<User> getFriends() {
		return friends;
	}

	/**
	 * Adds a user to the user's friend list
	 * @param friend any User
	 */
	public void addFriend(User friend)
	{
		this.friends.add(friend);
	}

	/**
	 * Add a hobby to the user's hobby list.
	 * @param aficion hobby name/description
	 * @return true if the hobby was successfully added
	 */
	public boolean addHobby(String hobby)
	{
		return this.hobbies.add(hobby);
	}

	/**
	 * Remove a hobby from the user's hobby list.
	 * @param hobby hobby name/description
	 * @return true if the hobby was successfully removed
	 */
	public boolean removeHobby(String hobby)
	{
		return this.hobbies.remove(hobby);
	}

	/**
	 * Modify a hobby that is already in the user's list.
	 * @param newValue new hobby name/description
	 * @param hobby the hobby to modify
	 * @return true if hobby was successfully modified
	 */
	public boolean modifyHobby(String newValue, String hobby)
	{
		boolean ok = false;
		
		if(!this.hobbies.contains(hobby))
		{
			ok = removeHobby(newValue);
		
			if(ok)
				ok = addHobby(hobby);
		}
		
		return ok;
	}
	
	public boolean hasFriend(User user)
	{
		return this.friends.contains(user);
	}

	public void addInboxMessage(Message m)
	{
		this.inbox.add(m);
	}
	
	/*public void addSentMessage(Message m)
	{
		this.sent.add(m);
	}*/
}