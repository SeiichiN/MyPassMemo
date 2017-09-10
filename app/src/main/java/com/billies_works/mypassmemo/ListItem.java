package com.billies_works.mypassmemo;

/**
 * Created by se-ichi on 17/09/03.
 */

public class ListItem {
    private long id = 0;
    private String library = null;
    //private String loginId = null;
    //private String password = null;
    //private String memo = null;

    public long getId() { return id; }
    public String getLibrary() { return library; }
    //public String getLoginId() { return loginId; }
    //public String getPassword() { return password;}
    //public String getMemo() { return memo;}

    public void setId(long id) { this.id = id; }
    public void setLibrary(String library) { this.library = library; }
    //public void setLoginId(String loginId) { this.loginId = loginId;}
    //public void setPassword(String password) { this.password = password; }
    //public void setMemo(String memo) { this.memo = memo; }

}
