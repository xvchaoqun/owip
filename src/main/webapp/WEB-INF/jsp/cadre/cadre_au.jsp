<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadre!=null}">编辑</c:if><c:if test="${cadre==null}">添加</c:if>
        ${CADRE_STATUS_MAP.get(status)}
    </h3>
</div>
<div class="modal-body ${(status==CADRE_STATUS_MIDDLE||status==CADRE_STATUS_LEADER)?'overflow-visible':''}">
    <form class="form-horizontal" action="${ctx}/cadre_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">
        <input type="hidden" name="status" value="${status}">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>类别</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="isDep" id="isDep1" ${cadre.isDep?"checked":""} value="1">
                        <label for="isDep1">
                            院系干部
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="isDep" id="isDep0" ${cadre.isDep?"":"checked"} value="0">
                        <label for="isDep0">
                            机关干部
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><c:if test="${empty cadre}"><span class="star">*</span></c:if>账号</label>
            <div class="col-xs-6 ${not empty cadre?'label-text':''}">
                <c:if test="${cadre==null}">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/notCadre_selects"
                             data-width="273"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                   <span class="help-block">注：从非干部库中选择</span>
                </c:if>
                <c:if test="${cadre!=null}">
                    <input type="hidden" name="userId" value="${sysUser.id}">
                    ${sysUser.realname}-${sysUser.code}
                </c:if>
            </div>
        </div>
        <c:if test="${status==CADRE_STATUS_MIDDLE||status==CADRE_STATUS_MIDDLE_LEAVE}">
        <c:if test="${_p_hasKjCadre}">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>干部类型</label>
            <div class="col-xs-6">
                <div class="input-group">
                <c:forEach items="${CADRE_TYPE_MAP}" var="entity">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="type" id="type${entity.key}"
                        ${cadre.type==entity.key?"checked":""} value="${entity.key}">
                        <label for="type${entity.key}">
                            ${entity.value}
                        </label>
                    </div>
                </c:forEach>
                    </div>
            </div>
        </div>
        </c:if>
         </c:if>
        <c:if test="${_p_useCadreState}">
        <div class="form-group">
            <label class="col-xs-4 control-label">${cm:getTextFromHTML(_pMap['cadreStateName'])}</label>
            <div class="col-xs-6">
                <select data-rel="select2" data-width="100" name="state" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_cadre_state"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=state]").val(${cadre.state});
                </script>
            </div>
        </div>
        </c:if>
        <c:if test="${cadre.id!=null && (status==CADRE_STATUS_MIDDLE_LEAVE||status==CADRE_STATUS_LEADER_LEAVE)}">
            <div class="form-group">
                <label class="col-xs-4 control-label">离任文件</label>
                <div class="col-xs-8 label-text">
                    <div id="tree3"></div>
                    <input type="hidden" name="dispatchCadreId">
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label"><c:if
                    test="${status==CADRE_STATUS_MIDDLE_LEAVE||status==CADRE_STATUS_LEADER_LEAVE}">离任后</c:if>所在单位及职务</label>
            <div class="col-xs-6">
                <textarea class="form-control" rows="3" name="title">${cadre.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否双肩挑</label>

            <div class="col-xs-8">
                <label>
                    <input name="isDouble" ${cadre.isDouble?"checked":""} type="checkbox"/>
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-xs-4 control-label">双肩挑单位</label>
            <div class="col-xs-6 input-group" style="padding-left: 12px">
                <select class="multiselect" multiple="" name="unitIds" data-width="273">
                    <c:forEach var="unitType" items="${cm:getMetaTypes('mc_unit_type')}">
                        <c:set var="unitList" value="${unitListMap.get(unitType.value.id)}"/>
                        <c:if test="${fn:length(unitList)>0}">
                        <optgroup label="${unitType.value.name}">
                            <c:forEach
                                    items="${unitList}"
                                    var="unitId">
                                <c:set var="unit"
                                       value="${unitMap.get(unitId)}"></c:set>
                                <option value="${unit.id}">${unit.name}</option>
                            </c:forEach>
                        </optgroup>
                        </c:if>
                    </c:forEach>
                </select>
                <div>（从正在运转单位中选择）</div>
                <div class="space-4"></div>
                <select class="multiselect" multiple="" name="historyUnitIds" data-width="273">
                    <c:forEach var="unitType" items="${cm:getMetaTypes('mc_unit_type')}">
                        <c:set var="unitList" value="${historyUnitListMap.get(unitType.value.id)}"/>
                        <c:if test="${fn:length(unitList)>0}">
                        <optgroup label="${unitType.value.name}">
                            <c:forEach
                                    items="${unitList}"
                                    var="unitId">
                                <c:set var="unit"
                                       value="${unitMap.get(unitId)}"></c:set>
                                <option value="${unit.id}">${unit.name}</option>
                            </c:forEach>
                        </optgroup>
                        </c:if>
                    </c:forEach>
                </select>
                <div>（从历史单位中选择）</div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">干部标签</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <select class="multiselect" multiple="" name="label" data-width="273" data-placeholder="请选择">
                        <c:import url="/metaTypes?__code=mc_cadre_label"/>
                    </select>
                    <script type="text/javascript">
                        $.register.multiselect($('#modalForm select[name=label]'), '${cadre.label}'.split(","));
                    </script>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark" rows="3">${cadre.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中"> ${not empty cadre?"确定":"添加"}
    </button>
</div>
<script>
    var doubleUnitIds = '${cadre.doubleUnitIds}';
    $.register.multiselect($('#modalForm select[name=unitIds]'), doubleUnitIds.split(","), {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
    });

    $.register.multiselect($('#modalForm select[name=historyUnitIds]'), doubleUnitIds.split(","), {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
    });
    $("#modal input[name=isDouble]").bootstrapSwitch();

    <c:if test="${cadre.id!=null && (status==CADRE_STATUS_MIDDLE_LEAVE||status==CADRE_STATUS_LEADER_LEAVE)}">
    var treeNode = ${tree};
    if (treeNode.children.length == 0) {
        $("#tree3").html("没有发文");
    } else {
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 2,
            children: treeNode,
            onSelect: function (select, node) {
                //node.expand(node.data.isFolder && node.isSelected());
            },
            onCustomRender: function (node) {
                if (!node.data.isFolder)
                    return "<span class='dynatree-title'>" + node.data.title
                        + "</span>&nbsp;&nbsp;<button class='openUrl btn btn-xs btn-default' data-url='${ctx}/pdf_preview?type=url&path=" + node.data.tooltip + "'>查看</button>"
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    }
    </c:if>
    $('textarea.limited').inputlimiter();
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            <c:if test="${cadre.id!=null && (status==CADRE_STATUS_MIDDLE_LEAVE||status==CADRE_STATUS_LEADER_LEAVE)}">
            if (treeNode.children.length > 0) {
                var selectIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                    return node.data.key;
                });
                if (selectIds.length > 1) {
                    SysMsg.warning("只能选择一个发文");
                    return;
                }
                $("#modal input[name=dispatchCadreId]").val(selectIds[0]);
            }
            </c:if>

            var selectedUnitIds = [];
            if($("input[name=isDouble]").bootstrapSwitch("state")){
                selectedUnitIds = $.map($('#modalForm select[name=unitIds] option:selected, ' +
                        '#modalForm select[name=historyUnitIds] option:selected'), function(option){
                    return $(option).val();
                });
                if(selectedUnitIds.length==0){
                    $.tip({
                        $target: $("#modalForm select[name=unitIds]").closest(".input-group"),
                        at: 'right center', my: 'left center', type: 'info',
                        msg: "请选择双肩挑单位。"
                    });
                    return;
                }
            }

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data:{unitIds: selectedUnitIds},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>