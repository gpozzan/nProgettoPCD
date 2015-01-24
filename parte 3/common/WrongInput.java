package common;

public class WrongInput extends Exception{
    private String errorString;
    public WrongInput(String es){
	errorString = es;
    }
    public String get(){
	return errorString;
    }
}
