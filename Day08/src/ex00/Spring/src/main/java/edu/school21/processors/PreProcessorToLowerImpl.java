package edu.school21.processors;

public class PreProcessorToLowerImpl implements PreProcessor{
    @Override
    public String process(String string) {
        return string.toLowerCase();
    }
}
