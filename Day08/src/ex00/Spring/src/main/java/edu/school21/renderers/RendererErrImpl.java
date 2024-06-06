package edu.school21.renderers;

import edu.school21.processors.PreProcessor;

public class RendererErrImpl implements Renderer{
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void rendererMessage(String string) {
        System.err.println(preProcessor.process(string));
    }
}
