package br.upe.sd.blockchain.node.repository;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
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
	//	Document query = new Document();
//		query.append("hash", block.getHash());
//		
//		FindIterable<Document> ds = this.col.find(query);
//		
		//if(ds == null) {
			this.col.insertOne(doc);
		//}
		
	}
	
	@Override
	public ArrayList<Block> list() {
		FindIterable<Document> docs = this.col.find().sort(new BasicDBObject("dateCreated", -1));
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		for(Document doc : docs) {
			Block b = new Block(
					doc.getString("owner"), 
					doc.getString("recipient"),
					doc.getString("time"),
					doc.getInteger("valor"), 
					doc.getString("previousHash")
			);
			
			blocks.add(b);
		}
				
		return blocks;
	}
	
	@Override
	public ArrayList<Block> list(String param, String id) {
		FindIterable<Document> docs = this.col.find(Filters.eq(param, id));
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		for(Document doc : docs) {			
			Block b = new Block(
					doc.getString("owner"), 
					doc.getString("recipient"),
					doc.getString("time"),
					doc.getInteger("valor"), 
					doc.getString("previousHash")
			);
			
			blocks.add(b);
		}
		
		return blocks;
	}
	
}
