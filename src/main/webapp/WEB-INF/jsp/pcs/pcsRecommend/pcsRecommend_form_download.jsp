<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>表格下载</h3>
  </div>
  <div class="modal-body">
    <c:if test="${param.stage==PCS_STAGE_FIRST}">
    <div style="font-size: 16px;text-indent: 2em">分党委请下载以下两个推荐提名表，打印后发给各党支部。各党支部全体党员充分酝酿两委委员候选人初步人选，达成统一意见后填写推荐提名表。</div>
    <div style="padding: 30px; text-align: center"><a href="${ctx}/attach?code=af_pcs_form_1_1">
      <i class="fa fa-file-excel-o fa-2x"></i> 党委委员候选人初步人选推荐提名表（党支部用）.xlsx</a></div>
    <div style="padding: 0 30px 30px 30px; text-align: center"><a href="${ctx}/attach?code=af_pcs_form_1_2">
      <i class="fa fa-file-excel-o fa-2x"></i> 纪委委员候选人初步人选推荐提名表（党支部用）.xlsx</a></div>
    </c:if>
  <c:if test="${param.stage==PCS_STAGE_SECOND}">
    <div style="font-size: 16px;text-indent: 2em">分党委请下载以下表格（点击下面的链接下载），并打印发给支部。</div>
    <div style="padding: 30px; text-align: center"><a href="${ctx}/pcsParty_export?file=1-1&stage=${PCS_STAGE_FIRST}&type=${PCS_USER_TYPE_DW}">
      <i class="fa fa-file-excel-o fa-2x"></i> 附表1-1. 党委委员候选人推荐提名汇总表（党支部用）.xlsx</a></div>
    <div style="padding: 0 30px 30px 30px; text-align: center"><a href="${ctx}/pcsParty_export?file=1-1&stage=${PCS_STAGE_FIRST}&type=${PCS_USER_TYPE_JW}">
      <i class="fa fa-file-excel-o fa-2x"></i> 附表1-2. 纪委委员候选人推荐提名汇总表（党支部用）.xlsx</a></div>
  </c:if>
    <c:if test="${param.stage==PCS_STAGE_THIRD}">
      <div style="font-size: 16px;text-indent: 2em">分党委请下载以下表格（点击下面的链接下载），并打印发给支部。</div>
      <div style="padding: 30px; text-align: center"><a href="${ctx}/pcsParty_export?file=1-1&stage=${PCS_STAGE_SECOND}&type=${PCS_USER_TYPE_DW}">
        <i class="fa fa-file-excel-o fa-2x"></i> 附表1-1. 党委委员候选人推荐提名汇总表（党支部用）.xlsx</a></div>
      <div style="padding: 0 30px 30px 30px; text-align: center"><a href="${ctx}/pcsParty_export?file=1-1&stage=${PCS_STAGE_SECOND}&type=${PCS_USER_TYPE_JW}">
        <i class="fa fa-file-excel-o fa-2x"></i> 附表1-2. 纪委委员候选人推荐提名汇总表（党支部用）.xlsx</a></div>
    </c:if>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-info">关闭</a></div>