<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsPost!=null}">编辑</c:if><c:if test="${crsPost==null}">添加</c:if>岗位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPost_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${crsPost.id}">
        <div class="row">
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>年度</label>
                    <div class="col-xs-6">
                        <div class="input-group date"
                             data-date-format="yyyy" data-date-min-view-mode="2" style="width: 100px">
                            <input required class="form-control" placeholder="请选择" name="year"
                                   type="text" value="${_thisYear}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>招聘类型</label>
                    <div class="col-xs-6">
                        <select required class="form-control" name="type"
                                data-rel="select2"
                                data-width="273"
                                data-placeholder="请选择所属单位">
                            <option></option>
                            <c:forEach items="${CRS_POST_TYPE_MAP}" var="type">
                                <option value="${type.key}">${type.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#modalForm select[name=type]").val('${crsPost.type}');
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">编号</label>
                    <div class="col-xs-8">
                        <input class="form-control digits" type="text" name="seq" value="${crsPost.seq}">
                        <span class="label-inline"> * 留空自动生成</span>
                    </div>
                </div>
                <shiro:hasPermission name="scRecord:list">
                <div class="form-group">
                    <label class="col-xs-3 control-label"> 对应选任纪实</label>
                    <div class="col-xs-6">
                        <input type="hidden" name="recordId" value="${scRecord.id}">
                        <input readonly class="form-control" type="text" name="recordCode" value="${scRecord.code}">
                    </div>
                    <button id="selectRecordBtn" type="button" class="btn btn-success btn-sm"><i class="fa fa-chevron-circle-down"></i> 选择</button>
                </div>
                </shiro:hasPermission>
                <div class="form-group">
                    <label class="col-xs-3 control-label">选择招聘岗位</label>
                    <div class="col-xs-8">
                        <select name="unitPostId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
                                data-placeholder="请选择">
                            <option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
                        </select>
                        <script>
                            $.register.del_select($("#modalForm select[name=unitPostId]"), 273)
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>岗位名称</label>
                    <div class="col-xs-8">
                        <input required class="form-control" type="text" name="name" value="${crsPost.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">分管工作</label>
                    <div class="col-xs-8">
                        <textarea class="form-control limited" maxlength="100" name="job"
                                  rows="5">${crsPost.job}</textarea>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
                    <div class="col-xs-8">
                        <select required data-rel="select2" data-width="273"
                                name="adminLevel" data-placeholder="请选择行政级别">
                            <option></option>
                            <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=adminLevel]").val(${crsPost.adminLevel});
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"> 所属单位</label>
                    <div class="col-xs-8">
                        <select data-rel="select2-ajax"
                                data-width="273" data-ajax-url="${ctx}/unit_selects?status=1"
                                name="unitId" data-placeholder="请选择单位">
                            <option value="${crsPost.unit.id}">${crsPost.unit.name}</option>
                        </select>
                    </div>
                </div>
                <%--<div class="form-group">
                    <label class="col-xs-3 control-label">部门属性</label>
                    <div class="col-xs-8">
                        <input class="form-control" type="text" disabled name="unitType"
                               value="${crsPost.unit.unitType.name}">
                    </div>
                </div>--%>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span> 招聘人数</label>
                    <div class="col-xs-8">
                        <input required class="form-control num" type="text" name="num"
                               value="${crsPost.num}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">备注</label>
                    <div class="col-xs-8">
                        <textarea class="form-control limited" name="remark">${crsPost.remark}</textarea>
                    </div>
                </div>

            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${crsPost!=null}">确定</c:if><c:if test="${crsPost==null}">添加</c:if>"/>
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
            $("#modalForm input[name=name]").val(rowData.postName);
            $("#modalForm textarea[name=job]").val(rowData.job);
            $("#modalForm select[name=adminLevel]").val(parseInt(rowData.adminLevel)).trigger("change");

            var unit = _cMap.unitMap[rowData.unitId];
            if(unit!=undefined) {
                $("#modalForm select[name=unitId]").html('<option value="{0}">{1}</option>'
                    .format(unit.id, unit.name))
                    .trigger("change");
            }
        }else{
            $("#modalForm input[name=recordId]").val('');
            $("#modalForm input[name=recordCode]").val('');
        }
        WebuiPopovers.hideAll();
    }
    $('#selectRecordBtn').webuiPopover({
        padding: true,
        backdrop: false,
        autoHide: false,
        trigger: 'click',
        content:function(data){
            return '<div id="popup-content">'+data+'</div>';
        },
        type:'async',
        cache:false,
        url:'/sc/scRecord?cls=10&year=${_thisYear}&scType=${cm:getMetaTypeByCode("mt_sctype_crs").id}'
    });

    $("#modalForm select[name=unitPostId]").change(function () {

		if ($(this).val() == null) return;

		var data = $(this).select2("data")[0];
		if (data['up']==undefined) return;
		var up = data['up'];

		$("#modalForm input[name=name]").val(up.name);
        $("#modalForm textarea[name=job]").val(up.job);
        $("#modalForm select[name=adminLevel]").val(parseInt(up.adminLevel)).trigger("change");

        var unit = _cMap.unitMap[up.unitId];
        if(unit!=undefined) {
            $("#modalForm select[name=unitId]").html('<option value="{0}">{1}</option>'
                .format(unit.id, unit.name))
                .trigger("change");
        }
	});

    $.register.date($('.input-group.date'));
    $('textarea.limited').inputlimiter();
    var $selectUnit = $.register.ajax_select($('#modalForm select[name=unitId]'));
    /*$selectUnit.on("change", function () {
        var unitType = $(this).select2("data")[0]['type'] || '';
        $('#modalForm input[name=unitType]').val(unitType);
    });*/

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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>