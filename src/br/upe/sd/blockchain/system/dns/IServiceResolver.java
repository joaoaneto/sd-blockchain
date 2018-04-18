package br.upe.sd.blockchain.system.dns;

import java.util.ArrayList;
import java.util.Map;

public interface IServiceResolver {

	public void register(String hostname, String address, int port);
	public void listen(String type);
	public HostDNS get(String hostname);
	public Map<String, HostDNS> getDNS();
	public ArrayList<String> getAll();
	public void remove(String hostname);
	public void unregisterAllServices();
}
