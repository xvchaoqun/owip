<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    培训方案预览
                    <div style="position: absolute; left:130px;top:8px;">
                        <form action="${ctx}/cet/cetProject_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传培训方案（PDF文件）
                            </button>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right ">

                        <a href="javascript:;" class="hideView btn btn-xs btn-success"
                           style="margin-right: 10px; top: -5px;">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/swf/preview?type=html&path=${cetProject.pdfFilePath}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        ${cetProject!=null?"修改":"添加"}${CET_PROJECT_TYPE_MAP.get(type)}
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/cet/cetProject_au" id="modalForm" method="post">
                            <input type="hidden" name="id" value="${cetProject.id}">
                            <input type="hidden" name="type" value="${type}">
                            <input type="hidden" name="fileName" value="${cetProject.fileName}">
                            <input type="hidden" name="pdfFilePath" value="${cetProject.pdfFilePath}">
                            <div class="form-group">
                                <label class="col-xs-3 control-label">年度</label>
                                <div class="col-xs-8">
                                    <div class="input-group">
                                        <input required class="form-control date-picker" placeholder="请选择年份"
                                               name="year"
                                               type="text"
                                               data-date-format="yyyy" data-date-min-view-mode="2"
                                               value="${empty cetProject.year?_thisYear:cetProject.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">培训时间</label>
                                <div class="col-xs-8">
                                    <input required class="form-control date-picker" name="startDate"
                                           type="text" style="width: 140px;float: left"
                                           data-date-format="yyyy-mm-dd"
                                           value="${cm:formatDate(cetProject.startDate,'yyyy-MM-dd')}"/>
                                    <div style="float: left;margin: 5px 5px 0 5px;"> 至 </div>
                                    <input required class="form-control date-picker" name="endDate"
                                           type="text" style="width: 140px;float: left"
                                           data-date-format="yyyy-mm-dd"
                                           value="${cm:formatDate(cetProject.endDate,'yyyy-MM-dd')}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">培训班名称</label>
                                <div class="col-xs-8">
                                    <input required class="form-control" type="text" name="name" value="${cetProject.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">专题分类</label>
                                <div class="col-xs-8">
                                    <select required name="projectTypeId" data-rel="select2"
                                            data-width="308"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach var="projectType" items="${projectTypes}">
                                            <option value="${projectType.id}">
                                                    ${projectType.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#modalForm select[name=projectTypeId]").val("${cetProject.projectTypeId}");
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">参训人员类型</label>
                                <div class="col-xs-9 label-text" id="traineeTypeDiv">
                                    <c:forEach items="${traineeTypeMap}" var="entity">
                                        <label>
                                            <input name="traineeTypeIds[]" type="checkbox" value="${entity.key}"> ${entity.value.name}&nbsp;
                                            <span class="lbl"></span>
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                            <%--<div class="form-group">
                                <label class="col-xs-3 control-label">文件名</label>
                                <div class="col-xs-6">
                                        <input required class="form-control" type="text" name="fileName" value="${cetProject.fileName}">
                                </div>
                            </div>--%>

                            <div class="form-group">
                                <label class="col-xs-3 control-label">培训方案(WORD文件)</label>
                                <div class="col-xs-8">
                                    <input class="form-control" type="file" name="_wordFilePath"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">总学时</label>
                                <div class="col-xs-8">
                                    <input required class="form-control period" type="text" name="period" value="${cetProject.period}">
                                </div>
                            </div>
                            <%--<div class="form-group">
                                <label class="col-xs-3 control-label">达到结业要求的学时数</label>
                                <div class="col-xs-6">
                                        <input required class="form-control period" type="text" name="requirePeriod" value="${cetProject.requirePeriod}">
                                </div>
                            </div>--%>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">备注</label>
                                <div class="col-xs-8">
					                <textarea class="form-control limited" name="remark">${cetProject.remark}</textarea>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer center">
                            <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
                            <button id="submitBtn" class="btn btn-primary" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
                                <i class="fa fa-check"></i> <c:if test="${cetProject!=null}">确定</c:if><c:if test="${cetProject==null}">添加</c:if></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
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
                        $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.pdfFilePath));
                        $("#modalForm input[name=fileName]").val(ret.fileName);
                        $("#modalForm input[name=pdfFilePath]").val(ret.pdfFilePath);
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

    var traineeTypeIds = ${cm:toJSONArray(traineeTypeIds)};
    for(i in traineeTypeIds){
        $('#modalForm input[name="traineeTypeIds[]"][value="'+ traineeTypeIds[i] +'"]').prop("checked", true);
    }
    $("#submitBtn").click(function(){
        if($('#modalForm input[name="traineeTypeIds[]"]:checked').length==0){
            $.tip({
                $target: $("#traineeTypeDiv"),
                at: 'top center', my: 'bottom center',
                msg: "请选择参训人员类型。"
            });
        }
        $("#modalForm").submit();return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            if($('#modalForm input[name="traineeTypeIds[]"]:checked').length==0){
                return false;
            }

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){

                        SysMsg.success("提交成功。",function(){
                            $.hideView();
                        })
                        //$("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $.register.date($('.date-picker'));
    $('textarea.limited').inputlimiter();
    $.fileInput($("#modalForm input[name=_pdfFilePath]"),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    $.fileInput($("#modalForm input[name=_wordFilePath]"),{
        allowExt: ['doc', 'docx'],
        allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
</script>