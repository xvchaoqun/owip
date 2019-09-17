<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_TYPE_MSG%>" var="CONTENT_TPL_TYPE_MSG"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_TYPE_WX%>" var="CONTENT_TPL_TYPE_WX"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_TYPE_MAP%>" var="CONTENT_TPL_TYPE_MAP"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_WX_TYPE_NEWS%>" var="CONTENT_TPL_WX_TYPE_NEWS"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>消息发送（${CONTENT_TPL_TYPE_MAP.get(shortMsgTpl.type)}）</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/shortMsgTpl_send" autocomplete="off" disableautocomplete id="sendForm"
          method="post">
        <input type="hidden" name="tplId" value="${param.id}">
        <input type="hidden" name="type" value="${param.type}">
        <div class="form-group">
            <label class="col-xs-2 control-label">模板名称</label>
            <div class="col-xs-5 label-text">
                ${shortMsgTpl.name}
            </div>
        </div>
        <c:if test="${param.type=='batch'}">
            <div class="form-group">
                <label class="col-xs-2 control-label">发送对象</label>
                <div class="col-xs-8">
                    <div id="tree3" style="height: 280px;">
                        <div style="height: 280px;line-height: 280px;font-size: 20px">
                            <i class="fa fa-spinner fa-spin"></i> 加载中，请稍后...
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${param.type!='batch'}">
            <div class="form-group">
                <label class="col-xs-2 control-label">${shortMsgTpl.type!=CONTENT_TPL_TYPE_MSG?'<span class="star">*</span>':''}
                    选择用户</label>
                <div class="col-xs-5">
                    <select ${shortMsgTpl.type!=CONTENT_TPL_TYPE_MSG?'required':''}
                            data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                            name="receiverId" data-placeholder="请输入账号或姓名或学工号">
                        <option></option>
                    </select>
                </div>
            </div>
            <c:if test="${shortMsgTpl.type!=CONTENT_TPL_TYPE_WX}">
                <div class="form-group">
                    <label class="col-xs-2 control-label"><span class="star">*</span>手机号码</label>
                    <div class="col-xs-5" style="width: 225px;">
                        <input required class="form-control mobile" type="text" name="mobile">
                    </div>
                </div>
            </c:if>
        </c:if>
        <c:if test="${shortMsgTpl.wxMsgType == CONTENT_TPL_WX_TYPE_NEWS}">
                <div class="form-group">
                    <label class="col-xs-2 control-label"><span class="star">*</span>标题</label>
                    <div class="col-xs-9">
                        <input required class="form-control" type="text" name="wxTitle" value="${shortMsgTpl.wxTitle}">
                    </div>
                </div>
         </c:if>
        <div class="form-group">
            <label class="col-xs-2 control-label"><span class="star">*</span>发送内容</label>
            <div class="col-xs-9 label-text">
                   <textarea required class="form-control limited" type="text"
                             name="content" rows="10" maxlength="500">${shortMsgTpl.content}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="sendBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-send"></i> 发送
    </button>
</div>

<script>
    <c:if test="${param.type=='batch'}">
    $.getJSON("${ctx}/shortMsgTpl_selectCadres_tree", {}, function (data) {
        var treeData = data.tree.children;
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function (select, node) {

                node.expand(node.data.isFolder && node.isSelected());
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });
    </c:if>
    <c:if test="${param.type!='batch'}">
    var $selectCadre = $.register.user_select($('#sendForm select[name=receiverId]'));
    $selectCadre.on("change", function () {
        var ret = $(this).select2("data")[0];
        $('#sendForm input[name=mobile]').val(ret.msgMobile || ret.mobile);
    });
    </c:if>

    $('textarea.limited').inputlimiter();

    $("#sendBtn").click(function () {
        $("#sendForm").submit();
        return false;
    });
    $("#sendForm").validate({
        submitHandler: function (form) {
            <c:if test="${param.type=='batch'}">
            var userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                if (!node.data.isFolder && !node.data.hideCheckbox)
                    return node.data.key;
            });
            if(userIds.length==0){
                SysMsg.warning("请选择发送对象。")
                return;
            }
            </c:if>
            var $btn = $("#sendBtn").button('loading');
            $(form).ajaxSubmit({
                <c:if test="${param.type=='batch'}">
                data: {userIds: userIds},
                </c:if>
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>