package domain.cet;

import java.io.Serializable;

public class CetPartySchool implements Serializable {
    private Integer id;

    private Integer partySchoolId;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartySchoolId() {
        return partySchoolId;
    }

    public void setPartySchoolId(Integer partySchoolId) {
        this.partySchoolId = partySchoolId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}