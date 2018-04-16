package br.upe.sd.blockchain.system.dns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.upe.sd.blockchain.node.Runner;

public class LocalDNS implements IServiceResolver {

	public Map<String, String> dnsTable = new HashMap<String, String>();
	
	@Override
	public void register(String hostname, String address, int port) {
		this.dnsTable.put(hostname, address);
	}
 
	@Override
	public void listen(String type) {
	}

	@Override
	public ArrayList<String> getAll() {
		ArrayList<String> hosts = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : this.dnsTable.entrySet()) {
		    String address  = entry.getValue();
		    
			hosts.add(address);
		}

		return hosts;
	}
	
	public Map<String, String> getDNSTable() {
		return this.dnsTable;
	}

	@Override
	public void register(String hostname, String address) {
		String filter = "upe";

		if(!this.dnsTable.containsKey(hostname) 
				&& (hostname.substring(0, filter.length()).equals(filter)
				&& !address.substring(1).equals(Runner.ADDRESS))) {
			
			this.dnsTable.put(hostname, address.substring(1));			
		}
		System.out.println(this.dnsTable);
	}

	@Override
	public String get(String hostname) {
		return this.dnsTable.get(hostname);
	}

	@Override
	public void remove(String hostname) {
		this.dnsTable.remove(hostname);
		System.out.println(this.dnsTable);
	}
}
