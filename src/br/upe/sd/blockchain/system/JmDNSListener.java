package br.upe.sd.blockchain.system;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

public class JmDNSListener implements ServiceListener {

    @Override
    public void serviceAdded(ServiceEvent event) {
        System.out.println("Service added: " + event.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        System.out.println("Service removed: " + event.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        System.out.println("Service resolved: " + event.getInfo());
    }
}
