

import com.google.gson.*;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.*;
import data.Collections.IntentManage;
import org.apache.commons.lang3.builder.ToStringExclude;
import java.io.IOException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Main {

    private static final String workspaceId = "4d7e98bc-aaab-4428-8edc-eed4abad30de";
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        Conversation conv = getStarted("cffbcc56-9d78-4cb9-9845-13a86f67b3f8", "s2Ial2Y8AaFf", "2018-02-16");
        IntentManage intMan = new IntentManage(conv, workspaceId);


        do{
            System.out.println("Tell me something!");
            String text = scanner.nextLine();
            if(!text.equalsIgnoreCase("EXIT")){
                System.out.println(sendQuestion(text,conv).getIntents().get(0).getIntent());
            }else{
                System.out.println("Bye! :)");
                break;
            }
        }while(true);



    }

    private static MessageResponse sendQuestion(String message, Conversation conv){
        InputData input = new InputData.Builder(message).build();

        MessageOptions options = new MessageOptions.Builder(workspaceId)
                .input(input)
                .build();

        return conv.message(options).execute();
    }



    private static void writeToFile(Object result, String path){
        byte[] toFileResult = result.toString().getBytes();
        Path p = Paths.get(path);
        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, APPEND))) {
            out.write(toFileResult, 0, toFileResult.length);
        } catch (IOException x) {
            System.err.println(x);
        }

    }

    private static Conversation getStarted(String username, String password, String date){
        try{
            Conversation result = new Conversation(date);
            result.setUsernameAndPassword(username,password);
            return result;
        }catch(Exception e){
            System.err.println("Not started!");
        }
        return null;
    }

}
