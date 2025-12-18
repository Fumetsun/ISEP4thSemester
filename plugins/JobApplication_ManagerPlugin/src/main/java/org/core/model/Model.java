package org.core.model;

import java.io.IOException;

public interface Model {


    public void generateTemplate(String outDir);

    public void generateTemplate();

    public int assessAnswers() throws IOException;
}
