package server;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class Message {
	String input = "";
	String senderString = "";
	String receiverString = "";
	String data = "";
	
	public Message(String input) {
		this.input = input;
	}
	
	public void parse() {
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setValidating(true);
		    factory.setIgnoringElementContentWhitespace(true);
		    try {
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document doc = builder.parse(input);
		        // Do something with the document here.
		        System.out.println(doc.getAttributes());
		    } catch (ParserConfigurationException e) {
		    } catch (SAXException e) {
		    } catch (IOException e) { 
		    }
	}
	//http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
}
