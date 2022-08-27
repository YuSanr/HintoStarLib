package cn.HiaXnLib.myException;

public class SqlNotConfig extends Exception{

    public SqlNotConfig(){
        super("Database must be configured ");
    }
    public SqlNotConfig(String msg){
        super(msg);
    }

}
