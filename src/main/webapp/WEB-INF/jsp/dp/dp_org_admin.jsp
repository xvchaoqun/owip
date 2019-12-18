<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dpParty!=null}">${dpParty.name}</c:if>-设定管理员</h3>
</div>
<div class="modal-body">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    添加管理员
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/dp/dpOrgAdmin_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                        <input type="hidden" name="id" value="${dpOrgAdmin.id}">
                        <input type="hidden" name="partyId" value="${dpParty.id}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/dp/teacher_select"
                                        name="userId" data-placeholder="请输入账号或姓名或工作证号">
                                    <option value="${sysUser.id}">${sysUser.username}</option>
                                </select></div>
                        </div>
                        <div class="clearfix form-actions">
                            <c:if test="${not empty dpParty}">
                            <div class="note">注：此处设定的是普通管理员（非委员会成员），如果是委员会成员，请在【委员会-查看委员】中进行管理</div>
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
         data-url-page="${ctx}/dp/dp_org_admin?partyId=${dpParty.id}"
         data-url-del="${ctx}/dp/dpOrgAdmin_del">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">工作证号</th>
                    <th class="col-xs-5">姓名</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${dpOrgAdmins}" var="dpOrgAdmin" varStatus="st">
                    <c:set value="${cm:getUserById(dpOrgAdmin.userId)}" var="sysUser"/>
                    <tr>
                        <td nowrap>${sysUser.code}</td>
                        <td nowrap>
                            ${sysUser.realname}
                        </td>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <button class="delBtn btn btn-danger btn-xs" data-id="${dpOrgAdmin.id}">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/dp/dp_org_admin" target="#modal .modal-content"
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