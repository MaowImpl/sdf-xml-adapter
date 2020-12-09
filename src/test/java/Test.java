import maow.javasdf.document.Document;
import maow.javasdf.io.SDFReader;
import maow.sdf.xml.util.XMLConvertedWriter;

import java.io.FileReader;
import java.io.FileWriter;

public class Test {
    public static void main(String[] args) throws Exception {
        Document document = new SDFReader(new FileReader("SDF.sdf")).readDocument();
        XMLConvertedWriter cw = new XMLConvertedWriter(document, new FileWriter("XML.xml"));
        cw.write().flush().close();
    }
}
