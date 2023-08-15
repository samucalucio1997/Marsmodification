package mars.tools.socket;

import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JComponent;

import mars.Globals;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.MemoryAccessNotice;
import mars.mips.hardware.RegisterFile;
import mars.tools.AbstractMarsToolAndApplication;
import mars.tools.BitmapDisplay;
import mars.tools.KeyboardAndDisplaySimulator;

public class Server extends AbstractMarsToolAndApplication {
    private static final int porta=4211;
    
    private static ServerSocket serverSocket;
    private static Server servidor = new Server();
    
    private int mapi;

    public static Server getServer(){
        return servidor;
    }

    public int getMapi() {
        return mapi;
    }

    public void setMapi(int mapi) {
        this.mapi = mapi;
    }

    public int getMapf() {
        return mapf;
    }

    public void setMapf(int mapf) {
        this.mapf = mapf;
    }

    private int mapf;


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
        servidor.Test();
        // try{
        //     servidor.start();
        // }catch (ClassNotFoundException e){
        //     System.out.println("Error in the start Server: "+e.getMessage());
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
    }

    public void Test(){
        for(int i=268501184;i<268564384;i+=4){
                updtData(i,1800775);
                setMapi(i);
             }
            setMapf(268564384);
    }


    private synchronized void updtData(int adress,int Value){
          synchronized (Globals.memoryAndRegistersLock){
              try {
                    Globals.memory.setWord(adress, Value);
            } catch (AddressErrorException e) {
                e.printStackTrace();
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

