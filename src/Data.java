
// All our data (variables, etc.) will be of this generic "data" type

public class Data {

    Object data;

    public Data(Object data){
        this.data = data;
    }

    public Double toDouble(){
        return (Double) data;
    }

    public Boolean toBoolean(){
        return (Boolean) data;
    }

    // For for-loop temp vars
    public void add1(){
        this.data = (Double) this.data + 1;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
