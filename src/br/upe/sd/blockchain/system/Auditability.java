package br.upe.sd.blockchain.system;

import br.upe.sd.blockchain.node.entities.Block;
import br.upe.sd.blockchain.node.entities.Blockchain;

public class Auditability {
	
	private Blockchain blockchain;
	
	public Auditability(Blockchain blockchain) {
		this.blockchain = blockchain;
	}
	
	public boolean isChainValid() {
		for(int i = 1; i < this.blockchain.getChain().size(); i++) {
			Block currentBlock = this.blockchain.getChain().get(i);
			Block previousBlock = this.blockchain.getChain().get(i - 1);
			
			if(currentBlock.getHash() != currentBlock.calculateHash()) {
				return false;
			}
			
			if(currentBlock.getPreviousHash() != previousBlock.getHash()) {
				return false;
			}
		}
		
		return true;
	}
	
}
