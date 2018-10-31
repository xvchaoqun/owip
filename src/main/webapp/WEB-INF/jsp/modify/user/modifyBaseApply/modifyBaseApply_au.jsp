<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body"  style="min-width: 1200px">
<form class="form-horizontal" action="${ctx}/user/modifyBaseApply_au" id="modalForm" method="post" enctype="multipart/form-data">
<div class="widget-box transparent" id="view-box">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <div class="tab-content padding-8">
                <jsp:include page="form.jsp"/>
            </div>
        </div>
    </div>
</div>
<!-- /.widget-box -->
    <div class="clearfix form-actions center">
        <c:if test="${not empty mba}">
            您的申请已提交，请等待审核。
        </c:if>
        <c:if test="${empty mba}">
        <button class="btn btn-info" type="button" id="submitBtn">
            <i class="ace-icon fa fa-check bigger-110"></i>
            保存
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            取消
        </button>
        </c:if>
    </div>
</form>
</div>

<script>
    $.fileInput($("#_avatar"),{
        style:'well',
        btn_choose:'更换头像',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'large',
        maxSize:${_uploadMaxSize},
        droppable:true,
        previewWidth: 143,
        previewHeight: 198,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    })
    $("#_avatar").find('button[type=reset]').on(ace.click_event, function(){
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar/${uv.username}'}]);
    });
    $("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar/${uv.username}?_=<%=new Date().getTime()%>'}]);

    <c:if test="${not empty mbis}">
    var mbis = ${cm:toJSONArray(mbis)};
    for(i in mbis){
        var mbi = mbis[i];
        if(mbi.code=='avatar'){
            $("#_avatarTitle").addClass("text-danger bolder");
            $("#_avatar").ace_file_input('show_file_list', [{type: 'image',
                name: '${ctx}/avatar?path={0}'.format(encodeURI(mbi.modifyValue))}]);
        }else {
            var $item = $("[data-code='{0}'][data-table='{1}']".format(mbi.code, mbi.tableName));
            $item.val(mbi.modifyValue);
            $item.closest("td").prev().addClass("text-danger bolder");
        }
    }
    </c:if>
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    })
    $("#modalForm").validate({
        submitHandler: function (form) {

            var base64 = new Base64()

            var codes=[], tables=[], tableIdNames=[], names=[], originals=[], modifys=[], types=[];
            $("*[data-code]").each(function(){
                codes.push($(this).data("code"));
                tables.push($(this).data("table"));
                tableIdNames.push($(this).data("table-id-name"));
                names.push($(this).data("name"));
                var ori = $(this).data("original");
                var mod = $(this).val()
                //console.log(ori + "=" + mod)
                //console.log(base64.encode(ori+"") + "=" + base64.encode(mod+""))
                originals.push(base64.encode(ori+''));
                modifys.push(base64.encode(mod+''));
                types.push($(this).data("type"));
            })
            //console.log(codes)
            //console.log(originals)
            //console.log(modifys)
            $(form).ajaxSubmit({
                data:{codes:codes, tables:tables, tableIdNames:tableIdNames,
                    names:names, originals:originals, modifys:modifys, types:types},
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $.hashchange();
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $.register.date($('.date-picker'));
</script>