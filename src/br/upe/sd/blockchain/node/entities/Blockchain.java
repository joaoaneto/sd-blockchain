package br.upe.sd.blockchain.node.entities;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONObject;

//import br.upe.sd.blockchain.node.repository.IRepository;

public class Blockchain {

	private ArrayList<Block> chain;
	
	//private IRepository repo;
	
	public Blockchain() {
		this.chain = new ArrayList<Block>();
		this.chain.add(this.createGenesisBlock());
	}
	
	public Block createGenesisBlock() {
		return new Block("0", "0", "2018-04-01 00:00:00.4", 0, "0");
	}
	
	public Block getLatestBlock() {
		return this.chain.get(this.chain.size() - 1);
	}
	
	public void addBlock(String data) {
		JSONObject obj = new JSONObject(data);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Block block = new Block(
				obj.getString("owner"), 
				obj.getString("recipient"),
				timestamp.toString(), obj.getInt("coins"), 
				this.getLatestBlock().getHash()
		);
		
		block.mine();
		
		this.chain.add(block);
	}
	
	public ArrayList<Block> getChain() {
		return this.chain;
	}
	
}
