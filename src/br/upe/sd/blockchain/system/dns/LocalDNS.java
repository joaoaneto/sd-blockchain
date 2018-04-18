package br.upe.sd.blockchain.system.dns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.upe.sd.blockchain.node.Runner;

public class LocalDNS implements IServiceResolver {

	public Map<String, HostDNS> dnsTable = new HashMap<String, HostDNS>();
	
	@Override
	public void listen(String type) {
	}

	@Override
	public ArrayList<String> getAll() {
		ArrayList<String> hosts = new ArrayList<>();
		
		for (Map.Entry<String, HostDNS> entry : this.dnsTable.entrySet()) {
		    
			if(!entry.getKey().equals("upe.db")) {
				String address = entry.getValue().getIp();
				hosts.add(address);
			}
			
		}

		return hosts;
	}
	
	public Map<String, HostDNS> getDNS() {
		return this.dnsTable;
	}
	
	public Map<String, HostDNS> getDNSTable() {
		return this.dnsTable;
	}

	@Override
	public void register(String hostname, String address, int port) {
		String filter = "upe";

		if(!this.dnsTable.containsKey(hostname) 
				&& (hostname.substring(0, filter.length()).equals(filter)
				&& !address.substring(1).equals(Runner.ADDRESS))) {
			
			HostDNS h = new HostDNS(address.substring(1), port);
			
			this.dnsTable.put(hostname, h);			
		} else if(hostname.equals(Runner.DB_HOSTNAME)) {
			HostDNS h = new HostDNS(address.substring(1), port);
			
			this.dnsTable.put(hostname, h);
		}
	}

	@Override
	public HostDNS get(String hostname) {
		return this.dnsTable.get(hostname);
	}

	@Override
	public void remove(String hostname) {
		this.dnsTable.remove(hostname);
	}

	@Override
	public void unregisterAllServices() {
		// TODO Auto-generated method stub
		
	}
}
