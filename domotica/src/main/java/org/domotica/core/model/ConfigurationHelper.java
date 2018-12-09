package org.domotica.core.model;

import java.io.*;

public class ConfigurationHelper {
    public final static String DEFAULT_CONFIG_FILE = ".domotica/config.json";

    public void save(Configuration configuration) throws IOException {
        File file = new File(fileName());
        if (! file.exists()){
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(configuration.toString());
        bufferedWriter.close();
    }

    public Configuration load() throws IOException {
        if (! configurationExists()){
            return new Configuration();
        }
        BufferedReader reader = new BufferedReader(new FileReader (fileName()));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return new Configuration().build(stringBuilder.toString());
    }

    private String fileName(){
        return System.getProperty("user.home")  + "/" + DEFAULT_CONFIG_FILE;
    }

    private boolean configurationExists(){
        File configurationFile = new File(fileName());
        return configurationFile.exists();
    }
}
