<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" style="width: 900px">
            <form class="form-horizontal" action="${ctx}/pcsProposal_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                <input type="hidden" name="id" value="${pcsProposal.id}">
                <input type="hidden" name="status" value="${pcsProposal.status}">
                <table class="table table-bordered table-unhover">
                    <tr>
                        <td width="100">提案人姓名</td>
                        <td>${candidate.realname}</td>
                        <td width="100">工作单位</td>
                        <td>${candidate.unitName}</td>
                    </tr>
                    <tr>
                        <td>联系电话</td>
                        <td><t:mask src="${candidate.mobile}" type="mobile"/></td>
                        <td>工作证号</td>
                        <td>${candidate.code}</td>
                    </tr>
                    <tr>
                        <td>邮 箱</td>
                        <td><t:mask src="${candidate.email}" type="email"/></td>
                        <td>提案日期</td>
                        <td>${cm:formatDate(empty pcsProposal?now:pcsProposal.createTime, "yyyy-MM-dd")}</td>
                    </tr>
                    <tr>
                        <td><span class="star">*</span>标 题</td>
                        <td colspan="3">
                            <input required class="form-control" type="text" name="name" value="${pcsProposal.name}">
                        </td>
                    </tr>
                    <tr>
                        <td><span class="star">*</span>关 键 字</td>
                        <td colspan="3">
                            <div class="input-group">
                            <input required class="form-control" data-role="tagsinput" type="text" name="keywords"
                                   value="${pcsProposal.keywords}" placeholder="输入每个关键字后按回车键保存...">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="star">*</span>提案类型</td>
                        <td colspan="3">
                            <div class="input-group">
                                <c:forEach items="${prTypes}" var="_type">
                                    <label>
                                        <input required name="type" ${pcsProposal.type==_type.id?'checked':''}
                                               type="radio" class="ace" value="${_type.id}"/>
                                        <span class="lbl" style="padding-right: 5px;"> ${_type.name}</span>
                                    </label>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 50px"><span class="star">*</span>提案内容及建议</td>
                        <td colspan="3">
                            <textarea required name="content" rows="10" style="width: 100%">${pcsProposal.content}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>邀请附议人
                            <a href="javascript:;"
                               class="popupBtn btn btn-info btn-xs"
                               data-width="900"
                               data-url="${ctx}/pcsProposal_candidates">
                                <i class="fa fa-plus-circle"></i> 邀请附议人</a>
                        </td>
                        <td colspan="3">
                            <table id="inviteTable"  class="table table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>工作证号</th>
                                    <th>代表姓名</th>
                                    <th>所在单位</th>
                                    <th>手机号</th>
                                    <th>邮箱</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                                </table>
                        </td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3">
                            <c:forEach var="file" items="${pcsProposal.files}">
                                <div class="file">
                                    <t:preview filePath="${file.filePath}" fileName="${file.fileName}" label="${cm:substr(file.fileName, 0, 15, '...')}"/>
                                    <a href="javascript:;" class="confirm"
                                       data-url="${ctx}/pcsProposal_batchDelFiles?ids=${file.id}"
                                       data-msg="确认删除该附件？"
                                       data-callback="_delFileCallback">删除</a></div>
                            </c:forEach>
                            <div class="files">
                                <input class="form-control" type="file" name="_files"/>
                            </div>
                            <button type="button" onclick="addFile()"
                                    class="addFileBtn btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <div class="modal-footer center">
                                <c:if test="${empty pcsProposal || pcsProposal.status==PCS_PROPOSAL_STATUS_SAVE}">
                                <a href="#" id="saveBtn" data-dismiss="modal" class="btn btn-primary btn-lg"><i
                                        class="fa fa-save"></i> 暂存</a>
                                </c:if>
                                <button type="button" id="submitBtn" data-loading-text="提交中..." class="btn btn-success btn-lg"><i class="fa fa-check"></i> 提交
                                </button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<style>
    .table.table-unhover > tbody > tr > td:nth-of-type(odd) {
        text-align: center;
        font-size: 18px;
        font-weight: bolder;
        vertical-align: middle;
    }

    .bootstrap-tagsinput {
        width: 800px !important;
    }

    .bootstrap-tagsinput input {
        width: 250px !important;
    }

    textarea {
        text-indent: 32px;
        line-height: 25px;
        /*font-family: "Arial";*/
        font-size: 16px;
        padding: 2px;
        margin: 2px;
        border: none;
        background: #FFFFFF url(/${ctx}img/dot_25.gif);
        resize: none;
    }

    .files{
        width: 300px;
        float: left;
        margin-top: 20px;
    }
    .addFileBtn{
        position: absolute;
        margin: 22px 20px;
    }
    .file{
        height: 25px;
        line-height: 25px;
        background: #FFFFFF url(/img/dot_25.gif);
        width: 350px;
        /*border-bottom: dotted 1px;*/
    }
    .file a.confirm{
        float: right;
    }
    .file:hover{
        background-color:#eadda9;
    }
</style>
<link href="${ctx}/extend/css/bootstrap-tagsinput.css" rel="stylesheet"/>
<script src="${ctx}/extend/js/bootstrap-tagsinput.min.js"></script>
<script type="text/template" id="seconder_tpl">
        {{_.each(users, function(u, idx){ }}
        <tr data-user-id="{{=u.userId}}">
            <td>{{=u.code}}</td>
            <td>{{=u.realname}}</td>
            <td>{{=u.unitName}}</td>
            <td>{{=u.mobile}}</td>
            <td>{{=u.email}}</td>
            <td>
                <button ${!allowModify?"":"disabled"} class="delRowBtn btn btn-danger btn-xs"><i class="fa fa-minus-circle"></i> 移除</button>
            </td>
        </tr>
        {{});}}
</script>
<script>

    $.register.fancybox();
    $("#inviteTable tbody").append(_.template($("#seconder_tpl").html())({users: ${cm:toJSONArray(candidates)}}));

    $(document).on("click", ".delRowBtn", function () {

       $(this).closest("tr").remove();
    })

    $.fileInput($('input[type=file]'), {
        no_file: '请上传pdf或图片文件...',
        allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });

    function addFile() {
        var _file = $('<input class="form-control" type="file" name="_files" />');
        $(".files").append(_file);
        $.fileInput(_file, {
            no_file: '请上传pdf或图片文件...',
            allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
            allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
        });
        return false;
    }

    function _delFileCallback(btn){

        $(btn).closest(".file").remove();
    }

    // $('#form-field-tags').tagsinput()
    $('input[data-role=tagsinput]').tagsinput({
        confirmKeys: [13]
    });

    $("#saveBtn").click(function(){
        $("#modalForm input[name=status]").val("${PCS_PROPOSAL_STATUS_SAVE}");
        $("#modalForm").submit();
        return false;
    });
    $("#submitBtn").click(function(){
        $("#modalForm input[name=status]").val("${PCS_PROPOSAL_STATUS_INIT}");
        $("#modalForm").submit();
        return false;
    });

    $("#modalForm").validate({

        submitHandler: function (form) {
            var inviteIds = [];
            $("#inviteTable tbody tr").each(function(){
                inviteIds.push($(this).data("user-id"));
            });
            //alert(inviteIds)
            var data = {inviteIds:inviteIds};
            var status = $("#modalForm input[name=status]").val();
            if (status == "${PCS_PROPOSAL_STATUS_SAVE}") {
                $("#saveBtn").button('loading');
                _ajaxSubmit(form, data);
            } else if(status == "${PCS_PROPOSAL_STATUS_INIT}"){
                _confirmSubmit(form, data);
            }
        }
    });

    function _ajaxSubmit(form, data) {

        $(form).ajaxSubmit({
            data: data,
            success: function (ret) {
                if (ret.success) {

                    var status = $("#modalForm input[name=status]").val();
                    if (status == "${PCS_PROPOSAL_STATUS_SAVE}") {

                        $("#saveBtn").button("reset");
                        //$("#modalForm input[name=id]").val(ret.id);
                        //$('input[type=file]').ace_file_input('reset_input');
                        $.openView({url:"${ctx}/pcsProposal_au?id=" + ret.id, callback:function(){
                            $.tip({
                                $target: $("#saveBtn"),
                                at: 'top center', my: 'bottom center', type: 'success',
                                msg: "填写内容已暂存，请及时填写完整并提交。"
                            });
                        }});
                    } else if(status == "${PCS_PROPOSAL_STATUS_INIT}"){

                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }

                }
            }
        });
    }

    function _confirmSubmit(form, data) {

        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> 确认无误',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<i class="fa fa-reply"></i> 返回修改',
                    className: 'btn-default btn-show'
                }
            },
            message: "是否确认提交该提案？",
            callback: function (result) {
                if (result) {
                    $("#submitBtn").button('loading');
                    _ajaxSubmit(form, data);
                }
            },
            title: '提交确认'
        });
    }

    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>