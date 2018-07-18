package brain.manipulation.conv;


import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Pedro Feiteira, n48119
 * This abstract class is used to initialize the IBM Watson assitant for all the Chat bot operations
 * watch -> https://www.ibm.com/watson/developercloud/conversation/api/v1/java.html?java
 */
public abstract class ConversationManipulation {

    private static final String CONV_USERNAME = "9eecfbe1-6284-41b7-aa41-4e637f3f12ca";
    private static final String CONV_PASSWORD = "Q6JOwfeBWhxa";
    private static final String CONV_VERSION = "2018-02-16";

    public static final String WORKSPACE_ID = "37b94673-abed-4221-af3b-4ef6b3bf302a";
    protected static final String RETURN_FORMAT = "%s was done successfully, with %s: %s";
    protected final Assistant conversation;

    public ConversationManipulation(String username, String password, String version) {
        this.conversation = createConversation(username, password, version);
    }

    @Test
    public void testAssistantCreation() {
        Assistant conv = createConversation(CONV_USERNAME, CONV_PASSWORD, CONV_VERSION);
        assertNotNull(conv);
    }

    private Assistant createConversation(String username, String password, String version) {
        return new Assistant(version, username, password);
    }

}
