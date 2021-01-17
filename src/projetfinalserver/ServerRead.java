/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetfinalserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author M-A
 */
public class ServerRead extends Thread {

    private final DatagramSocket datagramSocket;

    private ArrayList<Integer> ArrayListValidation = new ArrayList<>();

    private ArrayList<Integer> arrResultat = new ArrayList<>();

    public ServerRead(DatagramSocket datagramSocket, ArrayList<Integer> arrResultat) {
        this.datagramSocket = datagramSocket;
        this.arrResultat = arrResultat;
        for (int test : arrResultat) {
            System.err.println(test);
        }

    }

    //thread le lecture
    @Override
    public void run() {

        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buf, 1024);
            try {
                datagramSocket.receive(datagramPacket);
                String message = new String(datagramPacket.getData());

                char messageR[] = message.toCharArray();

                String send = "";
                //validation
                //Si le message est 'r' le solutionaire est changer
                if (messageR[0] == 'r') {
                    arrResultat = NewValue();

                    //si le message est 'v' il indique la fin et envoie un message avec la solution
                } else if (messageR[0] == 'v') {
                    String messageFin = "";
                    for (int object : arrResultat) {
                        messageFin += object + "/";
                    }

                    SendMessage(datagramPacket, messageFin);

                    //la validation pour savoir si le client a placer les boules dans le bon ordre et indique le bonne et maivaise.
                } else {

                    ArrayList<Integer> arrValidation = new ArrayList<>();

                    String validation[] = message.split("/");

                    for (int cptV = 0; cptV <= 4; cptV++) {

                        String convert = validation[cptV];
                        convert.replaceAll("\\s", "");

                        arrValidation.add(Integer.parseInt(convert.trim()));
                    }

                    for (int cptV = 0; cptV <= 4; cptV++) {

                        if (arrValidation.get(cptV) == arrResultat.get(cptV)) {
                            send += "t/";
                        } else {
                            send += "f/";
                        }

                    }
                    System.out.println(send);

                    SendMessage(datagramPacket, send);
                }

            } catch (IOException ex) {
                Logger.getLogger(ServerRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //creation d'un nouveau solitionaire
    private ArrayList<Integer> NewValue() {
        ArrayList<Integer> test = new ArrayList();
        while (test.size() <= 4) {

            Random ramdom = new Random();
            int rd = ramdom.nextInt(5) + 1;
            if (!test.contains(rd)) {
                test.add(rd);
            }

        }

        return test;
    }

    //Envoyer des message avec le thread ServerWrite
    private void SendMessage(DatagramPacket data, String message) {
        String ip = data.getAddress().toString();
        ServerWrite serverWrite = new ServerWrite(datagramSocket);
        //port
        serverWrite.send(message, ip.substring(1), data.getPort());
    }

}
