package brain.manipulation;

import brain.manipulation.conv.ConversationManipulation;
import com.ibm.watson.developer_cloud.assistant.v1.model.*;

import java.util.List;
import java.util.Map;

public class ChatManipulation extends ConversationManipulation {


    public ChatManipulation(String username, String password, String version) {
        super(username, password, version);
    }

    public String getInitialMessage(){
        String dialogNode = "Welcome";
        String result = "";
        GetDialogNodeOptions options = new GetDialogNodeOptions.Builder(WORKSPACE_ID, dialogNode).build();

        DialogNode response = conversation.getDialogNode(options).execute();
        result = ((Map)(response.getOutput().get("text"))).get("values").toString();
        int length = result.length();
        return result.substring(1,length-1);
    }

    public MessageResponse sendQuestion(String message, Context context){
        InputData input = new InputData.Builder(message).build();
        MessageOptions options = null;
        if(context == null){
            options = new MessageOptions.Builder(WORKSPACE_ID)
                    .input(input).build();
        }else {
            options = new MessageOptions.Builder(WORKSPACE_ID)
                    .input(input).context(context).build();}
        return conversation.message(options).execute();
    }

    public List<String> responseFormat(MessageResponse response){
        return response.getOutput().getText();
    }


}
