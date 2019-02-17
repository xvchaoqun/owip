<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${(empty cadreParty)?"添加":"更新"}${type==1?'民主党派':'党员'}干部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreParty_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreParty.id}">
        <input type="hidden" name="type" value="${type}">
        <c:if test="${empty sysUser}">
            <div class="form-group">
                <label class="col-xs-3 control-label">账号</label>

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
                <label class="col-xs-3 control-label">民主党派</label>

                <div class="col-xs-6">
                    <select required data-rel="select2" name="classId" data-width="272" data-placeholder="请选择民主党派">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_democratic_party"/>
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
                <div class="input-group" style="width: 120px">
                    <input class="form-control date-picker" name="growTime" type="text"
                           data-date-format="yyyy.mm" value="${cm:formatDate(cadreParty.growTime,'yyyy.MM')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <c:if test="${type==1}">
            <div class="form-group">
                <label class="col-xs-3 control-label">担任党派职务</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="post" value="${cadreParty.post}">
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark" rows="5">${cadreParty.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value='${(empty cadreParty)?"添加":"更新"}'/>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'))
    $('[data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>