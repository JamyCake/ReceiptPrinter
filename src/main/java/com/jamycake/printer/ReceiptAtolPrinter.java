package com.jamycake.printer;

import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;

import java.util.Properties;

public class ReceiptAtolPrinter implements Printer {

    private static final IFptr driver = new Fptr();
    private static final String JSON_DATA_TEMPLATE = "{\"type\": \"nonFiscal\"," +
            "\"items\": [{" +
            "\"type\": \"text\"," +
            "\"text\": \"%s\"," +
            "\"alignment\": \"left\"" +
            "}]" +
            "}";

    private Properties config;
    private String text;

    public ReceiptAtolPrinter(final Properties config, final String text) {
        this.config = config;
        this.text = text;
    }

    @Override
    public void print() {
        loadDriverConfig();
        String printerJsonData = String.format(JSON_DATA_TEMPLATE, text);
        driver.setParam(IFptr.LIBFPTR_PARAM_JSON_DATA, printerJsonData);

        executeDriverLifecycle();
    }

    private void executeDriverLifecycle() {
        driver.open();
        driver.processJson();
        driver.close();
        driver.destroy();
    }

    private void loadDriverConfig() {
        driver.setSingleSetting(IFptr.LIBFPTR_SETTING_PORT, config.getProperty("LIBFPTR_SETTING_PORT"));
        driver.setSingleSetting(IFptr.LIBFPTR_SETTING_MODEL, config.getProperty("LIBFPTR_SETTING_MODEL"));
        driver.setSingleSetting(IFptr.LIBFPTR_SETTING_IPPORT, config.getProperty("LIBFPTR_SETTING_IPPORT"));
        driver.setSingleSetting(IFptr.LIBFPTR_SETTING_BAUDRATE, config.getProperty("LIBFPTR_SETTING_BAUDRATE"));
        driver.setSingleSetting(IFptr.LIBFPTR_SETTING_COM_FILE, config.getProperty("LIBFPTR_SETTING_COM_FILE"));
        driver.setSingleSetting(IFptr.LIBFPTR_SETTING_IPADDRESS, config.getProperty("LIBFPTR_SETTING_IPADDRESS"));
        driver.setSingleSetting(IFptr.LIBFPTR_SETTING_USB_DEVICE_PATH, config.getProperty("LIBFPTR_SETTING_USB_DEVICE_PATH"));
        driver.applySingleSettings();
    }
}