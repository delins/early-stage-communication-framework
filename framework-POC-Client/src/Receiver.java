
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Receiver extends BufferedReader{	
	public Receiver(Reader in) {
		super(in);
	}

	/**
	 * Starts the runnable's loop and makes it wait and listen for incoming
	 * messages. Received messages are sent to the ReceiverParser.
	 * Also prints the message to the console if configured to do so.
	 */
	public void listen() {
		String serverResponse = "";

		try {
			while ((serverResponse = readLine()) != null) {		
						
			}
		} catch (IOException e) {
			System.err.format("Receiver threw a IOException");
			System.exit(1);
		}
	}
}
