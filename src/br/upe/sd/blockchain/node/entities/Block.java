package br.upe.sd.blockchain.node.entities;

public class Block {
	
	private String owner;
	private String recipient;
	private String time;
	private int value;
	private String previousHash;
	private String hash;
	private int nonce;
	
	public Block(String owner, String recipient, String time, int value, String previousHash) {
		this.setOwner(owner);
		this.setRecipient(recipient);
		this.setTime(time);
		this.setValor(value);
		this.setPreviousHash(previousHash);
		this.hash = this.calculateHash() ;
	}
	
	public Block(String owner, String recipient, String time, int value, String previousHash, String hash) {
		this.setOwner(owner);
		this.setRecipient(recipient);
		this.setTime(time);
		this.setValor(value);
		this.setPreviousHash(previousHash);
		this.setHash(hash);
	}
	
	
	public String calculateHash() {
        String result = null;

        try {
            String hashValor = this.owner + this.recipient + this.previousHash + this.time + this.value + this.nonce;
            String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(hashValor);
            return sha256hex;
        }catch(Exception ex) {
            ex.printStackTrace();
        }
                
        return result;
    }
	
	public void mine() {
		System.out.println("The block is being minimg...");
		while(!this.getHash().substring(0, 2).equals("00")) {
			this.setNonce(this.getNonce() + 1);
			this.hash = this.calculateHash();
		}
		System.out.println("The block was mined... Hash: " + this.hash);
	}

	public String getPreviousHash() {
		return this.previousHash;
	}

	public void setPreviousHash(String antHash) {
		this.previousHash = antHash;
	}

	public int getValor() {
		return this.value;
	}

	public void setValor(int value) {
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
}
