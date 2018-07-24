package ru.mediasoft.unipolls;

import java.util.ArrayList;
import java.util.List;

public class UserInfoModel {
    String username;
    Scopes scopes;
    String first_name;
    String last_name;
    String account_type;
    String language;
    String email_verified;
    String email;
    String href;
    String date_last_login;
    List<String> sso_connections;
    String date_created;
    String id;

    private class Scopes{
        List<String> available;
        List<String> granted;
    }
}
