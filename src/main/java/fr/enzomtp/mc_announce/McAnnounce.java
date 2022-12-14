package fr.enzomtp.mc_announce;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class McAnnounce implements DedicatedServerModInitializer {
	public static final String MOD_ID = "mc_announce";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeServer() {
		LOGGER.info("Hello world from Mc Server Announcer!");
		ServerLifecycleEvents.SERVER_STARTED.register(this::startTimer);
		ServerLifecycleEvents.SERVER_STOPPING.register(this::stopTimer);
	}

	private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);

	public void startTimer(MinecraftServer server) {
		LOGGER.info("Started Broadcasting");
		executor.scheduleAtFixedRate(() -> {
			try {
				String broadcast = "255.255.255.255";
				int broadcastport = 4445;
				String motd = server.getServerMotd();
				int srvport = server.getServerPort();
				String msg = "[MOTD]"+motd+"[/MOTD][AD]"+srvport+"[/AD]";
				msg = msg.replace("\n","   ");
				byte[] message = msg.getBytes();
				// Get the internet address of the specified host
				InetAddress address = InetAddress.getByName(broadcast);

				// Initialize a datagram packet with data and address
				DatagramPacket packet = new DatagramPacket(message, message.length, address, broadcastport);

				// Create a datagram socket, send the packet through it, close it.
				DatagramSocket dsocket = new DatagramSocket();
				dsocket.send(packet);
				dsocket.close();

			} catch (Exception e) {
				LOGGER.error("Failed to set status: " + e);
				e.printStackTrace();
			}

		}, 0, 5, TimeUnit.SECONDS);
	}
	public void stopTimer(MinecraftServer server) {
		executor.shutdownNow();
	}
}
