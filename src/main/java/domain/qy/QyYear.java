package domain.qy;

import java.io.Serializable;

public class QyYear implements Serializable {
    private Integer id;

    private Integer year;

    private String planPdf;

    private String planPdfName;

    private String planWord;

    private String planWordName;

    private String resultPdf;

    private String resultPdfName;

    private String resultWord;

    private String resultWordName;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPlanPdf() {
        return planPdf;
    }

    public void setPlanPdf(String planPdf) {
        this.planPdf = planPdf == null ? null : planPdf.trim();
    }

    public String getPlanPdfName() {
        return planPdfName;
    }

    public void setPlanPdfName(String planPdfName) {
        this.planPdfName = planPdfName == null ? null : planPdfName.trim();
    }

    public String getPlanWord() {
        return planWord;
    }

    public void setPlanWord(String planWord) {
        this.planWord = planWord == null ? null : planWord.trim();
    }

    public String getPlanWordName() {
        return planWordName;
    }

    public void setPlanWordName(String planWordName) {
        this.planWordName = planWordName == null ? null : planWordName.trim();
    }

    public String getResultPdf() {
        return resultPdf;
    }

    public void setResultPdf(String resultPdf) {
        this.resultPdf = resultPdf == null ? null : resultPdf.trim();
    }

    public String getResultPdfName() {
        return resultPdfName;
    }

    public void setResultPdfName(String resultPdfName) {
        this.resultPdfName = resultPdfName == null ? null : resultPdfName.trim();
    }

    public String getResultWord() {
        return resultWord;
    }

    public void setResultWord(String resultWord) {
        this.resultWord = resultWord == null ? null : resultWord.trim();
    }

    public String getResultWordName() {
        return resultWordName;
    }

    public void setResultWordName(String resultWordName) {
        this.resultWordName = resultWordName == null ? null : resultWordName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}