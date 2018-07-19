package web;

import brain.manipulation.conv.ConversationManipulation;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.*;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Pedro Feiteira, n48119
 * Rest server class
 */
public class ChatManipulationServer implements ChatManipulation {
    public static final String CONV_USERNAME = "9eecfbe1-6284-41b7-aa41-4e637f3f12ca";
    public static final String CONV_PASSWORD = "Q6JOwfeBWhxa";
    public static final String CONV_VERSION = "2018-02-16";

    private Context context = new Context();

    private Assistant conversation = new Assistant(CONV_VERSION,CONV_USERNAME,CONV_PASSWORD);

    /**
     * HTTP server GET Method
     * @return chatbot inital message
     */
   public String getInitialMessage() {
        String dialogNode = "Welcome";
        String result = "";
        GetDialogNodeOptions options = new GetDialogNodeOptions.Builder(ConversationManipulation.WORKSPACE_ID, dialogNode).build();

        DialogNode response = conversation.getDialogNode(options).execute();
        result = ((Map) (response.getOutput().get("text"))).get("values").toString();
        int length = result.length();
        return result.substring(1, length - 1);
    }

    /**
     * HTTP server POST Method. Receives the user input text and send it to the IBM Watson server by using Assistant
     * service.
     * @param message, user's input message
     * @return chatbot response
     */
   public String sendQuestion(String message) {
       String result = "";
        InputData input = new InputData.Builder(message).build();
        MessageOptions options = null;

        if (context == null) {
            options = new MessageOptions.Builder(ConversationManipulation.WORKSPACE_ID)
                    .input(input).build();

        } else {
            options = new MessageOptions.Builder(ConversationManipulation.WORKSPACE_ID)
                    .input(input).context(context).build();
        }
        MessageResponse reply = conversation.message(options).execute();
        context = reply.getContext();
        List<String> replyList = responseFormat(reply);
        for(String s : replyList){
            result += s + "\n";
        }
        return result;
    }

    /**
     * Get the chatbot response
     * @param response, Full response object
     * @return chatbot response
     */
    private List<String> responseFormat(MessageResponse response){
        return response.getOutput().getText();
    }


    public static void main(String[] args){
        String URI_BASE = "http://localhost:9999/";

        ResourceConfig config = new ResourceConfig();
        config.register(new ChatManipulationServer());
        config.registerClasses(RequestsResponseFilter.class);

        JdkHttpServerFactory.createHttpServer( URI.create(URI_BASE), config);

        System.err.println("Server ready....");
    }

}
