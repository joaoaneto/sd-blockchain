package br.upe.sd.blockchain.node;

import br.upe.sd.blockchain.node.server.NodeServer;
import br.upe.sd.blockchain.system.dns.IServiceResolver;
import br.upe.sd.blockchain.system.dns.LocalDNS;
import br.upe.sd.blockchain.system.dns.MulticastDNS;

public class Runner {

	public static final String HOSTNAME = "upe.host1";
	public static final String ADDRESS = "192.168.0.105";
	public static final int PORT = 4444;
	
	public static void main(String[] args) throws InterruptedException {
		IServiceResolver localDNS = new LocalDNS();
		IServiceResolver mDNS = new MulticastDNS(localDNS);		
		
		System.out.println("Registering " + HOSTNAME + " service...");
		mDNS.register(HOSTNAME, ADDRESS, PORT);
		
		System.out.println("Listening to services on network...");
		mDNS.listen("_http._tcp.local.");

		System.out.println("Starting Node HTTP Server...");
		NodeServer ns = new NodeServer(localDNS);
		ns.start();
	}
	
}
