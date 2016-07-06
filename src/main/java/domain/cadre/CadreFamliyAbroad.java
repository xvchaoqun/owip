package domain.cadre;

import java.io.Serializable;
import java.util.Date;

public class CadreFamliyAbroad implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Integer famliyId;

    private Integer type;

    private Date abroadTime;

    private String country;

    private String city;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getFamliyId() {
        return famliyId;
    }

    public void setFamliyId(Integer famliyId) {
        this.famliyId = famliyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getAbroadTime() {
        return abroadTime;
    }

    public void setAbroadTime(Date abroadTime) {
        this.abroadTime = abroadTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }
}