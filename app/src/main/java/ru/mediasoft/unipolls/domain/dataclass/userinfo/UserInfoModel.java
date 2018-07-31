package ru.mediasoft.unipolls.domain.dataclass.userinfo;

import java.util.List;

public class UserInfoModel {
    public String username;
    public Scopes scopes;
    public String first_name;
    public String last_name;
    public String account_type;
    public String language;
    public String email_verified;
    public String email;
    public String href;
    public String date_last_login;
    public List<String> sso_connections;
    public String date_created;
    public String id;

    public class Scopes{
        public List<String> available;
        public List<String> granted;
    }
}