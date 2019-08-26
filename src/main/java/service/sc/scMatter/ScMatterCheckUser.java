package service.sc.scMatter;

import domain.sc.scMatter.ScMatterCheckItem;

public class ScMatterCheckUser extends ScMatterCheckItem {

    private String realname;
    private String code;

    private String scRecordCode;
    private String scRecordCodes;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScRecordCode() {
        return scRecordCode;
    }

    public void setScRecordCode(String scRecordCode) {
        this.scRecordCode = scRecordCode;
    }

    public String getScRecordCodes() {
        return scRecordCodes;
    }

    public void setScRecordCodes(String scRecordCodes) {
        this.scRecordCodes = scRecordCodes;
    }
}
