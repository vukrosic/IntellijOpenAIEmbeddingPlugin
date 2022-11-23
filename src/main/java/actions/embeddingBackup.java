package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class embeddingBackup extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

/*
    String api_key = "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";
    ArrayList<File> files = new ArrayList<>();




    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("Hello World");
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        String path = project.getBasePath();
        assert path != null;
        File folder = new File(path);
        listFilesForFolder(folder);
        System.out.println(files.toArray().length);

        FileWriter writer = null;
        try {
            writer = new FileWriter(path + "/files.csv");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        ArrayList<String> methods1 = getMethods(files.get(0).getPath());
        System.out.println(methods1.toString());

    }



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
    }



    ArrayList<String> getMethods(String path){
        System.out.println("Getting methods");
        //String path = "D:\\Minecraft Mods\\Plugins\\untitled\\src\\newclass.java";
        String document = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                document += line + "\n";
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] lines = document.split("\n");
        int openBracketCount = 0;
        String method = "";
        ArrayList<String> methods = new ArrayList<>();
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].contains("{")) {
                openBracketCount++;
            }
            if(lines[i].contains("}")) {
                openBracketCount--;
            }
            if(openBracketCount == 1 && lines[i].equals("")) {
                method += "}";
                //System.out.println(method );
                methods.add(method);
                method = "";
                continue;
            }
            if(openBracketCount != 1) {
                String lineWithout4SpacesAtTheBeginning = lines[i].replaceFirst("^\\s{4}", "");
                method += lineWithout4SpacesAtTheBeginning + "\n";
            }
        }
        for(String m : methods) {
            System.out.println(m);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        return methods;
    }



    static void writeCSV(ArrayList<String> methods) throws IOException {
        System.out.println("Writing CSV = " + methods.get(0));
        FileWriter writer = null;
        try {
            writer = new FileWriter("D:\\Minecraft Mods\\Plugins\\template\\intellij-platform-plugin-template-main\\src\\main\\java\\actions\\file.csv");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            writer.append("Method Name");
            writer.append('\n');
            String entry = methods.get(0);
            // replace new lines with \n
            entry = entry.replace("\n", "\\n");
            writer.append(entry);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }*/

}
