<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${branchGroup.name} - 添加成员</h3>
</div>
<div class="modal-body">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title">
                添加党小组成员
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <form class="form-horizontal no-footer" action="${ctx}/branchGroupMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                    <input type="hidden" name="groupId" value="${branchGroup.id}">
                    <div class="form-group">
                        <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
                        <div class="col-xs-6">
                            <select required data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?branchId=${branchGroup.branchId}"
                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.username}</option>
                            </select></div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">是否是组长</label>
                        <div class="col-xs-6">
                            <input type="checkbox" name="isLeader"/>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="clearfix col-md-offset-3 col-md-9">
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
    <div class="popTableDiv"
         data-url-page="${ctx}/branchGroupMember?&groupId=${branchGroup.id}"
         data-url-del="${ctx}/branchGroupMember_del"
         data-url-co="${ctx}/branchGroupMember_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">学工号</th>
                    <th class="col-xs-5">姓名</th>
                    <th class="col-xs-5">类别</th>
                    <th class="col-xs-5">排序</th>
                    <th class="col-xs-5">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${branchGroupMembers}" var="branchGroupMember" varStatus="st">
                    <c:set value="${cm:getUserById(branchGroupMember.userId)}" var="sysUser"/>
                    <tr>
                        <td nowrap>${sysUser.code}</td>
                        <td nowrap>
                                ${sysUser.realname}
                        </td>
                        <td nowrap>${branchGroupMember.isLeader?"组长":"成员"}</td>
                        <td nowrap>
                            <a href="javascript:;"
                               <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                               class="changeOrderBtn" data-id="${branchGroupMember.id}" data-direction="1" title="上升"><i
                                    class="fa fa-arrow-up"></i></a>
                            <input type="text" value="1"
                                   class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                   title="修改操作步长">
                            <a href="javascript:;"
                               <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                               class="changeOrderBtn" data-id="${branchGroupMember.id}" data-direction="-1"
                               title="下降"><i class="fa fa-arrow-down"></i></a></td>
                        </td>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <button class="delBtn btn btn-danger btn-xs"
                                        data-callback="_reload2"
                                        data-id="${branchGroupMember.id}">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/branchGroupMember" target="#modal .modal-content"
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

    function _reload2(){
        $("#jqGrid2").trigger("reloadGrid");
    }

    $('#modalForm [data-rel="select2"]').select2();

    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload(_reload2);
                    }
                }
            });
        }
    });
    $.register.user_select($('#modal [data-rel="select2-ajax"]'));
    $("#modalForm :checkbox").bootstrapSwitch();
</script>