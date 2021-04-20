<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/parttime/constants.jsp" %>
<div class="tabbable">
  <ul class="preview title nav nav-tabs padding-12 tab-color-blue background-blue" style="padding-right: 20px">

      <div class="buttons" style="float: left;margin-bottom: 8px">
        <a href="javascript:;" class="hideView btn btn-sm btn-success">
          <i class="ace-icon fa fa-backward"></i>
          返回
        </a>
        <c:if test="${param.type=='approval' && !parttimeApply.isDeleted && fn:length(approvalResultMap)>0}">
        <button id="agree" style="margin-left: 220px; margin-right: 30px;"
           class="btn btn-primary btn-sm"><i class="fa fa-check"></i> 同意申请</button>
          <button id="disagree" class="btn btn-danger btn-sm">
          <i class="fa fa-times"></i>  不同意申请</button>
        </c:if>
      </div>
      <div class="buttons pull-right">

      <%--  <c:if test="${needExport}">
        <button class="linkBtn btn btn-primary btn-sm"
                data-url="${ctx}/cla/claApply_export?applyId=${claApply.id}">
          <i class="fa fa-file-excel-o"></i>  导出</button>
        </c:if>--%>
        <button class="openView btn btn-info btn-sm"
                data-url="${ctx}/parttime/parttimeApply_yearLogs?id=${parttimeApply.id}&type=${justView?'user':param.type}&approvalTypeId=${param.approvalTypeId}"
                style="margin-left: 10px">
          <i class="fa fa-history"></i>  本年度请假记录</button>

      </div>
  </ul>

  <div class="tab-content">
    <div class="tab-pane in active">
      <div class="preview">
      <table class="table table-bordered table-striped">
        <tbody>
        <tr>
          <td rowspan="3" class="bg-right">申请人情况</td>
          <td class="bg-right">姓名</td>
          <td class="bg-left">${sysUser.realname}</td>
          <td class="bg-right">工作证号</td>
          <td class="bg-left">${sysUser.code}</td>
          <td class="bg-right">性别</td>
          <td class="bg-left">${GENDER_MAP.get(sysUser.gender)}</td>
        </tr>
        <tr>
          <td class="bg-right">身份证号</td>
          <td colspan="3" class="bg-left"><t:mask src="${sysUser.idcard}" type="idCard"/></td>
          <td class="bg-right">联系电话</td>
          <td class="bg-left"><t:mask src="${cadreMobile}" type="mobile"/></td>
        </tr>
        <tr>
          <td class="bg-right" nowrap>工作单位及职务</td>
          <td colspan="3" class="bg-left">${cadre.title}</td>
          <td class="bg-right">行政级别</td>
          <td class="bg-left">${cm:getMetaType(cadre.adminLevel).name} </td>
        </tr>
        <tr>
          <td rowspan="9" class="bg-right" nowrap>申报情况</td>
          <td rowspan="2" class="bg-right">是否首次</td>
          <td colspan="5" class="bg-left">
            <c:forEach items="${PARTTIME_APPLY_FIRST_MAP}" var="type">
              <input type="checkbox" class="big chkBox" ${type.key==(parttimeApply.isFirst?1:0)?"checked":""} disabled> ${type.value}
              &nbsp;&nbsp;&nbsp;&nbsp;
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td colspan="5" class="bg-left">兼职时间： ${cm:formatDate(parttimeApply.startTime, "yyyy年 MM月 dd日")}  至  ${cm:formatDate(parttimeApply.endTime, "yyyy年 MM月 dd日")}</td>
        </tr>
        <tr>
          <td class="bg-right">是否有国境外背景</td>
          <td colspan="5" class="bg-left">
            <c:forEach items="${PARTTIME_APPLY_BACKGROUND_MAP}" var="type">
              <input type="checkbox" class="big chkBox" ${type.key==(parttimeApply.background?1:0)?"checked":""} disabled> ${type.value}
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td class="bg-right">是否取酬</td>
          <td colspan="5" class="bg-left">
            <c:forEach items="${PARTTIME_APPLY_HAS_PAY_MAP}" var="type">
              <input type="checkbox" class="big chkBox" ${type.key==(parttimeApply.hasPay?1:0)?"checked":""} disabled> ${type.value}
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td class="bg-right">取酬金额</td>
          <td colspan="5" class="bg-left">${parttimeApply.balance}</td>
        </tr>
        <tr>
          <td class="bg-right">申请理由
          </td>
          <td colspan="5" class="bg-left">${parttimeApply.reason}</td>
        </tr>

        <tr>
          <td class="bg-right">备注</td>
          <td colspan="5" class="bg-left">${parttimeApply.remark}</td>
        </tr>
        <tr>
          <td class="bg-right">其他说明材料</td>
          <td colspan="5" class="bg-left">
            <c:forEach items="${files}" var="file">
              <a href="${ctx}/parttime/parttimeApply_download?id=${file.id}" target="_blank">${file.fileName}</a>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td class="bg-right">申请人签字</td>
          <td colspan="6" class="bg-left">申请人：${sysUser.realname} &nbsp;&nbsp;&nbsp;&nbsp; 申请日期：${cm:formatDate(parttimeApply.applyTime, "yyyy年 MM月 dd日")}</td>
        </tr>
        <c:forEach items="${approvalResultMap}" var="result">
          <c:if test="${result.value.value!=-1}">
            <c:set var="approvalLog" value="${cm:getParttimeApprovalLog(parttimeApply.id, result.key)}"/>
            <tr>
              <td colspan="7" class="bg-right center">
                <c:if test="${result.key==-1}">组织部初审</c:if>
                <c:if test="${result.key==0}">组织部终审</c:if>
                <c:if test="${result.key>0}">
                  <c:set var="approvalType" value="${approverTypeMap.get(result.key)}"/>
                  ${approvalType.name}
                </c:if>意见
                <c:if test="${result.key>0 && result.key==parttimeApply.flowNode}">
                <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
                  <button type="button" class="popupBtn btn btn-success btn-xs"
                          data-url="${ctx}/parttime/parttimeApply_approval_direct?applyId=${parttimeApply.id}&approvalTypeId=${result.key}">
                <i class="fa fa-check"></i> 审批通过</button>
                </shiro:hasPermission>
                </c:if>
              </td>
            </tr>
            <tr>
              <td colspan="7"   class="bg-left">
                审批结果：${result.value.value==null?"未审批":(result.value.value==0?"未通过":"通过")}
                <br/>
                <c:if test="${not empty approvalLog}">
                  <c:set var="sysUser" value="${cm:getUserById(approvalLog.userId)}"/>
                  审批意见：${approvalLog.remark}<br/>
                  <c:if test="${!justView}">审批人：${sysUser.realname}<br/></c:if>
                  审批时间：${cm:formatDate(approvalLog.createTime,'yyyy-MM-dd')}
                  <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
                  &nbsp;<button type="button" class="popupBtn btn btn-primary btn-xs"
                          data-url="${ctx}/parttime/parttimeApply_approval_direct_au?applyId=${parttimeApply.id}&approvalLogId=${approvalLog.id}&approvalTypeId=${result.key}&type=${param.type}">
                    <i class="fa fa-edit"></i> 修改</button>
                  </shiro:hasPermission>
                </c:if>
              </td>
            </tr>
          </c:if>
        </c:forEach>
        </tbody>
      </table>
        </div>
    </div>
  </div>
</div>

<style>
  .bg-right{
    background-color: #7db4d8!important;
    color: white;
    font-weight: bolder;
    font-size: 16px;
  }
  .bg-right.center{
    text-align: center!important;
  }
  .preview{
    width: 850px;
  }
  .preview.title{
    width: 870px;
  }
</style>
<script>
<c:if test="${param.type=='approval'}">
  $("#agree").click(function(){
    $.loadModal("${ctx}/parttime/parttimeApply_approval?applyId=${parttimeApply.id}&approvalTypeId=${param.approvalTypeId}&pass=1");
  });
  $("#disagree").click(function(){
    $.loadModal("${ctx}/parttime/parttimeApply_approval?applyId=${parttimeApply.id}&approvalTypeId=${param.approvalTypeId}&pass=0");
  });
</c:if>
</script>
