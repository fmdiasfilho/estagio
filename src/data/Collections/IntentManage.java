package data.Collections;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;

import java.util.ArrayList;
import java.util.List;

public class IntentCollection {

    private final Conversation conv;
    private final String workspaceId;
    private List<Intent> intents;

    public IntentCollection(Conversation conv, String workspaceId){
        this.conv = conv;
        this.workspaceId = workspaceId;
        intents = new ArrayList<>();
    }
    
    public void addIntent(String intent){
        CreateIntentOptions options = new CreateIntentOptions.Builder(workspaceId, intent)
                .examples(new ArrayList<CreateExample>())
                .build();
        conv.createIntent(options).execute();
    }

    public void deleteIntent(String intent){
        DeleteIntentOptions options = new DeleteIntentOptions.Builder(workspaceId,intent).build();
        conv.deleteIntent(options).execute();
    }

    public void updateIntent(String intent, String description, ArrayList<CreateExample> examples, CreateExample example){
        UpdateIntentOptions options = new UpdateIntentOptions.Builder(workspaceId,intent).newExamples(examples)
                .newDescription(description).build();
        conv.updateIntent(options).execute();
    }

    public IntentExport getIntent(String intent){
        GetIntentOptions options = new GetIntentOptions.Builder(workspaceId, intent).build();
        return conv.getIntent(options).execute();
    }

    public 


}
