package br.upe.sd.blockchain.node;

import br.upe.sd.blockchain.system.Auditability;

public class NodeRunner {

	public static void main(String[] args) {
		Blockchain upeCoin = new Blockchain();
		Auditability audit = new Auditability(upeCoin);
		
		System.out.println("Mining the first block...");
		upeCoin.addBlock(new Block(1, "13/04/2018", 24, ""));
		
		System.out.println("Mining the second block...");
		upeCoin.addBlock(new Block(2, "13/04/2018", 47, ""));
		
		System.out.println("Mining the third block...");
		upeCoin.addBlock(new Block(3, "13/04/2018", 89, ""));
				
		System.out.println(audit.isChainValid());
	}
	
}
