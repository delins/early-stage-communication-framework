import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 1234);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
			
			String fromServer;
			
			XMLOutputFactory factory = XMLOutputFactory.newInstance();

			try {
				XMLStreamWriter writer =
				   factory.createXMLStreamWriter(socket.getOutputStream());
	
				writer.writeStartDocument();
				writer.writeStartElement("stream");
				
				writer.writeStartElement("message");
					writer.writeStartElement("from");
						writer.writeCharacters("Phineas");
					writer.writeEndElement();
					writer.writeStartElement("to");
						writer.writeCharacters("Ferb");
					writer.writeEndElement();
					writer.writeStartElement("data");
						writer.writeCharacters("What are we gonna do today?");
					writer.writeEndElement();
				writer.writeEndElement();
				writer.flush();
				
				writer.writeStartElement("register");
					writer.writeStartElement("userid");
						writer.writeCharacters("Ferb");
					writer.writeEndElement();
					writer.writeStartElement("password");
						writer.writeCharacters("isabella");
					writer.writeEndElement();
				writer.writeEndElement();
				writer.flush();

				writer.writeEndDocument();
				writer.flush();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//http://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
	
}
