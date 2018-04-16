package br.upe.sd.blockchain.node.repository;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.upe.sd.blockchain.system.dns.IServiceResolver;

public class MongoConnection {

	MongoClient client;
	MongoDatabase database;
	
	public MongoConnection(String hostname, IServiceResolver sr) {
		this.client = new MongoClient(sr.get(hostname));
		this.database = client.getDatabase("sd");
	}
	
	public void insertOne(Document document) {
		MongoCollection<Document> collection = this.database.getCollection("blockchain");
		collection.insertOne(document);
	}
	
	public void getAll() {
		
	}
	
}
