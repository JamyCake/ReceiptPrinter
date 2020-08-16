package com.jamycake.printer;

import java.util.Properties;

public class PrinterConsoleRunner {

    private static final String PRINTER_PROPERTIES_FILE = "printer.properties";

    public static final String HELP_RESPONSE = "Should transfer to command line arguments the path to receipt file to print";
    public static final String SUCCESS_RESPONSE = "Printing success! If not may be port is busy.";
    private String response;

    PrinterConsoleRunner() {
    }

    public static void main(String... args) throws Exception {
        PrinterConsoleRunner runner = new PrinterConsoleRunner();
        runner.run(args);
        System.out.println(runner.response);
    }

    public void run(String... args) throws Exception {
        if (args.length > 0) {
            Printer printer = createPrinter(args[0]);
            printer.print();
            this.response = SUCCESS_RESPONSE;
        } else {
            this.response = HELP_RESPONSE;
        }

    }

    public Printer createPrinter(String receipt) throws Exception {
        Properties printerProperties = getPrinterProperties();
        String receiptContent = getReceiptContent(receipt);
        return new ReceiptAtolPrinter(printerProperties, receiptContent);
    }

    private Properties getPrinterProperties() throws Exception {
        PropertiesProvider provider = new FilePropertiesProvider(PRINTER_PROPERTIES_FILE);
        return provider.loadConfig();
    }

    private String getReceiptContent(String path) throws Exception {
        ReceiptContentProvider contentProvider = new ReceiptContentProvider(path);
        return contentProvider.getReceiptContents();
    }
}
