package chatapp;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.io.IOException;

public class ClientApp {

	private String host;
	private int port;
	private String username;

	public static void main(String[] args) throws UnknownHostException, IOException {
		new ClientApp("127.0.0.1", 8080).run();
	}

	public ClientApp(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws UnknownHostException, IOException {
		Socket client = new Socket(host, port);

		new Thread(new MessageReceiveComponent(client.getInputStream())).start();

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a username: ");
		username = sc.nextLine();

		System.out.println("Send text: ");
		PrintStream output = new PrintStream(client.getOutputStream());
		while (sc.hasNextLine()) {
			output.println(username + ": " + sc.nextLine());
		}
		
		output.close();
		sc.close();
		client.close();
	}
}

class MessageReceiveComponent implements Runnable {

	private InputStream server;

	public MessageReceiveComponent(InputStream server) {
		this.server = server;
	}

	@Override
	public void run() {
		Scanner s = new Scanner(server);
		while (s.hasNextLine()) {
			System.out.println(s.nextLine());
		}
		s.close();
	}
}
