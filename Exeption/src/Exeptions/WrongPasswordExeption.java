package Exeptions;

public class WrongPasswordExeption extends Exception {
    public WrongPasswordExeption(){
        super("Wrong password");
    }
}
