package domain.sys;

import java.io.Serializable;
import java.util.Date;

public class SysConfig implements Serializable {
    private Integer id;

    private String schoolName;

    private String schoolShortName;

    private String schoolLoginUrl;

    private Date termStartDate;

    private Date termEndDate;

    private String siteCopyright;

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

    private Integer loginTimeout;

    private String city;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }

    public String getSiteCopyright() {
        return siteCopyright;
    }

    public void setSiteCopyright(String siteCopyright) {
        this.siteCopyright = siteCopyright == null ? null : siteCopyright.trim();
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

    public Integer getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(Integer loginTimeout) {
        this.loginTimeout = loginTimeout;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }
}