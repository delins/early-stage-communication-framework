package server;

import javax.xml.stream.events.XMLEvent;

public class Parser {
	// STAX way to go:
	//http://docs.oracle.com/javase/tutorial/jaxp/stax/why.html
	//http://stackoverflow.com/questions/2653918/xmlstreamreader-and-a-real-stream
	public void parse(XMLEvent event) {
		if (event.isStartElement()) {
			System.out.println("Start element: " + event.toString());
		}
		else if (event.isCharacters()) {
			System.out.println("Characters: " + event.toString());
		}
		else if (event.isEndElement()) {
			System.out.println("End element: " + event.toString());
		}
	}
	
	public String whatAmI() {
		return this.getClass().toString();
	}
}
