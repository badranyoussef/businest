package org.authentication;

public class UserAuthentication {

    public boolean login(String username, String password) {
        if (username == "admin" && password == "admin") {
            return true;
        } else {
            return false;
        }
    }

    public boolean logout() {
        return true;
    }
}
