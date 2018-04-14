package br.upe.sd.blockchain.system;

import java.util.ArrayList;

public interface IServiceResolver {

	public void register(String hostname, String address, int port);
	public void listen(String type);
	public ArrayList<String> getAll();
}
