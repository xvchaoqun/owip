package domain.crs;

import java.io.Serializable;

public class CrsApplicantViewWithBLOBs extends CrsApplicantView implements Serializable {
    private String career;

    private String report;

    private static final long serialVersionUID = 1L;

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career == null ? null : career.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }
}