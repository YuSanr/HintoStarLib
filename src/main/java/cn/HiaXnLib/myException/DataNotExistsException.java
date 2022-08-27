package cn.HiaXnLib.myException;

public class DataNotExistsException extends Exception {

    public DataNotExistsException(){

        super();

    }
    public DataNotExistsException(String msg){
        super(msg);
    }

}
