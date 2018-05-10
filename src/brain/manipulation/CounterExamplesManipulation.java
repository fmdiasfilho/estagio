package brain.manipulation;

import brain.manipulation.conv.ConversationManipulation;
import com.ibm.watson.developer_cloud.assistant.v1.model.*;

import java.util.List;

public class CounterExamplesManipulation extends ConversationManipulation {

    public CounterExamplesManipulation(String username, String password, String version) {
        super(username, password, version);
    }

    public List<Counterexample> listCounterExamples() {
        ListCounterexamplesOptions listCounterexamplesOptions = new ListCounterexamplesOptions.Builder(WORKSPACE_ID).build();
        CounterexampleCollection response = conversation.listCounterexamples(listCounterexamplesOptions).execute();
        return response.getCounterexamples();
    }

    public Counterexample createCounterexample(String counterExample, String text) {
        CreateCounterexampleOptions createCounterexampleOptions = new CreateCounterexampleOptions.Builder(WORKSPACE_ID, text).build();
        System.out.format(RETURN_FORMAT, "Create", "Counter example", counterExample);
        return conversation.createCounterexample(createCounterexampleOptions).execute();
    }

    public Counterexample getCounterexample(String counterExample) {
        GetCounterexampleOptions getCounterexampleOptions = new GetCounterexampleOptions.Builder(WORKSPACE_ID, counterExample).build();
        System.out.format(RETURN_FORMAT, "Get", "Counter example", counterExample);
        return conversation.getCounterexample(getCounterexampleOptions).execute();
    }

    public Counterexample updateCounterexample(String counterExample, String newCounterexampleText) {
        UpdateCounterexampleOptions updateCounterexampleOptions = new UpdateCounterexampleOptions.Builder(WORKSPACE_ID, counterExample).newText(newCounterexampleText).build();
        System.out.format(RETURN_FORMAT, "Create", "Counter example text", newCounterexampleText);
        return conversation.updateCounterexample(updateCounterexampleOptions).execute();
    }

    public void deleteCounterexample(String counterExample) {
        DeleteCounterexampleOptions deleteCuonterexampleOptions = new DeleteCounterexampleOptions.Builder(WORKSPACE_ID, counterExample).build();
        conversation.deleteCounterexample(deleteCuonterexampleOptions).execute();
        System.out.format(RETURN_FORMAT, "Create", "Counter example", counterExample);
    }


}
