package br.upe.sd.blockchain.node;

import java.util.ArrayList;

import br.upe.sd.blockchain.system.Auditability;
import br.upe.sd.blockchain.system.IServiceResolver;
import br.upe.sd.blockchain.system.MulticastDNS;

//import br.upe.sd.blockchain.system.Auditability;

public class NodeRunner {

	public static void main(String[] args) {
		
		NodeServer ns = new NodeServer();
		ns.start();

		System.out.println("eae");
		
		IServiceResolver sr = new MulticastDNS();
		
		ArrayList<String> nodes = sr.getAll();

		Block newBlock = new Block(1, "13/04/2018", 24, "");
	}
	
}
