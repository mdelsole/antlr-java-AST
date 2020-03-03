
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

    public boolean equals (Object obj) {
        if (data == obj) return true;
        if (data == null || this.getClass() != obj.getClass()) return false;
        Data compare = (Data) obj ;
        return this.data.equals(compare.data);
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
