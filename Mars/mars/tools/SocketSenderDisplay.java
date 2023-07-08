package mars.tools;

import javax.swing.*;

public class SocketSenderDisplay extends AbstractMarsToolAndApplication{
    public static String heading = "SocketS";
    private JPanel Panel;
    private JButton Client;
    private JButton Server;
    private JTextArea ServerIP; 
    public static int memReceiver;//memoria que será escutada
    public static int memSender; // memoria que será enviada no socket
    /**
     * Simple constructor
     *
     * @param title   String containing title bar text
     * @param heading
     */
    protected SocketSenderDisplay(String title, String heading) {
        super(title, heading);
    }
    
    public static void main(String[] args){
           new SocketSenderDisplay(heading, heading + "Aplications").go();
    }
    protected JComponent GetPainel(){
        Panel = new JPanel();
        Client = new JButton("Client");
        Server = new JButton("Server");
        
        return Panel;
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
