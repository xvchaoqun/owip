<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/27
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div class="tabbable">
  <ul class="preview title nav nav-tabs padding-12 tab-color-blue background-blue" style="padding-right: 20px">

      <div class="buttons" style="float: left;margin-bottom: 8px">
        <a href="javascript:;" class="closeView btn btn-sm btn-success">
          <i class="ace-icon fa fa-backward"></i>
          返回
        </a>
        <c:if test="${param.type=='approval' && !applySelf.isDeleted && fn:length(approvalResultMap)>0}">
        <button id="agree" style="margin-left: 220px; margin-right: 30px;"
           class="btn btn-primary btn-sm"><i class="fa fa-check"></i> 同意申请</button>
          <button id="disagree" class="btn btn-danger btn-sm">
          <i class="fa fa-times"></i>  不同意申请</button>
        </c:if>
      </div>
      <div class="buttons pull-right">
        <button class="openView btn btn-info btn-sm"
                data-url="${ctx}/applySelf_yearLogs?id=${applySelf.id}&type=${justView?'user':param.type}&approvalTypeId=${param.approvalTypeId}"
                style="margin-left: 150px">
          <i class="fa fa-history"></i>  本年度因私出国境记录</button>

      </div>
  </ul>

  <div class="tab-content">
    <div id="home4" class="tab-pane in active">
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
          <td colspan="3" class="bg-left">${sysUser.idcard}</td>
          <td class="bg-right">联系电话</td>
          <td class="bg-left">${cadreMobile}</td>
        </tr>
        <tr>
          <td class="bg-right" nowrap>工作单位及职务</td>
          <td colspan="3" class="bg-left">${cadre.title}</td>
          <td class="bg-right">行政级别</td>
          <td class="bg-left">${adminLevelMap.get(cadre.typeId).name} </td>
        </tr>
        <tr>
          <td rowspan="9" class="bg-right" nowrap>申请因私出国（境）情况</td>
          <td rowspan="2" class="bg-right">出行时间</td>
          <td colspan="5" class="bg-left">
            <c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
              <input type="checkbox" class="big chkBox" ${type.key==applySelf.type?"checked":""} disabled> ${type.value}
              &nbsp;&nbsp;&nbsp;&nbsp;
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td colspan="5" class="bg-left">${cm:formatDate(applySelf.startDate, "yyyy年 MM月 dd日")}  至  ${cm:formatDate(applySelf.endDate, "yyyy年 MM月 dd日")}</td>
        </tr>
        <tr>
          <td class="bg-right">前往国家或地区</td>
          <td colspan="5" class="bg-left">${applySelf.toCountry}</td>
        </tr>
        <tr>
          <td class="bg-right">出国（境）事由
          </td>
          <td colspan="5" class="bg-left">${fn:replace(applySelf.reason, "+++", ",")}</td>
        </tr>
        <tr>
          <td class="bg-right">同行人员</td>
          <td colspan="5" class="bg-left">${fn:replace(applySelf.peerStaff, "+++", ",")}</td>
        </tr>
        <tr>
          <td class="bg-right">费用来源</td>
          <td colspan="5" class="bg-left">${applySelf.costSource}</td>
        </tr>
        <tr>
          <td class="bg-right">所需证件</td>
          <td colspan="5" class="bg-left">

            <c:forEach items="${fn:split(applySelf.needPassports, ',')}" var="typeId" varStatus="vs">
              ${cm:getMetaType(typeId).name}
              ${!vs.last?", ":""}
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td class="bg-right">其他说明材料</td>
          <td colspan="5" class="bg-left">
            <c:forEach items="${files}" var="file">
              <a href="${ctx}/applySelf_download?id=${file.id}" target="_blank">${file.fileName}</a>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td class="bg-right">申请人签字</td>
          <td colspan="5" class="bg-left">申请人：${sysUser.realname} &nbsp;&nbsp;&nbsp;&nbsp; 申请日期：${cm:formatDate(applySelf.applyDate, "yyyy年 MM月 dd日")}</td>
        </tr>
        <c:forEach items="${approvalResultMap}" var="result">
          <c:if test="${result.value.value!=-1}">
            <c:set var="approvalLog" value="${cm:getApprovalLog(applySelf.id, result.key)}"/>
            <tr>
              <td colspan="7" class="bg-right center">
                <c:if test="${result.key==-1}">组织部初审</c:if>
                <c:if test="${result.key==0}">组织部终审</c:if>
                <c:if test="${result.key>0}">
                  <c:set var="approvalType" value="${approverTypeMap.get(result.key)}"/>
                  ${approvalType.name}
                </c:if>意见
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
    loadModal("${ctx}/applySelf_approval?applySelfId=${applySelf.id}&approvalTypeId=${param.approvalTypeId}&status=1");
  });
  $("#disagree").click(function(){
    loadModal("${ctx}/applySelf_approval?applySelfId=${applySelf.id}&approvalTypeId=${param.approvalTypeId}&status=0");
  });
</c:if>
</script>
