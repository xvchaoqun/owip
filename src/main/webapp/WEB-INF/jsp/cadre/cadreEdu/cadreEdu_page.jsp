<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

        <!-- PAGE CONTENT BEGINS -->
 <div class="buttons pull-right">
    <shiro:hasPermission name="cadreEdu:edit">
        <a class="btn btn-info btn-sm" onclick="_au()" data-width="900"><i class="fa fa-plus"></i> 添加学习经历</a>
    </shiro:hasPermission>
     <c:if test="${fn:length(cadreEdus)>0}">
     <shiro:hasPermission name="cadreEdu:del">
         <button  onclick="_batchDel()" class="btn btn-danger btn-sm">
             <i class="fa fa-times"></i> 删除
         </button>
     </shiro:hasPermission>
     </c:if>
</div>
<h4>&nbsp;</h4>
<div class="space-4"></div>
<div class="table-container">
    <table id="edu-table" style="min-width: 1900px" class="table table-actived  table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">
            <label class="pos-rel">
                <input type="checkbox" class="ace checkAll">
                <span class="lbl"></span>
            </label>
        </th>
        <th>学历</th>
        <th>最高学历</th>
        <th>毕业学校</th>
        <th>院系</th>
        <th>所学专业</th>
        <th>学校类型</th>
        <th>入学时间</th>
        <th>毕业时间</th>
        <th>学制</th>
        <th>学习方式</th>
        <th>学位</th>
        <th>最高学位</th>
        <th>学位授予国家</th>
        <th>学位授予单位</th>
        <th>学位授予日期</th>
        <shiro:hasPermission name="cadreEdu:changeOrder">
            <c:if test="${!_query && commonList.recNum>1}">
                <th nowrap class="hidden-480">排序</th>
            </c:if>
        </shiro:hasPermission>
        <th nowrap></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cadreEdus}" var="cadreEdu" varStatus="st">
        <tr>
            <td class="center">
                <label class="pos-rel">
                    <input type="checkbox" value="${cadreEdu.id}" class="ace">
                    <span class="lbl"></span>
                </label>
            </td>
            <td>${eduMap.get(cadreEdu.eduId).name}</td>
            <td>${cadreEdu.isHighEdu?"是":"否"}</td>
            <td>${cadreEdu.school}</td>
            <td>${cadreEdu.dep}</td>
            <td>${cadreEdu.major}</td>
            <td>${CADRE_SCHOOL_TYPE_MAP.get(cadreEdu.schoolType)}</td>
            <td>${cm:formatDate(cadreEdu.enrolTime,'yyyy.MM')}</td>
            <td>${cm:formatDate(cadreEdu.finishTime,'yyyy.MM')}</td>
            <td>${cadreEdu.schoolLen}</td>
            <td>${learnStyleMap.get(cadreEdu.learnStyle).name}</td>
            <td>${cadreEdu.degree}</td>
            <td>${cadreEdu.isHighDegree?"是":"否"}</td>
            <td>${cadreEdu.degreeCountry}</td>
            <td>${cadreEdu.degreeUnit}</td>
            <td>${cm:formatDate(cadreEdu.degreeTime, 'yyyy-MM-dd')}</td>
            <shiro:hasPermission name="cadreEdu:changeOrder">
                <c:if test="${!_query && commonList.recNum>1}">
                    <td class="hidden-480">
                        <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreEdu.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                        <input type="text" value="1"
                               class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                        <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreEdu.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                    </td>
                </c:if>
            </shiro:hasPermission>
            <td>
                <div class="hidden-sm hidden-xs action-buttons">
                    <shiro:hasPermission name="cadreEdu:edit">
                        <button onclick="_au(${cadreEdu.id})" class="btn btn-default btn-mini btn-xs">
                            <i class="fa fa-edit"></i> 编辑
                        </button>
                    </shiro:hasPermission>

                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    </div>
                <wo:page commonList="${commonList}" uri="${ctx}/cadreEdu_page" target="#view-box .tab-content" pageNum="5"
                         model="3"/>
<div class="space-4"></div>
<div class="buttons pull-right">
        <a class="btn btn-primary btn-sm" onclick="tutor_au()" data-width="900"><i class="fa fa-plus"></i> 添加导师信息</a>
<c:if test="${fn:length(cadreTutors)>0}">
        <shiro:hasPermission name="cadreTutor:del">
            <button  onclick="tutor_batchDel()" class="btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 删除
            </button>
        </shiro:hasPermission>
    </c:if>
</div>
<h4>&nbsp;</h4>
<div class="space-4"></div>
<table id="tutor-table" class="table table-actived  table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">
            <label class="pos-rel">
                <input type="checkbox" class="ace checkAll">
                <span class="lbl"></span>
            </label>
        </th>
        <th>类别</th>
        <th>姓名</th>
        <th>所在单位及职务(职称)</th>
        <shiro:hasPermission name="cadreTutor:changeOrder">
            <c:if test="${fn:length(cadreTutors)>1}">
                <th nowrap class="hidden-480">排序</th>
            </c:if>
        </shiro:hasPermission>
        <th nowrap></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cadreTutors}" var="cadreTutor" varStatus="st">
        <tr>
            <td class="center">
                <label class="pos-rel">
                    <input type="checkbox" value="${cadreTutor.id}" class="ace">
                    <span class="lbl"></span>
                </label>
            </td>
            <td>${CADRE_TUTOR_TYPE_MAP.get(cadreTutor.type)}</td>
            <td>${cadreTutor.name}</td>
            <td>${cadreTutor.title}</td>
            <shiro:hasPermission name="cadreEdu:changeOrder">
                <c:if test="${fn:length(cadreTutors)>1}">
                    <td class="hidden-480">
                        <a href="#" <c:if test="${st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreTutor.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                        <input type="text" value="1"
                               class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                        <a href="#" <c:if test="${st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreTutor.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                    </td>
                </c:if>
            </shiro:hasPermission>
            <td>
                <div class="hidden-sm hidden-xs action-buttons">
                    <shiro:hasPermission name="cadreEdu:edit">
                        <button onclick="tutor_au(${cadreTutor.id})" class="btn btn-default btn-mini btn-xs">
                            <i class="fa fa-edit"></i> 编辑
                        </button>
                    </shiro:hasPermission>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<script>
    $("#edu-table .changeOrderBtn").click(function(){

        var id = $(this).data("id");
        var direction = parseInt($(this).data("direction"));
        var step = $(this).closest("td").find("input").val();
        var addNum = (parseInt(step)||1)*direction;
        $.post("${ctx}/cadreEdu_changeOrder",{id:id, cadreId:"${param.cadreId}", addNum:addNum},function(ret){
            if(ret.success) {
                _reload();
                SysMsg.success('操作成功。', '成功');
            }
        });
    });
   $("#tutor-table .changeOrderBtn").click(function(){

        var id = $(this).data("id");
        var direction = parseInt($(this).data("direction"));
        var step = $(this).closest("td").find("input").val();
        var addNum = (parseInt(step)||1)*direction;
        $.post("${ctx}/cadreTutor_changeOrder",{id:id, cadreId:"${param.cadreId}", addNum:addNum},function(ret){
            if(ret.success) {
                _reload();
                SysMsg.success('操作成功。', '成功');
            }
        });
    });
    function tutor_au(id){
        var url = "${ctx}/cadreTutor_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }
   function _delTutor(){
       bootbox.confirm("确定删除该记录吗？", function (result) {
           if (result) {
               $.post("${ctx}/cadreTutor_del", {id: id}, function (ret) {
                   if (ret.success) {
                       _reload();
                       SysMsg.success('操作成功。', '成功');
                   }
               });
           }
       });
   }

    function _au(id) {
        var url = "${ctx}/cadreEdu_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url, 900);
    }

/*    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreEdu_del", {id: id}, function (ret) {
                    if (ret.success) {

                        SysMsg.success('操作成功。', '成功', function(){_reload();});
                    }
                });
            }
        });
    }*/
    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreEdu_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
    function tutor_batchDel(){
        var ids = $.map($("#tutor-table td :checkbox:checked"),function(item, index){
            return $(item).val();
        });
        if(ids.length==0){
            SysMsg.warning("请选择行", "提示");
            return ;
        }
        bootbox.confirm("确定删除这"+ids.length+"条数据？",function(result){
            if(result){
                $.post("${ctx}/cadreTutor_batchDel",{ids:ids},function(ret){
                    if(ret.success) {

                        SysMsg.success('操作成功。', '成功',function(){_reload();});
                    }
                });
            }
        });
    }
    function _batchDel(){

        var ids = $.map($("#edu-table td :checkbox:checked"),function(item, index){
            return $(item).val();
        });
        if(ids.length==0){
            SysMsg.warning("请选择行", "提示");
            return ;
        }
        bootbox.confirm("确定删除这"+ids.length+"条数据？",function(result){
            if(result){
                $.post("${ctx}/cadreEdu_batchDel",{ids:ids},function(ret){
                    if(ret.success) {

                        SysMsg.success('操作成功。', '成功',function(){_reload();});
                    }
                });
            }
        });
    }
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>