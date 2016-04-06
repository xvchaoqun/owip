<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="cadreWork:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加工作经历</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <shiro:hasPermission name="cadreWork:del">
                    <a class="btn btn-danger btn-sm" onclick="_batchDel()"><i class="fa fa-trash"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>开始日期</th>
							<th>结束日期</th>
							<th>工作单位</th>
							<th>担任职务或者专技职务</th>
							<th>行政级别</th>
							<th>院系/机关工作经历</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${cadreWorks}" var="cadreWork" varStatus="st">

                        <tr>
								<td>${cm:formatDate(cadreWork.startTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(cadreWork.endTime,'yyyy-MM-dd')}</td>
								<td>${cadreWork.unit}</td>
								<td>${cadreWork.post}</td>
								<td>${adminLevelMap.get(cadreWork.typeId).name}</td>
								<td>${cadreWork.workType==1?"院系工作经历":"机关工作经历"}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreWork:edit">
                                        <button onclick="_au(${cadreWork.id})" class="btn btn-default btn-mini btn-xs">
                                            <i class="fa fa-edit"></i> 编辑
                                        </button>
                                        <button onclick="showSubWork(${cadreWork.id})" class="btn btn-mini btn-xs btn-warning">
                                            <i class="fa fa-edit"></i> 编辑期间工作
                                        </button>
                                        <button onclick="showDispatch(${cadreWork.id})" class="btn btn-default btn-mini btn-xs">
                                            <i class="fa fa-edit"></i> 关联任免文件
                                        </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="cadreWork:del">
                                    <button class="btn btn-danger btn-mini btn-xs" onclick="_del(${cadreWork.id})">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <%--<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>--%>
                                            <shiro:hasPermission name="cadreWork:edit">
                                            <li>
                                                <a href="#" data-id="${cadreWork.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="cadreWork:del">
                                            <li>
                                                <a href="#" data-id="${cadreWork.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                        </ul>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
<wo:page commonList="${commonList}" uri="${ctx}/cadreWork_page" target="#view-box .tab-content" pageNum="5"
         model="3"/>
<script>

    function _au(id) {
        url = "${ctx}/cadreWork_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function showSubWork(id){
        loadModal("${ctx}/cadreWork_page?cadreId=${param.cadreId}&fid="+id, 1000);
    }

    var _id;
    function showDispatch(id){
        _id = id;
        loadModal("${ctx}/cadreWork_addDispatchs?cadreId=${cadre.id}&id="+id, 1000);
    }

    function closeSwfPreview(){
        showDispatch(_id);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreWork_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _batchDel(){

        var ids = $.map($("#view-box .table td :checkbox:checked"),function(item, index){
            return $(item).val();
        });
        if(ids.length==0){
            SysMsg.warning("请选择行", "提示");
            return ;
        }
        bootbox.confirm("确定删除这"+ids.length+"条数据？",function(result){
            if(result){
                $.post("${ctx}/cadreWork_batchDel",{ids:ids},function(ret){
                    if(ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreWork_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>