package br.upe.sd.blockchain.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DNSFileSystem implements IServiceResolver {

	Map<String, String> dnsTable = new HashMap<String, String>();
	
	String fileName = null;
	
	FileInputStream in = null;
	FileOutputStream out = null;
	ObjectOutputStream os = null;
	
	public DNSFileSystem(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void register(String hostname, String address, int port) {
		this.dnsTable.put(hostname, address);
		System.out.println(this.dnsTable);
		try {
			this.out = new FileOutputStream(this.fileName);
			this.os = new ObjectOutputStream(out);
			this.os.writeObject(this.dnsTable);
			
			this.os.close();
			this.out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Data added...");
		
	}

	@Override
	public void listen(String type) {
		// TODO Auto-generated method stub
		
	}

}
