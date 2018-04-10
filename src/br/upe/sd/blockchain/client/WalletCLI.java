package br.upe.sd.blockchain.client;

import org.apache.commons.cli.*;

public class WalletCLI {

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("t", false, "transaction");
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if(cmd.hasOption("t")) {
				System.out.println("You need a block transaction");
			}
			
		} catch (ParseException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
