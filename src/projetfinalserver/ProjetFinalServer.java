/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetfinalserver;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author M-A
 */
public class ProjetFinalServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //creation du solutionaire avec des nombre entre 1 et 5
        ArrayList<Integer> test = new ArrayList();
        while (test.size() <= 4) {

            Random ramdom = new Random();
            int rd = ramdom.nextInt(5) + 1;
            if (!test.contains(rd)) {
                test.add(rd);
            }

        }
      
        //prendre les informations d'entrer par le clavier
        Scanner keyboard = new Scanner(System.in);
        
        //le ip
        System.out.println("donner le ip");
        String ip = keyboard.next();
        
        //le port
        System.out.println("donner le port");
        String port = keyboard.next();


        int iPort = Integer.valueOf(port);
        
        //creation du thread serverUPD
        new ServerUPD(ip,iPort , test).start();
    }
}
