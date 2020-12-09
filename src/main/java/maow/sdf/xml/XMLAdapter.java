package maow.sdf.xml;

import maow.javasdf.adapter.LanguageAdapter;
import maow.javasdf.attribute.Attribute;
import maow.javasdf.attribute.holder.InnerHolder;
import maow.javasdf.attribute.holder.NestedHolder;
import maow.javasdf.attribute.types.InnerAttribute;
import maow.javasdf.attribute.types.NestedAttribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

public class XMLAdapter implements LanguageAdapter<Document> {
    @Override
    public Document convert(maow.javasdf.document.Document document) {
        final Document xml = DocumentHelper.createDocument();
        final Element root = xml.addElement(document.getName());

        document.getRootInnerAttributes().forEach(innerAttribute -> addAttribute(root, innerAttribute));
        document.getAttributes().forEach(attribute -> addElement(root, attribute));

        return xml;
    }

    private void addElement(Element parentElement, Attribute attribute) {
        if (attribute.getName().equals("")) return;
        final Element element = addElement(parentElement, attribute.getName());

        addInnerElements(element, attribute);
        addNestedElements(element, attribute);
    }

    private Element addElement(Element parentElement, String name) {
        return parentElement.addElement(fixElementName(name));
    }

    private void addElementText(Element element, Attribute attribute) {
        final String value = attribute.getValue();
        if (value.length() > 0) {
            element.setText(value);
        }
    }

    private void addInnerElements(Element element, Attribute attribute) {
        if (attribute instanceof InnerHolder) {
            final InnerHolder innerHolder = (InnerHolder) attribute;
            for (InnerAttribute innerAttribute : innerHolder.getInnerAttributes()) {
                addAttribute(element, innerAttribute);
            }
        }
    }

    private void addNestedElements(Element element, Attribute attribute) {
        if (attribute instanceof NestedHolder) {
            final NestedHolder nestedHolder = (NestedHolder) attribute;
            for (NestedAttribute nestedAttribute : nestedHolder.getNestedAttributes()) {
                addNestedElement(element, nestedAttribute);
            }
        } else {
            addElementText(element, attribute);
        }
    }

    private void addNestedElement(Element parentElement, NestedAttribute nestedAttribute) {
        final Element element = addElement(parentElement, nestedAttribute.getName());

        addInnerElements(element, nestedAttribute);

        final List<NestedAttribute> nestedAttributes = nestedAttribute.getNestedAttributes();
        if (nestedAttributes.size() > 0) {
            nestedAttributes.forEach(subNestedAttribute -> addNestedElement(element, subNestedAttribute));
        } else {
            addElementText(element, nestedAttribute);
        }
    }

    private String fixElementName(String name) {
        return name.replaceAll("[!;.]+", "");
    }

    private void addAttribute(Element element, InnerAttribute innerAttribute) {
        element.addAttribute(fixElementName(innerAttribute.getName()), innerAttribute.getValue());
    }
}
