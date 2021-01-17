/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetfinalserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M-A
 */
public class ServerWrite {
  private final DatagramSocket datagramSocket;

    public ServerWrite(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    //Envoyer les message au clients
    public void send(String message, String address, int port) {
        try {
             System.out.println(address);
            byte[] buf = new byte[1024];
            DatagramPacket dataGramPacket = new DatagramPacket(buf, 1024);
            
            //le message
            dataGramPacket.setData(message.getBytes());
            
            //client ip          
            dataGramPacket.setAddress(InetAddress.getByName(address));
            //le port
            dataGramPacket.setPort(port);
            datagramSocket.send(dataGramPacket);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
