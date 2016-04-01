package bean.m;

/**
 * Created by fafa on 2016/3/31.
 */
public class Breadcrumb {

    private String url;
    private String name;
    private String href;
    private String cls;
    private String target;

    public Breadcrumb(String name) {
        this.name = name;
    }

    public Breadcrumb(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Breadcrumb(String url, String name, String href, String cls, String target) {
        this.url = url;
        this.name = name;
        this.href = href;
        this.cls = cls;
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
