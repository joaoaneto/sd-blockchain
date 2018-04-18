package br.upe.sd.blockchain.system.dns;

import java.net.InetAddress;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

public class JmDNSListener implements ServiceListener {

	IServiceResolver sr = null;
	
	public JmDNSListener(IServiceResolver sr) {
		this.sr = sr;
	}
	
    @Override
    public void serviceAdded(ServiceEvent event) {    	
        System.out.println("Service added: " + event.getInfo().getName());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        System.out.println("Service removed: " + event.getInfo().getName());
        this.sr.remove(event.getName());
    }

    @Override
    public void serviceResolved(ServiceEvent event) {    	
    	String serviceName = null;
    	InetAddress address = null;
    	
    	try {
            System.out.println("Service resolved: " + event.getInfo().getName());
    		
    		serviceName = event.getName();
    		address = event.getInfo().getAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	this.sr.register(serviceName, address.toString(), event.getInfo().getPort());
    }
}
