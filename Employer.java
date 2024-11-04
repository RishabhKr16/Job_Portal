
package jobportal;
import java.io.*;

public class Employer implements Serializable{
    
    private String Name;
    private String userName;
    private char[] password;
    private String phone;
    private String email;
    
    String getName(){
        return Name;
    }
    String getUserName(){
        return userName;
    }
    String getPhone(){
        return phone;
    }
    char[] getPassword(){
      
        return password;
    }
    String getEmail(){
        return email;
    }
    void setName(String name){
        this.Name=name;
    }
    void setUserName(String name){
        this.userName=name;
    }    
    void setPhone(String phone){
        this.phone=phone;
    }
    void setPassword(char[] pwd){
        password=pwd;
    }
    void setEmail(String email){
        this.email=email;
    }
}
