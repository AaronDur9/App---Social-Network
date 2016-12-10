package abd.p1.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friend_request_messages")
public class FriendRequestMessage extends Message
{
	
	public FriendRequestMessage() {
		
	}
	public FriendRequestMessage(Message msg)
	{
		super(msg.getSource(), msg.getTarget(), msg.getMsgTimestamp(), msg.isRead());
	}
}