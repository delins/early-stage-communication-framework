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
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

class Connection implements Runnable {
    private Socket server;
    private String line, input;
    String socketName;
    
    Connection(Socket server) {
    	this.server = server;
    }

    public void run () {
    	input = "";

    	try {
	        // Get input from the client
	        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	        PrintStream out = new PrintStream(server.getOutputStream());
	        socketName = server.getRemoteSocketAddress().toString();
	      
	        XMLInputFactory factory = XMLInputFactory.newInstance();
	        Parser p = new Parser();
	        boolean parsingInner = false;
	        String currentBlock = "";
	        
	        try {
	        	XMLEventReader reader = factory.createXMLEventReader(in);
	        	while(reader.hasNext()){
		        	XMLEvent event = reader.nextEvent();
		        	
		        	// First handle startelements.
		        	// The parsingInner boolean tells us if we're ready to tackle a new block,
		        	// or if we're still in one.
		        	if (event.isStartElement()) {	
		        		if (!parsingInner) {
		        			// We got to the start of a new block
		        			
		        			StartElement start = event.asStartElement();
			        		String startName = start.getName().toString();
	    					if (startName.equals("message")) {
	    						System.out.println("\n\nStarting message parsing");
	    						p = new MessageParser();
	    						p.parse(event);
	    						parsingInner = true;
	    						currentBlock = "message";
	    					}
	    					else if (startName.toString().equals("register")) {
	    						System.out.println("\n\nStarting register parsing");
	    						p = new RegisterParser();    
	    						p.parse(event);
	    						parsingInner = true;
	    						currentBlock = "register";
	    					}		 
    					} else {
    						// We are parsing the inner portion of a block
    						p.parse(event);
    					}
		        	} else if (event.isEndElement()) {	
		        		EndElement end = event.asEndElement();
		        		String endName = end.getName().toString();
		        		if (endName.equals(currentBlock)) {
		        			parsingInner = false;
		        			currentBlock = "";
			        		//System.out.println("I was a: " + p.whatAmI());
		        		} else {
    						p.parse(event);
		        		}	        		
		        	} else if (event.isEndDocument()) {
		        		System.out.println("\nEnd of stream reached");
		    	        server.close();
		        	} else if (parsingInner) {
		        			p.parse(event);
		        		
		        	} else {
		        		System.out.println("\tDidn't parse " + event.toString());
		        	}
		        		   
	        	}
	          
	      } catch (XMLStreamException e) {
	          e.printStackTrace();
	      }
	        
	        if (Config.debug) {
	        	System.out.println(String.format("Socket to %s closed", socketName));
	        }
    	} catch (IOException ioe) {
	        System.out.println("IOException on socket listen: " + ioe);
	        ioe.printStackTrace();
    	} finally {
    		if (server.isConnected()) {
    			try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
  
    // not in use currently
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
