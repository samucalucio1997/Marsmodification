package mars.tools.socket;

import java.io.Serializable;

public class SendObjectSerializable implements Serializable {
    private int obj;

    public SendObjectSerializable(int obj) {
        this.obj=obj;
    }

    public int getObj() {
        return obj;
    }

    public void setObj(int obj) {
        this.obj = obj;
    }
}
