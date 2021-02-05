<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<c:set value="${empty mainCadrePost || mainCadrePost.id==cadrePost.id}" var="displayFirstMainCadrePost"/>
<c:set value="${_pMap['postTimeToDay']=='true'}" var="_p_postTimeToDay"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <c:if test="${empty param.transfer}"><h3><c:if test="${cadrePost!=null}">编辑</c:if><c:if test="${cadrePost==null}">添加</c:if>主职</h3></c:if>
    <c:if test="${param.transfer==1}"><h3>转移兼职到主职</h3></c:if>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadrePost_au?cadreId=${cadre.id}" autocomplete="off"
          disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadrePost.id}">
        <input type="hidden" name="isMainPost" value="1">
        <div class="col-xs-12">
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label">姓名</label>
                    <div class="col-xs-9 label-text">
                        ${cadre.realname}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">关联岗位</label>
                    <div class="col-xs-8">
                        <select data-ajax-url="${ctx}/unitPost_selects" data-width="258"
                                name="unitPostId" data-placeholder="请选择">
                            <option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.name}-${unitPost.unitName}</option>
                        </select>
                        <span class="help-block blue">注：如果选择了关联岗位，则以下蓝色字段将同步此岗位相关的信息，且不可修改</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label blue"><span class="star">*</span>岗位名称</label>
                    <div class="col-xs-8">
                        <textarea required class="form-control noEnter" name="postName">${cadrePost.postName}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label blue"><span class="star">*</span>是否正职</label>
                    <div class="col-xs-9">
                        <div class="input-group">
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="isPrincipal" id="isPrincipal1" value="1">
                                <label for="isPrincipal1">
                                    是
                                </label>
                            </div>
                            &nbsp;&nbsp;
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="isPrincipal" id="isPrincipal0" value="0">
                                <label for="isPrincipal0">
                                    否
                                </label>
                            </div>
                        </div>
                        <c:if test="${not empty cadrePost.isPrincipal}">
                            <script>
                                $("#modalForm input[name=isPrincipal][value=${cadrePost.isPrincipal?1:0}]").prop("checked", true);
                            </script>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label blue"><span class="star">*</span>职务属性</label>

                    <div class="col-xs-9">
                        <select required data-rel="select2" name="postType"
                                data-width="272"
                                data-placeholder="请选择">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_post"/>
                        </select>
                        <script type="text/javascript">
                            $("#modal form select[name=postType]").val(${cadrePost.postType});
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
                    <div class="col-xs-9">
                        <select required data-rel="select2" name="adminLevel"
                                data-width="272" data-placeholder="请选择">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_admin_level"/>
                        </select>
                        <script type="text/javascript">
                            $("#modal form select[name=adminLevel]").val(${cadrePost.adminLevel});
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label blue"><span class="star">*</span>职务类别</label>
                    <div class="col-xs-9">
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
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-4 control-label blue"><span class="star">*</span>所在单位</label>
                    <div class="col-xs-8">
                        <select required data-rel="select2-ajax"
                                data-width="256" data-ajax-url="${ctx}/unit_selects"
                                name="unitId" data-placeholder="请选择所属单位">
                            <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>职务</label>
                    <div class="col-xs-8">
                        <textarea required class="form-control noEnter" name="post">${cadrePost.post}</textarea>

                        <span class="help-block blue">注：一般与岗位名称相同，可自行修改</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">是否第一主职</label>
                    <div class="col-xs-8">
                        <label>
                            <input name="isFirstMainPost" ${(empty cadrePost || cadrePost.isFirstMainPost)?"checked":""}
                                   type="checkbox"/>
                            <span class="lbl"></span>
                        </label>
                        <span class="help-block">注：第一主职将显示在干部库列表中</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">任职日期</label>
                    <div class="col-xs-6">
                        <div class="input-group">
                            <c:if test="${_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_lpWorkTime" type="text"
                                   data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadrePost.lpWorkTime,'yyyy.MM.dd')}"/>
                            </c:if>
                            <c:if test="${!_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_lpWorkTime" type="text"
                                    data-date-min-view-mode="1" data-date-format="yyyy.mm" value="${cm:formatDate(cadrePost.lpWorkTime,'yyyy.MM')}"/>
                            </c:if>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">任职始任日期</label>
                    <div class="col-xs-6">
                        <div class="input-group">
                            <c:if test="${_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_npWorkTime" type="text"
                                   data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadrePost.npWorkTime,'yyyy.MM.dd')}"/>
                            </c:if>
                            <c:if test="${!_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_npWorkTime" type="text"
                                    data-date-min-view-mode="1" data-date-format="yyyy.mm" value="${cm:formatDate(cadrePost.npWorkTime,'yyyy.MM')}"/>
                            </c:if>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer overflow-hidden">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${cadrePost!=null}">确定</c:if><c:if test="${cadrePost==null}">添加</c:if>"/>
</div>

<script>
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $("#modal input[name=isFirstMainPost]").bootstrapSwitch();
    $.register.date($('.date-picker'));

    function _templateResult(state) {
        var $state = state.text;
        if (state.code != undefined) {
            $state += "(" + state.code + ")";
        }
        if (state.up != undefined) {
            if ($.trim(state.up.job) != '')
                $state += "-" + state.up.job;
            if ($.trim(state.up.unitName) != '')
                $state += "-" + state.up.unitName;
        }
        //console.log("$state=" + $state);
        state.text = $state;
        return $state;
    }

    $.register.ajax_select($('#modalForm select[name=unitId]'));
    $.register.del_select($('#modalForm select[name=unitPostId]'),
        {templateResult: _templateResult})
        .on("change", function () {
            //console.log($(this).select2("data")[0])
            var data = $(this).select2("data")[0];
            var up = data == undefined ? undefined : data['up'];
            //console.log(up)
            if (up != undefined) {
                $('#modalForm textarea[name=postName]').val(up.name)
                $('#modalForm textarea[name=post]').val(up.name); // 职务默认为岗位名称
                $("#modalForm input[name=isPrincipal][value=" + (up.isPrincipal ? 1 : 0) + "]").prop("checked", true);
                $("#modalForm select[name=postType]").val(up.postType).trigger("change");
                $("#modalForm select[name=adminLevel]").val(up.adminLevel).trigger("change");
                $("#modalForm select[name=postClassId]").val(up.postClass).trigger("change");
                var option = new Option(up.unitName, up.unitId, true, true);
                $("#modalForm select[name=unitId]").append(option).trigger('change');

                $('#modalForm label.blue').closest(".form-group")
                    .find("select,input,textarea").prop("disabled", true)
            } else {
                $('#modalForm label.blue').closest(".form-group")
                    .find("select,input,textarea").prop("disabled", false)
            }
        });
    <c:if test="${not empty unitPost}">
    $('#modalForm label.blue').closest(".form-group")
        .find("select,input,textarea").prop("disabled", true);
    </c:if>

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