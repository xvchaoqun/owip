<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/partyMemberGroup?status=${status}"
             data-url-export="${ctx}/partyMember_data?isDeleted=0&isPresent=1"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.unitId ||not empty param.classId||not empty param.partyId
                ||not empty param.postId || not empty param.typeIds}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>

                    <div class="tab-content">
                        <div class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="partyMember:edit">
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/partyMember_au"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyMember:del">
                    <button data-url="${ctx}/partyMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </shiro:hasPermission>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出</a>
                    <div class="type-select">
                        <span class="typeCheckbox ${param.isHistory!=1?"checked":""}">
                        <input ${param.isHistory!=1?"checked onclick='return false'":""} type="checkbox" value="0"> 现任班子成员
                        </span>
                        <span class="typeCheckbox ${param.isHistory==1?"checked":""}">
                        <input ${param.isHistory==1?"checked onclick='return false'":""} type="checkbox" value="1"> 离任班子成员
                        </span>
                    </div>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">
                                <input type="hidden" name="isHistory" value="${empty param.isHistory?0:param.isHistory}">
                                <div class="form-group">
                                    <label>姓名</label>
                                    <div class="input-group">
                                        <input type="hidden" name="status" value="${status}">
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>所属单位</label>
                                    <select name="unitId" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                          <c:forEach items="${unitMap}" var="unit"> 
                                            <option value="${unit.key}">${unit.value.name}</option>
                                              </c:forEach> 
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=unitId]").val('${param.unitId}');
                                    </script>
                                </div>
                                <div class="form-group">
                                    <label>${_p_partyName}类别</label>
                                    <select name="classId" data-rel="select2" data-placeholder="请选择">
                                        <option></option>
                                        <c:import url="/metaTypes?__code=mc_party_class"/>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=classId]").val('${param.classId}');
                                    </script>
                                </div>
                                    <div class="form-group">
                                        <label>所属${_p_partyName}</label>
                                        <select name="partyId" data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                data-placeholder="请选择所属${_p_partyName}">
                                            <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                        </select>
                                        <script>
                                            $.register.del_select($("#searchForm select[name=partyId]"), 350)
                                        </script>
                                    </div>
                                <div class="form-group">
                                    <label>职务</label>
                                    <select name="postId" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                         <c:import url="/metaTypes?__code=mc_party_member_post"/>
                                    </select> 
                                    <script>
                                        $("#searchForm select[name=postId]").val('${param.postId}');
                                    </script> 
                                </div>
                                <div class="form-group">
                                    <label>分工</label>
                                    <select name="typeIds" class="multiselect" multiple="" data-placeholder="请选择"> 
                                          <c:import url="/metaTypes?__code=mc_party_member_type"/>
                                    </select> 
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query || not empty param.sort}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div>
                </div></div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="../partyMember/partyMember_colModel.jsp?isHistory=${empty param.isHistory?0:param.isHistory}"/>
<script>
    $(".typeCheckbox").click(function () {
        if($(this).hasClass("checked")) return;
        $("#searchForm input[name=isHistory]").val($(":checkbox", this).val());
        $("#searchForm .jqSearchBtn").click();
    })
    $.register.multiselect($('#searchForm select[name=typeIds]'), ${cm:toJSONArray(selectedTypeIds)});
    $.register.user_select($('#searchForm select[name=userId]'));
    function _adminCallback(){
        $("#modal").modal("hide")
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/partyMember_data?callback=?&isDeleted=0'
            +'&isPresent=1&isHistory=${empty param.isHistory?0:param.isHistory}'
            +'&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>