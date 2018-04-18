package br.upe.sd.blockchain.node.server;

import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import br.upe.sd.blockchain.system.dns.IServiceResolver;

public class BlockDispatcher {

	public IServiceResolver sr;
	
	public BlockDispatcher(IServiceResolver sr) {
		this.sr = sr;
	}
	
	public void dispatcher(String data) {
		ArrayList<String> hosts = sr.getAll();
				
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        
        CloseableHttpClient client = HttpClients.custom()
        		.setConnectionManager(cm)
        		.build();
        
        try {
	        
	        PostThread[] threads = new PostThread[hosts.size()];
	        
	        System.out.println(this.sr.getAll());
	        
	        for(int i = 0; i < hosts.size(); i++) {
				System.out.println("Sending the transaction data to " + hosts.get(i));
				
				HttpPost httpPost = new HttpPost("http://" + hosts.get(i) + ":4444/mine");
				
				threads[i] = new PostThread(client, httpPost, i + 1, data);
				
				threads[i].start();
	        }
	        
	        for(int i = 0; i < hosts.size(); i++) {
				threads[i].join();
	        }
	        
        	client.close();
       
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        }	
	}
	
	static class PostThread extends Thread {
		
		private CloseableHttpClient client;
		private HttpContext context;
		private HttpPost httpPost;
		private int id;
		private String data;
		
		public PostThread(CloseableHttpClient client, HttpPost httpPost, int id, String data) {
			this.client = client;
			this.context = new BasicHttpContext();
			this.httpPost = httpPost;
			this.id = id;
			this.data = data;
		}
		
		@Override
		public void run() {
			
			try {
				CloseableHttpClient client = HttpClients.createDefault();
				
				byte[] bytesEncoded = Base64.encodeBase64(data.getBytes());
				
				ByteArrayEntity entity = new ByteArrayEntity(bytesEncoded);

			    this.httpPost.setEntity(entity);
			    this.httpPost.setHeader("Content-type", "application/octet-stream");
			    
			    client.execute(this.httpPost);
			    
				client.close();	
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		
	}
	
}
