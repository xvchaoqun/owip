<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设置短信接收人</h3>
</div>
<div class="modal-body">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/shortMsgReceiver_au" id="modalForm" method="post">
                        <input type="hidden" name="tplId" value="${contentTpl.id}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">账号</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.username}-${sysUser.code}</option>
                                </select></div>
                        </div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    确定
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-10"></div>
    <div class="popTableDiv"
         data-url-page="${ctx}/contentTpl_receivers?id=${contentTpl.id}"
         data-url-del="${ctx}/shortMsgReceiver_del">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">姓名</th>
                    <th class="col-xs-5">手机号</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${records}" var="receiver" varStatus="st">
                    <tr>
                        <c:set var="user" value="${cm:getUserById(receiver.userId)}"/>
                        <td nowrap>
                           ${user.realname}（${user.code}）
                        </td>
                        <td nowrap>${user.mobile}</td>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${receiver.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/contentTpl_receivers" target="#modal .modal-content"
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
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    register_user_select($('#modal [data-rel="select2-ajax"]'));
</script>