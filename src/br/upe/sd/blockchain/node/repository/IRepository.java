package br.upe.sd.blockchain.node.repository;

import java.util.ArrayList;

import br.upe.sd.blockchain.node.entities.Block;

public interface IRepository {
	
	public void insert(Block block);
	public ArrayList<Block> list();
	public ArrayList<Block> list(String param, String id);	

}
