<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal"  aria-hidden="true" class="close">&times;</button>
  <a onclick="au2()" class="btn btn-default pull-right" style="margin-right: 20px">添加期间工作</a>
  <h3>期间工作</h3>
</div>
<div class="modal-body">
  <c:if test="${commonList.recNum>0}">
    <table class="table table-actived table-striped table-bordered table-hover table-condensed">
      <thead>
      <tr>
        <th>开始日期</th>
        <th>结束日期</th>
        <th>工作单位</th>
        <th>担任职务或者专技职务</th>
        <th>行政级别</th>
        <th>院系/机关工作经历</th>
        <th nowrap></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${cadreWorks}" var="cadreWork" varStatus="st">
        <tr>
          <td>${cm:formatDate(cadreWork.startTime,'yyyy-MM-dd')}</td>
          <td>${cm:formatDate(cadreWork.endTime,'yyyy-MM-dd')}</td>
          <td>${cadreWork.unit}</td>
          <td>${cadreWork.post}</td>
          <td>${adminLevelMap.get(cadreWork.typeId).name}</td>
          <td>${cadreWork.workType==1?"院系工作经历":"机关工作经历"}</td>

          <td>
            <div class="hidden-sm hidden-xs action-buttons">
              <shiro:hasPermission name="cadreWork:edit">
                <button onclick="au2(${cadreWork.id})" class="btn btn-mini">
                  <i class="fa fa-edit"></i> 编辑
                </button>

                <button onclick="showDispatch(${cadreWork.id})" class="btn btn-mini">
                  <i class="fa fa-edit"></i> 添加任免文件
                </button>
              </shiro:hasPermission>
              <shiro:hasPermission name="cadreWork:del">
                <button class="btn btn-danger btn-mini" onclick="_del2(${cadreWork.id})">
                  <i class="fa fa-trash"></i> 删除
                </button>
              </shiro:hasPermission>
            </div>
            <div class="hidden-md hidden-lg">
              <div class="inline pos-rel">
                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                  <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                </button>

                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                  <%--<li>
                  <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                              <span class="blue">
                                  <i class="ace-icon fa fa-search-plus bigger-120"></i>
                              </span>
                  </a>
              </li>--%>
                  <shiro:hasPermission name="cadreWork:edit">
                    <li>
                      <a href="#" data-id="${cadreWork.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                      </a>
                    </li>
                  </shiro:hasPermission>
                  <shiro:hasPermission name="cadreWork:del">
                    <li>
                      <a href="#" data-id="${cadreWork.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                      </a>
                    </li>
                  </shiro:hasPermission>
                </ul>
              </div>
            </div>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <c:if test="${!empty commonList && commonList.pageNum>1 }">
      <div class="row my_paginate_row">
        <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
        <div class="col-xs-6">
          <div class="my_paginate">
            <ul class="pagination">
              <wo:page commonList="${commonList}" uri="${ctx}/cadreWork_page" target="#view-box .tab-content" pageNum="5"
                       model="3"/>
            </ul>
          </div>
        </div>
      </div>
    </c:if>
  </c:if>
  <c:if test="${commonList.recNum==0}">
    <div class="well well-lg center">
      <h4 class="green lighter">暂无记录</h4>
    </div>
  </c:if>
</div>
<script>

  function au2(id){

    var url = "${ctx}/cadreWork_au?fid=${param.fid}&cadreId=${param.cadreId}";
    if(id>0) url += "&id="+id;

    loadModal(url);
  }

  function _del2(id){
    bootbox.confirm("确定删除该记录吗？", function (result) {
      if (result) {
        $.post("${ctx}/cadreWork_del", {id: id}, function (ret) {
          if (ret.success) {
            showSubWork("${param.fid}");
            toastr.success('操作成功。', '成功');
          }
        });
      }
    });
  }
</script>