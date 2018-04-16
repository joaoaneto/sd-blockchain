package br.upe.sd.blockchain.node.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.HttpServer;

import br.upe.sd.blockchain.node.entities.Block;
import br.upe.sd.blockchain.node.entities.Blockchain;
import br.upe.sd.blockchain.system.dns.IServiceResolver;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class NodeServer extends Thread {

	private IServiceResolver sr;
	
	public NodeServer(IServiceResolver sr) {
		this.sr = sr;
	}
	
	@Override
	public void run() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
	 
			server.createContext("/transaction", new TransactionHandler());
			server.createContext("/mine", new MineHandler(this.sr));
	        server.setExecutor(null);
	        
	        server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
    static class TransactionHandler implements HttpHandler {
        
    	private IServiceResolver sr;
    	
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "The data was multicasted";
            t.sendResponseHeaders(200, response.length());
            
            InputStream is = t.getRequestBody();

			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String data = writer.toString();
			
			System.out.println(data);
			
//			BlockDispatcher bd = new BlockDispatcher(this.sr);
//			bd.dispatcher(data);
            	
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

	
    static class MineHandler implements HttpHandler {
    
    	private IServiceResolver sr;
    	
    	public MineHandler(IServiceResolver sr) {
    		this.sr = sr;
    	}
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "The data is mining...";
            t.sendResponseHeaders(200, response.length());
            
            InputStream is = t.getRequestBody();

			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String data = writer.toString();
			
			Blockchain blockchain = new Blockchain();
			blockchain.addBlock(data);
							
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
