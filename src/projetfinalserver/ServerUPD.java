/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetfinalserver;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M-A
 */
public class ServerUPD extends Thread {

    private final String ipLocal;
    private final int localPort;
    private ArrayList<Integer> arrResultat;
    private ServerWrite threadWrite;

    public ServerUPD(String ipLocal, int localPort, ArrayList<Integer> arrResultat) {
        this.ipLocal = ipLocal;
        this.localPort = localPort;
        this.arrResultat = arrResultat;

    }

    @Override
    //Creation du thrad ecriture et  lecture
    public void run() {
        try {
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(ipLocal), localPort);

            DatagramSocket datagramSocket = new DatagramSocket(socketAddress);

            new Thread(new ServerRead(datagramSocket, arrResultat)).start();
            threadWrite = new ServerWrite(datagramSocket);

        } catch (SocketException ex) {
            Logger.getLogger(ServerUPD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerUPD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String message, String ip, int port) {
        threadWrite.send(message, ip, port);

    }

}
