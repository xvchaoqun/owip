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
      <a href="javascript:;" class="closeView btn btn-xs btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
    </h4>
    </div>
<c:if test="${not empty applySelf}">
  <div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
      <h4 class="widget-title lighter">
        <i class="ace-icon fa fa-star orange"></i>
        因私出国（境）行程
      </h4>
      <div class="widget-toolbar">
        <a href="javascript:;" data-action="collapse">
          <i class="ace-icon fa fa-chevron-up"></i>
        </a>
      </div>
    </div>
    <div class="widget-body" style="display: block;">
      <div class="widget-main no-padding">
        <table class="table table-bordered table-hover">
          <thead>
          <tr>
            <th>编号</th>
            <th>申请日期</th>
            <th>出行时间</th>
            <th>出发时间</th>
            <th>返回时间</th>
            <th>出行天数</th>
            <th>前往国家或地区</th>
            <th>事由</th>
            <th>组织部初审</th>
            <th>组织部终审</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>S${applySelf.id}</td>
            <td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
            <td>${APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
            <td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
            <td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
            <td>${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</td>
            <td>${applySelf.toCountry}</td>
            <td>${fn:replace(applySelf.reason, '+++', ',')}</td>
            <td></td>
            <td></td>
          </tr>
          </tbody>
        </table>
      </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
  </div>
  </c:if>

  <c:if test="${passportDraw.type==PASSPORT_DRAW_TYPE_TW}">
    <div class="widget-box transparent">
      <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
          <i class="ace-icon fa fa-star orange"></i>
          因公出访台湾
        </h4>
        <div class="widget-toolbar">
          <a href="javascript:;" data-action="collapse">
            <i class="ace-icon fa fa-chevron-up"></i>
          </a>
        </div>
      </div>
      <div class="widget-body" style="display: block;">
        <div class="widget-main no-padding">
          <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
              <th>申请日期</th>
              <th>出发时间</th>
              <th>回国时间</th>
              <th>出行天数</th>
              <th>事由</th>
              <th>费用来源</th>
              <th>国台办批件</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>${cm:formatDate(passportDraw.applyDate,'yyyy-MM-dd')}</td>
              <td>${cm:formatDate(passportDraw.startDate,'yyyy-MM-dd')}</td>
              <td>${cm:formatDate(passportDraw.endDate,'yyyy-MM-dd')}</td>
              <td>${cm:getDayCountBetweenDate(passportDraw.startDate, passportDraw.endDate)}</td>
              <td>${fn:replace(passportDraw.reason, '+++', ',')}</td>
              <td>${passportDraw.costSource}</td>
              <td></td>
            </tr>
            </tbody>
          </table>
        </div><!-- /.widget-main -->
      </div><!-- /.widget-body -->
    </div>
  </c:if>
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
    <td colspan="3" class="bg-left">${cadre.title}</td>
    <td class="bg-right">行政级别</td>
    <td class="bg-left">${adminLevelMap.get(cadre.typeId).name} </td>
  </tr>
  <tr>
    <td rowspan="2" class="bg-right">申请</td>
    <td class="bg-right">申请日期</td>
    <td colspan="5" class="bg-left">
      ${cm:formatDate(passportDraw.applyDate,'yyyy-MM-dd')}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">是否签注</td>
    <td colspan="5" class="bg-left">
      ${passportDraw.needSign?"是":"否"}
    </td>
  </tr>

  <tr>
    <td rowspan="4" class="bg-right">审批</td>
    <td class="bg-right">审批状态</td>
    <td colspan="5" class="bg-left">
      ${PASSPORT_DRAW_STATUS_MAP.get(passportDraw.status)}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">审批时间</td>
    <td colspan="5" class="bg-left">
      ${cm:formatDate(passportDraw.approveTime,'yyyy-MM-dd HH:mm:ss')}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">审批人</td>
    <td colspan="5" class="bg-left">
      <c:if test="${not empty passportDraw.userId}">
      ${cm:getUserById(passportDraw.userId).realname}
      </c:if>
    </td>
  </tr>
  <tr>
    <td  class="bg-right">备注</td>
    <td colspan="5" class="bg-left">
      ${passportDraw.approveRemark}
    </td>
  </tr>
  <c:if test="${passportDraw.status==PASSPORT_DRAW_STATUS_PASS}">
  <tr>
    <td rowspan="5" class="bg-right">领取</td>
    <td  class="bg-right">状态</td>
    <td colspan="5" class="bg-left">
        ${PASSPORT_DRAW_DRAW_STATUS_MAP.get(passportDraw.drawStatus)}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">预计归还时间</td>
    <td colspan="5" class="bg-left">
        ${cm:formatDate(passportDraw.returnDate,'yyyy-MM-dd')}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">办理人</td>
    <td colspan="5" class="bg-left">
<c:if test="${not empty passportDraw.drawUserId}">
    ${cm:getUserById(passportDraw.drawUserId).realname}
  </c:if>
    </td>
  </tr>
  <tr>
    <td  class="bg-right">领取时间</td>
    <td colspan="5" class="bg-left">
      ${cm:formatDate(passportDraw.drawTime,'yyyy-MM-dd HH:mm:ss')}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">证件拍照</td>
    <td colspan="5" class="bg-left">
      <c:if test="${not empty passportDraw.drawRecord}">
      <a href="${ctx}/pic?path=${cm:encodeURI(passportDraw.drawRecord)}" target="_blank">
        <img src="${ctx}/pic?path=${cm:encodeURI(passportDraw.drawRecord)}"  style="max-height: 50px"/>
      </a>
      </c:if>
    </td>
  </tr>
  <tr>
    <td rowspan="6" class="bg-right">归还</td>
    <td class="bg-right">实际归还时间</td>
    <td colspan="5" class="bg-left">
      ${cm:formatDate(passportDraw.realReturnDate,'yyyy-MM-dd')}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">实际出发时间</td>
    <td colspan="5" class="bg-left">
      ${cm:formatDate(passportDraw.realStartDate,'yyyy-MM-dd')}
    </td>
  </tr>
  <tr>
    <td  class="bg-right">实际返回时间</td>
    <td colspan="5" class="bg-left">
      ${cm:formatDate(passportDraw.realEndDate,'yyyy-MM-dd')}
    </td>
  </tr>
  <tr>
    <td class="bg-right">实际出行国家或地区</td>
    <td colspan="5" class="bg-left">
      ${passportDraw.realToCountry}
    </td>
  </tr>
  <tr>
    <td class="bg-right">使用记录拍照</td>
    <td colspan="5" class="bg-left">
    <c:if test="${not empty passportDraw.useRecord}">
      <a href="${ctx}/pic?path=${cm:encodeURI(passportDraw.useRecord)}" target="_blank">
        <img src="${ctx}/pic?path=${cm:encodeURI(passportDraw.useRecord)}"  style="max-height: 50px"/>
      </a>
      </c:if>
    </td>
  </tr>
  <tr>
    <td class="bg-right">备注</td>
    <td colspan="5" class="bg-left">
      ${passportDraw.returnRemark}
    </td>
  </tr>
    </c:if>
  </tbody>
</table>
      </div>
    </div><!-- /.widget-main -->
  </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
</div>
