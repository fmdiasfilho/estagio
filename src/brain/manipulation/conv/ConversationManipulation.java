package brain.manipulation.conv;


import com.ibm.watson.developer_cloud.assistant.v1.Assistant;

public abstract class ConversationManipulation {

    protected static final String WORKSPACE_ID = "37b94673-abed-4221-af3b-4ef6b3bf302a";
    protected static final String RETURN_FORMAT = "%s was done successfully, with %s: %s";
    protected final Assistant conversation;

    public ConversationManipulation(String username, String password, String version){
            this.conversation = createConversation(username,password,version);
    }

    public Assistant getConversation() {
        return conversation;
    }

    private Assistant createConversation(String username, String password, String version){
        return new Assistant(version,username,password);
    }

}
