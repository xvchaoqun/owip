<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>管理专题班</h3>
</div>
<div class="modal-body">
    <shiro:hasPermission name="cetCourse:edit">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加专题班
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/cet/cetCourseItem_au" id="modalForm" method="post">
                        <input type="hidden" name="courseId" value="${param.courseId}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">专题班名称</label>
                            <div class="col-xs-6 label-text">
                                <input required class="form-control" type="text" name="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">学时</label>
                            <div class="col-xs-6">
                                <input required class="form-control period" type="text" name="period">
                            </div>
                        </div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    确定
                                </button>
                                &nbsp; &nbsp; &nbsp;
                                <button class="btn btn-default btn-sm" type="reset">
                                    <i class="ace-icon fa fa-undo"></i>
                                    重置
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-10"></div>
    </shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/cet/cetCourseItem?courseId=${param.courseId}"
         data-url-del="${ctx}/cet/cetCourseItem_del"
         data-url-co="${ctx}/cet/cetCourseItem_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered table-center">
                <thead>
                <tr>
                    <th width="80">编号</th>
                    <th>专题班名称</th>
                    <th width="80">学时</th>
                    <c:if test="${!_query && commonList.recNum>1}">
                        <th nowrap width="40">排序</th>
                    </c:if>
                    <th nowrap width="80"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cetCourseItems}" var="cetCourseItem" varStatus="st">
                    <tr>
                        <td nowrap>专题班${st.count}</td>
                        <td nowrap style="text-align: left">${cetCourseItem.name}</td>
                        <td nowrap>${cetCourseItem.period}</td>
                        <c:if test="${!_query && commonList.recNum>1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${cetCourseItem.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${cetCourseItem.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="cetCourse:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${cetCourseItem.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/cet/cetCourseItem" target="#modal .modal-content"
                         pageNum="5"
                         model="3"/>
            </c:if>
        </c:if>
        <c:if test="${commonList.recNum==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>
    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        $("#jqGrid").trigger("reloadGrid");
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
</script>