<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    培训方案预览
                    <div style="position: absolute; left:140px;top:0px;">
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
                        <c:import url="${ctx}/pdf_preview?type=html&path=${cetProject.pdfFilePath}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        ${cetProject!=null?"修改":"添加"}${CET_PROJECT_TYPE_MAP.get(type)}
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/cet/cetProject_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                            <input type="hidden" name="id" value="${cetProject.id}">
                            <input type="hidden" name="type" value="${type}">
                            <input type="hidden" name="fileName" value="${cetProject.fileName}">
                            <input type="hidden" name="pdfFilePath" value="${cetProject.pdfFilePath}">
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>年度</label>
                                <div class="col-xs-8">
                                    <div class="input-group" style="width: 90px">
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
                                <label class="col-xs-3 control-label"><span class="star">*</span>培训时间</label>
                                <div class="col-xs-8">
                                    <div class="input-group">
                                    <input required class="form-control date-picker" name="startDate"
                                           type="text" style="width: 90px;float: left"
                                           data-date-format="yyyy-mm-dd"
                                           value="${cm:formatDate(cetProject.startDate,'yyyy-MM-dd')}"/>
                                    <div style="float: left;margin: 5px 5px 0 5px;"> 至 </div>
                                    <input required class="form-control date-picker" name="endDate"
                                           type="text" style="width: 90px;float: left"
                                           data-date-format="yyyy-mm-dd"
                                           value="${cm:formatDate(cetProject.endDate,'yyyy-MM-dd')}"/>
                                        </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>培训班名称</label>
                                <div class="col-xs-8">
                                    <textarea required class="form-control" name="name">${cetProject.name}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>培训班类型</label>
                                <div class="col-xs-8">
                                    <select required name="projectTypeId" data-rel="select2"
                                            data-width="308"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach var="projectType" items="${cetProjectTypes}">
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
                                <label class="col-xs-3 control-label">培训内容分类</label>
                                <div class="col-xs-9">
                                    <div class="input-group">
                                        <select class="multiselect" multiple="" name="category" data-width="308" data-placeholder="请选择">
                                            <c:import url="/metaTypes?__code=mc_cet_project_category"/>
                                        </select>
                                        <script type="text/javascript">
                                            $.register.multiselect($('#modalForm select[name=category]'), '${cetProject.category}'.split(","));
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>参训人员类型</label>
                                <div class="col-xs-9 label-text" id="traineeTypeDiv">
                                    <c:forEach items="${traineeTypeMap}" var="entity">
                                        <label>
                                            <input name="_traineeTypeIds[]" type="checkbox" value="${entity.key}"> ${entity.value.name}&nbsp;
                                            <span class="lbl"></span>
                                        </label>
                                    </c:forEach>
                                    <input name="otherTypeId" type="checkbox" value="0"> 其他&nbsp;
                                    <span class="lbl"></span>
                                </div>
                            </div>
                            <div class="form-group hidden" id="otherTraineeType">
                                <div class="col-xs-offset-3 col-xs-8">
                                    <input class="form-control"  style="width: 252px"
                                           type="text" name="otherTraineeType" value="${cetProject.otherTraineeType}">
                                </div>
                            </div>
                            <%--<div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>文件名</label>
                                <div class="col-xs-6">
                                        <input required class="form-control" type="text" name="fileName" value="${cetProject.fileName}">
                                </div>
                            </div>--%>

                            <div class="form-group">
                                <label class="col-xs-3 control-label" style="margin-top: -15px">培训方案(WORD文件)</label>
                                <div class="col-xs-8">
                                    <input class="form-control" type="file" name="_wordFilePath"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>总学时</label>
                                <div class="col-xs-8">
                                    <input required class="form-control period"  style="width: 80px"
                                           type="text" name="period" value="${cetProject.period}">
                                </div>
                            </div>
                            <%--<div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>达到结业要求的学时数</label>
                                <div class="col-xs-6">
                                        <input required class="form-control period" type="text" name="requirePeriod" value="${cetProject.requirePeriod}">
                                </div>
                            </div>--%>
                            <div class="form-group">
                                <label class="col-xs-3 control-label" style="margin-top: -15px">是否计入<br/>年度学习任务</label>
                                <div class="col-xs-6">
                                    <input type="checkbox" class="big" name="isValid" ${(empty cetProject || cetProject.isValid)?"checked":""}/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">备注</label>
                                <div class="col-xs-8">
					                <textarea class="form-control limited" name="remark">${cetProject.remark}</textarea>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer center">
                            <%--<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>--%>
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

    function traineeTypeChange(){
        if ($("input[name=otherTypeId]:checked").val()!='0'){
            $("#otherTraineeType").addClass("hidden");
            $("input[name=otherTraineeType]", "#otherTraineeType").prop("disabled", true).removeAttr("required");
        }else {
            $("#otherTraineeType").removeClass("hidden");
            $("input[name=otherTraineeType]", "#otherTraineeType").prop("disabled", false).attr("required", "required");
        }
    }

    $("input[name=otherTypeId]").on("click", function () {
        traineeTypeChange();
    })
    traineeTypeChange();

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
                        $("#dispatch-file-view").load("${ctx}/pdf_preview?type=html&path=" + encodeURI(ret.pdfFilePath));
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
    $.each(traineeTypeIds, function (i, item) {
        //console.log(item)
        if (item==0){
            $("#modalForm input[name=otherTypeId]").prop("checked", true);
            $("#modalForm #otherTraineeType").removeClass("hidden");
            $("input[name=otherTraineeType]", "#otherTraineeType").prop("disabled", false).prop("required", "required");
        }
        $('#modalForm input[name="_traineeTypeIds[]"][value="'+ item +'"]').prop("checked", true);
    })
    $("#submitBtn").click(function(){
        if($('#modalForm input[name="_traineeTypeIds[]"]:checked').length==0 && !$('#modalForm input[name="otherTypeId"]:checked')){
            $.tip({
                $target: $("#traineeTypeDiv"),
                /*at: 'right center', my: 'left center',*/
                msg: "请选择参训人员类型。"
            });
        }
        $("#modalForm").submit();return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            if($('#modalForm input[name="_traineeTypeIds[]"]:checked').length==0 && !$('#modalForm input[name="otherTypeId"]:checked')){
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
    $("#modalForm input[name=isValid]").bootstrapSwitch();
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