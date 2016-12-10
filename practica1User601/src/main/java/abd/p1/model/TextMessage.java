package abd.p1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "text_messages")
public class TextMessage extends Message
{
	@Column(nullable = false)
	private String msgText; //Text that the Message contains.
	
	
	public TextMessage() {
		
	}
	
	public TextMessage(Message msg, String msgTxt)
	{
		super(msg.getSource(), msg.getTarget(), msg.getMsgTimestamp(), msg.isRead());
		this.msgText = msgTxt;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
}