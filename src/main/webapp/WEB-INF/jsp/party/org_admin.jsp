<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
    ${party!=null?party.name:''}
    ${branch!=null?branch.name:''}-设定管理员</h3>
</div>
<div class="modal-body">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加管理员
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/orgAdmin_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                        <input type="hidden" name="id" value="${orgAdmin.id}">
                        <input type="hidden" name="partyId" value="${party.id}">
                        <input type="hidden" name="branchId" value="${branch.id}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.username}</option>
                                </select></div>
                        </div>
                        <div class="clearfix form-actions">
                            <c:if test="${not empty party}">
                            <div class="note">注：此处设定的是普通管理员（非班子成员），如果是班子成员，请在【分党委领导班子-查看委员】中进行管理</div>
                                </c:if>
                            <c:if test="${not empty branch}">
                            <div class="note">注：此处设定的是普通管理员（非支部委员），如果是支部委员，请在【支部委员会-查看委员】中进行管理</div>
                                </c:if>
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
    <div class="popTableDiv"
         data-url-page="${ctx}/org_admin?partyId=${party.id}&branchId=${branch.id}"
         data-url-del="${ctx}/orgAdmin_del">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">工号</th>
                    <th class="col-xs-5">姓名</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orgAdmins}" var="orgAdmin" varStatus="st">
                    <c:set value="${cm:getUserById(orgAdmin.userId)}" var="sysUser"/>
                    <tr>
                        <td nowrap>${sysUser.code}</td>
                        <td nowrap>
                            ${sysUser.realname}
                        </td>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="orgAdmin:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${orgAdmin.id}">
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
                <wo:page commonList="${commonList}" uri="${ctx}/org_admin" target="#modal .modal-content"
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
    $('#modalForm [data-rel="select2"]').select2();

    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $.register.user_select($('#modal [data-rel="select2-ajax"]'));
</script>