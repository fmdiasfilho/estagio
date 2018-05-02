package brain.manipulation;

import brain.manipulation.conv.ConversationManipulation;
import com.ibm.watson.developer_cloud.assistant.v1.model.*;
import java.util.List;

public class IntentManipulation extends ConversationManipulation {

    public IntentManipulation(String username, String password, String version) {
        super(username, password, version);
    }

    public List<IntentExport> listIntents(){
        ListIntentsOptions listIntentsOptions = new ListIntentsOptions.Builder(WORKSPACE_ID).build();
        IntentCollection response = conversation.listIntents(listIntentsOptions).execute();
        return response.getIntents();
    }

    public Intent createIntent(String intent, String description){
        CreateIntentOptions createIntentOps = new CreateIntentOptions.Builder(WORKSPACE_ID,intent)
                .description(description).build();
        System.out.format(RETURN_FORMAT,"Create", "Intent", intent);
        return conversation.createIntent(createIntentOps).execute();
    }

    public IntentExport getIntent(String intent){
        GetIntentOptions getIntentOptions = new GetIntentOptions.Builder(WORKSPACE_ID, intent).build();
        System.out.format(RETURN_FORMAT,"Get", "Intent", intent);
        return conversation.getIntent(getIntentOptions).execute();
    }

    /*
        Update intent will only update intent's examples and description (optional)
     */
    public Intent updateIntentExamples(String intent, String newDescription, List<CreateExample> examples){
        UpdateIntentOptions updateIntentOptions = null;
        if(newDescription == null){
            updateIntentOptions = new UpdateIntentOptions.Builder(WORKSPACE_ID,intent).newExamples(examples).build();
        }else{
            updateIntentOptions = new UpdateIntentOptions.Builder(WORKSPACE_ID,intent).newExamples(examples).newDescription(newDescription).build();
        }
        System.out.format(RETURN_FORMAT,"Update", "Examples", examples.toArray());
        return conversation.updateIntent(updateIntentOptions).execute();
    }

    public void deleteIntent(String intent){
        DeleteIntentOptions deleteIntentOptions = new DeleteIntentOptions.Builder(WORKSPACE_ID, intent).build();
        conversation.deleteIntent(deleteIntentOptions).execute();
        System.out.format(RETURN_FORMAT,"Delete", "Intent", intent);
    }
}
