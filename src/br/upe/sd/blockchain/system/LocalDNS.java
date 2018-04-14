package br.upe.sd.blockchain.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LocalDNS implements IServiceResolver {

	Map<String, String> dnsTable = new HashMap<String, String>();
	
	@Override
	public void register(String hostname, String address, int port) {
		// TODO Auto-generated method stub
		this.dnsTable.put(hostname, address);
	}

	@Override
	public void listen(String type) {
		// TODO Auto-generated method stub	
	}

	@Override
	public ArrayList<String> getAll() {
		// TODO Auto-generated method stub
		ArrayList<String> hosts = new ArrayList<>();
		
		Iterator it = this.dnsTable.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			hosts.add(pair.getValue().toString());
			it.remove();
		}
		
		return hosts;
	}

}
