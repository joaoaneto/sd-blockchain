package br.upe.sd.blockchain.node.entities;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONObject;

import br.upe.sd.blockchain.node.Runner;
import br.upe.sd.blockchain.node.repository.BlockchainRepository;
import br.upe.sd.blockchain.node.repository.IRepository;
import br.upe.sd.blockchain.node.repository.MongoFactory;
import br.upe.sd.blockchain.system.dns.IServiceResolver;

public class Blockchain {
	
	private IRepository repo;
	
	public Blockchain(IServiceResolver sr) {
		String addr = sr.get(Runner.DB_HOSTNAME).getIp();
		int port = sr.get(Runner.DB_HOSTNAME).getPort();
		MongoFactory mf = new MongoFactory(addr, port);
		
		this.repo = new BlockchainRepository(mf.getInstance("sd"));

		if(this.repo.list("owner", "0").size() == 0) {			
			this.repo.insert(this.createGenesisBlock());
		}
	}
	
	public Block createGenesisBlock() {
		return new Block("0", "0", "2018-04-01 00:00:00.4", 0, "0");
	}
	
	public Block getLatestBlock() {
		System.out.println(this.repo.list().get(this.repo.list().size() - 1).getHash());
		return this.repo.list().get(this.repo.list().size() - 1);
	}
	
	public void addBlock(String data) {
		JSONObject obj = new JSONObject(data);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Block block = new Block(
				obj.getString("owner"), 
				obj.getString("recipient"),
				timestamp.toString(),
				obj.getInt("coins"), 
				this.getLatestBlock().getHash()
		);
		
		block.mine();
		
		this.repo.insert(block);
	}
	
	public ArrayList<Block> getChain() {
		return this.repo.list();
	}
	
}
