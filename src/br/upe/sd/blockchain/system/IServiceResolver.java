package br.upe.sd.blockchain.system;

public interface IServiceResolver {

	public void register(String hostname, String address, int port);
	public void search(String hostname);
	
}
