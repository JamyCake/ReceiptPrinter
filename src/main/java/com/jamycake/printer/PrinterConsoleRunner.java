package com.jamycake.printer;

import java.util.Properties;

public class PrinterConsoleRunner {

    private static final String PRINTER_PROPERTIES_FILE = "printer.properties";

    public static final String HELP_RESPONSE =
            "Use [path to receipt file] [encode] as argument\n" +
                    "The standard encode is KOI8-R";
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
        if (args.length == 1) {
            runPrinter(args[0]);
        } else if (args.length > 1){
            ReceiptContentProvider.CHARSET_NAME = args[1];
            runPrinter(args[0]);
        }
        else {
            response = HELP_RESPONSE;
        }

    }

    private void runPrinter(String arg) throws Exception {
        Printer printer = createPrinter(arg);
        printer.print();
        response = SUCCESS_RESPONSE;
    }

    public Printer createPrinter(String receiptPath) throws Exception {
        String receiptContent = getReceiptContent(receiptPath);
        Properties printerProperties = getPrinterProperties();
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
