import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

import commands.VerifyPort;
import thread.ServerThread;

public class myCloudServer {

	/**
	 * Check if port number is valid
	 * @String[] list of arguments to check 
	 * @return Port in integer
	 */
	private static int verifyPort(String[] args) {
		
		String port = "";
		if(args.length == 1){
			port = new VerifyPort(args[0]).verifyPort();
		} else {
			System.err.println("You must provide a port.");
	    	System.exit(-1);
		}
		return Integer.parseInt(port);
	}
	
	/**
	 * Initialize server
	 * @String[] list of arguments to check 
	 */
	public static void main(String[] args) throws IOException {
		int port = verifyPort(args);
		myCloudServer server = new myCloudServer();
		server.startServer(port);
	}
	
	/**
	 * Method to connect the server
	 * @integer port number
	 */
	private void startServer(int port) throws IOException {
		
		//---------------Substituir------------------
		//ServerSocket sSoc = null;
		//sSoc = new ServerSocket(port);
		
		//------------------TLS----------------------
		ServerSocket sSoc;

		System.setProperty("javax.net.ssl.keyStore", "keystore.server");
		System.setProperty("javax.net.ssl.keyStorePassword", "si027marcos&rafael&daniela");
		ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
		sSoc = ssf.createServerSocket(port);

		//-------------------------------------------
		
		System.out.println("Server connected");
		
		while(true) {
			try {
				Socket inSoc = sSoc.accept();
				ServerThread newServerThread = new ServerThread(inSoc);
				newServerThread.start();
		    }
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		//sSoc.close();
	}
}
