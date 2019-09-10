<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${(empty cadreParty)?"添加":"更新"}${type==1?'民主党派':'党员'}干部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreParty_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${cadreParty.id}">
        <input type="hidden" name="type" value="${type}">
        <c:if test="${empty sysUser}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>

                <div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                            data-width="272"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty sysUser}">
            <div class="form-group">
                <label class="col-xs-3 control-label">账号</label>

                <div class="col-xs-6 label-text">
                        ${sysUser.realname}-${sysUser.code}
                    <input type="hidden" name="userId" value="${sysUser.id}">
                </div>
            </div>
        </c:if>
        <c:if test="${type==2}">
            <div class="form-group">
                <label class="col-xs-3 control-label">政治面貌</label>
                <div class="col-xs-6 label-text">
                    中共党员
                </div>
            </div>
        </c:if>
        <c:if test="${type==1}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>民主党派</label>

                <div class="col-xs-6">
                    <select required data-rel="select2" name="classId" data-width="272" data-placeholder="请选择民主党派">
                        <option></option>
                        <jsp:include page="/WEB-INF/jsp/base/metaType/dpTypes.jsp?dp=1"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=classId]").val(${cadreParty.classId});
                    </script>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">党派加入时间</label>
            <div class="col-xs-6">
                    <c:if test="${_p_hasPartyModule}">
                        <div class="input-group" style="width: 130px">
                        <input class="form-control date-picker" name="_growTime" type="text" placeholder="yyyy.mm.dd"
                               data-date-format="yyyy.mm.dd"
                               value="${cm:formatDate(cadreParty.growTime,'yyyy.MM.dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </c:if>
                    <c:if test="${!_p_hasPartyModule}">
                        <div class="input-group" style="width: 110px">
                        <input class="form-control date-picker" name="_growTime" type="text"
                               data-date-min-view-mode="1" placeholder="yyyy.mm"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadreParty.growTime,'yyyy.MM')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </c:if>
            </div>
        </div>
        <c:if test="${type==1}">
            <div class="form-group">
                <label class="col-xs-3 control-label">担任党派职务</label>
                <div class="col-xs-6">
                    <textarea class="form-control" name="post" rows="3">${cadreParty.post}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">是否第一民主党派</label>
                <div class="col-xs-6">
                    <label>
                        <input name="isFirst" ${(empty cadreParty || cadreParty.isFirst)?"checked":""}
                               type="checkbox"/>
                        <span class="lbl"></span>
                    </label>
                    <span class="help-block">注：仅针对拥有多个民主党派的情况，如果只有一个民主党派请选“是”</span>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark" rows="2">${cadreParty.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定
    </button>
</div>

<script>

    $("#modal input[name=isFirst]").bootstrapSwitch();
    $('textarea.limited').inputlimiter();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $.register.date($('.date-picker'))
    $('[data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>