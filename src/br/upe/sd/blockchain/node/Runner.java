package br.upe.sd.blockchain.node;

import br.upe.sd.blockchain.node.server.NodeServer;
import br.upe.sd.blockchain.system.dns.IServiceResolver;
import br.upe.sd.blockchain.system.dns.LocalDNS;
import br.upe.sd.blockchain.system.dns.MulticastDNS;

public class Runner {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Starting HTTP Server");
		
		IServiceResolver localDNS = new LocalDNS();

		IServiceResolver mDNS = new MulticastDNS(localDNS);
		mDNS.listen("_http._tcp.local.");
				
		NodeServer ns = new NodeServer(localDNS);
		ns.start();

		System.out.println("Registering the BC service on host1");
		
		mDNS.register("br.upe", "192.168.0.103", 4444);
		
		Thread.sleep(30000);
		
		System.out.println(localDNS.getAll());
	}
	
}
