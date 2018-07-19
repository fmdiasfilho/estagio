package brain.manipulation;

import brain.manipulation.conv.ConversationManipulation;
import com.ibm.watson.developer_cloud.assistant.v1.model.*;

import java.util.List;

/**
 * @author Pedro Feiteira, n48119
 * This class has the IBM Watson assistant examples operations
 * For more information watch -> https://www.ibm.com/watson/developercloud/conversation/api/v1/java.html?java#examples-api
 */
public class ExamplesManipulation extends ConversationManipulation {

    public ExamplesManipulation(String username, String password, String version) {
        super(username, password, version);
    }

    public List<Example> listExamples(String intent) {
        ListExamplesOptions listExamplesOptions = new ListExamplesOptions.Builder(WORKSPACE_ID, intent).build();
        ExampleCollection response = conversation.listExamples(listExamplesOptions).execute();
        System.out.format(RETURN_FORMAT, "List", "Intent", intent);
        return response.getExamples();
    }

    public Example createExample(String intent, String example) {
        CreateExampleOptions createExampleOptions = new CreateExampleOptions.Builder(WORKSPACE_ID, intent, example).build();
        System.out.format(RETURN_FORMAT, "Create", "Example", example);
        return conversation.createExample(createExampleOptions).execute();
    }

    public Example getExample(String intent, String example) {
        GetExampleOptions getExampleOptions = new GetExampleOptions.Builder(WORKSPACE_ID, intent, example).build();
        System.out.format(RETURN_FORMAT, "Get", "Example", example);
        return conversation.getExample(getExampleOptions).execute();
    }

    public Example updateExample(String intent, String example, String newExampleText) {
        UpdateExampleOptions updateExampleOptions = new UpdateExampleOptions.Builder(WORKSPACE_ID, intent, example).newText(newExampleText).build();
        System.out.format(RETURN_FORMAT, "Update", "Example", example);
        return conversation.updateExample(updateExampleOptions).execute();
    }

    public void deleteExample(String intent, String example) {
        DeleteExampleOptions deleteExampleOptions = new DeleteExampleOptions.Builder(WORKSPACE_ID, intent, example).build();
        conversation.deleteExample(deleteExampleOptions).execute();
        System.out.format(RETURN_FORMAT, "Delete", "Example", example);
    }


}
