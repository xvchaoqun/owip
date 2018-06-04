package domain.sys;

import java.io.Serializable;

public class SysConfig implements Serializable {
    private Integer id;

    private String xssIgnoreUri;

    private Integer uploadMaxSize;

    private String shortMsgUrl;

    private String schoolName;

    private String schoolShortName;

    private String schoolLoginUrl;

    private String schoolEmail;

    private String siteCopyright;

    private String siteHome;

    private String siteKeywords;

    private String siteDescription;

    private String siteName;

    private String siteShortName;

    private String mobilePlantformName;

    private String mobileTitle;

    private String favicon;

    private String logo;

    private String logoWhite;

    private String loginBg;

    private String loginTop;

    private String loginTopBgColor;

    private String appleIcon;

    private String screenIcon;

    private String qrLogo;

    private String loginMsg;

    private Boolean displayLoginMsg;

    private String cadreTemplateFsNote;

    private Integer loginTimeout;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXssIgnoreUri() {
        return xssIgnoreUri;
    }

    public void setXssIgnoreUri(String xssIgnoreUri) {
        this.xssIgnoreUri = xssIgnoreUri == null ? null : xssIgnoreUri.trim();
    }

    public Integer getUploadMaxSize() {
        return uploadMaxSize;
    }

    public void setUploadMaxSize(Integer uploadMaxSize) {
        this.uploadMaxSize = uploadMaxSize;
    }

    public String getShortMsgUrl() {
        return shortMsgUrl;
    }

    public void setShortMsgUrl(String shortMsgUrl) {
        this.shortMsgUrl = shortMsgUrl == null ? null : shortMsgUrl.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getSchoolShortName() {
        return schoolShortName;
    }

    public void setSchoolShortName(String schoolShortName) {
        this.schoolShortName = schoolShortName == null ? null : schoolShortName.trim();
    }

    public String getSchoolLoginUrl() {
        return schoolLoginUrl;
    }

    public void setSchoolLoginUrl(String schoolLoginUrl) {
        this.schoolLoginUrl = schoolLoginUrl == null ? null : schoolLoginUrl.trim();
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail == null ? null : schoolEmail.trim();
    }

    public String getSiteCopyright() {
        return siteCopyright;
    }

    public void setSiteCopyright(String siteCopyright) {
        this.siteCopyright = siteCopyright == null ? null : siteCopyright.trim();
    }

    public String getSiteHome() {
        return siteHome;
    }

    public void setSiteHome(String siteHome) {
        this.siteHome = siteHome == null ? null : siteHome.trim();
    }

    public String getSiteKeywords() {
        return siteKeywords;
    }

    public void setSiteKeywords(String siteKeywords) {
        this.siteKeywords = siteKeywords == null ? null : siteKeywords.trim();
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription == null ? null : siteDescription.trim();
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName == null ? null : siteName.trim();
    }

    public String getSiteShortName() {
        return siteShortName;
    }

    public void setSiteShortName(String siteShortName) {
        this.siteShortName = siteShortName == null ? null : siteShortName.trim();
    }

    public String getMobilePlantformName() {
        return mobilePlantformName;
    }

    public void setMobilePlantformName(String mobilePlantformName) {
        this.mobilePlantformName = mobilePlantformName == null ? null : mobilePlantformName.trim();
    }

    public String getMobileTitle() {
        return mobileTitle;
    }

    public void setMobileTitle(String mobileTitle) {
        this.mobileTitle = mobileTitle == null ? null : mobileTitle.trim();
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon == null ? null : favicon.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getLogoWhite() {
        return logoWhite;
    }

    public void setLogoWhite(String logoWhite) {
        this.logoWhite = logoWhite == null ? null : logoWhite.trim();
    }

    public String getLoginBg() {
        return loginBg;
    }

    public void setLoginBg(String loginBg) {
        this.loginBg = loginBg == null ? null : loginBg.trim();
    }

    public String getLoginTop() {
        return loginTop;
    }

    public void setLoginTop(String loginTop) {
        this.loginTop = loginTop == null ? null : loginTop.trim();
    }

    public String getLoginTopBgColor() {
        return loginTopBgColor;
    }

    public void setLoginTopBgColor(String loginTopBgColor) {
        this.loginTopBgColor = loginTopBgColor == null ? null : loginTopBgColor.trim();
    }

    public String getAppleIcon() {
        return appleIcon;
    }

    public void setAppleIcon(String appleIcon) {
        this.appleIcon = appleIcon == null ? null : appleIcon.trim();
    }

    public String getScreenIcon() {
        return screenIcon;
    }

    public void setScreenIcon(String screenIcon) {
        this.screenIcon = screenIcon == null ? null : screenIcon.trim();
    }

    public String getQrLogo() {
        return qrLogo;
    }

    public void setQrLogo(String qrLogo) {
        this.qrLogo = qrLogo == null ? null : qrLogo.trim();
    }

    public String getLoginMsg() {
        return loginMsg;
    }

    public void setLoginMsg(String loginMsg) {
        this.loginMsg = loginMsg == null ? null : loginMsg.trim();
    }

    public Boolean getDisplayLoginMsg() {
        return displayLoginMsg;
    }

    public void setDisplayLoginMsg(Boolean displayLoginMsg) {
        this.displayLoginMsg = displayLoginMsg;
    }

    public String getCadreTemplateFsNote() {
        return cadreTemplateFsNote;
    }

    public void setCadreTemplateFsNote(String cadreTemplateFsNote) {
        this.cadreTemplateFsNote = cadreTemplateFsNote == null ? null : cadreTemplateFsNote.trim();
    }

    public Integer getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(Integer loginTimeout) {
        this.loginTimeout = loginTimeout;
    }
}