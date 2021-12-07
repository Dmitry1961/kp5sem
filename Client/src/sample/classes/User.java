package sample.classes;

import sample.patternIterator.Collection;
import sample.patternIterator.Iterator;

import java.util.ArrayList;

public class User implements Collection {
    private String idUser;
    private String username;
    private String login;
    private String password;
    private String[] log;
    //public ArrayList<String> log = new ArrayList<String>();
    public User(){};
    public User(String idUser, String username, String login, String password) {
        this.idUser = idUser;
        this.username = username;
        this.login = login;
        this.password = password;
    }

    public User(String[] log) {
        this.log = log;
    }

    public String[] getLog() {
        return log;
    }

    public void setLog(String[] log) {
        this.log = log;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Iterator getIterator() {
        return new Login();
    }

    private class Login implements Iterator{
        int index=0;


        @Override
        public boolean hasNext() {
            if(index<log.length){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            return log[index++];
        }
    }
}
