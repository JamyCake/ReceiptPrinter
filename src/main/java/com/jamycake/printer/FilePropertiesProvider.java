package com.jamycake.printer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FilePropertiesProvider implements PropertiesProvider {
    private File file;

    public FilePropertiesProvider(String filePath) throws FileNotFoundException {
        this.file = new File(filePath);
        this.preCheck(this.file);
    }

    private void preCheck(File file) throws FileNotFoundException {
        if (file == null) {
            throw new NullPointerException();
        } else if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        } else if (file.length() == 0L) {
            throw new FileNotFoundException("File is empty: " + file.getAbsolutePath());
        }
    }

    public Properties loadConfig() throws IOException {
        InputStream inputStream = new FileInputStream(this.file);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
