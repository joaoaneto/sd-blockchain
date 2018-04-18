package br.upe.sd.blockchain.node.repository;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.upe.sd.blockchain.client.Wallet;
import br.upe.sd.blockchain.node.entities.Block;

public class WalletRepository {

	MongoDatabase database;
	MongoCollection<Document> col;
	MongoCollection<Document> col2;

	
	public WalletRepository(MongoDatabase database) {
		this.database = database;
		this.col = database.getCollection("wallet");
		this.col2 = database.getCollection("blockchain");
	}
	
	public void insert(Wallet wallet) {
		JSONObject obj = new JSONObject(wallet);
		Document doc = Document.parse(obj.toString());
		
		this.col.insertOne(doc);	
	}
	
	public void update(Wallet wallet) {
		JSONObject obj = new JSONObject(wallet);
		Document doc = Document.parse(obj.toString());
		this.col.updateOne(new BasicDBObject("uuid", wallet.getUuid()),
				new Document("$set", new Document("coins", wallet.getCoins())));
	}
	
	public ArrayList<Block> getAllTransactions(String uuid) {
		ArrayList<Block> blocks = new ArrayList<Block>();
				
		BasicDBObject c1 = new BasicDBObject("owner", uuid);
		BasicDBObject c2 = new BasicDBObject("recipient", uuid);
		
		BasicDBList or = new BasicDBList();
		or.add(c1);
		or.add(c2);
		
		BasicDBObject query = new BasicDBObject("$or", or);
		FindIterable<Document> docs = col2.find(query);
		
		if(this.col2.count() == 0) {
			return blocks;
		}
		
		for(Document doc : docs) {
			Block b = new Block(
					doc.getString("owner"), 
					doc.getString("recipient"),
					doc.getString("time"),
					doc.getInteger("valor"), 
					doc.getString("previousHash"),
					doc.getString("hash")
			);
			
			blocks.add(b);
		}
		
		return blocks;
	}
	
	public Wallet find(String uuid) {
		Document doc = this.col.find(new BasicDBObject("uuid", uuid)).first();

		if(doc == null) {
			return null;
		}
		
		return new Wallet(doc.getString("name"), doc.getInteger("coins"), doc.getString("uuid"));
	}
	
}
