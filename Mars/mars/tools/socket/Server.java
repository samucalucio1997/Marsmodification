package mars.tools.socket;

import mars.Globals;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.MemoryAccessNotice;
import mars.tools.AbstractMarsToolAndApplication;
import mars.tools.BitmapDisplay;
import mars.tools.KeyboardAndDisplaySimulator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends AbstractMarsToolAndApplication {
    private static final int porta=4211;

    private static ServerSocket serverSocket;

    /**
     * Simple constructor
     *
     * @param title   String containing title bar text
     * @param heading
     */
    protected Server() {
        super("Server", "heading");
    }

    public void start() throws IOException, ClassNotFoundException {
        System.out.println("Inicio do servidor");
        serverSocket = new ServerSocket(porta);
        ConnectionWait();
    }



    private void ConnectionWait() throws IOException, ClassNotFoundException {
        while(true){
            Socket clientsock =serverSocket.accept();
            System.out.println(porta);
            System.out.println("Your machine was connected with: " +clientsock.getRemoteSocketAddress()+"IP adress");
            ObjectInputStream in = new ObjectInputStream(clientsock.getInputStream());
            SocketReceive(in.readAllBytes());
        }
    }
    public void SocketReceive(byte[] b) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        ObjectInputStream objin  = new ObjectInputStream(in);
        KeyboardAndDisplaySimulator.receive = (KeyEvent) objin.readObject();
    }
    public static int getPorta() {
        return porta;
    }
    public static void main(String[] args){
        try{
            Server servidor = new Server();
//            KeyboardAndDisplaySimulator.ref.
            servidor.start();
        }catch (ClassNotFoundException e){
            System.out.println("Error in the start Server: "+e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private synchronized void updtMMIOControlAndData(int controlAddr, int controlValue, int dataAddr, int dataValue, boolean controlOnly){
      if(BitmapDisplay.a==1){
          synchronized (Globals.memoryAndRegistersLock){
              try {
               Globals.memory.setByte(controlAddr, controlValue);

              }catch (AddressErrorException aee){

              }
          }
      }
    }
















    public static void run() {
        try {
            ServerSocket socket = new ServerSocket(4211);
            System.out.println("Aguardando conectar");
            while (true) {
                Socket so = socket.accept();

                ObjectInputStream input = new ObjectInputStream(so.getInputStream());
                MemoryAccessNotice pacote = (MemoryAccessNotice) input.readObject();// memória recebida do "cliente"

                ObjectOutputStream href = new ObjectOutputStream(so.getOutputStream());
                href.writeObject((Integer) KeyboardAndDisplaySimulator.getMMIO());//aqui o servidor enviará pacotes tbm
                href.flush();
                input.close();
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected JComponent buildMainDisplayArea() {
        return null;
    }
}

