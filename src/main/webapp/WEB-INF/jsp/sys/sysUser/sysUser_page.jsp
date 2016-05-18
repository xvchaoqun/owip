<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="USER_SOURCE_ADMIN" value="<%=SystemConstants.USER_SOURCE_ADMIN%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/sysUser_au"
             data-url-page="${ctx}/sysUser_page"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-12">
            <c:set var="_query" value="${not empty param.type ||not empty param.source ||not empty param.realname ||not empty param.username
            ||not empty param.roleId ||not empty param.typeId || not empty param.locked}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasRole name="admin">
                    <a class="editBtn btn btn-info btn-sm">
                        <i class="fa fa-plus"></i> 添加账号
                    </a>
                    <shiro:hasPermission name="sysUser:edit">
                        <button class="jqEditBtn btn btn-primary btn-sm">
                            <i class="fa fa-edit"></i> 修改信息
                        </button>
                    </shiro:hasPermission>
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/sysUserRole">
                        <i class="fa fa-pencil"></i> 修改角色
                    </button>
                    <button disabled id='unlockBtn' class="jqBatchBtn btn btn-success btn-sm"
                            data-url="${ctx}/sysUser_del" data-title="账号解禁"
                            data-msg="确定解禁该账号吗?" data-querystr="&locked=0">
                        <i class="fa fa-edit"></i> 解禁
                    </button>
                    <button disabled id='lockBtn' class="jqBatchBtn btn btn-danger btn-sm"
                            data-url="${ctx}/sysUser_del" data-title="账号禁用"
                            data-msg="确定禁用该账号吗?" data-querystr="&locked=1">
                        <i class="fa fa-edit"></i> 禁用
                    </button>
                </shiro:hasRole>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                                    <div class="form-group">
                                        <label>账号</label>
                                            <input class="form-control search-query" name="username" type="text" value="${param.username}"
                                                   placeholder="请输入账号">
                                    </div>
                                    <div class="form-group">
                                        <label>姓名</label>
                                            <input class="form-control search-query" name="realname" type="text" value="${param.realname}"
                                                   placeholder="请输入姓名">
                                    </div>
                                    <div class="form-group">
                                        <label>类别</label>
                                            <select name="type" data-placeholder="请选择" class="select2 tag-input-style">
                                                <option></option>
                                                <c:forEach items="${USER_TYPE_MAP}" var="userType">
                                                    <option value="${userType.key}">${userType.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}');
                                            </script>
                                    </div>
                                    <div class="form-group">
                                        <label>角色</label>
                                            <select name="roleId" data-placeholder="请选择" class="select2 tag-input-style">
                                                <option></option>
                                                <c:forEach items="${roleMap}" var="role">
                                                    <option value="${role.key}">${role.value.description}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=roleId]").val('${param.roleId}');
                                            </script>
                                    </div>
                                    <div class="form-group">
                                        <label>状态</label>
                                            <select name="locked" data-placeholder="请选择">
                                                <option></option>
                                                <option value="0">正常账号</option>
                                                <option value="1">禁用账号</option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=locked]").val('${param.locked}');
                                            </script>
                                    </div>
                                    <div class="form-group">
                                        <label>来源</label>
                                            <select name="source" data-placeholder="请选择" class="select2 tag-input-style">
                                                <option></option>
                                                <c:forEach items="${USER_SOURCE_MAP}" var="userSource">
                                                    <option value="${userSource.key}">${userSource.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=source]").val('${param.source}');
                                            </script>
                                    </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
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
        </div><div id="item-content"></div>
    </div>
</div>
<style>
    .avatar{
        width: 16px;
        cursor: pointer;
    }
    .ace-file-name{
        text-align: center!important;
    }
    .ace-file-name img{
        border: none!important;
    }
</style>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/sysUser_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '账号', name: 'username', width: 150 ,frozen:true},
            { label: '学工号', name: 'code', width: 150 ,frozen:true},
            { label: '姓名',name: 'realname',resizable:false, width: 90, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            } ,frozen:true },
            { label:'头像', name: 'avatar', width: 50, formatter:function(cellvalue, options, rowObject){
                var html ='<img title="点击修改头像" src="${ctx}/avatar/{0}?_={1}"'
                        +'class="avatar" data-id="{2}"'
                        +'data-hasimg="{3}" data-username="{4}">';
                html = html.format(rowObject.username, new Date().getTime(), rowObject.id, rowObject.avatar!='', rowObject.username)
                return html;
            },frozen:true},
            { label: '类别', name: 'typeName', width: 100 },
            { label: '性别',  name: 'genderName', width: 50 },
            { label: '系统角色',  name: 'roleIds', width: 150 , formatter:function(cellvalue, options, rowObject){
                var roles = [];
                if(cellvalue!=undefined) {
                    var roleIdArray = cellvalue.split(",");
                    for (var i = 1; i < roleIdArray.length - 1; i++) {
                        roles.push(_cMap.roleMap[roleIdArray[i]].description);
                    }
                }
                return roles.join(",")
            } },
            { label: '身份证号码',  name: 'idcard', width: 150 },
            { label: '联系电话',  name: 'mobile', width: 150 },
            { label: '邮箱',  name: 'email', width: 150 },
            { label: '账号来源', name: 'sourceName', width: 100 },
            { label: '状态', name: 'lockedName', width: 60, formatter:function(cellvalue, options, rowObject){
                return (rowObject.locked)?"禁用":"正常";
            } },
            { label:'创建时间', name: 'createTime', width: 150 },
            {  hidden:true, name: 'locked',formatter:function(cellvalue, options, rowObject){
                return (rowObject.locked)?1:0;
            }}
        ],
        onSelectRow: function(id,status){
            jgrid_sid=id;
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#lockBtn, #unlockBtn").prop("disabled", true);
            } else if (status) {
                var rowData = $(this).getRowData(id);
                console.log((status && rowData.locked) + " " + (status && !rowData.locked))
                $("#lockBtn").prop("disabled", rowData.locked==1)
                $("#unlockBtn").prop("disabled", rowData.locked==0)
            } else {
                $("#lockBtn, #unlockBtn").prop("disabled", true);
            }
        },
        rowattr: function(rowData, currentObj, rowId)
        {
            if(rowData.locked) {
                //console.log(rowData)
                return {'class':'danger'}
            }
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('.avatar').on('click', showAvatarModal);
    });
    $(window).triggerHandler('resize.jqGrid');
    
    /*$(".table th.sortable").click(function(){

        var $this = $(this);
        var order = $this.hasClass("asc")?"desc":"asc";

        $("#searchForm input[name=sort]").val($this.data("field"));
        $("#searchForm input[name=order]").val(order);
        //alert($("div.myTableDiv #searchForm").serialize())
        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "&" + $("#searchForm").serialize());
    });*/

    $("#searchForm select").select2();

    /*function _del(username, locked){

        bootbox.confirm("确定"+(locked==0?"解禁":"禁用")+"该账号吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysUser_del", {username: username, locked:locked}, function (ret) {
                    if (ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }*/

    function showAvatarModal(){
        var modal =
                '<div class="modal fade">\
                  <div class="modal-dialog">\
                   <div class="modal-content">\
                    <div class="modal-header">\
                        <button type="button" class="close" data-dismiss="modal">&times;</button>\
                        <h4 class="blue">修改头像</h4>\
                    </div>\
                    \
                    <form class="no-margin" action="${ctx}/updateAvatar" method="post">\
                     <div class="modal-body">\
                     <input type="hidden" name="userId" value="'+$(this).data("id")+'"/>\
                        <div class="space-4"></div>\
                        <div style="width:75%;margin-left:12%;"><input type="file" name="_avatar" /></div>\
                     </div>\
                    \
                     <div class="modal-footer center">\
                        <button type="submit" class="btn btn-sm btn-success"><i class="ace-icon fa fa-check"></i> 确定</button>\
                        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="ace-icon fa fa-trash"></i> 取消</button>\
                     </div>\
                    </form>\
                  </div>\
                 </div>\
                </div>';

        var modal = $(modal);
        modal.modal("show").on("hidden", function(){
            modal.remove();
        });

        var working = false;

        var form = modal.find('form:eq(0)');
        var file = form.find('input[type=file]').eq(0);
        file.ace_file_input({
            style:'well',
            btn_choose:'点击选择新头像',
            btn_change:null,
            no_icon:'ace-icon fa fa-picture-o',
            thumbnail:'small',
            before_remove: function() {
                //don't remove/reset files while being uploaded
                return !working;
            },
            //previewSize:143,
            previewWidth: 143,
            previewHeight: 198,
            allowExt: ['jpg', 'jpeg', 'png', 'gif'],
            allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
        });
        if($(this).data("hasimg"))
            file.ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar/'+$(this).data("username")}]);
        form.on('submit', function(){
            if(!file.data('ace_input_files')) return false;

            //file.ace_file_input('disable');
            form.find('button').attr('disabled', 'disabled');
            form.find('.modal-body').append("<div class='center'><i class='ace-icon fa fa-spinner fa-spin bigger-150 orange'></i></div>");

            var deferred = new $.Deferred;
            working = true;
            deferred.done(function() {

                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
                            //page_reload();
                            form.find('button').removeAttr('disabled');
                            form.find('input[type=file]').ace_file_input('enable');
                            form.find('.modal-body > :last-child').remove();
                            modal.modal("hide");

                            _reload();
                            SysMsg.success('更新成功。', '成功');
                        }
                    }
                });
                working = false;
            });
            setTimeout(function(){
                deferred.resolve();
            } , parseInt(Math.random() * 800 + 800));
            return false;
        });

    }

    function _export() {

        location.href = "${ctx}/sysUser_export?" + $("searchForm").serialize();
    }
  /*  function _search() {

        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "&" + $("#searchForm").serialize());
    }
    function _reset() {

        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "");
    }*/
    /*function _reload() {
        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sysUser_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }*/
</script>