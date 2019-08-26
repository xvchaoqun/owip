<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CisConstants.CIS_INSPECTOR_TYPE_OW%>" var="CIS_INSPECTOR_TYPE_OW"/>
<c:set value="<%=CisConstants.CIS_INSPECTOR_STATUS_NOW%>" var="CIS_INSPECTOR_STATUS_NOW"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cisInspectObj!=null}">编辑</c:if><c:if test="${cisInspectObj==null}">添加</c:if>干部考察材料</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cisInspectObj_au"
          autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${cisInspectObj.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                           type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2" value="${cisInspectObj==null?_thisYear:cisInspectObj.year}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>考察类型</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="typeId" data-placeholder="请选择" data-width="270">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_cis_type"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=typeId]").val(${cisInspectObj==null?(cm:getMetaTypeByCode("mt_cis_type_assign").id):cisInspectObj.typeId});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">编号</label>
            <div class="col-xs-6">
                <input class="form-control digits" type="text" name="seq" value="${cisInspectObj.seq}">
                <span class="label-inline"> * 留空自动生成</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>考察日期</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input  class="form-control date-picker required" name="inspectDate"
                            type="text" data-date-format="yyyy.mm.dd"
                            value="${cm:formatDate(cisInspectObj.inspectDate, "yyyy.MM.dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <shiro:hasPermission name="scRecord:list">
            <div class="form-group">
                <label class="col-xs-3 control-label"> 对应选任纪实</label>
                <div class="col-xs-6">
                    <input type="hidden" name="recordId" value="${scRecord.id}">
                    <input readonly class="form-control" type="text" name="recordCode" value="${scRecord.code}">
                </div>
                <button id="selectRecordBtn" type="button" class="btn btn-success btn-sm"><i
                        class="fa fa-chevron-circle-down"></i> 选择
                </button>
            </div>
        </shiro:hasPermission>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>考察对象</label>
            <div class="col-xs-6">
                <c:set var="cadreSelectUrl" value="${ctx}/cadre_selects?type=0&key=1"/>
                <c:if test="${not empty scRecord.id}">
                    <c:set var="cadreSelectUrl" value="${ctx}/sc/scGroupTopic_users?recordId=${scRecord.id}"/>
                </c:if>
                <select required data-rel="select2-ajax" data-ajax-url="${cadreSelectUrl}"
                        name="userId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
                    <option value="${cadre.userId}">${cadre.realname}-${cadre.code}</option>
                </select>
            </div>
            <%--<c:if test="${not empty cisInspectObj}">
            <div class="col-xs-6 label-text">
                    <input type="hidden" name="userId" value="${cadre.userId}">
                  ${cadre.realname}-${cadre.code}
            </div>
            </c:if>--%>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">所在单位及职务</label>
            <div class="col-xs-6">
                <textarea class="form-control noEnter" name="post">${cisInspectObj.post}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">拟任职务</label>
            <div class="col-xs-6">
                <select name="unitPostId" data-rel="select2-ajax"
                        data-ajax-url="${ctx}/unitPost_selects" data-width="273"
                        data-placeholder="请选择">
                    <option value="${unitPost.id}" delete="${unitPost.status==UNIT_POST_STATUS_DELETE}">${unitPost.code}-${unitPost.name}</option>
                </select>
                <script>
                    $.register.del_select($("#modalForm select[name=unitPostId]"))
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>考察主体</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="inspectorType" data-placeholder="请选择"  data-width="270">
                    <option></option>
                    <c:forEach var="type" items="<%=CisConstants.CIS_INSPECTOR_TYPE_MAP%>">
                    <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=inspectorType]").val(${cisInspectObj==null?CIS_INSPECTOR_TYPE_OW:cisInspectObj.inspectorType});
                </script>
            </div>
        </div>
        <div class="form-group" id="otherInspectorTypeDiv" style="display: none">
            <label class="col-xs-3 control-label"><span class="star">*</span>其他考察主体</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="otherInspectorType"
                       value="${cisInspectObj.otherInspectorType}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>考察组负责人</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/cisInspector_selects"
                        name="chiefInspectorId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
                    <option value="${chiefInspector.id}" delete="${chiefInspector.status!=CIS_INSPECTOR_STATUS_NOW}">${chiefInspector.user.realname}-${chiefInspector.user.code}</option>
                </select>
            </div>
        </div>

        <%--<div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>谈话人数</label>
            <div class="col-xs-6">
                <input required class="form-control digits" type="text" name="talkUserCount"
                       value="${cisInspectObj.talkUserCount}">
            </div>
        </div>--%>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark">${cisInspectObj.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button type="button" id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        ${cisInspectObj!=null?'确定':'添加'}
    </button>
</div>

<script>

    function _selectItem(id, rowData){
        //console.log(rowData)
        if(id>0){
            var code = rowData.code;
            $("#modalForm input[name=recordId]").val(id);
            $("#modalForm input[name=recordCode]").val(code);
            $("#modalForm select[name=unitPostId]").html('<option value="{0}">{1}</option>'
                    .format(rowData.unitPostId, rowData.postCode + "-" + rowData.postName))
                    .trigger("change");
            $('#modalForm select[name=userId]').data("ajax-url", "${ctx}/sc/scGroupTopic_users?recordId="+id)
                .val(null).trigger("change");
        }else{
            $("#modalForm input[name=recordId]").val('');
            $("#modalForm input[name=recordCode]").val('');
            $('#modalForm select[name=userId]').data("ajax-url", "${ctx}/cadre_selects?type=0&key=1")
                .val(null).trigger("change");
        }

        $.register.user_select($('#modalForm select[name=userId]'));
        WebuiPopovers.hideAll();
    }
    $('#selectRecordBtn').webuiPopover({
        padding: true,
        backdrop: false,
        autoHide: false,
        trigger: 'click',
        content: function (data) {
            return '<div id="popup-content">' + data + '</div>';
        },
        type: 'async',
        cache: false,
        url: '/sc/scRecord?cls=10&year=${_thisYear}'
    });

    function inspectorTypeChange(){
        var $inspectorType = $("select[name=inspectorType]");
        if($inspectorType.val()=="<%=CisConstants.CIS_INSPECTOR_TYPE_OTHER%>"){
            $("#otherInspectorTypeDiv").show();
            $("input[name=otherInspectorType]").attr("required", "required");
        }else{
            $("#otherInspectorTypeDiv").hide();
            $("input[name=otherInspectorType]").removeAttr("required");
        }
    }
    $("select[name=inspectorType]").change(function(){
        inspectorTypeChange();
    });
    inspectorTypeChange();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
    $.register.del_select($('#modalForm select[name=chiefInspectorId]'));
    var $selectCadre = $.register.user_select($('#modalForm select[name=userId]'));
    $selectCadre.on("change",function(){
        //console.log($(this).select2("data")[0])
        var title = $(this).select2("data")[0]['title']||'';
        $('#modalForm textarea[name=post]').val(title);
    });
</script>