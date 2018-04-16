package br.upe.sd.blockchain.node.repository;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.upe.sd.blockchain.node.entities.Block;

public class BlockchainRepository {

	MongoDatabase database;
	MongoCollection<Document> col;
	
	public BlockchainRepository(MongoDatabase database) {
		this.database = database;
		this.col = database.getCollection("blockchain");
	}
	
	public void insert(Block block) {
		JSONObject obj = new JSONObject(block);
		Document doc = Document.parse(obj.toString());
		this.col.insertOne(doc);
	}
	
	public void get(String hash) {
		
	}
	
	public void update() {
		
	}
	
	public void remove() {
		
	}
	
}
