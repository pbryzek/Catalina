
package com.csc.catalina.guava;

public class BaseGuavaMessage {
    private final String id;

    private String uniqueId;

    public BaseGuavaMessage(String id) {
        this.id = id;
    }

    public BaseGuavaMessage(String id, String uniqueId) {
        this.id = id;
        this.uniqueId = uniqueId;
    }

    public String getMessageId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

}
