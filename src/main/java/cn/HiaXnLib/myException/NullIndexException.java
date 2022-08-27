package cn.HiaXnLib.myException;

public class NullIndexException extends Exception{

    public NullIndexException(){
        super("Lack of index settings ");
    }
    public NullIndexException(String str){
        super(str);
    }

}
