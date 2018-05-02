package brain.manipulation;

import brain.manipulation.conv.ConversationManipulation;
import com.ibm.watson.developer_cloud.assistant.v1.model.*;

import java.util.HashMap;
import java.util.Map;

public class DialogNodeManipulation extends ConversationManipulation {

    public DialogNodeManipulation(String username, String password, String version) {
        super(username, password, version);
    }

    public DialogNodeCollection listDialogNodes(){
        ListDialogNodesOptions listDialogNodesOptions = new ListDialogNodesOptions.Builder(WORKSPACE_ID).build();
        return conversation.listDialogNodes(listDialogNodesOptions).execute();
    }

    public DialogNode createDialogNode(String dialogNode, String title, String parent, String conditions, String output){
        Map<String, String > outputMap = new HashMap<>();
        outputMap.put("text", output);


        CreateDialogNodeOptions createDialogNodeOptions = new CreateDialogNodeOptions.Builder(WORKSPACE_ID, dialogNode)
                    .conditions(conditions).parent(parent)
                    .output(outputMap)
                    .title(title)
                    .build();

        System.out.format(RETURN_FORMAT,"Create", "Conditions", conditions);
        DialogNode dn = conversation.createDialogNode(createDialogNodeOptions).execute();
        return dn;
    }

    public DialogNode getDialogNode(String dialogNode){
        GetDialogNodeOptions getDialogNodeOptions = new GetDialogNodeOptions.Builder(WORKSPACE_ID,dialogNode).build();
        System.out.format(RETURN_FORMAT,"Get", "DialogNode", dialogNode);
        return conversation.getDialogNode(getDialogNodeOptions).execute();
    }

    public DialogNode updateDialogNode(String dialogNode, String newOutput){
        Map<String, String> newMap = new HashMap<>();
        newMap.put("text",newOutput);
        UpdateDialogNodeOptions updateDialogNodeOptions = new UpdateDialogNodeOptions.Builder(WORKSPACE_ID,dialogNode).newOutput(newMap).build();
        System.out.format(RETURN_FORMAT,"Update", "NewOutput", newOutput);
        return conversation.updateDialogNode(updateDialogNodeOptions).execute();
    }

    public void deleteDialogNode(String dialogNode){
        DeleteDialogNodeOptions deleteDialogNodeOptions = new DeleteDialogNodeOptions.Builder(WORKSPACE_ID,dialogNode).build();
        conversation.deleteDialogNode(deleteDialogNodeOptions).execute();
        System.out.format(RETURN_FORMAT,"Delete", "DialogNode", dialogNode);
    }
}
