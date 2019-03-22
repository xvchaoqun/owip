<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${party!=null?party.name:''}-设定管理员</h3>
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
                    <form class="form-horizontal no-footer" action="${ctx}/pmd/pmdPartyAdmin_add" autocomplete="off" disableautocomplete id="modalForm" method="post">
                        <input type="hidden" name="partyId" value="${party.id}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.username}</option>
                                </select></div>
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
    <div class="popTableDiv"
         data-url-page="${ctx}/pmd/pmdPartyAdmin?partyId=${party.id}"
         data-url-del="${ctx}/pmd/pmdPartyAdmin_del">

            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">工号</th>
                    <th class="col-xs-5">姓名</th>
                    <th class="col-xs-5">类别</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${partyAdmins}" var="admin" varStatus="st">
                    <c:set value="${cm:getUserById(admin.userId)}" var="sysUser"/>
                    <tr>
                        <td nowrap>${sysUser.code}</td>
                        <td nowrap>${sysUser.realname}</td>
                        <td nowrap>${PMD_ADMIN_TYPE_MAP.get(admin.type)}</td>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="orgAdmin:del">
                                    <button class="delBtn btn btn-danger btn-xs"
                                            data-callback="_reload"
                                            data-id="${admin.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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
                        _reload();
                        pop_reload();
                    }
                }
            });
        }
    });
    $.register.user_select($('#modal [data-rel="select2-ajax"]'));
</script>