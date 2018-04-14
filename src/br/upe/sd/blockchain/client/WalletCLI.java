package br.upe.sd.blockchain.client;

import org.apache.commons.cli.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class WalletCLI {

	public static void main(String[] args) {
		Options options = new Options();
		Option option = new Option("t", "transaction");
		option.setArgs(2);
		options.addOption(option);
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if(cmd.hasOption("t")) {
				System.out.println("You need a block transaction");

				CloseableHttpClient client = HttpClients.createDefault();
				
				HttpPost httpPost = new HttpPost("http://localhost:8000/mine");
				
				String[] values = cmd.getOptionValues("t");
				
				JSONObject obj = new JSONObject();
				obj.put("recipient", values[0]);
				obj.put("value", Integer.parseInt(values[1]));

			    StringEntity entity = new StringEntity(obj.toString());

			    httpPost.setEntity(entity);
			    httpPost.setHeader("Content-type", "application/json");
			    
			    client.execute(httpPost);
			    
				client.close();
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
}