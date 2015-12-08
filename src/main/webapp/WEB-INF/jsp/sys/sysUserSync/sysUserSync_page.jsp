<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="USER_SOURCE_MAP" value="<%=SystemConstants.USER_SOURCE_MAP%>"/>
<c:set var="USER_SOURCE_JZG" value="<%=SystemConstants.USER_SOURCE_JZG%>"/>
<c:set var="USER_SOURCE_BKS" value="<%=SystemConstants.USER_SOURCE_BKS%>"/>
<c:set var="USER_SOURCE_YJS" value="<%=SystemConstants.USER_SOURCE_YJS%>"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="buttons pull-right">
            <a data-type="${USER_SOURCE_JZG}" class="syncBtn btn btn-info btn-sm btn-purple" data-loading-text="<i class='fa fa-refresh fa-spin'></i> 人事库同步中..." autocomplete="off"><i class="fa fa-refresh"></i> 同步人事库</a>
            <a  data-type="${USER_SOURCE_BKS}" class="syncBtn btn btn-info btn-sm btn-grey" data-loading-text="<i class='fa fa-refresh fa-spin'></i> 本科生库同步中..." autocomplete="off"><i class="fa fa-refresh"></i> 同步本科生库</a>
            <a data-type="${USER_SOURCE_YJS}" class="syncBtn btn btn-info btn-sm btn-pink" data-loading-text="<i class='fa fa-refresh fa-spin'></i> 研究生库同步中..." autocomplete="off"><i class="fa fa-refresh"></i> 同步研究生库</a>
        </div>
        <h4>&nbsp;</h4>
        <div class="myTableDiv"
             data-url-au="${ctx}/sysUserSync_au"
             data-url-page="${ctx}/sysUserSync_page"
             data-url-del="${ctx}/sysUserSync_del"
             data-url-bd="${ctx}/sysUserSync_batchDel"
             data-url-co="${ctx}/sysUserSync_changeOrder"
             data-querystr="${pageContext.request.queryString}">


            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>类型</th>
							<th>触发方式</th>
							<th>是否结束</th>
                            <th>是否自动结束</th>
							<th>当前记录数</th>
							<th>总记录数</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>更新时间</th>

                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sysUserSyncs}" var="sysUserSync" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${sysUserSync.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${USER_SOURCE_MAP.get(sysUserSync.type)}</td>
								<td>${sysUserSync.autoStart?"系统自动":"功能按钮"}</td>
                                <td>${sysUserSync.isStop?"是":"否"}</td>
								<td>${sysUserSync.autoStop?"是":"否"}</td>
								<td>${sysUserSync.currentCount}</td>
								<td>${sysUserSync.totalCount}</td>
								<td>${cm:formatDate(sysUserSync.startTime, "yyyy-MM-dd HH:mm:ss")}</td>
								<td>${cm:formatDate(sysUserSync.endTime, "yyyy-MM-dd HH:mm:ss")}</td>
								<td>${cm:formatDate(sysUserSync.updateTime, "yyyy-MM-dd HH:mm:ss")}</td>

                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <c:if test="${!sysUserSync.isStop}">
                                    <button class="btn btn-danger btn-mini" onclick="_stop(${sysUserSync.id})">
                                        <i class="fa fa-stop-circle-o"></i> 强制结束
                                    </button>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${!empty commonList && commonList.pageNum>1 }">
                    <div class="row my_paginate_row">
                        <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
                        <div class="col-xs-6">
                            <div class="my_paginate">
                                <ul class="pagination">
                                    <wo:page commonList="${commonList}" uri="${ctx}/sysUserSync_page" target="#page-content" pageNum="5"
                                             model="3"/>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
</div>
<script>

    function _stop(id){

        $.post("${ctx}/sync_stop",{id:id},function(ret){
            if (ret.success) {
                toastr.success('操作成功。', '成功');
                page_reload()
            }
        })
    }
    var t;
    /*function checkSyncStatus(){

        t = setInterval("page_reload()", 5000);
        $.getJSON("${ctx}/sync_status",{},function(ret){
            if (ret.success) {
                $(".syncBtn").each(function(){
                    var type = $(this).data("type");
                    $(this).button(ret["lastSyncIsNotStop-"+type]?'loading':'reset');
                });
            }
        });
    }
    checkSyncStatus();*/

    $(".syncBtn").click(function(){
        var $this = $(this);
        bootbox.confirm("确定同步（将耗费很长时间）？", function (result) {
            if (result) {
                var $btn = $this.button('loading')
                $.post("${ctx}/sync_user",{type:$this.data("type")},function(ret){
                    if(ret.success){
                        $btn.button('reset');
                        toastr.success('同步成功。', '成功');
                        //clearTimeout(t);
                    }
                });

            }
        });
    });
    $(".syncBks").click(function(){
        var $btn = $(this).button('loading')
        setTimeout(function(){
            $btn.button('reset')
        }, 2000);
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>