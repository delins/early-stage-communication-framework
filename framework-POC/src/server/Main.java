package server;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hi");
		ConnectionListener listener = new ConnectionListener();
		listener.startListening();
	}

}
