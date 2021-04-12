package chatapp;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class ServerApp {

	private int port;
	private List<PrintStream> clients;
	private ServerSocket server;

	public static void main(String[] args) throws IOException {
		new ServerApp(8080).run();
	}

	public ServerApp(int port) {
		this.port = port;
		this.clients = new ArrayList<PrintStream>();
	}

	public void run() throws IOException {
		server = new ServerSocket(port) {
			protected void finalize() throws IOException {
				this.close();
			}
		};

		while (true) {
			Socket client = server.accept();			
			this.clients.add(new PrintStream(client.getOutputStream()));
			new Thread(new SendMessageComponent(this, client.getInputStream())).start();
		}
	}

	void broadcastMessages(String msg) {
		for (PrintStream client : this.clients) {
			client.println(msg);
		}
	}
}

class SendMessageComponent implements Runnable {

	private Server server;
	private InputStream client;

	public SendMessageComponent(Server server, InputStream client) {
		this.server = server;
		this.client = client;
	}

	@Override
	public void run() {
		String message;
		
		Scanner sc = new Scanner(this.client);
		while (sc.hasNextLine()) {
			message = sc.nextLine();
			server.broadcastMessages(message);
		}

		sc.close();
	}
}
