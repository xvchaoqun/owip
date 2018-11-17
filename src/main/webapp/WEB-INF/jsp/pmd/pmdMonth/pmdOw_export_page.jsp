<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}报表</h3>
</div>
<div class="modal-body" style="padding: 0 15px">
    <div class="bs-callout bs-callout-warning">
        <h4>表1：${_sysConfig.schoolName}党费缴纳报表.xlsx</h4>
        <a href="javascript:;" data-url="${ctx}/pmd/pmdOw_export?monthId=${pmdMonth.id}"
           class="downloadBtn btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
    <div class="bs-callout bs-callout-warning">
        <h4>表2：${_sysConfig.schoolName}各党委线上缴纳党费总额.xlsx</h4>
        <a href="javascript:;" data-url="${ctx}/pmd/pmdOw_parties_export?monthId=${pmdMonth.id}&isDetail=0"
           class="downloadBtn btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
    <div class="bs-callout bs-callout-warning">
        <h4>表3：${_sysConfig.schoolName}各党委线上缴纳党费明细.xlsx</h4>
        <a href="javascript:;" data-url="${ctx}/pmd/pmdOw_parties_export?monthId=${pmdMonth.id}&isDetail=1"
           class="downloadBtn btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
    <div class="bs-callout bs-callout-warning">
        <h4>表4：${_sysConfig.schoolName}线上缴纳党费明细.xlsx</h4>
        <a href="javascript:;" data-url="${ctx}/pmd/pmdOw_pay_export?monthId=${pmdMonth.id}"
           class="downloadBtn btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载明细表</a>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>