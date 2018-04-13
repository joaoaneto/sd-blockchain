package br.upe.sd.blockchain.node;

import java.util.ArrayList;

public class Blockchain {

	private ArrayList<Block> chain;
	
	public Blockchain() {
		this.chain = new ArrayList<Block>();
		this.chain.add(this.createGenesisBlock());
	}
	
	public Block createGenesisBlock() {
		return new Block(0, "13/04/2018", 0, "0");
	}
	
	public Block getLatestBlock() {
		return this.chain.get(this.chain.size() - 1);
	}
	
	public void addBlock(Block block) {
		block.setPreviousHash(this.getLatestBlock().getHash());
		this.chain.add(block);
	}
	
	public ArrayList<Block> getChain() {
		return this.chain;
	}
	
}
