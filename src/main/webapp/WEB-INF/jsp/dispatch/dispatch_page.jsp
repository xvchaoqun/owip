<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/dispatch_au"
             data-url-page="${ctx}/dispatch_page"
             data-url-del="${ctx}/dispatch_del"
             data-url-bd="${ctx}/dispatch_batchDel"
             data-url-co="${ctx}/dispatch_changeOrder"
             data-querystr="${pageContext.request.queryString}">



                <div class="widget-box hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <mytag:sort-form css="form-horizontal " id="searchForm">
                                <div class="row">
                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">年份</label>
                                            <div class="col-xs-6">
                                                <div class="input-group">
                                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                    <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                                                           data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}" />
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">任免日期</label>
                                            <div class="col-xs-6">
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="任免日期范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                                    <input placeholder="请选择任免日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_workTime" value="${param._workTime}"/>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">发文类型</label>
                                            <div class="col-xs-6">
                                                <select class="form-control" data-rel="select2" name="typeId" data-placeholder="请选择发文类型">
                                                    <option></option>
                                                    <c:forEach var="metaType" items="${metaTypeMap}">
                                                        <option value="${metaType.value.id}">${metaType.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=typeId]").val(${param.typeId});
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">发文日期</label>
                                            <div class="col-xs-6">
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="发文日期范围">
                                                    <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                                                    <input placeholder="请选择发文日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_pubTime" value="${param._pubTime}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-4">

                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">发文号</label>
                                            <div class="col-xs-6">
                                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                       placeholder="请输入发文号">
                                            </div>
                                        </div>


                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">常委会</label>
                                            <div class="col-xs-6">
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="党委常委会日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                                    <input placeholder="请选择党委常委会日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_meetingTime" value="${param._meetingTime}"/>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>




                                <div class="clearfix form-actions center">
                                        <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:set var="_query" value="${not empty param.year ||not empty param.typeId ||not empty param.code ||not empty param._pubTime ||not empty param._workTime ||not empty param._meetingTime || not empty param.code || not empty param.sort}"/>
                                    <c:if test="${_query}">&nbsp; &nbsp; &nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </mytag:sort-form>
                        </div>
                    </div>
                </div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="dispatch:edit">
                        <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                        <a class="exportBtn btn btn-success btn-sm tooltip-success"
                           data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                        <shiro:hasPermission name="dispatch:del">
                            <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                        </shiro:hasPermission>
                    </c:if>
                </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>年份</th>
							<th>发文类型</th>
							<th>发文号</th>
							<th>党委常委会日期</th>
							<th>发文日期</th>
							<th>任免日期</th>
							<th>任免文件</th>
							<th>上会ppt</th>
							<th>备注</th>
                        <shiro:hasPermission name="dispatch:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dispatchs}" var="dispatch" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${dispatch.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td nowrap>${dispatch.year}</td>
								<td nowrap>${metaTypeMap.get(dispatch.typeId).name}</td>
								<td nowrap>${dispatch.code}</td>
								<td nowrap>${cm:formatDate(dispatch.meetingTime,'yyyy-MM-dd')}</td>
								<td nowrap>${cm:formatDate(dispatch.pubTime,'yyyy-MM-dd')}</td>
								<td nowrap>${cm:formatDate(dispatch.workTime,'yyyy-MM-dd')}</td>
								<td nowrap><c:if test="${not empty dispatch.fileName}">
                                    <a href="/dispatch_download?id=${dispatch.id}&type=file" target="_blank">下载</a>
                                    <a href="javascript:void(0)" onclick="swf_preview(${dispatch.id}, 'file')">预览</a>
                                </c:if>
                                </td>
								<td nowrap><c:if test="${not empty dispatch.pptName}"><a href="/dispatch_download?id=${dispatch.id}&type=ppt" target="_blank">下载</a>
                                    <a href="javascript:void(0)" onclick="swf_preview(${dispatch.id}, 'ppt')">预览</a>
                                    </c:if>
                                </td>
								<td>${dispatch.remark}</td>
                            <shiro:hasPermission name="dispatch:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${dispatch.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${dispatch.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="dispatch:edit">
                                    <button data-id="${dispatch.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="dispatch:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${dispatch.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
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
                                            <shiro:hasPermission name="dispatch:edit">
                                            <li>
                                                <a href="#" data-id="${dispatch.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="dispatch:del">
                                            <li>
                                                <a href="#" data-id="${dispatch.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <c:if test="${!empty commonList && commonList.pageNum>1 }">
                    <div class="row my_paginate_row">
                        <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
                        <div class="col-xs-6">
                            <div class="my_paginate">
                                <ul class="pagination">
                                    <wo:page commonList="${commonList}" uri="${ctx}/dispatch_page" target="#page-content" pageNum="5"
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    function swf_preview(id, type){

        loadModal("${ctx}/swf_preview?id="+id + "&type=" + type);
    }
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('[data-rel="select2-ajax"]').select2({
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
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>