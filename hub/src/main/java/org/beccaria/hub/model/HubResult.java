package org.beccaria.hub.model;

public class HubResult {
    public final static String SUCCESS = "success";
    public final static String ERROR = "error";

    private String result;
    private String message;
    private String id;

    public HubResult(){
        this.result = ERROR;
        this.message = "none";
        this.id = "unknown";
    }
    public HubResult setResult(String result){
        this.result = result;
        return this;
    }
    public HubResult setMessage(String message){
        this.message = message;
        return this;
    }
    public HubResult setId(String id){
        this.id = id;
        return this;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();

        buffer.append("{ ");
        buffer.append("\"id\": \"").append(id).append("\", ");
        buffer.append("\"result\": \"").append(result).append("\", ");
        buffer.append("\"message\": \"").append(message).append("\" ");
        buffer.append(" }");

        return  buffer.toString();
    }
}
