package jobs4u.base.notificationmanagement.domain;

import java.io.Serializable;

public class MessageDTO implements Serializable {



    private int DATA1_LEN_L;
    private int DATA1_LEN_M;

    private String data;

    public MessageDTO(String data, int dataL, int dataM) {
        this.DATA1_LEN_L = dataL;
        this.DATA1_LEN_M = dataM;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                '}';
    }

    public byte[] getBytes() {
        return data.getBytes();
    }
}