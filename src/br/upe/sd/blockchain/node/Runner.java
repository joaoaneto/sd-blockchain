package br.upe.sd.blockchain.node;

import br.upe.sd.blockchain.node.entities.Block;
import br.upe.sd.blockchain.node.entities.Blockchain;
import br.upe.sd.blockchain.node.server.NodeServer;
import br.upe.sd.blockchain.system.Auditability;
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
		
		
		Blockchain upeCoin = new Blockchain();
		Auditability audit = new Auditability(upeCoin);
		
		System.out.println("Mining the first block...");
		
		Block b1 = new Block("1", "3", "13/04/2018", 24, "");
		b1.mine();
		System.out.println(b1.getHash());

	}
	
}
