package br.upe.sd.blockchain.system;

import java.util.ArrayList;
import java.util.Map;

import br.upe.sd.blockchain.client.Wallet;
import br.upe.sd.blockchain.node.Runner;
import br.upe.sd.blockchain.node.entities.Block;
import br.upe.sd.blockchain.node.repository.BlockchainRepository;
import br.upe.sd.blockchain.node.repository.IRepository;
import br.upe.sd.blockchain.node.repository.MongoFactory;
import br.upe.sd.blockchain.node.repository.WalletRepository;
import br.upe.sd.blockchain.system.dns.HostDNS;
import br.upe.sd.blockchain.system.dns.IServiceResolver;

public class Monitor extends Thread {
	
	WalletRepository repo;
	IServiceResolver sr;
	String uuid;
	
	public Monitor(IServiceResolver sr, String uuid) {
		this.uuid = uuid;
		this.sr = sr;
	}
	
	@Override
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(30000);
				System.out.println("\nLocal DNS:");
				for (Map.Entry<String, HostDNS> entry : this.sr.getDNS().entrySet()) {
				    System.out.println(entry.getKey() + " - " 
				    		+ entry.getValue().getIp() + " - " 
				    		+ entry.getValue().getPort());				
				}
				
				System.out.println("\nWallet status:");
				this.coinsCalculate(this.uuid);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void coinsCalculate(String uuid) {
		int input = 0;
		int output = 0;
	
    	String addr = this.sr.get(Runner.DB_HOSTNAME).getIp();
		int port = this.sr.get(Runner.DB_HOSTNAME).getPort();
		
		MongoFactory mf = new MongoFactory(addr, port);    		
		this.repo = new WalletRepository(mf.getInstance("sd"));
		
		Wallet wallet = this.repo.find(uuid);

		ArrayList<Block> blocks = this.repo.getAllTransactions(uuid);
				
		for(Block b : blocks) {
			if(b.getOwner().equals(uuid)) {
				output += b.getValor();
			}
			if(b.getRecipient().equals(uuid)) {
				input += b.getValor();
			}
		}
						
		wallet.setCoins(wallet.getCoins() - output);
		wallet.setCoins(wallet.getCoins() + input);
				
		System.out.println("Wallet name: " + wallet.getName());
		System.out.println("Wallet uuid: " + wallet.getUuid());
		System.out.println("Wallet coins: " + wallet.getCoins());
	}
	
}
