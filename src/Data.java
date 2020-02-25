
// All our data (variables, etc.) will be of this generic "data" type

public class Data {

    Object data;

    public Data(Object data){
        this.data = data;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
