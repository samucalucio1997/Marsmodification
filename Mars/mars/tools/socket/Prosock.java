package mars.tools.socket;

import mars.tools.KeyboardAndDisplaySimulator;

import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Prosock extends Thread{
    public static KeyboardAndDisplaySimulator key;

    private Socket socket;
    private static String IPServer="localhost";
    public static KeyEvent syscall;//memory adress with your value that will be sended
    public static KeyEvent receive;//memory that will be received

    public Prosock() {

    }/*
        	passar como parametro o endereço de memoria que contem
	        o syscall
		*/

    public void Start() throws IOException {
        socket = new Socket(IPServer,Server.getPorta());
        PrepareSenderLoop();
        //System.out.println(IPServer);
    }

    private void PrepareSenderLoop() throws IOException {
        while(KeyboardAndDisplaySimulator.sender!=null){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objout = new ObjectOutputStream(out);
        objout.writeObject(KeyboardAndDisplaySimulator.sender);
        KeyboardAndDisplaySimulator.sender = null;
        objout.flush();
        objout.reset();
        }
    }



    public static void main(String[] args) {
        try{
            Prosock sock = new Prosock();
            sock.Start();

            //	System.out.println("it was runned");
        }catch (IOException e){
            System.out.println("Erro ao conectar um servidor:"+e.getMessage());
        }
        System.out.println("Cliente finalizado");
    }











    public void GetConnection(String IPserver) {
        try {
            while (true) {
                Socket novo = new Socket(IPserver, 4211);
                ObjectOutputStream dado = new ObjectOutputStream(novo.getOutputStream());
                dado.writeObject(this.GetToSend());
                dado.flush();
                dado.reset();
                ObjectInputStream volta = new ObjectInputStream(novo.getInputStream());
                //MemoryAccessNotice st = (MemoryAccessNotice) volta.readObject();
                //new BitmapDisplay().updateColorForAddress(st);
                SendObjectSerializable receive = (SendObjectSerializable) volta.readObject();
               // KeyboardAndDisplaySimulator.receive = receive;
                int bornRegister = key.readyBitSet(1);
                KeyboardAndDisplaySimulator.ref.updateMMIOControlAndData(KeyboardAndDisplaySimulator.RECEIVER_CONTROL, bornRegister, KeyboardAndDisplaySimulator.RECEIVER_DATA, KeyboardAndDisplaySimulator.receive.getKeyChar()&0x00000ff);
                volta.reset();
                dado.close();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public SendObjectSerializable GetToSend(){// método de envio
        // if(KeyboardAndDisplaySimulator.sender!='\u0000'){
        //     return new SendObjectSerializable(KeyboardAndDisplaySimulator.sender);
        // }
        return null;
    }
}
