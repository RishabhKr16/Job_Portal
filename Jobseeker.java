package jobportal;

import java.io.Serializable;

public class Jobseeker implements Serializable{
  private String Name;
    private String userName;
    private char[] password; 
    private String phone;
    private String email;
    private String qual;
    
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
    
    String getQual(){
        return qual;
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
    
    void setQual(String qual){
        this.qual=qual;
    }
}
