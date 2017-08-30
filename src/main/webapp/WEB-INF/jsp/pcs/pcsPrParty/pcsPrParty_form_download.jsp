<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>表格下载</h3>
  </div>
  <div class="modal-body">
    <c:if test="${param.stage==PCS_STAGE_FIRST}">
    <div style="font-size: 16px;text-indent: 2em">分党委请下载以下表格（点击下面的链接下载），并打印发给支部。</div>
    <div style="padding: 30px; text-align: left"><a href="${ctx}/attach?code=af_pcs_pr_form_1">
      <i class="fa fa-file-excel-o fa-2x"></i> 附表1. 代表候选人推荐票（党员推荐用，报党支部）.xlsx</a></div>
    <div style="padding: 0 0 30px 30px; text-align: left"><a href="${ctx}/attach?code=af_pcs_pr_form_2">
      <i class="fa fa-file-excel-o fa-2x"></i> 附表2. 党支部酝酿代表候选人提名汇总表（党支部汇总用表，报分党委）.xlsx</a></div>
    </c:if>
  <c:if test="${param.stage==PCS_STAGE_SECOND}">
    <div style="font-size: 16px;text-indent: 2em">分党委请下载以下表格（点击下面的链接下载），并打印发给支部。</div>
    <div style="padding: 30px; text-align: center"><a href="${ctx}/pcsParty_export?file=1-1&stage=${PCS_STAGE_FIRST}&type=${PCS_USER_TYPE_DW}">
      <i class="fa fa-file-excel-o fa-2x"></i> 附表1-1. 党委委员候选人推荐提名汇总表（党支部用）.xlsx</a></div>
    <div style="padding: 0 30px 30px 30px; text-align: center"><a href="${ctx}/pcsParty_export?file=1-1&stage=${PCS_STAGE_FIRST}&type=${PCS_USER_TYPE_JW}">
      <i class="fa fa-file-excel-o fa-2x"></i> 附表1-2. 纪委委员候选人推荐提名汇总表（党支部用）.xlsx</a></div>
  </c:if>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-info">关闭</a></div>