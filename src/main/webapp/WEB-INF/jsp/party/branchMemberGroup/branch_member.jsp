<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${branchMemberGroup.name}-委员管理</h3>
</div>
<div class="modal-body">
    <shiro:hasPermission name="branchMember:edit">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加委员
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/branchMember_au" id="modalForm" method="post">
                        <input type="hidden" name="groupId" value="${branchMemberGroup.id}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">账号</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.username}</option>
                                </select></div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">选择类别</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2" name="typeId" data-placeholder="请选择类别">
                                    <option></option>
                                    <jsp:include page="/metaTypes?__code=mc_branch_member_type"/>
                                </select>
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
         data-url-page="${ctx}/branch_member?id=${branchMemberGroup.id}"
         data-url-del="${ctx}/branchMember_del"
         data-url-co="${ctx}/branchMember_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th class="col-xs-5">姓名</th>
                    <th class="col-xs-5">类别</th>
                    <shiro:hasPermission name="branchMember:changeOrder">
                        <c:if test="${!_query && commonList.recNum>1}">
                            <th nowrap>排序</th>
                        </c:if>
                    </shiro:hasPermission>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${branchMembers}" var="branchMember" varStatus="st">
                    <tr>
                        <td nowrap>
                            <c:if test="${branchMember.isAdmin}">
                                <span class="label label-success arrowed-in arrowed-in-right">管理员</span>
                            </c:if>${cm:getUserById(branchMember.userId).realname}
                        </td>
                        <td nowrap>${typeMap.get(branchMember.typeId).name}</td>
                        <shiro:hasPermission name="branchMember:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#"
                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${branchMember.id}" data-direction="1" title="上升"><i
                                            class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                           title="修改操作步长">
                                    <a href="#"
                                       <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${branchMember.id}" data-direction="-1"
                                       title="下降"><i class="fa fa-arrow-down"></i></a></td>
                                </td>
                            </c:if>
                        </shiro:hasPermission>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="branchMember:edit">
                                    <c:if test="${!branchMember.isAdmin}">
                                        <button class="adminBtn btn btn-success btn-mini btn-xs" data-id="${branchMember.id}">
                                            <i class="fa fa-times"></i> 设为管理员
                                        </button>
                                    </c:if>
                                    <c:if test="${branchMember.isAdmin}">
                                        <button class="adminBtn btn btn-danger btn-mini btn-xs" data-id="${branchMember.id}">
                                            <i class="fa fa-times"></i> 删除管理员
                                        </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="branchMember:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${branchMember.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown"
                                            data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <shiro:hasPermission name="branchMember:del">
                                            <li>
                                                <a href="#" data-id="${branchMember.id}" class="delBtn tooltip-error"
                                                   data-rel="tooltip" title="删除">
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
            <div class="text-center">
                <div class="pagination pagination-centered">
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/branch_member" target="#modal .modal-content"
                                 pageNum="5"
                                 model="3"/>
                    </c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${commonList.recNum==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>
    $('[data-rel="select2"]').select2();
    $("#modal .adminBtn").click(function () {

        $.post("${ctx}/branchMember_admin", {id: $(this).data("id")}, function (data) {
            if (data.success) {
                pop_reload();
                SysMsg.success('操作成功。', '成功');
            }
        });
    })
    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }/*, errorPlacement: function (error, element) {

        }, invalidHandler: function (form, validator) {
            //var errors = validator.numberOfInvalids();
            SysMsg.error("请选择用户和类别", '错误');
        }*/
    });
    register_user_select($('#modalForm select[name=userId]'));
</script>