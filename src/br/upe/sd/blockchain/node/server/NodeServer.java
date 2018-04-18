package br.upe.sd.blockchain.node.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpServer;

import br.upe.sd.blockchain.client.Wallet;
import br.upe.sd.blockchain.node.Runner;
import br.upe.sd.blockchain.node.entities.Block;
import br.upe.sd.blockchain.node.entities.Blockchain;
import br.upe.sd.blockchain.node.repository.BlockchainRepository;
import br.upe.sd.blockchain.node.repository.IRepository;
import br.upe.sd.blockchain.node.repository.MongoFactory;
import br.upe.sd.blockchain.node.repository.WalletRepository;
import br.upe.sd.blockchain.system.Auditability;
import br.upe.sd.blockchain.system.dns.IServiceResolver;

import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class NodeServer extends Thread {

	private IServiceResolver sr;
	private IServiceResolver sr2;

	public NodeServer(IServiceResolver sr, IServiceResolver sr2) {
		this.sr = sr;
		this.sr2 = sr2;
	}
	
	@Override
	public void run() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(Runner.PORT), 0);
	 
			server.createContext("/transaction", new TransactionHandler(this.sr));
			server.createContext("/mine", new MineHandler(this.sr));
			
			server.createContext("/blockchain", new BlockchainListHandler(this.sr));
			server.createContext("/wallet", new CreateWalletHandler(this.sr));
			server.createContext("/auditability", new AuditabilityHandler(this.sr));
				
			server.createContext("/off", new PowerOffHandler(this.sr2));
			
			server.setExecutor(null);
	        
	        server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
    static class TransactionHandler implements HttpHandler {
        
    	private IServiceResolver sr;
    	
    	public TransactionHandler(IServiceResolver sr) {
    		this.sr = sr;
    	}
    	
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "The data was multicasted";
            t.sendResponseHeaders(200, response.length());
            
            InputStream is = t.getRequestBody();

			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String data = writer.toString();
						
			BlockDispatcher bd = new BlockDispatcher(this.sr);
			bd.dispatcher(data);
            	
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

	
    static class MineHandler implements HttpHandler {
    
    	IServiceResolver sr;
    	
    	public MineHandler(IServiceResolver sr) {
    		this.sr = sr;
    	}
    	
        @Override
        public void handle(HttpExchange t) throws IOException {           
            InputStream is = t.getRequestBody();
            
			byte[] value = Base64.decodeBase64(is.readAllBytes());			
			String data = new String(value);
			
			Blockchain blockchain = new Blockchain(this.sr);
			blockchain.addBlock(data);

			String response = "The data is mining...";
			t.sendResponseHeaders(200, response.length());
	           
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class BlockchainListHandler implements HttpHandler {
        
    	IServiceResolver sr;
    	IRepository repo;
    	
    	public BlockchainListHandler(IServiceResolver sr) {
    		this.sr = sr;    	    		
    	}
    	
        @Override
        public void handle(HttpExchange t) throws IOException {					        	
        	String addr = this.sr.get(Runner.DB_HOSTNAME).getIp();
    		int port = this.sr.get(Runner.DB_HOSTNAME).getPort();
    		
    		MongoFactory mf = new MongoFactory(addr, port);    		
    		this.repo = new BlockchainRepository(mf.getInstance("sd"));
    		
			ArrayList<Block> blocks = this.repo.list();
	
			String json = new Gson().toJson(blocks);
			
            t.sendResponseHeaders(200, json.length());
            
            OutputStream os = t.getResponseBody();
            os.write(json.getBytes());
            os.close();
        }
    }
    
    static class PowerOffHandler implements HttpHandler {
    	
    	IServiceResolver sr;

    	public PowerOffHandler(IServiceResolver sr) {
    		this.sr = sr;
    	}
    	
        @Override
        public void handle(HttpExchange t) throws IOException {					        	
        	this.sr.unregisterAllServices();
			
            String response = "Services unregistered";
        	
            t.sendResponseHeaders(200, response.length());
            
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class CreateWalletHandler implements HttpHandler {
        
    	private IServiceResolver sr;
    	
    	public CreateWalletHandler(IServiceResolver sr) {
    		this.sr = sr;
    	}
    	
        @Override
        public void handle(HttpExchange t) throws IOException {
        	String addr = this.sr.get(Runner.DB_HOSTNAME).getIp();
    		int port = this.sr.get(Runner.DB_HOSTNAME).getPort();
    		
    		MongoFactory mf = new MongoFactory(addr, port);    		
    		WalletRepository wp = new WalletRepository(mf.getInstance("sd"));

            InputStream is = t.getRequestBody();

			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String data = writer.toString();

			JSONObject obj = new JSONObject(data);
			Wallet wallet = new Wallet(obj.getString("name"), obj.getInt("coins"));
			
			wp.insert(wallet);
			
            String response = "Wallet added";
            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class AuditabilityHandler implements HttpHandler {
        
    	private IServiceResolver sr;
    	
    	public AuditabilityHandler(IServiceResolver sr) {
    		this.sr = sr;
    	}
    	
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream is = t.getRequestBody();
            
            Auditability audit = new Auditability(this.sr);
           
			JSONObject obj = new JSONObject();
			obj.put("integrity", audit.isChainValid());
			
            t.sendResponseHeaders(200, obj.toString().length());

            OutputStream os = t.getResponseBody();
            os.write(obj.toString().getBytes());
            os.close();
        }
    }
   
}
