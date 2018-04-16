package br.upe.sd.blockchain.node.entities;

import java.util.ArrayList;

import org.json.JSONObject;

import br.upe.sd.blockchain.node.repository.IRepository;

public class Blockchain {

	private ArrayList<Block> chain;
	
	private IRepository repo;
	
	public Blockchain() {
		this.chain = new ArrayList<Block>();
		this.chain.add(this.createGenesisBlock());
	}
	
	public Block createGenesisBlock() {
		return new Block("", "", "01/01/2018", 0, "0");
	}
	
	public Block getLatestBlock() {
		return this.chain.get(this.chain.size() - 1);
	}
	
	public void addBlock(String data) {
		
		JSONObject obj = new JSONObject(data);
		
		Block block = new Block(obj.getString("owner"), obj.getString("recipient"), "", Integer.parseInt(obj.getString("coins")), 
				this.getLatestBlock().getHash());
		
		//to BD
		this.chain.add(block);
	}
	
	public ArrayList<Block> getChain() {
		return this.chain;
	}
	
}
