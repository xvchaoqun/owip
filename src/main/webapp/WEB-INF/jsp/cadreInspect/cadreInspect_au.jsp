<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreInspect!=null}">编辑</c:if><c:if test="${cadreInspect==null}">添加</c:if>考察对象
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreInspect_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="inspectId" value="${cadreInspect.id}">
        <shiro:hasPermission name="scRecord:list">
            <div class="form-group">
                <label class="col-xs-4 control-label"> 对应选任纪实</label>
                <div class="col-xs-6">
                    <input type="hidden" name="recordId" value="${scRecord.id}">
                    <input readonly class="form-control" type="text" name="recordCode" value="${scRecord.code}">
                </div>
                <button id="selectRecordBtn" type="button" class="btn btn-success btn-sm"><i
                        class="fa fa-chevron-circle-down"></i> 选择
                </button>
            </div>
        </shiro:hasPermission>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>姓名</label>
            <div class="col-xs-6 ${cadre!=null?'label-text':''}">
                <c:if test="${cadre==null}">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                            data-width="273"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${cadre.userId}">${cadre.realname}-${cadre.code}</option>
                    </select>
                </c:if>
                <c:if test="${cadre!=null}">
                    <input type="hidden" name="userId" value="${cadre.userId}">
                    ${cadre.realname}-${cadre.code}
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-4 control-label">现所在单位及职务</label>
            <div class="col-xs-6">
                <textarea class="form-control" rows="3" name="title">${cadre.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">拟任职务</label>
            <div class="col-xs-6">
                <select name="unitPostId" data-rel="select2-ajax"
                        data-ajax-url="${ctx}/unitPost_selects" data-width="273"
                        data-placeholder="请选择">
                    <option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
                </select>
                <script>
                    $.register.del_select($("#modalForm select[name=unitPostId]"))
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="inspectRemark" rows="5">${cadreInspect.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button type="button" id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        ${cadreInspect!=null?'确定':'添加'}
    </button>
</div>

<script>
    function _selectItem(id, rowData){
        //console.log(rowData)
        if(id>0){
            var code = rowData.code;
            $("#modalForm input[name=recordId]").val(id);
            $("#modalForm input[name=recordCode]").val(code);
            $("#modalForm select[name=unitPostId]").html('<option value="{0}">{1}</option>'
                    .format(rowData.unitPostId, rowData.postCode + "-" + rowData.postName))
                    .trigger("change");
            $('#modalForm select[name=userId]').data("ajax-url", "${ctx}/sc/scGroupTopic_users?recordId="+id)
                .val(null).trigger("change");
        }else{
            $("#modalForm input[name=recordId]").val('');
            $("#modalForm input[name=recordCode]").val('');
            $('#modalForm select[name=userId]').data("ajax-url", "${ctx}/sysUser_selects")
                .val(null).trigger("change");
        }

        $.register.user_select($('#modalForm select[name=userId]'));
        WebuiPopovers.hideAll();
    }
    $('#selectRecordBtn').webuiPopover({
        padding: true,
        backdrop: false,
        autoHide: false,
        trigger: 'click',
        content: function (data) {
            return '<div id="popup-content">' + data + '</div>';
        },
        type: 'async',
        cache: false,
        url: '/sc/scRecord?cls=10&year=${_thisYear}'
    });

    $('textarea.limited').inputlimiter();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#modalForm select[name=userId]'));
    $.register.ajax_select($('#modalForm select[name=unitPostId]'));
</script>