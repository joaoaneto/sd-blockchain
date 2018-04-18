package br.upe.sd.blockchain.node.repository;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.upe.sd.blockchain.client.Wallet;

public class WalletRepository {
	MongoDatabase database;
	MongoCollection<Document> col;
	
	public WalletRepository(MongoDatabase database) {
		this.database = database;
		this.col = database.getCollection("wallet");
	}
	
	public void insert(Wallet wallet) {
		JSONObject obj = new JSONObject(wallet);
		Document doc = Document.parse(obj.toString());
		
		this.col.insertOne(doc);	
	}
	
	public Wallet get(String uuid) {
		return null;
	}
}
