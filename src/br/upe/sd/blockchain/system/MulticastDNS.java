package br.upe.sd.blockchain.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class MulticastDNS implements IServiceResolver {

	@Override
	public void register(String hostname, String address, int port) {
		
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", hostname, port, "teste");
            jmdns.registerService(serviceInfo);

            jmdns.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
	}

	@Override
	public void listen(String type) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            jmdns.addServiceListener(type, new JmDNSListener());

            Thread.sleep(30000);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
		
	}

}
