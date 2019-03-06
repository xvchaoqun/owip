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
                        <input type="hidden" name="id" value="${branchMember.id}">
                        <c:set var="sysUser" value="${cm:getUserById(branchMember.userId)}"/>
                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2-ajax" data-width="362" data-ajax-url="${ctx}/sysUser_selects"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.username}</option>
                                </select></div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>选择类别</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2" data-width="362" name="typeId" data-placeholder="请选择类别">
                                    <option></option>
                                    <jsp:include page="/metaTypes?__code=mc_branch_member_type"/>
                                </select>
                                <script>
                                    $("#modal select[name=typeId]").val('${branchMember.typeId}');
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">任职时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control date-picker" name="assignDate" type="text"
                                           data-date-min-view-mode="1"
                                           data-date-format="yyyy.mm" value="${cm:formatDate(branchMember.assignDate,'yyyy.MM')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">办公电话</label>

                            <div class="col-xs-6">
                                <input class="form-control" type="text" name="officePhone" value="${branchMember.officePhone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">手机号</label>

                            <div class="col-xs-6">
                                <input class="form-control" type="text" name="mobile" value="${branchMember.mobile}">
                            </div>
                        </div>
                        <div class="clearfix form-actions center">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    ${empty branchMember?"添加":"修改"}
                                </button>
                                <c:if test="${not empty branchMember}">
                                &nbsp; &nbsp; &nbsp;
                                <button class="jqOpenViewBtn btn btn-default btn-sm"
                                        data-url="${ctx}/branch_member" data-width="800">
                                    <i class="ace-icon fa fa-undo"></i>
                                    返回添加
                                </button>
                                </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-10"></div>
    </shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/branch_member?id=${branchMemberGroup.id}"
         data-url-au="${ctx}/branch_member?id=${branchMemberGroup.id}"
         data-url-del="${ctx}/branchMember_del"
         data-url-co="${ctx}/branchMember_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>工号</th>
                    <th>姓名</th>
                    <th>任职时间</th>
                    <th>办公电话</th>
                    <th>手机号</th>
                    <th>类别</th>
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
                        <c:set var="user" value="${cm:getUserById(branchMember.userId)}"/>
                        <td>${user.code}</td>
                        <td nowrap>
                            <c:if test="${branchMember.isAdmin}">
                                <i class="fa fa-user"></i>
                            </c:if>${user.realname}
                        </td>
                        <td>${cm:formatDate(branchMember.assignDate, "yyyy.MM")}</td>
                        <td>${branchMember.officePhone}</td>
                        <td>${branchMember.mobile}</td>
                        <td nowrap>${cm:getMetaType(branchMember.typeId).name}</td>
                        <shiro:hasPermission name="branchMember:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="javascript:;"
                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${branchMember.id}" data-direction="1" title="上升"><i
                                            class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                           title="修改操作步长">
                                    <a href="javascript:;"
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
                                        <button class="adminBtn btn btn-success btn-xs" data-id="${branchMember.id}">
                                            <i class="fa fa-check"></i> 设为管理员
                                        </button>
                                    </c:if>
                                    <c:if test="${branchMember.isAdmin}">
                                        <button class="adminBtn btn btn-warning btn-xs" data-id="${branchMember.id}">
                                            <i class="fa fa-trash"></i> 删除管理员
                                        </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="branchMember:edit">
                                    <button class="editBtn btn btn-primary btn-xs"
                                    data-width="800" data-id-name="memberId"
                                     data-id="${branchMember.id}">
                                        <i class="fa fa-edit"></i> 修改
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="branchMember:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${branchMember.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-xser btn-primary dropdown-toggle" data-toggle="dropdown"
                                            data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <shiro:hasPermission name="branchMember:del">
                                            <li>
                                                <a href="javascript:;" data-id="${branchMember.id}" class="delBtn tooltip-error"
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
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/branch_member" target="#modal .modal-content"
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
    $.register.date($('.date-picker'));
    $('[data-rel="select2"]').select2();
    $("#modal .adminBtn").click(function () {

        $.post("${ctx}/branchMember_admin", {id: $(this).data("id")}, function (data) {
            if (data.success) {
                pop_reload();
                //SysMsg.success('操作成功。', '成功');
            }
        });
    });
    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
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
    $.register.user_select($('#modalForm select[name=userId]'));
</script>