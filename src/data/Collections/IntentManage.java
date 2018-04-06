package data.Collections;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class IntentManage {

    private final Conversation conv;
    private final String workspaceId;

    public IntentManage(Conversation conv, String workspaceId){
        this.conv = conv;
        this.workspaceId = workspaceId;
    }
    
    public void addIntent(String intent){
        CreateIntentOptions options = new CreateIntentOptions.Builder(workspaceId, intent)
                .examples(new ArrayList<CreateExample>())
                .build();
        System.out.println(conv.createIntent(options).execute());
    }

    public void deleteIntent(String intent){
        DeleteIntentOptions options = new DeleteIntentOptions.Builder(workspaceId,intent).build();
        System.out.println(conv.deleteIntent(options).execute());
    }

    public void updateIntent(String intent, String description, ArrayList<CreateExample> examples, CreateExample example){
        UpdateIntentOptions options = new UpdateIntentOptions.Builder(workspaceId,intent).newExamples(examples)
                .newDescription(description).build();
        System.out.println(conv.updateIntent(options).execute());
    }

    public IntentExport getIntent(String intent){
        GetIntentOptions options = new GetIntentOptions.Builder(workspaceId, intent).build();
        System.out.println(conv.getIntent(options).execute());
        return conv.getIntent(options).execute();
    }

    public IntentCollection listIntents(){
        ListIntentsOptions options = new ListIntentsOptions.Builder(workspaceId).build();
        System.out.println(conv.listIntents(options).execute());
        return conv.listIntents(options).execute();
    }

   //Examples
    public void createExample(String intent, String example){
        CreateExampleOptions options = new CreateExampleOptions.Builder(workspaceId, intent, example).build();
        System.out.println(conv.createExample(options).execute());
    }

    public void deleteExample(String intent, String example){
        DeleteExampleOptions options = new DeleteExampleOptions.Builder(workspaceId,intent,example).build();
        System.out.println(conv.deleteExample(options).execute());
    }

    public void updateExample(String intent, String example, String newText){
        UpdateExampleOptions options = new UpdateExampleOptions.Builder(workspaceId, intent, example)
                .newText(newText)
                .build();
        System.out.println(conv.updateExample(options).execute());
    }

    //Entity
    public void createEntity(String entityName, ArrayList<CreateValue> values){
        CreateEntityOptions options = new CreateEntityOptions.Builder(workspaceId, entityName)
                .values(values)
                .build();

        System.out.println(conv.createEntity(options).execute());
    }

    public void deleteEntity(String entityName){
        DeleteEntityOptions options = new DeleteEntityOptions.Builder(workspaceId, entityName).build();
        System.out.println(conv.deleteEntity(options).execute());
    }


    //Values
    public void addValue(String entityName, String value){
        CreateValueOptions options = new CreateValueOptions.Builder(workspaceId, entityName, value).build();
        System.out.println(conv.createValue(options).execute());
    }

    public void deleteValue(String entityName, String value){
        DeleteValueOptions options = new DeleteValueOptions.Builder(workspaceId,entityName,value).build();
        System.out.println(conv.deleteValue(options).execute());
    }

    //DialogNodes
    public void createDialogNode(String dialogNode, String conditions, String title, HashMap<String, String> output, String newKey, String newValue){
       output.put(newKey,newValue);
        CreateDialogNodeOptions options = new CreateDialogNodeOptions.Builder(workspaceId, dialogNode)
                .conditions(conditions)
                .output(output)
                .title(title)
                .build();

        System.out.println(conv.createDialogNode(options).execute());
    }

    public void deleteDialogNode(String dialogNode){
        DeleteDialogNodeOptions options = new DeleteDialogNodeOptions.Builder(workspaceId, dialogNode).build();

        System.out.println(conv.deleteDialogNode(options).execute());
    }
}
