<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cgLeader!=null?'编辑':'添加'}办公室主任</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cg/cgLeader_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cgLeader.id}">
        <input type="hidden" name="teamId" value="${teamId}">
        <input type="hidden" name="isCurrent" value="${isCurrent}">
        <div class="form-group">
            <label class="col-xs-3 control-label"> 是否席位制</label>
            <div class="col-xs-6">
                <input type="checkbox" name="isPost" ${cgLeader.isPost?"checked":""}/>
            </div>
        </div>
        <div class="form-group hiddenUnitPostId" hidden>
            <label class="col-xs-3 control-label"><span class="star">*</span> 关联岗位</label>
            <div class="col-xs-6">
                <select name="unitPostId" class="form-control"
                        data-width="272"
                        data-rel="select2-ajax"
                        data-ajax-url="${ctx}/unitPost_selects"
                        data-placeholder="请选择">
                    <option value="${cgLeader.unitPostId}">${unitPost.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 办公室主任</label>
            <div class="col-xs-6">
                <select required name="userId" class="form-control"
                        data-width="272"
                        data-rel="select2-ajax"
                        data-ajax-url="${ctx}/cadre_selects?key=1"
                        data-placeholder="请选择">
                    <option value="${cgLeader.userId}">${sysUser.realname}</option>
                </select>
                <label hidden name="remind" style="color: red">该岗位没有干部。</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 确定时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 272px">
                    <input class="form-control date-picker"
                           name="confirmDate"
                           type="text"
                           data-date-format="yyyy.mm.dd"
                           value="${cm:formatDate(cgLeader.confirmDate,'yyyy.MM.dd')}"/>
                    <span class="input-group-addon">
                        <i class="fa fa-calendar bigger-110"></i>
                    </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 联系方式</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="phone" value="${cgLeader.phone}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 备注</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="remark" value="${cgLeader.remark}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cgLeader?'确定':'添加'}
    </button>
</div>
<script>

    var isPostInput = $("input[name=isPost]");
    var unitPostIdSelect = $("select[name=unitPostId]");
    var userIdSelect = $("select[name=userId]");

    isPostInput.on("switchChange.bootstrapSwitch",function (event, state) {
    relatePostChange();
    clearSelect();
});

function relatePostChange(){

    if (${empty cgLeader}) clearSelect();//添加时，是否席位制改变，清空选择的值

    if (isPostInput.bootstrapSwitch("state")) {//是席位制

        unitPostIdSelect.attr("required","required");
        userIdSelect.prop("disabled",true);
        $(".hiddenUnitPostId").show();
    }else {

        unitPostIdSelect.removeAttr("required");
        userIdSelect.prop("disabled",false);
        $(".hiddenUnitPostId").hide();
    }
}

relatePostChange();

function clearSelect(){

    unitPostIdSelect.val(null).trigger("change");
    userIdSelect.val(null).trigger("change");
}

    unitPostIdSelect.on("change",function () {

    var data = $(this).select2("data")[0];

    var up = undefined;

    if (data != undefined) up = data['up'];

    if (up != undefined && up.cadre !=undefined && up.cadre.user !=undefined){//如果选择的岗位有现任干部

        var option = new Option(up.cadre.user.realname, up.cadre.user.id, true, true);
        userIdSelect.append(option).trigger('change');
        $("label[name=remind]").hide();
    }else if (unitPostIdSelect.val()!='' && unitPostIdSelect.val()!=null) {//如果选择的岗位没有现任干部

        userIdSelect.val(null).trigger('change');
        $("label[name=remind]").show();
    }else if(unitPostIdSelect.val()=='' || unitPostIdSelect.val()==null){//如果没有选择岗位

        userIdSelect.val(null).trigger("change");
        $("label[name=remind]").hide();
    }
});

    $("#submitBtn").click(function(){

        userIdSelect.prop("disabled",false);
        $("#modalForm").submit();
        if (isPostInput.bootstrapSwitch("state"))userIdSelect.prop("disabled",true);
        return false;});
    $("#modalForm").validate({

        submitHandler: function (form) {

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>