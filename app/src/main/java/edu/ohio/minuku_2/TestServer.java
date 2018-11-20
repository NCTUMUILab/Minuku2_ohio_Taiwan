package edu.ohio.minuku_2;

import edu.ohio.minuku_2.Receiver.WifiReceiver;

public class TestServer {

    public void testServer() {
        WifiReceiver wifiReceiver = new WifiReceiver();
        wifiReceiver.testSendingTripData();
    }
}
