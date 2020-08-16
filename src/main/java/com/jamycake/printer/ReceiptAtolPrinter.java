package com.jamycake.printer;

import java.util.Properties;
import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;

public class ReceiptAtolPrinter implements Printer {
    private static final IFptr driver = new Fptr();
    private static final String JSON_DATA_TEMPLATE = "{\"type\": \"nonFiscal\",\"items\": [{\"type\": \"text\",\"text\": \"%s\",\"alignment\": \"left\"}]}";
    private final Properties config;
    private final String text;

    public ReceiptAtolPrinter(Properties config, String text) {
        this.config = config;
        this.text = text;
    }

    public void print() {
        this.loadDriverConfig();
        String printerJsonData = String.format(JSON_DATA_TEMPLATE, text);
        driver.setParam(IFptr.LIBFPTR_PARAM_JSON_DATA, printerJsonData);
        this.executeDriverLifecycle();
    }

    private void executeDriverLifecycle() {
        driver.open();
        driver.processJson();
        driver.close();
        driver.destroy();
    }

    private void loadDriverConfig() {
        driver.setSingleSetting("Port", this.config.getProperty("LIBFPTR_SETTING_PORT"));
        driver.setSingleSetting("Model", this.config.getProperty("LIBFPTR_SETTING_MODEL"));
        driver.setSingleSetting("IPPort", this.config.getProperty("LIBFPTR_SETTING_IPPORT"));
        driver.setSingleSetting("BaudRate", this.config.getProperty("LIBFPTR_SETTING_BAUDRATE"));
        driver.setSingleSetting("ComFile", this.config.getProperty("LIBFPTR_SETTING_COM_FILE"));
        driver.setSingleSetting("IPAddress", this.config.getProperty("LIBFPTR_SETTING_IPADDRESS"));
        driver.setSingleSetting("UsbDevicePath", this.config.getProperty("LIBFPTR_SETTING_USB_DEVICE_PATH"));
        driver.applySingleSettings();
    }
}
