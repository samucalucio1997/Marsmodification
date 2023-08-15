package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.tools.socket.Server;
import mars.tools.BitmapDisplay;


public class SyscallSend extends AbstractSyscall{
    public SyscallSend() {super(37, "Sender");}

    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {
        if(Server.getServer().getMapi()!=0){
            
        }       
    }
}
