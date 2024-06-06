package edu.school21.renderers;

import edu.school21.processors.PreProcessor;

public class RendererStandardImpl implements Renderer{
    private final PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void rendererMessage(String string) {
        System.out.println(preProcessor.process(string));
    }
}
