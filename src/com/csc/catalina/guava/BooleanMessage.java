
package com.csc.catalina.guava;

public class BooleanMessage extends BaseGuavaMessage {

    public boolean val;

    public BooleanMessage(String id, boolean val) {
        super(id);
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }
}
