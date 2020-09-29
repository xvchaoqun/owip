<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_cadrePostPro_noNeed" value="${_pMap['cadrePostPro_noNeed']=='true'}"/>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadrePostPro!=null}">编辑</c:if><c:if test="${cadrePostPro==null}">添加</c:if>专技岗位过程信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadrePostPro_au?toApply=${param.toApply}&cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadrePostPro.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label">姓名</label>

            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否当前专技岗位</label>

            <div class="col-xs-6">
                <label>
                    <input name="isCurrent" ${cadrePostPro.isCurrent?"checked":""} type="checkbox"/>
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>岗位类别</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="type"
                        data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_post_pro_type').id}"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=type]").val(${cadrePostPro.type});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>职级</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="postLevel"
                        data-placeholder="请选择">
                    <option></option>
                    <option value="正高">正高</option>
                    <option value="副高">副高</option>
                    <option value="中级">中级</option>
                    <option value="初级">初级</option>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=postLevel]").val('${cadrePostPro.postLevel}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">${_cadrePostPro_noNeed?'':'<span class="star">*</span>'} 专业技术职务</label>
            <div class="col-xs-6">
                <c:set value="${cm:getMetaType(cadrePostPro.post)}" var="post"/>
                <select ${_cadrePostPro_noNeed?'':'required'} data-rel="select2" name="post" data-placeholder="请选择">
                    <option value="${post.id}">${post.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">${_cadrePostPro_noNeed?'':'<span class="star">*</span>'} 专技职务任职时间</label>

            <div class="col-xs-6">
                <div class="input-group" style="width: 120px">
                    <input ${_cadrePostPro_noNeed?'':'required'} class="form-control date-picker" name="_holdTime" type="text"
                           data-date-min-view-mode="1" placeholder="yyyy.mm"
                           data-date-format="yyyy.mm" value="${cm:formatDate(cadrePostPro.holdTime,'yyyy.MM')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">${_cadrePostPro_noNeed?'':'<span class="star">*</span>'} 职称级别</label>

            <div class="col-xs-6">
                <c:set value="${cm:getMetaType(cadrePostPro.level)}" var="level"/>
                <select ${_cadrePostPro_noNeed?'':'required'} data-rel="select2" name="level" data-placeholder="请选择">
                    <option value="${level.id}">${level.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">专技岗位分级时间</label>

            <div class="col-xs-6">
                <div class="input-group" style="width: 120px">
                    <input class="form-control date-picker" name="_gradeTime" type="text"
                           data-date-min-view-mode="1" placeholder="yyyy.mm"
                           data-date-format="yyyy.mm" value="${cm:formatDate(cadrePostPro.gradeTime,'yyyy.MM')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadrePostPro.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${cadrePostPro!=null}">确定</c:if><c:if test="${cadrePostPro==null}">添加</c:if>"/>
</div>

<script>
    function postLevelChange(){

        var postLevel = $('select[name=postLevel]').val();
        $.getJSON("${ctx}/cadrePostPro_metaTypes?cadreId=${cadre.id}",{postLevel:postLevel},function(ret){
            $("select[name=post]").select2({
                data: ret.options1
            })
            $("select[name=level]").select2({
                data: ret.options2
            })
        });
    }

    $('select[name=postLevel]').select2().on("change", function () {
        $('select[name=post], select[name=level]').html('<option></option>');
        $('select[name=post], select[name=level]').val(null).trigger("change");
        postLevelChange();
    });

    postLevelChange();

    $.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadrePostPro").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&opType=${param.opType}&applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${param.module}');
                        </c:if>
                        </c:if>
                    }
                }
            });
        }
    });
    $("#modal :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
</script>