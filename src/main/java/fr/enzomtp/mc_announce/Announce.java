package fr.enzomtp.mc_announce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Announce {
	public static final Logger LOGGER = LoggerFactory.getLogger("mc_announce");
	static void AnnounceServer() {
		LOGGER.info("Started Broadcasting");
		try {
			while (true) {
				String broadcast = "255.255.255.255";
				int port = 4445;
				byte[] message = "[MOTD]Broadcasted Server[/MOTD][AD]25565[/AD]".getBytes();

				// Get the internet address of the specified host
				InetAddress address = InetAddress.getByName(broadcast);

				// Initialize a datagram packet with data and address
				DatagramPacket packet = new DatagramPacket(message, message.length, address, port);

				// Create a datagram socket, send the packet through it, close it.
				DatagramSocket dsocket = new DatagramSocket();
				dsocket.send(packet);
				dsocket.close();

				TimeUnit.SECONDS.sleep(2);
				}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
