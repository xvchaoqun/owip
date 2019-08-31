<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=DrConstants.DR_MEMBER_STATUS_NOW%>" var="DR_MEMBER_STATUS_NOW"/>
<div class="modal-header">
    <button type="button" class="close closeBtn">&times;</button>
    <h3>${drOffline!=null?'编辑':'添加'}线下民主推荐</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/drOffline_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOffline.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    <input required style="width: 80px;" autocomplete="off" class="form-control date-picker"
                           placeholder="请选择年份"
                           name="year" type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2"
                           value="${empty drOffline.year?_thisYear:drOffline.year}"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>推荐类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" data-width="273"
                        name="type" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_dr_recommend_type"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=type]").val(${drOffline.type});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>推荐日期</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker"
                           name="recommendDate"
                           type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${empty drOffline.recommendDate?_today:(cm:formatDate(drOffline.recommendDate,'yyyy-MM-dd'))}"/>
                    <span class="input-group-addon"> <i
                            class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">推荐岗位</label>
            <div class="col-xs-6">
                <input type="hidden" name="recordId" value="${scRecord.id}">
                <input id="postName" readonly class="form-control" type="text" value="${scRecord.postName}">
            </div>
            <button type="button" class="btn btn-sm btn-success" id="selectPostBtn"><i class="fa fa-plus"></i> 选择岗位
            </button>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>推荐组负责人</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/drMember_selects"
                        name="chiefMemberId" data-placeholder="请输入账号或姓名或学工号" data-width="270">
                    <option value="${chiefMember.id}" delete="${chiefMember.status!=DR_MEMBER_STATUS_NOW}">${chiefMember.user.realname}-${chiefMember.user.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${drOffline.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button class="closeBtn btn btn-default">取消</button>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${drOffline!=null}">确定</c:if><c:if
            test="${drOffline==null}">添加</c:if></button>
</div>
<style>
    .popover {
        width:auto;
        max-width:900px;
    }
</style>
<script>
    $("#modal .closeBtn").click(function () {
        $('#modalForm #postName').popover('hide');
        $("#modal").modal('hide');
    })
    function _closePopover(){
        $('#modalForm #postName')
            .popover('hide');
    }
    $('#modalForm #postName')
        .popover({
            container:'body',
            placement: "bottom",
            title: '请选择岗位（从干部选任纪实-单个岗位调整（选拔方式为“民主推荐”）中选择岗位）<a href="javascript:;" onclick="_closePopover()" class="pull-right">关闭</a>',
            html:true,
            content: '<div id="popoverContent" style="width:850px;text-align: center;"><i class="fa fa-spinner fa-spin"></i> 加载中...</div>',
            trigger: "manual"});
    $("#modalForm #selectPostBtn").click(function () {
        $('#modalForm #postName').popover('show');
    })
    $('#modalForm #postName').on('shown.bs.popover', function () {
        $.get("${ctx}/drOffline_selectPost",function(html){
            $("#popoverContent").html(html);
        })
    })
    function _drOffline_selectPost(scRecordId, postName){

        $("#modalForm input[name=recordId]").val(scRecordId);
        $("#postName").val(postName);
        _closePopover()
    }

    $("#submitBtn").click(function () {
        if($.trim($("#modalForm input[name=recordId]").val())==''){
            $.tip({
                $target: $("#selectPostBtn").closest("form").find("button"),
                at: 'right center', my: 'left center', type: 'info',
                msg: "请选择推荐岗位。"
            });
        }
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
    $.register.del_select($('select[name=chiefMemberId]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>