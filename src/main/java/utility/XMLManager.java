package utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.restassured.path.xml.XmlPath;

public class XMLManager {

	/*
	 * This method, getXmlDocument, takes a string path to an XML file and returns a
	 * Document object representing the parsed XML data
	 */
	public static Document getXmlDocument(String pathToXml)
			throws SAXException, IOException, ParserConfigurationException {

		// Load the XML file as an InputStream using the class loader
		InputStream xmlfile = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToXml);

		// Create a new instance of DocumentBuilderFactory for building a Document
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		// Set the factory to be namespace-aware to handle XML namespaces
		dbFactory.setNamespaceAware(true);

		// Create a new DocumentBuilder using the configured factory
		DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
		// Parse the XML file and return a new DOM Document object
		return documentBuilder.parse(xmlfile);
	}

	/*
	 * The method docToStr takes a Document object as input and converts it into a
	 * String representation of the XML content
	 */
	public static String docToStr(Document doc) throws TransformerException {
		// Initialize a TransformerFactory to create Transformer instances
		TransformerFactory tf = TransformerFactory.newInstance();
		// Create a Transformer object to perform the XML to String transformation
		Transformer transformer = tf.newTransformer();

		// Create a source that holds the XML Document in DOM form
		DOMSource domSource = new DOMSource(doc);

		// Initialize a StringWriter to capture the output of the transformation
		StringWriter writer = new StringWriter();

		// Prepare the result destination linked to the StringWriter
		StreamResult result = new StreamResult(writer);

		// Perform the transformation from DOMSource to StreamResult
		transformer.transform(domSource, result);

		// Return the string representation of the XML document
		return writer.toString();
	}

	/*
	 * This method is designed to retrieve data from an XML structure (whether from
	 * a request, response, or any XML content) based on a given XPath expression.
	 */
	public static String getXmlElementsAsString(String xmlString, String xmlPath) {
		// Parse the XML string using XmlPath to easily navigate the XML structure
		XmlPath xmlpath = new XmlPath(xmlString);

		// Extract the text content of the XML elements specified by the XPath
		// expression and unescape XML entities
		return StringEscapeUtils.unescapeXml(xmlpath.getString(xmlPath));
	}

	/**
	 * Converts a string representation of XML into a Document object.
	 * 
	 * @param xmlStr The XML string to be parsed into a Document.
	 * @return A Document representing the DOM structure of the XML, or null if
	 *         parsing fails.
	 */
	public static Document strToDoc(String xmlStr) {
		/*
		 * Initialize the DocumentBuilderFactory to get a parser that produces DOM
		 * object trees
		 */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Try creating a DocumentBuilder to parse the XML string
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XML string using a StringReader to read the string into an
			// InputSource.
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));

			// Return the parsed Document
			return doc;
		} catch (Exception e) {
			// Print exception stack trace to help diagnose issues in the parsing process.
			e.printStackTrace();
		}

		// Return null in case of exceptions, indicate that the parsing was
		// unsuccessful.
		return null;
	}

}