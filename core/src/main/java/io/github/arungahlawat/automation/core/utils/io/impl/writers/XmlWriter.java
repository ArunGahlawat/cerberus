package io.github.arungahlawat.automation.core.utils.io.impl.writers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Writer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class XmlWriter implements Writer<Document> {
    private static XmlWriter instance;

    private XmlWriter() {
    }

    public static XmlWriter getInstance() {
        if (instance == null)
            instance = new XmlWriter();
        return instance;
    }

    @Override
    public String write(Document object, String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource domSource = new DOMSource(object);
            File file = new File(filePath);
            org.apache.commons.io.FileUtils.createParentDirectories(file);
            String absolutePath = file.getAbsolutePath();
            transformer.transform(domSource, new StreamResult(file));
            return absolutePath;
        } catch (TransformerException | IOException e) {
            Log.error("Error creating xml document {}", e.getMessage());
        }
        return null;
    }

    public String write(Map<String, Object> data, String rootName, String childName, String childAttributeKeyName, String childAttributeValueName, String filePath) {
        Document document = getDocument();
        Element rootNode = document.createElement(rootName);
        document.appendChild(rootNode);
        for (String key : data.keySet()) {
            Element childRoot = document.createElement(childName);
            rootNode.appendChild(childRoot);

            Element keyNode = document.createElement(childAttributeKeyName);
            keyNode.appendChild(document.createTextNode(key));
            childRoot.appendChild(keyNode);

            Element valueNode = document.createElement(childAttributeValueName);
            valueNode.appendChild(document.createTextNode(String.valueOf(data.get(key))));
            childRoot.appendChild(valueNode);
        }
        return write(document, filePath);
    }

    public Document getDocument() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            Log.error("Error creating xml document {}", e.getMessage());
        }
        return null;
    }
}
