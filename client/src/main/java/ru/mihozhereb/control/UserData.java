package ru.mihozhereb.control;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

public class UserData {
    private static String userLogin;
    private static String userPassword;

    public static String getUserLogin() {
        return userLogin;
    }

    public static void setUserLogin(String userLogin) {
        UserData.userLogin = userLogin;
    }

    public static String getUserPassword() {
        return DigestUtils.sha3_224Hex(userPassword);
    }

    public static void setUserPassword(String userPassword) {
        UserData.userPassword = userPassword;
    }

    public static String register(String login, String password, UDPClient client) {
        try {
            Response resp = client.sendRequest(new Request(
                    "register", "", null, login, DigestUtils.sha3_224Hex(password)
            ));
            return resp.response();
        } catch (IOException e) {
            return "Connection error. Retry later...";
        }
    }
}
