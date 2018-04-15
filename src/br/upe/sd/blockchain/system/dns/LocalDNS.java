package br.upe.sd.blockchain.system.dns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
						
		for(String address : this.dnsTable.values()) {
			hosts.add(address);
		}

		return hosts;
	}

	@Override
	public void register(String hostname, String address) {
		if(!this.dnsTable.containsKey(hostname)) {
			this.dnsTable.put(hostname, address);			
		}
	}

	@Override
	public String get(String hostname) {
		return this.dnsTable.get(hostname);
	}

	@Override
	public void remove(String hostname) {
		this.dnsTable.remove(hostname);
	}
}
