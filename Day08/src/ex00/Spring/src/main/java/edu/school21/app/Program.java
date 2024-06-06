package edu.school21.app;

import edu.school21.printers.Printer;
import edu.school21.printers.PrinterWithPrefixImpl;
import edu.school21.processors.PreProcessor;
import edu.school21.processors.PreProcessorToUpperImpl;
import edu.school21.renderers.Renderer;
import edu.school21.renderers.RendererErrImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        System.out.println("An example of code using classes in a standard way:");
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix ");
        printer.print("Hello!");

        System.out.println("Using these components with Spring:");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Printer printerErr = context.getBean("printerWithPrefix1", Printer.class);
        printerErr.print("Hello!");

        ///////////////////////////////////////////////////////////////////
        System.out.println("More examples:");
        Printer printerStd = context.getBean("printerWithPrefix2", Printer.class);
        printerStd.print("Hello!");

        Printer datePrinter = context.getBean("printerWithDateTime", Printer.class);
        datePrinter.print("Hello!");
    }
}
