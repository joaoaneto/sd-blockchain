package br.upe.sd.blockchain.node.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.HttpServer;

import br.upe.sd.blockchain.node.entities.Block;
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
	 
			server.createContext("/mine", new MineHandler(this.sr));
	        server.setExecutor(null);
	        
	        server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
    static class MineHandler implements HttpHandler {
    
    	private IServiceResolver sr;
    	
    	public MineHandler(IServiceResolver sr) {
    		this.sr = sr;
    	}
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            
            InputStream is = t.getRequestBody();

			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String theString = writer.toString();
			
			System.out.println(theString);
			
			BlockDispatcher bd = new BlockDispatcher(this.sr);
			
			bd.dispatcher(theString);
            	
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
