package br.upe.sd.blockchain.node.repository;

import br.upe.sd.blockchain.node.Block;

public class LocalRepository implements IRepository {

	private Block[] blockchain;

	public Block[] getBlockchain() {
		return blockchain;
	}

	public void addToBlockchain(Block[] blockchain) {
		this.blockchain = blockchain;
	}
	
	//TODO
}
