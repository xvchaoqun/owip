package domain.party;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartyStaticView implements Serializable {
    private Integer id;

    private String name;

    private BigDecimal bks;

    private BigDecimal ss;

    private BigDecimal bs;

    private BigDecimal student;

    private BigDecimal positiveBks;

    private BigDecimal positiveSs;

    private BigDecimal positiveBs;

    private BigDecimal positiveStudent;

    private BigDecimal teacher;

    private BigDecimal teacherRetire;

    private BigDecimal teacherTotal;

    private BigDecimal positiveTeacher;

    private BigDecimal positiveTeacherRetire;

    private BigDecimal positiveTeacherTotal;

    private BigDecimal bksBranch;

    private BigDecimal ssBranch;

    private BigDecimal bsBranch;

    private BigDecimal sbBranch;

    private BigDecimal bsbBranch;

    private BigDecimal studentBranchTotal;

    private BigDecimal teacherBranch;

    private BigDecimal retireBranch;

    private BigDecimal teacherBranchTotal;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getBks() {
        return bks;
    }

    public void setBks(BigDecimal bks) {
        this.bks = bks;
    }

    public BigDecimal getSs() {
        return ss;
    }

    public void setSs(BigDecimal ss) {
        this.ss = ss;
    }

    public BigDecimal getBs() {
        return bs;
    }

    public void setBs(BigDecimal bs) {
        this.bs = bs;
    }

    public BigDecimal getStudent() {
        return student;
    }

    public void setStudent(BigDecimal student) {
        this.student = student;
    }

    public BigDecimal getPositiveBks() {
        return positiveBks;
    }

    public void setPositiveBks(BigDecimal positiveBks) {
        this.positiveBks = positiveBks;
    }

    public BigDecimal getPositiveSs() {
        return positiveSs;
    }

    public void setPositiveSs(BigDecimal positiveSs) {
        this.positiveSs = positiveSs;
    }

    public BigDecimal getPositiveBs() {
        return positiveBs;
    }

    public void setPositiveBs(BigDecimal positiveBs) {
        this.positiveBs = positiveBs;
    }

    public BigDecimal getPositiveStudent() {
        return positiveStudent;
    }

    public void setPositiveStudent(BigDecimal positiveStudent) {
        this.positiveStudent = positiveStudent;
    }

    public BigDecimal getTeacher() {
        return teacher;
    }

    public void setTeacher(BigDecimal teacher) {
        this.teacher = teacher;
    }

    public BigDecimal getTeacherRetire() {
        return teacherRetire;
    }

    public void setTeacherRetire(BigDecimal teacherRetire) {
        this.teacherRetire = teacherRetire;
    }

    public BigDecimal getTeacherTotal() {
        return teacherTotal;
    }

    public void setTeacherTotal(BigDecimal teacherTotal) {
        this.teacherTotal = teacherTotal;
    }

    public BigDecimal getPositiveTeacher() {
        return positiveTeacher;
    }

    public void setPositiveTeacher(BigDecimal positiveTeacher) {
        this.positiveTeacher = positiveTeacher;
    }

    public BigDecimal getPositiveTeacherRetire() {
        return positiveTeacherRetire;
    }

    public void setPositiveTeacherRetire(BigDecimal positiveTeacherRetire) {
        this.positiveTeacherRetire = positiveTeacherRetire;
    }

    public BigDecimal getPositiveTeacherTotal() {
        return positiveTeacherTotal;
    }

    public void setPositiveTeacherTotal(BigDecimal positiveTeacherTotal) {
        this.positiveTeacherTotal = positiveTeacherTotal;
    }

    public BigDecimal getBksBranch() {
        return bksBranch;
    }

    public void setBksBranch(BigDecimal bksBranch) {
        this.bksBranch = bksBranch;
    }

    public BigDecimal getSsBranch() {
        return ssBranch;
    }

    public void setSsBranch(BigDecimal ssBranch) {
        this.ssBranch = ssBranch;
    }

    public BigDecimal getBsBranch() {
        return bsBranch;
    }

    public void setBsBranch(BigDecimal bsBranch) {
        this.bsBranch = bsBranch;
    }

    public BigDecimal getSbBranch() {
        return sbBranch;
    }

    public void setSbBranch(BigDecimal sbBranch) {
        this.sbBranch = sbBranch;
    }

    public BigDecimal getBsbBranch() {
        return bsbBranch;
    }

    public void setBsbBranch(BigDecimal bsbBranch) {
        this.bsbBranch = bsbBranch;
    }

    public BigDecimal getStudentBranchTotal() {
        return studentBranchTotal;
    }

    public void setStudentBranchTotal(BigDecimal studentBranchTotal) {
        this.studentBranchTotal = studentBranchTotal;
    }

    public BigDecimal getTeacherBranch() {
        return teacherBranch;
    }

    public void setTeacherBranch(BigDecimal teacherBranch) {
        this.teacherBranch = teacherBranch;
    }

    public BigDecimal getRetireBranch() {
        return retireBranch;
    }

    public void setRetireBranch(BigDecimal retireBranch) {
        this.retireBranch = retireBranch;
    }

    public BigDecimal getTeacherBranchTotal() {
        return teacherBranchTotal;
    }

    public void setTeacherBranchTotal(BigDecimal teacherBranchTotal) {
        this.teacherBranchTotal = teacherBranchTotal;
    }
}