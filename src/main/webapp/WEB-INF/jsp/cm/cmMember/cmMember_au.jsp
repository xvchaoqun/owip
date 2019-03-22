<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_MAP%>" var="CM_MEMBER_TYPE_MAP"/>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_JW%>" var="CM_MEMBER_TYPE_JW"/>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_CW%>" var="CM_MEMBER_TYPE_CW"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cmMember!=null?'编辑':'添加'}${isQuit?'离任':'现任'}${CM_MEMBER_TYPE_MAP.get(type)}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cmMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cmMember.id}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="isQuit" value="${isQuit}">

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>届数</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="configId" data-width="272" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${pcsConfigs}" var="pcsConfig">
                        <option value="${pcsConfig.id}">${pcsConfig.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=configId]").val("${empty cmMember?
                    currentPcsConfig.id:cmMember.configId}")
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>委员</label>

            <div class="col-xs-6">
                <c:set var="sysUser" value="${cm:getUserById(cmMember.userId)}"/>
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                        data-width="272"
                        name="userId" data-placeholder="请输入账号或姓名或工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>职务</label>
            <div class="col-xs-6">
                <c:set var="metaTypeMap" value="${cm:getMetaTypes(type==CM_MEMBER_TYPE_JW?'mc_committee_member_jw'
                    :'mc_committee_member_dw')}"/>
                <select required data-rel="select2" name="post"
                        data-width="272" data-placeholder="请选择职务">
                    <option></option>
                    <c:forEach var="entity" items="${metaTypeMap}">
                        <c:set value="${entity.value}" var="metaType"/>
                        <c:if test="${type!=CM_MEMBER_TYPE_CW||(type==CM_MEMBER_TYPE_CW&&metaType.boolAttr)}">
                      <option value="${metaType.id}" data-bool-attr="${metaType.boolAttr}"
                              data-extra-attr="${metaType.extraAttr}">${metaType.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=post]").val('${cmMember.post}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>任职日期</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="postDate"
                           type="text" data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(cmMember.postDate, "yyyy-MM-dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任命文件</label>

            <div class="col-xs-6">
                <input class="form-control" type="file" name="_postFilePath"/>
            </div>
        </div>

        <c:if test="${isQuit}">
            <div class="form-group">
                <label class="col-xs-3 control-label">离任日期</label>

                <div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="quitDate"
                               type="text" data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(cmMember.quitDate, "yyyy-MM-dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">离任原因</label>

                <div class="col-xs-6">
					<textarea class="form-control limited noEnter"
                              name="quitReason">${cmMember.quitReason}</textarea>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
					 <textarea class="form-control limited noEnter"
                               name="remark">${cmMember.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> <c:if
            test="${cmMember!=null}">确定</c:if><c:if test="${cmMember==null}">添加</c:if></button>
</div>
<script>
    $.fileInput($('#modalForm input[type=file]'),{
        no_file:'请上传pdf文件',
        allowExt: ['pdf']
    })

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>