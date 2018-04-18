package br.upe.sd.blockchain.system.dns;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class MulticastDNS implements IServiceResolver {

	IServiceResolver sr = null;
	JmDNS jmdns;
	
	public MulticastDNS(IServiceResolver sr) {
		this.sr = sr;
	}
	
	@Override
	public void register(String hostname, String address, int port) {
		
        try {
            this.jmdns = JmDNS.create(InetAddress.getByName(address));

            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.", hostname, port, "BC service");
            this.jmdns.registerService(serviceInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
	}

	@Override
	public void listen(String type) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            jmdns.addServiceListener(type, new JmDNSListener(this.sr));
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
		
	}

	@Override
	public ArrayList<String> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HostDNS get(String hostname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String hostname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, HostDNS> getDNS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregisterAllServices() {
		this.jmdns.unregisterAllServices();
	}

}
