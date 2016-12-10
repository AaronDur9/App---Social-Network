package abd.p1.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
@Inheritance(strategy = InheritanceType.JOINED)
public class Message
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int messageId;
	
	@ManyToOne
	private User target;
	
	@ManyToOne
	private User source;
	
	@Column(columnDefinition = "timestamp")
	private Timestamp msgTimestamp;
	
	private boolean isRead; //Indicates if the Message has been read by the user.

	public Message()
	{
		this.source = null;
		this.target = null;
		this.msgTimestamp = null;
	}
	
	public Message(User source, User target, Timestamp msgTimestamp, boolean isRead)
	{
		this.source = source;
		this.target = target;
		this.msgTimestamp = msgTimestamp;
		this.isRead = isRead;
	}

	public User getSource() {
		return source;
	}

	public void setSource(User source) {
		this.source = source;
	}

	public User getTarget() {
		return target;
	}

	public void setTarget(User target) {
		this.target = target;
	}

	public Timestamp getMsgTimestamp() {
		return msgTimestamp;
	}

	public void setMsgTimestamp(Timestamp msgTimestamp) {
		this.msgTimestamp = msgTimestamp;
	}

	public boolean isRead() {
		return this.isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public int getMsgId() {
		return messageId;
	}

	public void setMsgId(int msgId) {
		this.messageId = msgId;
	}
}