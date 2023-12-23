package io.github.arungahlawat.automation.core.utils.io.impl.readers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.Reader;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static io.github.arungahlawat.automation.core.utils.io.FileUtils.transformFilePath;

public class XmlReader implements Reader<Document> {
    private static XmlReader instance;

    private XmlReader() {
    }

    public static XmlReader getInstance() {
        if (instance == null)
            instance = new XmlReader();
        return instance;
    }

    @Override
    public Document read(String filePath) {
        filePath = transformFilePath(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(inputStream);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            Log.warn("Could not read file at path {}. Exception:{}. Trying in resources ...", filePath, e.getMessage());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
                if (inputStream == null)
                    throw new IOException("Could not read resource as stream");
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                return documentBuilder.parse(inputStream);
            } catch (IOException | ParserConfigurationException | SAXException ex) {
                Log.error("Error reading file {} in resources. Error: {}", filePath, ex.getMessage());
            }
        }
        return null;
    }

    public String readString(String filePath) {
        Document document = read(filePath);
        DOMSource domSource = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult streamResult = new StreamResult(new StringWriter());
            transformer.transform(domSource, streamResult);
            return streamResult.getWriter().toString();
        } catch (TransformerException e) {
            Log.error("Exception in reading xml file {}", e.getMessage());
        }
        return null;
    }
}
