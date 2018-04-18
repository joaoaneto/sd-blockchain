package br.upe.sd.blockchain.client;

import java.util.UUID;

import br.upe.sd.blockchain.node.Runner;
import br.upe.sd.blockchain.node.repository.BlockchainRepository;
import br.upe.sd.blockchain.node.repository.MongoFactory;

public class Wallet {
	
	private UUID uuid;
	private String name;
	private int coins;
	
	public Wallet(String name, int coins) {
		this.setName(name);
		this.setCoins(coins);
		this.setUuid(UUID.randomUUID());
	}
	
	public Wallet(String name, int coins, String uuid) {
		this.setName(name);
		this.setCoins(coins);
		this.setUuid(UUID.fromString(uuid));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCoins() {		
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getUuid() {
		return uuid.toString();
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
