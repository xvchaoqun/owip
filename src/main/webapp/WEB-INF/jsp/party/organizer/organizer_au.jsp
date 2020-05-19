<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_ORGANIZER_TYPE_MAP" value="<%=OwConstants.OW_ORGANIZER_TYPE_MAP%>"/>
<c:set var="OW_ORGANIZER_TYPE_SCHOOL" value="<%=OwConstants.OW_ORGANIZER_TYPE_SCHOOL%>"/>
<c:set var="OW_ORGANIZER_TYPE_UNIT" value="<%=OwConstants.OW_ORGANIZER_TYPE_UNIT%>"/>
<c:set var="OW_ORGANIZER_TYPE_BRANCH" value="<%=OwConstants.OW_ORGANIZER_TYPE_BRANCH%>"/>
<c:set var="OW_ORGANIZER_STATUS_MAP" value="<%=OwConstants.OW_ORGANIZER_STATUS_MAP%>"/>
<c:set var="OW_ORGANIZER_STATUS_NOW" value="<%=OwConstants.OW_ORGANIZER_STATUS_NOW%>"/>
<c:set var="OW_ORGANIZER_STATUS_LEAVE" value="<%=OwConstants.OW_ORGANIZER_STATUS_LEAVE%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${organizer!=null?'编辑':'添加'}${OW_ORGANIZER_STATUS_MAP.get(status)}${OW_ORGANIZER_TYPE_MAP.get(type)}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/organizer_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${organizer.id}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="status" value="${status}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 年度</label>
            <div class="col-xs-6">
                <div class="input-group date" data-date-format="yyyy" data-date-min-view-mode="2" style="width: 130px">
                    <input required class="form-control" placeholder="请选择" name="year"
                           type="text" value="${empty orginizer?_thisYear:organizer.year}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-width="272"
                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                        name="userId" data-placeholder="请输入账号或姓名或工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.username}</option>
                </select>
            </div>
        </div>
        <c:if test="${type!=OW_ORGANIZER_TYPE_SCHOOL}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 联系党委</label>
                <div class="col-xs-6">
                    <select required data-width="272" data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择">
                        <option value="${party.id}">${party.name}</option>
                    </select>
                </div>
            </div>
            <c:if test="${type==OW_ORGANIZER_TYPE_BRANCH}">
                <div class="form-group" style="${(empty branch)?'display: none':''}"
                     id="branchDiv">
                    <label class="col-xs-3 control-label"><span class="star">*</span> 联系党支部</label>
                    <div class="col-xs-6">
                        <select required data-width="272"
                                data-ajax-url="${ctx}/branch_selects?del=0&auth=1"
                                name="branchId" data-placeholder="请选择">
                            <option value="${branch.id}">${branch.name}</option>
                        </select>
                    </div>
                </div>
            </c:if>
            <script>
                $.register.party_branch_select($("#modalForm"), "branchDiv",
                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}", "partyId", "branchId", true);
            </script>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 联系方式</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="phone" value="${organizer.phone}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 任职时间</label>
            <div class="col-xs-6">
                <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                    <input class="form-control" name="appointDate" type="text"
                           value="${cm:formatDate(organizer.appointDate,'yyyy.MM.dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <c:if test="${status != OW_ORGANIZER_STATUS_NOW}">
            <div class="form-group">
                <label class="col-xs-3 control-label"> 离任时间</label>
                <div class="col-xs-6">
                    <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                        <input class="form-control" name="dismissDate" type="text"
                               value="${cm:formatDate(organizer.dismissDate,'yyyy.MM.dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty organizer && status == OW_ORGANIZER_STATUS_NOW}">
            <div class="form-group">
                <label class="col-xs-3 control-label"> 是否同步最新信息</label>
                <div class="col-xs-6">
                    <input type="checkbox" name="syncBaseInfo"/>
                    <span class="help-block">注：最新信息是指入党时间、编制类别等</span>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" type="text" name="remark">${organizer.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${empty organizer && status == OW_ORGANIZER_STATUS_LEAVE}">
        <div class="note">
            注：直接添加离任信息时，系统将同步一条数据至历史任职情况
        </div>
    </c:if>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty organizer?'确定':'添加'}</button>
</div>
<script>

   $('#modalForm select[name="userId"]').on('change',function(){

        var userId=$('#modalForm select[name="userId"]').val();
        if ($.isBlank(userId)){
            $("#modalForm input[name=phone]").val('');
            return;
        }
        var mobile = $('#modalForm select[name="userId"]').select2("data")[0].mobile;
        $("#modalForm input[name=phone]").val(mobile);

    });
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
    $("#modalForm :checkbox").bootstrapSwitch();
  /*  $.register.del_select($('#modalForm select[name=partyId]'));*/
    $.register.user_select($('#modalForm select[name=userId]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.input-group.date'));
</script>