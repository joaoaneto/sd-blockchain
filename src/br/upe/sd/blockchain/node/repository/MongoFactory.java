package br.upe.sd.blockchain.node.repository;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.upe.sd.blockchain.system.dns.IServiceResolver;

public class MongoFactory {

	MongoClient client;
	
	public MongoFactory(String address) {
		this.client = new MongoClient(address);
	}
	
	public MongoDatabase getInstance(String name) {
		return this.client.getDatabase(name);
	}
	
}
