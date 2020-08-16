package com.jamycake.printer;

import java.util.Properties;

public interface PropertiesProvider {
    Properties loadConfig() throws Exception;
}
