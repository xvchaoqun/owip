<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="ANNUAL_TYPE_MODULE_MAP" value="<%=SystemConstants.ANNUAL_TYPE_MODULE_MAP%>"/>
<div class="widget-box myTableDiv" data-module="${param.module}"
     data-querystr="pageSize=${param.pageSize}&${cm:encodeQueryString(pageContext.request.queryString)}"
     style="width:800px">
  <div class="widget-header">
    <h4 class="smaller">
        ${ANNUAL_TYPE_MODULE_MAP.get(cm:toByte(param.module))}
      <div class="pull-right" style="margin-right: 10px">
        <a class="popupBtn btn btn-success btn-xs" data-url="${ctx}/annualType_au?module=${param.module}"><i
                class="fa fa-plus"></i> 添加</a>
      </div>
    </h4>
  </div>
  <div class="widget-body">
    <div class="widget-main">
      <table class="table table-bordered table-striped">
        <thead>
        <tr>
          <th width="30">序号</th>
          <th width="50">所属年份</th>
          <th width="200">名称</th>
          <th width="50">排序</th>
          <th width="100"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${annualTypes}" var="record" varStatus="vs">
          <tr>
            <td>${vs.count + (commonList.pageNo-1)*commonList.pageSize}</td>
            <td>${record.year}</td>
            <td style="text-align: left">${record.name}</td>
            <td nowrap>

              <a href="javascript:;" data-url="${ctx}/annualType_changeOrder"
                 data-callback="_reload" data-module="${record.module}"
                 class="changeOrderBtn" data-id="${record.id}" data-direction="1" title="上升">
                <i class="fa fa-arrow-up"></i></a>
              <input type="text" value="1"
                     class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                     title="修改操作步长">
              <a href="javascript:;" data-url="${ctx}/annualType_changeOrder"
                 data-callback="_reload" data-module="${record.module}"
                 class="changeOrderBtn" data-id="${record.id}" data-direction="-1" title="下降"><i
                      class="fa fa-arrow-down"></i></a>

            </td>
            <td>
              <a class="popupBtn btn btn-primary btn-xs"
                 data-url="${ctx}/annualType_au?id=${record.id}&module=${param.module}"><i class="fa fa-edit"></i>
                修改</a>
              <button class="confirm btn btn-danger btn-xs"
                      data-url="${ctx}/annualType_del?id=${record.id}"
                      data-title="删除"
                      data-msg="确定删除该类型？" data-callback="_reload"  data-module="${record.module}"
                      ><i class="fa fa-times"></i> 删除
              </button>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
        <c:if test="${!empty commonList && commonList.pageNum>1 }">
            <wo:page commonList="${commonList}" uri="${ctx}/annualTypeList_item?module=${param.module}"
                     target="div[data-module=${param.module}]" op="replace" pageNum="5" model="3"/>
        </c:if>
    </div>
  </div>
</div>