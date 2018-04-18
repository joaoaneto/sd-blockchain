package br.upe.sd.blockchain.system;

import java.util.ArrayList;

import br.upe.sd.blockchain.node.Runner;
import br.upe.sd.blockchain.node.entities.Block;
import br.upe.sd.blockchain.node.entities.Blockchain;
import br.upe.sd.blockchain.node.repository.BlockchainRepository;
import br.upe.sd.blockchain.node.repository.IRepository;
import br.upe.sd.blockchain.node.repository.MongoFactory;
import br.upe.sd.blockchain.system.dns.IServiceResolver;

public class Auditability {
	
	private Blockchain blockchain;
	IServiceResolver sr;
	IRepository repo;
	
	public Auditability(IServiceResolver sr) {
		this.sr = sr;
	}
	
	public boolean isChainValid() {
    	String addr = this.sr.get(Runner.DB_HOSTNAME).getIp();
		int port = this.sr.get(Runner.DB_HOSTNAME).getPort();
		
		MongoFactory mf = new MongoFactory(addr, port);    		
		this.repo = new BlockchainRepository(mf.getInstance("sd"));
		
		ArrayList<Block> blocks = this.repo.list();
		
		for(int i = 1; i < blocks.size(); i++) {
			Block currentBlock = blocks.get(i);
			Block previousBlock = blocks.get(i - 1);
						
			if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
				return false;
			}
		}
		
		return true;
	}
	
}
