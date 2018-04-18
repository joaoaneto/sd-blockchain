package br.upe.sd.blockchain.node;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.mongodb.client.MongoDatabase;

import br.upe.sd.blockchain.client.Wallet;
import br.upe.sd.blockchain.node.entities.Block;
import br.upe.sd.blockchain.node.repository.BlockchainRepository;
import br.upe.sd.blockchain.node.repository.IRepository;
import br.upe.sd.blockchain.node.repository.MongoFactory;
import br.upe.sd.blockchain.node.server.NodeServer;
import br.upe.sd.blockchain.system.Monitor;
import br.upe.sd.blockchain.system.dns.IServiceResolver;
import br.upe.sd.blockchain.system.dns.LocalDNS;
import br.upe.sd.blockchain.system.dns.MulticastDNS;

public class Runner {
	
	public static final String HOSTNAME = "upe.host1";
	public static final String ADDRESS = "192.168.0.105";
	public static final int PORT = 4444;
	
	public static final String DB_HOSTNAME = "upe.db";
	public static final int DB_PORT = 27017;
	
	public static void main(String[] args) throws InterruptedException {
		IServiceResolver localDNS = new LocalDNS();
		IServiceResolver mDNS = new MulticastDNS(localDNS);		

		Wallet wallet = new Wallet("Host 1", 120);
		System.out.println(wallet.getUuid());
		
		System.out.println("Registering database service...");
		mDNS.register(DB_HOSTNAME, ADDRESS, DB_PORT);
		
		System.out.println("Registering " + HOSTNAME + " service...");
		mDNS.register(HOSTNAME, ADDRESS, PORT);
		
		System.out.println("Listening to services on network...");
		mDNS.listen("_http._tcp.local.");

		System.out.println("Starting Node HTTP Server...");
		NodeServer ns = new NodeServer(localDNS, mDNS);
		ns.start();
		
		Monitor monitor = new Monitor(localDNS);
		monitor.start();
	}

}
