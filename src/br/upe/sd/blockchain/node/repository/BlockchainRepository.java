package br.upe.sd.blockchain.node.repository;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.upe.sd.blockchain.node.entities.Block;

public class BlockchainRepository implements IRepository {

	MongoDatabase database;
	MongoCollection<Document> col;
	
	public BlockchainRepository(MongoDatabase database) {
		this.database = database;
		this.col = database.getCollection("blockchain");
	}
	
	@Override
	public void insert(Block block) {
		JSONObject obj = new JSONObject(block);
		Document doc = Document.parse(obj.toString());

		if(this.col.count() == 0) {
			this.col.insertOne(doc);
		}
		
		Block b = this.list().get(this.list().size() - 1);
		if(!b.getOwner().equals(block.getOwner())
				|| !b.getRecipient().equals(block.getRecipient())
				|| b.getValor() != block.getValor()) {
			this.col.insertOne(doc);
			System.out.println("The block was save in the blockchain!!");
		}else {
			System.out.println("The block has already been mined by another host!!");
		}
	}
	
	@Override
	public ArrayList<Block> list() {
		FindIterable<Document> docs = this.col.find().sort(new BasicDBObject("dateCreated", -1));
		ArrayList<Block> blocks = new ArrayList<Block>();

		if(this.col.count() == 0) {
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
	
	@Override
	public ArrayList<Block> list(String param, String id) {
		FindIterable<Document> docs = this.col.find(Filters.eq(param, id));
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		if(this.col.count() == 0) {
			System.out.println("Should here");
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
	
}
