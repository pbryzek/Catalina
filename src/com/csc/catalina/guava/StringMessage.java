
package com.csc.catalina.guava;

public class StringMessage extends BaseGuavaMessage {

    public String message;

    public StringMessage(String id, String message) {
        super(id);
        this.message = message;
    }

    public StringMessage(String id, String uniqueId, String message) {
        super(id, uniqueId);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
