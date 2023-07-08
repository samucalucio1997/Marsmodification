package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;

public class SyscallSend extends AbstractSyscall{
    public SyscallSend() {super(37, "Sender");}

    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {

    }
}
