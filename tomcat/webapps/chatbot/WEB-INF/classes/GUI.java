import brain.manipulation.Brain;
import brain.manipulation.ChatManipulation;
import brain.manipulation.conv.ConversationManipulation;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GUI extends HttpServlet {

    //TODO MISS CONTEXT ::  context = response.getContext();
    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        //Clean system error
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {
                // keep empty
            }
        }));

        //Get body from http POST
        String text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //Chat link creation
        ChatManipulation chat = new ChatManipulation(ConversationManipulation.CONV_USERNAME,ConversationManipulation.CONV_PASSWORD, ConversationManipulation.CONV_VERSION);
        MessageResponse inputToBot = chat.sendQuestion(text,null);
        //Response
        response.setContentType("text/plain");
        PrintWriter output = response.getWriter();
        List<String> outputList = chat.responseFormat(inputToBot);
        outputList.forEach(currentOut -> {
            output.println(currentOut);
        });
    }

    public void destroy()
    {
        // Leaving empty. Use this if you want to perform
        //something at the end of Servlet life cycle.
    }
}
