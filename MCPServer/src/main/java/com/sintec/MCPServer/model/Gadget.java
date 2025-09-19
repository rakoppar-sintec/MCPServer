package com.sintec.MCPServer.model;

import java.util.Map;

public class Gadget {
    private String id;
    private String name;
    private Map<String, Object> data;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
}

