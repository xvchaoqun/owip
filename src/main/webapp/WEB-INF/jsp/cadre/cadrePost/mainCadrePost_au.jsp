<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadrePost!=null}">编辑</c:if><c:if test="${cadrePost==null}">添加</c:if>主职</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadrePost_au?cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadrePost.id}">
        <input type="hidden" name="isMainPost" value="1">
            <div class="form-group">
                <label class="col-xs-3 control-label">姓名</label>
                <div class="col-xs-6 label-text">
                    ${sysUser.realname}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">职务</label>

                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="post" value="${cadrePost.post}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">职务属性</label>

                <div class="col-xs-6">
                    <select required data-rel="select2" name="postId"
                            data-width="272"
                            data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=postId]").val(${cadrePost.postId});
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">行政级别</label>
                <div class="col-xs-6">
                    <select required data-rel="select2" name="adminLevelId"
                            data-width="272" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=adminLevelId]").val(${cadrePost.adminLevelId});
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">职务类别</label>
                <div class="col-xs-6">
                    <select required data-rel="select2" name="postClassId"
                            data-width="272" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post_class"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=postClassId]").val(${cadrePost.postClassId});
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">所在单位</label>

                <div class="col-xs-6">
                    <select required data-rel="select2-ajax"
                            data-width="272" data-ajax-url="${ctx}/unit_selects"
                            name="unitId" data-placeholder="请选择所属单位">
                        <option value="${unit.id}">${unit.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">是否双肩挑</label>

                <div class="col-xs-6">
                    <label>
                        <input name="isDouble" ${cadrePost.isDouble?"checked":""} type="checkbox"/>
                        <span class="lbl"></span>
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">双肩挑单位</label>

                <div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                            name="doubleUnitId"
                            data-width="272" data-placeholder="请选择所属单位">
                        <option value="${doubleUnit.id}">${doubleUnit.name}</option>
                    </select>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${cadrePost!=null}">确定</c:if><c:if test="${cadrePost==null}">添加</c:if>"/>
</div>

<script>
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#modalForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {
                    results: data.options, pagination: {
                        more: (params.page * 10) < data.totalCount
                    }
                };
            },
            cache: true
        }
    });

    function isDoubleChange(){
        if($("input[name=isDouble]").bootstrapSwitch("state")){
            $("select[name=doubleUnitId]").attr("required", "required").prop("disabled", false);
        }else{
            $("select[name=doubleUnitId]").removeAttr("required").val(null).trigger("change").prop("disabled", true);
        }
    }
    $('input[name=isDouble]').on('switchChange.bootstrapSwitch', function(event, state) {
        isDoubleChange();
    });
    isDoubleChange();

    $("#modal :checkbox").bootstrapSwitch();
    $.register.date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });

</script>