package br.upe.sd.blockchain.system;

import java.util.Map;

import br.upe.sd.blockchain.node.repository.IRepository;
import br.upe.sd.blockchain.system.dns.HostDNS;
import br.upe.sd.blockchain.system.dns.IServiceResolver;

public class Monitor extends Thread {
	
	IRepository repo;
	IServiceResolver sr;
	
	public Monitor(IServiceResolver sr) {
//		this.repo = repo;
		this.sr = sr;
	}
	
	@Override
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(30000);
				System.out.println("Local DNS:");
				for (Map.Entry<String, HostDNS> entry : this.sr.getDNS().entrySet()) {
				    System.out.println(entry.getKey() + " - " 
				    		+ entry.getValue().getIp() + " - " 
				    		+ entry.getValue().getPort());				
				}
				
				System.out.println("\nWallet status");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
				

		}
		
	}
	
	public void test() {

	}
	
}
