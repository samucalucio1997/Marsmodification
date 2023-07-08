package mars.tools.socket;

import mars.mips.hardware.MemoryAccessNotice;
import mars.tools.KeyboardAndDisplaySimulator;

import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int porta=4211;
    private static ServerSocket serverSocket;
    public void start() throws IOException {
        System.out.println("Inicio do servidor");
        serverSocket = new ServerSocket(porta);
        ConnectionWait();
    }
    private void ConnectionWait()throws IOException{
        while(true){
            Socket clientsock =serverSocket.accept();

            System.out.println(porta);
            System.out.println("Your machine was connected with: "
                    +clientsock.getRemoteSocketAddress()+"IP adress");
        }
    }
    public void SocketReceive(byte[] b) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        ObjectInputStream objin  = new ObjectInputStream(in);
        KeyboardAndDisplaySimulator.receive = (KeyEvent) objin.readObject();
       KeyboardAndDisplaySimulator.ref.updateMMIOControlAndData(-65536,-65532,1,65);
        KeyboardAndDisplaySimulator.ref.updateMMIOControlAndData(-65536,-65532,1,49);
    }
    public static int getPorta() {
        return porta;
    }
    public static void main(String[] args){
        try{
            Server servidor = new Server();
            KeyboardAndDisplaySimulator.ref.updateMMIOControlAndData(-65536,-65532,1,65);
            KeyboardAndDisplaySimulator.ref.updateMMIOControlAndData(-65536,-65532,1,49);
            servidor.start();
        }catch (IOException e){
            System.out.println("Error in the start Server: "+e.getMessage());
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
}

