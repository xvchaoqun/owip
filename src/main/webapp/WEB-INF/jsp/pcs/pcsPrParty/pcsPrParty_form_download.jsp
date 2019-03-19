<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp" %>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>表格下载</h3>
  </div>
  <div class="modal-body">
    <c:if test="${param.stage==PCS_STAGE_FIRST}">
    <div style="font-size: 16px;text-indent: 2em">${_p_partyName}请下载以下两个推荐提名表，打印后发给各党支部。各党支部全体党员充分酝酿两委委员候选人初步人选，达成统一意见后填写推荐提名表。</div>
    <div style="padding: 30px 0 30px 30px; text-align: left"><a href="${ctx}/attach?code=af_pcs_pr_form_1">
      <i class="fa fa-file-excel-o fa-2x"></i> 党员代表大会代表候选人推荐票（党员推荐使用，交由所在党支部汇总）.xlsx</a></div>
    <div style="padding: 0 0 30px 30px; text-align: left"><a href="${ctx}/attach?code=af_pcs_pr_form_2">
      <i class="fa fa-file-excel-o fa-2x"></i> 党支部酝酿代表候选人提名汇总表（党支部汇总用表，报${_p_partyName}、党总支）.xlsx</a></div>
    </c:if>
  <c:if test="${param.stage==PCS_STAGE_SECOND}">
    <div style="font-size: 16px;text-indent: 2em">${_p_partyName}请下载以下两个表格（“二下”名单），打印后发给各党支部。各党支部广泛征求全体党员意见。</div>
    <div style="padding: 30px 0 30px 10px; text-align: left"><a href="${ctx}/pcsPrParty_export?file=1">
      <i class="fa fa-file-excel-o fa-2x"></i> 党员代表大会代表候选人推荐票（党员推荐使用，交由所在党支部汇总）.xlsx</a></div>
    <div style="padding: 0 0px 30px 10px; text-align: left"><a href="${ctx}/pcsPrParty_export?file=2">
      <i class="fa fa-file-excel-o fa-2x"></i> 党支部酝酿代表候选人提名汇总表（党支部汇总用表，报${_p_partyName}、党总支）.xlsx</a></div>
  </c:if>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-info">关闭</a></div>