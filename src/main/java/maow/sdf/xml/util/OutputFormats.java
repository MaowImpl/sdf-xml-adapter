package maow.sdf.xml.util;

import org.dom4j.io.OutputFormat;

public class OutputFormats {
    public static final OutputFormat STANDARD;

    static {
        STANDARD = new OutputFormat();
        STANDARD.setPadText(true);
        STANDARD.setTrimText(false);
        STANDARD.setIndent(true);
        STANDARD.setNewlines(true);
    }
}
