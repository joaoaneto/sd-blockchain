package br.upe.sd.blockchain.client;

public class Wallet {
	
	private String uuid;
	private String name;
	
	public Wallet(String pUuid, String pName) {
		this.setUuid(pUuid);
		this.setName(pName);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
