package actions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import net.minidev.json.JSONObject;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HelloWorldAction extends AnAction {
    String api_key = "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";
    // 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
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("Hello World!");
        //Messages.showInfoMessage("Hello World!", "Hello World");
        // get highleghted text from editor
        // Messages.showInfoMessage(e.getData(PlatformDataKeys.EDITOR).getSelectionModel().getSelectedText(), "Hello World");
        String prompt = e.getData(PlatformDataKeys.EDITOR).getSelectionModel().getSelectedText();

        if(api_key == "") {
            String api_key = Messages.showInputDialog("Please input your API Key", "API Key", null);
        }



        String url = "https://api.openai.com/v1/completions";
        String result = "";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            // add "Authorization: Bearer $OPENAI_API_KEY"
            con.setRequestProperty("Authorization", "Bearer " + api_key);
            String jsonInputString = "{ \"model\": \"code-davinci-002\", \"prompt\": \"" + prompt + "\", \"temperature\": 0, \"max_tokens\": 256, \"top_p\": 1, \"frequency_penalty\": 0, \"presence_penalty\": 0 }";
            con.setDoOutput(true);
            con.getOutputStream().write(jsonInputString.getBytes(StandardCharsets.UTF_8));
            // call the API
            result = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8)).readLine();
            // convert result to json
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            String generation = jsonObject.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
            Messages.showInfoMessage(generation, "CHOICE");
            // write the result to the editor, next line
            //e.getData(PlatformDataKeys.EDITOR).getDocument().insertString(e.getData(PlatformDataKeys.EDITOR).getCaretModel().getOffset(), generation);
            e.getData(PlatformDataKeys.EDITOR).getSelectionModel().removeSelection();
            //move cursor to a new line
            //e.getData(PlatformDataKeys.EDITOR).getCaretModel().moveToOffset(e.getData(PlatformDataKeys.EDITOR).getCaretModel().getOffset());
            // use WriteCommandAction.runWriteCommandAction and insert it into a new line
            WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
                e.getData(PlatformDataKeys.EDITOR).getDocument().insertString(e.getData(PlatformDataKeys.EDITOR).getCaretModel().getOffset(), generation);
                // this offset will move the cursor to the end of the line
            });
            // deselect and put cursor at the end

        } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }




}
