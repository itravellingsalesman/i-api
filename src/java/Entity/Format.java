package Entity;

public class Format {
    private boolean success;
    private String message;
    private Object data;
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean Success) {
        this.success = Success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object Data) {
        this.data = Data;
    }   
}