
package com.csc.catalina.guava;

public class IntMessage extends BaseGuavaMessage {

    public int data;

    public IntMessage(String id, int data) {
        super(id);
        this.data = data;
    }

    public IntMessage(String id, String uniqueId, int data) {
        super(id, uniqueId);
        this.data = data;
    }

    public int getData() {
        return data;
    }
}
