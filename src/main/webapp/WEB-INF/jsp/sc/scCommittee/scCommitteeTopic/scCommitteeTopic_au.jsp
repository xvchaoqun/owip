<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">党委常委会议题</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8 row">
                <form class="form-horizontal" action="${ctx}/sc/scCommitteeTopic_au" id="modalForm" method="post"
                      enctype="multipart/form-data">
                    <div style="width: 500px;float: left;margin-right: 25px">
                        <div class="widget-box" style="height: 500px;">
                            <div class="widget-header">
                                <h4 class="smaller">
                                    ${scCommitteeTopic!=null?"修改":"添加"}
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="row">
                                        <input type="hidden" name="id" value="${scCommitteeTopic.id}">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label" style="white-space: nowrap">党委常委会</label>
                                            <div class="col-xs-8">
                                                <select required name="committeeId" data-rel="select2"
                                                        data-width="324"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach var="scCommittee" items="${scCommittees}">
                                                        <option value="${scCommittee.id}">党委常委会[${cm:formatDate(scCommittee.holdDate, "yyyyMMdd")}]号</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#modalForm select[name=committeeId]").val("${committeeId}");
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">议题序号</label>
                                            <div class="col-xs-8">
                                                 <input required class="form-control digits" name="seq" value="${scCommitteeTopic.seq}"/>
                                                <span class="help-block">* 留空自动生成</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">议题名称</label>
                                            <div class="col-xs-8">
                                                 <textarea class="form-control noEnter" rows="3"
                                                           name="name">${scCommitteeTopic.name}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">表决情况</label>
                                            <div class="col-xs-7 label-text">
                                                <input type="checkbox" class="big" name="hasVote" ${scCommitteeTopic.hasVote?'checked':''}> 干部选拔任用表决
                                                <input type="checkbox" class="big" name="hasOtherVote"  ${scCommitteeTopic.hasOtherVote?'checked':''}> 其他事项表决
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">表决票</label>
                                            <div class="col-xs-8">
                                                <input class="form-control" type="file" name="_voteFilePath"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">备注</label>
                                            <div class="col-xs-8">
                                             <textarea class="form-control limited" rows="7"
                                                       name="remark">${scCommitteeTopic.remark}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="width: 500px;float: left;margin-right: 25px">
                        <div class="widget-box">
                            <div class="widget-header">
                                <h4 class="smaller">
                                    议题内容
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <textarea id="contentId">${scCommitteeTopic.content}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="width: 500px;float: left;margin-right: 25px">
                        <div class="widget-box">
                            <div class="widget-header">
                                <h4 class="smaller">
                                    议题讨论备忘
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <textarea id="memoId">${scCommitteeTopic.memo}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="clear: both"></div>
                    <div class="clearfix form-actions center">
                        <button class="btn btn-info btn-sm"
                                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                                type="button" id="submitBtn">
                            <i class="ace-icon fa fa-check "></i>
                            ${scCommitteeTopic!=null?"修改":"添加"}
                        </button>
                    </div>
                </form>
            </div>
            <!-- /.widget-main -->
        </div>
        <!-- /.widget-body -->
    </div>
    <!-- /.widget-box -->
</div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    $.fileInput($("#modalForm input[name=_voteFilePath]"),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

    var contentKe = KindEditor.create('#contentId', {
        //cssPath:"${ctx}/css/ke.css",
        items: ["wordpaste", "source", "|", "fullscreen"],
        height: '450px',
        width: '480px',
        minWidth: 480,
        filterMode:false,
        pasteType:0
    });

    var memoKe = KindEditor.create('#memoId', {
        //cssPath:"${ctx}/css/ke.css",
        items: ["wordpaste", "source", "|", "fullscreen"],
        height: '450px',
        width: '480px',
        minWidth: 480,
        filterMode:false,
        pasteType:0
    });

    //$.register.date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();

    $("#submitBtn").click(function () {
        if($("#modalForm :checkbox:checked").length==0){
            $.tip({
                $target: $("#modalForm [name=hasVote]").closest("div"),
                at: 'right center', my: 'left center', type: 'info',
                msg: "请选择表决情况。"
            });
        }
        if($.trim(contentKe.html())==''){
            $.tip({
                $target: $("#modalForm #contentId").closest("div").find(".ke-container"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请填写议题内容。"
            });
        }

        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            if($("#modalForm :checkbox:checked").length==0){
                return;
            }
            var content = $.trim(contentKe.html());
            if(content==''){
                return;
            }
            var $btn = $("#submitBtn").button('loading');

            $(form).ajaxSubmit({
                data: {content: contentKe.html(), memo: memoKe.html()},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>