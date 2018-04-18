<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}报表</h3>
</div>
<div class="modal-body">
    <div class="bs-callout bs-callout-warning">
        <h4>表1：北京师范大学党费缴纳报表.xlsx</h4>
        <a href="${ctx}/pmd/pmdOw_export?monthId=${pmdMonth.id}"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
    <div class="bs-callout bs-callout-warning">
        <h4>表2：北京师范大学各党委线上缴纳党费总额.xlsx</h4>
        <a href="${ctx}/pmd/pmdOw_parties_export?monthId=${pmdMonth.id}&isDetail=0"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
    <div class="bs-callout bs-callout-warning">
        <h4>表3：北京师范大学各党委线上缴纳党费明细.xlsx</h4>
        <a href="${ctx}/pmd/pmdOw_parties_export?monthId=${pmdMonth.id}&isDetail=1"
           class="btn btn-lg btn-outline"><i class="fa fa-download"></i> 下载汇总表</a>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>