package maow.sdf.xml.util;

import maow.javasdf.document.Document;
import maow.javasdf.io.conversion.AbstractConvertedWriter;
import maow.sdf.xml.XMLAdapter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.Writer;

public class XMLConvertedWriter extends AbstractConvertedWriter {
    private final XMLWriter writer;

    public XMLConvertedWriter(Document document, Writer out, OutputFormat format) {
        super(document, out);
        this.writer = new XMLWriter(out, format);
    }

    public XMLConvertedWriter(Document document, Writer out) {
        this(document, out, OutputFormats.STANDARD);
    }

    public XMLConvertedWriter write() throws IOException {
        write(document);
        return this;
    }

    @Override
    public void write(Document document) throws IOException {
        XMLAdapter adapter = new XMLAdapter();
        org.dom4j.Document xml = adapter.convert(document);
        writer.write(xml);
    }

    public XMLConvertedWriter flush() throws IOException {
        writer.flush();
        return this;
    }

    public void close() throws IOException {
        writer.close();
    }
}
