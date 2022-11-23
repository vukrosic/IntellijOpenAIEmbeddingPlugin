package actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import net.minidev.json.JSONObject;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EmbeddingsAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        /*
        String pathToClass = "D:\\Minecraft Mods\\Plugins\\untitled\\src\\newclass.java";
        // separate class into lines
        String[] classLinesArray = getClassLines(pathToClass);
        // combine lines into methods
        ArrayList<String> methodCodes = combineLinesIntoMethods(classLinesArray);
        System.out.println(methodCodes.size());
        // get method names
        ArrayList<String> methodNames = getMethodNames(methodCodes);
        // write method names and method code to csv file*/
        /*String pathToCSVFile = "D:\\Minecraft Mods\\Plugins\\untitled\\src\\file.csv";
        //writeCSV(methodNames, methodCodes, pathToClass, pathToCSVFile);
        String query = "get method named second method";
        String queryEmbeddingString = callOpenAIAPI(query, "code-search-babbage-text-001");
        // print
        System.out.println(queryEmbeddingString);*/

        /*
        double[][] vectorCode = new double[3][3];
        vectorCode[0] = new double[]{-0.0147361215, -0.02963059, -0.0138553195};
        vectorCode[1] = new double[]{-0.01221718, -0.019939644, -0.016098524};
        vectorCode[2] = new double[]{-0.023861252, -0.023841335, -0.023602324};
        double[] vectorQuery = new double[]{-0.0147361215, -0.02963059, -0.0138553195};
        double simiilarity;
        double bestSimilarity = 0;
        int bestSimilarityIndex = 0;
        for(int i = 0; i < vectorCode.length; i++){
            simiilarity = cosineSimilarity(vectorCode[i], vectorQuery);
            if(simiilarity > bestSimilarity){
                bestSimilarity = simiilarity;
                bestSimilarityIndex = i;
            }
        }
        for(int i = 0; i < vectorCode[bestSimilarityIndex].length; i++){
            System.out.println(vectorCode[bestSimilarityIndex][i]);
        }*/
    }



    private static String getBestMethodsForQuery(String query, String pathToCSVFile) {
        int lineCount = 0;
        // read csv file
        ArrayList<String> csvLines = readCSV(pathToCSVFile);
        //get every line in csvLines except first
        // print csvLines
        System.out.println(csvLines);
        return "";
    }

    private static ArrayList<String> readCSV(String pathToCSVFile) {
        ArrayList<String> csvLines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathToCSVFile));
            String line;
            while ((line = br.readLine()) != null) {
                csvLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvLines;
    }

    static String[] getClassLines(String path){
        String document = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                document += line + "\n";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return document.split("\n");
    }


    static ArrayList<String> combineLinesIntoMethods(String[] classLinesArray){
        ArrayList<String> methods = new ArrayList<>();
        int openBracketCount = 0;
        String method = "";
        for(int i = 0; i < classLinesArray.length; i++) {
            if(classLinesArray[i].contains("{")) {
                openBracketCount++;
            }
            if(classLinesArray[i].contains("}")) {
                openBracketCount--;
            }
            if(openBracketCount == 1 && !classLinesArray[i].equals("")) {
                // if line is not empty
                method += "}";
                methods.add(method);
                method = "";
                continue;
            }
            if(openBracketCount != 1) {
                String lineWithout4SpacesAtTheBeginning = classLinesArray[i].replaceFirst("^\\s{4}", "");
                method += lineWithout4SpacesAtTheBeginning + "\n";
            }
        }
        // remove first method because it is empty
        methods.remove(0);
        return methods;
    }

    private static ArrayList<String> getMethodNames(ArrayList<String> methods) {
        ArrayList<String> methodNames = new ArrayList<>();
        for(String method : methods) {
            String[] methodLines = method.split("\n");
            String methodName = methodLines[0].split("\\(")[0].toString();
            // copy value of mathodName and not reference
            String nameValueCopy = new String(methodName);
            methodNames.add(nameValueCopy.split(" ")[nameValueCopy.split(" ").length - 1]);
        }
        return methodNames;
    }
/*
    public void listFilesForFolder(File folder) {
        //File[] classFiles;
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                //System.out.println(fileEntry.getName().endsWith(".class"));
                if (fileEntry.getName().endsWith(".class")) {
                    //classFiles = folder.listFiles();
                    files.add(fileEntry);
                }
            }
        }
    }*/

    static void writeCSV(ArrayList<String> methodNames, ArrayList<String> methodCodes, String pathToClass, String pathToCSVFile) throws IOException {
        FileWriter writer = null;
        // create new FileWriter
        try {
            writer = new FileWriter(pathToCSVFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            writer.append("code");
            writer.append(",");
            writer.append("function_name");
            writer.append(",");
            writer.append("filepath");
            writer.append(",");
            writer.append("code_embedding");
            writer.append('\n');
            for (int i = 0; i < methodNames.size(); i++) {
                // replace \n with \\n so that it is not interpreted as a new line
                // writer.append("\"" + methodCodes.get(i).replace("\n", "\\n"));
                // separate method name and method code with comma
                //writer.append(",");
                //writer.append(methodNames.get(i));
                //writer.append(",");
                writer.append("\"" + pathToClass+ "\"");
                writer.append(",");
                // get embedding
                String embedding = getCodeEmbedding(methodCodes.get(i));
                // double every " and put "" around embedding so commas don't mess up csv
                writer.append("\"" + embedding.replace("\"", "\"\"") + "\"");
                writer.append('\n');
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getCodeEmbedding(String methodCode) throws IOException {
        return callOpenAIAPI(methodCode, "code-search-babbage-code-001");
    }

    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
/*
    private static String getQueryEmbedding(String query) {
        String api_key = "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";
        String url = "https://api.openai.com/v1/embeddings";
        callOpenAIAPI("https://api.openai.com/v1/engines/davinci/completions", "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", query);
    }*/

    static String callOpenAIAPI(String methodCode, String model) throws IOException {
        String url = "https://api.openai.com/v1/embeddings";
        String api_key = "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";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + api_key);
            System.out.println(methodCode);
            String data = methodCode.replace("\n", "\\n");
            // replace " with \" so that it is not interpreted as a string
            data = data.replace("\"", "\\\"");
            String jsonInputString = "{ \"input\": \"" + data + "\", \"model\": \"" + model + "\"}";
            con.setDoOutput(true);
            con.getOutputStream().write(jsonInputString.getBytes(StandardCharsets.UTF_8));
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}


