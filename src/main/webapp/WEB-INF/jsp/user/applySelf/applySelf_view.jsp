<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/27
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box transparent" id="view-box" style="width: 900px">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:;" class="closeView btn btn-mini btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
    </h4>
    </div>
  <div class="widget-body">
    <div class="widget-main padding-4">
      <div class="tab-content padding-8">
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
    <td class="bg-left">${sysUser.mobile}</td>
  </tr>
  <tr>
    <td class="bg-right">工作单位及现任职务</td>
    <td colspan="3" class="bg-left">${unitMap.get(cadre.unitId).name}&nbsp;&nbsp;${cadre.title}</td>
    <td class="bg-right">行政级别</td>
    <td class="bg-left">${adminLevelMap.get(cadre.typeId).name} </td>
  </tr>
  <tr>
    <td rowspan="9" class="bg-right">申请因私出国（境）情况</td>
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
        ${cm:getMetaType("mc_passport_type", typeId).name}
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
    <td colspan="5" class="bg-left">申请人：${sysUser.realname} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  申请日期：${cm:formatDate(applySelf.applyDate, "yyyy年 MM月 dd日")}</td>
  </tr>
  <tr>
    <td colspan="7" style="text-align: center">所在单位正职意见</td>
  </tr>
  <tr>
    <td colspan="7" height="100">
    </td>
  </tr>
  <tr>
    <td colspan="7" style="text-align: center">校领导意见</td>
  </tr>
  <tr>
    <td colspan="7" height="100">
    </td>
  </tr>
  <tr>
    <td colspan="7" style="text-align: center">党委组织部意见</td>
  </tr>
  <tr>
    <td colspan="7" height="100">
    </td>
  </tr>
  </tbody>
</table>
      </div>
    </div><!-- /.widget-main -->
  </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
</div>