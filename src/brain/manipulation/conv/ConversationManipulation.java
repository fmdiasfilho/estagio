package brain.manipulation;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;

import java.util.List;

public class ConversationManipulation implements ConversationManipulationInterface {

    protected static final String WORKSPACE_ID = "37b94673-abed-4221-af3b-4ef6b3bf302a";
    protected final Conversation conversation;

    public ConversationManipulation(String username, String password, String version){
            this.conversation = createConversation(username,password,version);
    }

    public Conversation getConversation() {
        return conversation;
    }

    private Conversation createConversation(String username, String password, String version){
        return new Conversation(username,password,version);
    }

}
