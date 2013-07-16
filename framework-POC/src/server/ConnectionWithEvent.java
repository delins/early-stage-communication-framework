package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

class ConnectionWithEvent implements Runnable {
    private Socket server;
    private String line, input;
    String socketName;
    
    ConnectionWithEvent(Socket server) {
    	this.server = server;
    }

    public void run () {
    	input = "";

    	try {
	        // Get input from the client
	        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	        PrintStream out = new PrintStream(server.getOutputStream());
	        socketName = server.getRemoteSocketAddress().toString();
	        
	        /*
	        while((line = in.readLine()) != null && !line.equals(".")) {
	          input = input + line;
	          if (Config.debug) {
		          System.out.println("Server received \"" + line + "\" from " + socketName );
	          }
	        }
	        */
	        XMLInputFactory factory = XMLInputFactory.newInstance();

	      //get Reader connected to XML input from somewhere..
	      //Reader reader = getXmlReader();

	      try {

	          XMLEventReader eventReader =
	              factory.createXMLEventReader(in);
	          
	          while(eventReader.hasNext()){

	        	    XMLEvent event = eventReader.nextEvent();
	        	    /*
	        	    if(event.getEventType() == XMLStreamConstants.START_ELEMENT){
	        	        StartElement startElement = event.asStartElement();
	        	        System.out.println(startElement.getName().getLocalPart());
	        	    }
	        	    
	        	    //handle more event types here...
	        	    System.out.println("\ngetEventType...");
	        	    System.out.println(event.getEventType());
	        	    System.out.println("\ngetLocation...");
	        	    System.out.println(event.getLocation());
	        	    System.out.println("\ngetClass...");
	        	    System.out.println(event.getClass());
	        	    System.out.println("\nCDATA...");
	        	    System.out.println(event.CDATA);
	        	    */
	        	    if (event.getEventType() == 1) {
	        	    	System.err.println("start element: " + event.toString());
	        	    }
	        	    if (event.getEventType() == 4) {
	        	    	System.err.println("characters: " + event.asCharacters());
	        	    }
	        	    if (event.getEventType() == 2) {
	        	    	System.err.println("end element: " + event.toString());
	        	    }
	        	    if (event.getEventType() == 7) {
	        	    	System.err.println("start document: " + event.toString());
	        	    }
	        	    
	        	    
	        	    System.out.println(event.getEventType() + " " + getEventTypeString(event.getEventType()));
	        	    
	        	}
	          
	      } catch (XMLStreamException e) {
	          e.printStackTrace();
	      }
	
	        // Now write to the client
	        if (Config.debug) {
		        System.out.println(String.format("Overall message from %s is \"%s\"", socketName, input));
		         
	        }	
	        server.close();
	        
	        if (Config.debug) {
	        	System.out.println(String.format("Socket to %s closed", socketName));
	        }
    	} catch (IOException ioe) {
	        System.out.println("IOException on socket listen: " + ioe);
	        ioe.printStackTrace();
    	}
    }
    public final static String getEventTypeString(int eventType) {
        switch (eventType) {
            case XMLEvent.START_ELEMENT:
                return "START_ELEMENT";

            case XMLEvent.END_ELEMENT:
                return "END_ELEMENT";

            case XMLEvent.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";

            case XMLEvent.CHARACTERS:
                return "CHARACTERS";

            case XMLEvent.COMMENT:
                return "COMMENT";

            case XMLEvent.START_DOCUMENT:
                return "START_DOCUMENT";

            case XMLEvent.END_DOCUMENT:
                return "END_DOCUMENT";

            case XMLEvent.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";

            case XMLEvent.ATTRIBUTE:
                return "ATTRIBUTE";

            case XMLEvent.DTD:
                return "DTD";

            case XMLEvent.CDATA:
                return "CDATA";

            case XMLEvent.SPACE:
                return "SPACE";
        }
        return "UNKNOWN_EVENT_TYPE , " + eventType;
    }
    
}
