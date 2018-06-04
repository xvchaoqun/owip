<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<c:set var="user" value="${cm:getUserById(param.userId)}"/>
<div class="row">
  <div class="col-xs-12">
    <div class="widget-box transparent">
      <div class="widget-header">
        <h4 class="widget-title lighter smaller">
          <a href="javascript:" class="hideView btn btn-xs btn-success">
            <i class="ace-icon fa fa-backward"></i>
            返回</a>
        </h4>
      </div>

          <div style="margin: 10px 20px; width: 1200px;">
          <table id="detailTable" class="table table-bordered table-striped" data-offset-top="101">
            <thead class="multi">
            <tr>
              <th colspan="7"><span style="padding-left: 20px;font-size: 18px;font-weight: bolder;"><span style="color:#033975;font-size: 25px;">${user.realname}</span> 同志得票详情（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</span></th>
            </tr>
            <tr>
              <th width="80">序号</th>
              <th>院系级党委、党总支、直属党支部</th>
              <th style="width:180px">是否推荐</th>
              <th style="width:180px">支部数</th>
              <th style="width:180px">推荐支部数</th>
              <th style="width:180px">未推荐支部数</th>
              <th style="width:180px">推荐比例</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="total" value="0"/>
            <c:set var="rc" value="0"/>
            <c:set var="nrc" value="0"/>
            <c:set var="actualMemberCount" value="0"/>
            <c:forEach items="${beans}" var="bean" varStatus="vs">
              <c:set var="recommendBranchCount" value="${bean.isDirectBranch?(bean.isRecommend?1:0):(fn:length(bean.branchIds))}"/>
              <c:set var="notRecommendBranchCount" value="${bean.isDirectBranch?(bean.isRecommend?0:1):(fn:length(bean.notbranchIds))}"/>
              <c:set var="totalBranchCount" value="${bean.isDirectBranch?1:(fn:length(bean.totalBranchIds))}"/>
              <c:set var="total" value="${total+totalBranchCount}"/>
              <c:set var="rc" value="${rc+recommendBranchCount}"/>
              <c:set var="nrc" value="${nrc+notRecommendBranchCount}"/>
              <tr data-party-id="${bean.partyId}">
                <td>${vs.count}</td>
                <td style="text-align: left;white-space: nowrap;">${bean.partyName}</td>
                <td>
                  <span class="label ${bean.isRecommend?"label-success":"label-danger"}">
                ${bean.isRecommend?"是":"否"}
                  </span>
                </td>
                <td>

                  <c:if test="${!bean.isDirectBranch && totalBranchCount>0}">
                  <a href="javascript:;" class="branchDetail" data-type="1" data-branch-ids="${cm:join(bean.totalBranchIds, ",")}">${totalBranchCount}</a>
                  </c:if>
                  <c:if test="${bean.isDirectBranch || totalBranchCount==0}">
                          ${totalBranchCount}
                  </c:if>
                </td>
                <td>
                  <c:if test="${!bean.isDirectBranch && recommendBranchCount>0}">
                  <a href="javascript:;" class="branchDetail" data-type="2" data-branch-ids="${cm:join(bean.branchIds, ",")}">${recommendBranchCount}</a>
                  </c:if>
                  <c:if test="${bean.isDirectBranch || recommendBranchCount==0}">
                    ${recommendBranchCount}
                  </c:if>
                </td>
                <td>
                  <c:if test="${!bean.isDirectBranch && notRecommendBranchCount>0}">
                  <a href="javascript:;" class="branchDetail" data-type="3" data-branch-ids="${cm:join(bean.notbranchIds, ",")}">${notRecommendBranchCount}</a>
                  </c:if>
                  <c:if test="${bean.isDirectBranch || notRecommendBranchCount==0}">
                    ${notRecommendBranchCount}
                  </c:if>
                </td>
                <td>
                  <c:if test="${totalBranchCount>0}">
                  <fmt:formatNumber value="${recommendBranchCount/totalBranchCount}" type="percent"
                                      pattern="#0.00%"/>
                  </c:if>
                </td>
              </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
              <th colspan="3" style="text-align: right">汇总</th>
              <th>${total}</th>
              <th>${rc}</th>
              <th>${nrc}</th>
             <th>
               <c:if test="${total>0}">
               <fmt:formatNumber value="${rc/total}" type="percent"
                                  pattern="#0.00%"/>
               </c:if>
             </th>
            </tr>
            </tfoot>
          </table>
          </div>
        </div>
  </div>
</div>
<style>
  .table tr th,
  .table tbody tr td{
    text-align: center;
  }
</style>
<script>
  $(".branchDetail").click(function(){

      var branchIds = $(this).data("branch-ids");
      var partyId = $(this).closest("tr").data("party-id");
      var type = $(this).data("type");
      $.loadModal("${ctx}/pcsOw_recommend_detail_branchs?type="+type+"&partyId="+partyId+"&branchIds=" + branchIds);

  });
  stickheader($("#detailTable"));
  $(window).resize();
</script>