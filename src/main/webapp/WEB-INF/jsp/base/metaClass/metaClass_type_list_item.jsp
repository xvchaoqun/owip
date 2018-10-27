<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box myTableDiv" data-cls="${metaClass.code}" style="width:900px">
  <div class="widget-header">
    <h4 class="smaller">
      ${metaClass.name}
      <div class="pull-right" style="margin-right: 10px">
        <a class="popupBtn btn btn-success btn-xs" data-url="${ctx}/metaClass_type_au?cls=${metaClass.code}"><i
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
          <th width="200">类型名称</th>
          <th width="100">排序</th>
          <th width="100"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cm:getMetaTypes(metaClass.code)}" var="entity" varStatus="vs">
          <c:set var="record" value="${entity.value}"/>
          <tr>
            <td>${vs.count}</td>
            <td style="text-align: left">${record.name}</td>
            <td nowrap>
              <a href="javascript:;" data-url="${ctx}/metaClass_type_changeOrder?cls=${metaClass.code}"
                 data-callback="_reload" data-cls="${metaClass.code}"
                 class="changeOrderBtn" data-id="${record.id}" data-direction="1" title="上升">
                <i class="fa fa-arrow-up"></i></a>
              <input type="text" value="1"
                     class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                     title="修改操作步长">
              <a href="javascript:;" data-url="${ctx}/metaClass_type_changeOrder?cls=${metaClass.code}"
                 data-callback="_reload" data-cls="${metaClass.code}"
                 class="changeOrderBtn" data-id="${record.id}" data-direction="-1" title="下降"><i
                      class="fa fa-arrow-down"></i></a>
            </td>
            <td>
              <a class="popupBtn btn btn-primary btn-xs"
                 data-url="${ctx}/metaClass_type_au?id=${record.id}&cls=${metaClass.code}"><i class="fa fa-edit"></i>
                修改</a>
              <button class="confirm btn btn-danger btn-xs"
                      data-url="${ctx}/metaClass_type_del?id=${record.id}&cls=${metaClass.code}"
                      data-title="删除"
                      data-msg="确定删除该类型？" data-callback="_reload"  data-cls="${metaClass.code}"
                      ><i class="fa fa-times"></i> 删除
              </button>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>