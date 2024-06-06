package edu.school21.printers;

import edu.school21.renderers.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;
    private final LocalDateTime localDateTime;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
        this.localDateTime = LocalDateTime.now();
    }

    @Override
    public void print(String string) {
        renderer.rendererMessage(localDateTime + " - " + string);
    }
}
