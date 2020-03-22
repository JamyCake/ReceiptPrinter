package com.jamycake.printer;

import com.jamycake.propertiesProvider.FilePropertiesProvider;
import com.jamycake.propertiesProvider.PropertiesProvider;

import java.util.Properties;

public class PrinterConsoleRunner {

    public static final String HELP_RESPONSE = "Should transfer to command line arguments " +
            "the path to receipt file to print and charset if it need";
    public static final String SUCCESS_RESPONSE = "Printing success! If not may be port is busy.";
    public static final String WAS_NOT_RUN_PRINTER_RESPONSE = "The printer was not run";

    PrinterConsoleRunner(){
        response = WAS_NOT_RUN_PRINTER_RESPONSE;
    }

    private String response;
    private String userCharset;

    public static void main(String ... args) throws Exception{
        PrinterConsoleRunner runner = new PrinterConsoleRunner();
        runner.run(args);

        System.out.println(runner.response);
    }

    public void run(String ... args) throws Exception{
        if (args.length == 1){
            print(args[0]);
        } else if (args.length > 1){
            printWithUserCharset(args[0], args[1]);
        } else {
            response = HELP_RESPONSE;
        }
    }

    private void print(String receiptPath) throws Exception {
        Printer printer = createPrinter(receiptPath);
        printer.print();

        response = SUCCESS_RESPONSE;
    }

    private void printWithUserCharset(String receiptPath, String charset) throws Exception{
        this.userCharset = charset;
        print(receiptPath);
    }

    public Printer createPrinter(String receipt) throws Exception{
        Properties printerProperties = getPrinterProperties();
        String receiptContent = getReceiptContent(receipt);

        if (receiptContent == null) throw new NullPointerException();

        return new ReceiptAtolPrinter(printerProperties, receiptContent);
    }

    private Properties getPrinterProperties() throws Exception {
        String PATH_TO_PRINTER_PROPERTIES_FILE = "printer.properties";
        PropertiesProvider provider = new FilePropertiesProvider(PATH_TO_PRINTER_PROPERTIES_FILE);
        return provider.loadConfig();
    }

    private String getReceiptContent(String  path) throws Exception {
        ReceiptContentProvider contentProvider = new ReceiptContentProvider(path);

        if (userCharset != null){
            contentProvider.setCharset(userCharset);
        }

        return contentProvider.getReceiptContent();
    }
}
