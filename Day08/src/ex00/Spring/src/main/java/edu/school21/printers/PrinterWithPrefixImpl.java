package edu.school21.printers;

import edu.school21.renderers.Renderer;

public class PrinterWithPrefixImpl implements Printer{
    private String prefix;
    private final Renderer renderer;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String string) {
        renderer.rendererMessage(prefix + string);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
