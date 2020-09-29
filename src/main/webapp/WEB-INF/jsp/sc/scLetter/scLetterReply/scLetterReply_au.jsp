<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au" style="width: 1510px">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    纪委回复文件预览
                    <div style="position: absolute; left:180px;top:0px;">
                        <form action="${ctx}/sc/scLetter_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传纪委回复文件
                            </button>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right" style="right:15px;">

                        <a href="javascript:;" class="hideView btn btn-xs btn-success">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/pdf_preview?type=html&path=${cm:sign(scLetterReply.filePath)}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au" style="width: 700px;">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        ${scLetterReply!=null?"修改":"添加"}纪委回复情况
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scLetterReply_au" autocomplete="off" disableautocomplete id="modalForm" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <input type="hidden" name="id" value="${scLetterReply.id}">
                                <input type="hidden" name="letterId" value="${letterId}">
                                <input type="hidden" name="filePath" value="${cm:sign(scLetterReply.filePath)}">
                                <input type="hidden" name="fileName" value="${scLetterReply.fileName}">

                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>类型</label>

                                    <div class="col-xs-6">
                                        <select required data-rel="select2" name="type" data-placeholder="请选择" data-width="240">
                                            <option></option>
                                            <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_sc_letter_reply_type').id}"/>
                                        </select>
                                        <script type="text/javascript">
                                            $("#modalForm select[name=type]").val(${scLetterReply.type});
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">回复文件编号</label>

                                    <div class="col-xs-6"  style="width: 263px">
                                        <input class="form-control digits" type="text" name="num"
                                               value="${scLetterReply.num}">
                                        <span class="label-inline"> * 留空自动生成</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>回复日期</label>

                                    <div class="col-xs-6">
                                        <div class="input-group date" style="width:240px" data-date-format="yyyy-mm-dd" >
                                            <input required class="form-control" name="replyDate"
                                                   type="text"
                                                   value="${cm:formatDate(scLetterReply.replyDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-4 control-label">回复情况：</label>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div style="padding: 10px;">
                                            <div id="itemList" style="max-height: 407px;overflow-y: auto;">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">备注</label>

                                    <div class="col-xs-6">
                                        <textarea class="form-control limited"
                                                  name="remark">${scLetterReply.remark}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    ${scLetterReply!=null?"修改":"添加"}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/template" id="itemListTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2 table-center">
        <thead>
        <tr>
            <th width="80">姓名</th>
            <th width="80">工作证号</th>
            <th width="250">纪委回复情况</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        {{_.each(users, function(u, idx){ }}
        <tr data-user-id="{{=u.userId}}">
            <td>{{=u.realname}}</td>
            <td>{{=u.code}}</td>
            <td>
                 <textarea class="form-control limited">{{=u.content}}</textarea>
            </td>
            <td style="text-align: left">
                <select data-rel="select2" class="content" data-placeholder="请选择" data-width="240">
                    <option></option>
                    <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_sc_letter_reply_content').id}"/>
                </select>
            </td>
        </tr>
        {{});}}
        </tbody>
    </table>
</script>
<style>
    .select2-hidden-accessible {
        position: relative !important;}
</style>
<script>
    $(document).on('change', 'select.content', function(){
        var content = $(this).find("option:selected").text();
        //alert(content)
        $(this).closest("tr").find("textarea").val(content);
    })
    var selectedItems = ${cm:toJSONArrayWithFilter(itemList, "userId,code,realname,content")};
    $("#itemList").append(_.template($("#itemListTpl").html())({users: selectedItems}));

    $.register.user_select($('#modalForm [data-rel="select2-ajax"]'));
    $.register.date($('.input-group.date'));
    $('#modalForm [data-rel="select2"]').select2();
    $("#upload-file").change(function () {
        if ($("#upload-file").val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var viewHtml = $("#dispatch-file-view").html()
            $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $("#dispatch-file-view").load("${ctx}/pdf_preview?type=html&path=" + ret.filePath);

                        $("#modalForm input[name=filePath]").val(ret.filePath);
                        $("#modalForm input[name=fileName]").val(ret.fileName);
                    } else {
                        $("#dispatch-file-view").html(viewHtml)
                    }
                    $btn.button('reset');
                    $this.removeAttr("disabled");
                }
            });
            $this.attr("disabled", "disabled");
        }
    });

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var items = [];
            $.each(selectedItems, function (i, user) {
                var content = $("#itemList tr[data-user-id="+user.userId+"] textarea").val();
                var item ={userId:user.userId, content:content};
                items.push(item);
            });
            if ($.trim($("#modalForm input[name=filePath]").val()) == "") {
                SysMsg.warning("请上传纪委回复文件");
                return;
            }
            //console.log(JSON.stringify(items))
            $(form).ajaxSubmit({
                data: {items: $.base64.encode(JSON.stringify(items))},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>