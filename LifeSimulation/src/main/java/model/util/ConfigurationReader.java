package model.util;

import java.io.*;

public class ConfigurationReader {

    public ConfigurationReader() {
    }

    public String[] readFromTXTFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            if ((line = reader.readLine()) != null) {
                return line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}