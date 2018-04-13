package br.upe.sd.blockchain.node;

import java.security.*;

public class Block {
	
	private int index;
	private String time;
	private int value;
	private String previousHash;
	private String hash;
	private int nonce;
	
	public Block(int index, String time, int value, String previousHash) {
		this.setIndex(index);
		this.setTime(time);
		this.setValor(value);
		this.setPreviousHash(previousHash);
		this.hash = this.calculateHash() ;
	}
	
	public String calculateHash() {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String hashValor = this.index + this.previousHash + this.time + this.value;
            byte[] hash = digest.digest(hashValor.getBytes());
            return hash.toString();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
	
	public void mine() {
		while(this.getHash().substring(0, 3) != "000") {
			this.setNonce(this.getNonce() + 1);
			this.hash = this.calculateHash();
		}
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
}
