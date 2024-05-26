package edu.school21.processors;


import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.*;


import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;


@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set <? extends Element> elements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : elements) {
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            HtmlInput htmlInput;
            try (FileWriter fileWriter = new FileWriter("target/classes/" + htmlForm.fileName())) {
                fileWriter.write(String.format("<form action = \"%s\" method = \"%s\">\n"
                        , htmlForm.action(), htmlForm.method()));
                for (Element field : element.getEnclosedElements()) {
                    htmlInput = field.getAnnotation(HtmlInput.class);
                    if (htmlInput != null) {
                        fileWriter.write(String.format("\t<input type = \"%s\" name = \"%s\" placeholder = \"%s\">\n"
                                , htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
                    }
                }
                fileWriter.write(String.format("\t<input type = \"submit\" value = \"Send\">\n"));
                fileWriter.write(String.format("</form>\n"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }
}
