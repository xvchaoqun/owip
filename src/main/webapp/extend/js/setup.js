var ctx="";
bootbox.setDefaults({locale:'zh_CN'});
$.fn.select2.defaults.set("language", "zh-CN");
$.fn.select2.defaults.set("theme", "classic");
$.fn.select2.defaults.set("allowClear", true);
$.fn.select2.defaults.set("width", "200px");
$(document).on("change","[data-rel=select2],.date-picker",function(){
    try{$(this).valid();}catch (e){}
});
$(document).on("click","button[type=reset],input[type=reset]",function(event){
    $("select").val(null).trigger("change");
    $(this).closest('form')[0].reset();
    //validator.resetForm();
    event.stopPropagation();
});

var console = console || {
    log : function(){
        return false;
    }
}

var isJson = function(obj){
    var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
    return isjson;
}

$.ajaxSetup({
    cache: false,
    dataFilter : function(data, type){
        var ret;

        try {
            ret = JSON.parse(data)
        }catch(e){
            //console.log(data)
            return data;
        }
        //console.log(ret)
        if(ret.success) return data;

        if(ret.msg == "login"){
            bootbox.dialog({message:"登陆超时，请您重新登陆",
                closeButton: false,
                buttons: {
                    close: {
                        label: "确定",
                        className: "btn-info",
                        callback: function () {
                            //window.open('','_self','');
                            $("#modal").modal('hide');
                            window.close();
                        }
                    },
                    login: {
                        label: "重新登陆",
                        className: "btn-success",
                        callback: function () {
                            if(window.opener){
                                //window.open('','_self','');
                                window.close();
                            }
                            location.href=ctx+"/login";
                        }
                    }
                }});
            //$(".modal").width(300);
        }else if(ret.msg=="filemax"){

            toastr.warning('文件大小超过10M。', '文件大小限制');
        }else if(ret.msg=="wrong"){

        }else if(ret.msg=="duplicate"){

            toastr.warning('该记录已经存在，请不要重复添加。', '重复');
        }else
            toastr.error(ret.msg, '操作失败');

         return data;
    },error:function(jqXHR, textStatus, errorMsg){
        //alert( '发送AJAX请求到"' + this.url + '"时出错[' + jqXHR.status + ']：' + errorMsg );
        toastr.error('系统异常，请稍后再试。', '系统异常');
    }
});

function loadModal(url, width){
    if(width>0){
        $('#modal .modal-dialog').addClass("modal-width900");
    }else{
        $('#modal .modal-dialog').removeClass("modal-width900");
    }
    $('#modal .modal-content').load(url,function(data){
        if(!data.startWith("{")) $("#modal").modal('show').draggable({handle :".modal-header"});
    });
}

_.templateSettings = {
    evaluate    : /\{\{([\s\S]+?)\}\}/g,
    interpolate : /\{\{=([\s\S]+?)\}\}/g,
    escape      : /\{\{-([\s\S]+?)\}\}/g
};

function msg_prompt(elem, event){
  //  alert(0)
    var settings = {
        animation:'pop',
        title:'温馨提示',
        content:'<div class="alert alert-success">' + $(elem).data("prompt") + '</div>'
    };
    $(elem).webuiPopover(settings).webuiPopover('show');
    //$(elem).qtip({content:$(elem).data("msg"),show: true});
    event.stopPropagation();
}
/********************************/
//add tooltip for small view action buttons in dropdown menu
//tooltip placement on right or left
function tooltip_placement(context, source) {
    var $source = $(source);
    var $parent = $source.closest('table')
    var off1 = $parent.offset();
    var w1 = $parent.width();

    var off2 = $source.offset();
    //var w2 = $source.width();

    if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
    return 'left';
}

//select/deselect all rows according to table header checkbox
var active_class = 'active';
$(document).on('click', '.table > thead > tr > th input[type=checkbox]:eq(0)', function(){
    var th_checked = this.checked;//checkbox inside "TH" table header
    $(this).closest('table').find('tbody > tr').each(function(){
        var row = this;
        if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
        else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
    });
});
//select/deselect a row when the checkbox is checked/unchecked
$(document).on('click', '.table td input[type=checkbox]' , function(){
    var $row = $(this).closest('tr');
    if(this.checked) $row.addClass(active_class);
    else $row.removeClass(active_class);
});

//↓↓↓↓↓↓页面列表操作↓↓↓↓↓↓
function page_reload() {

    $("#modal").modal('hide');
    var $div = $(".myTableDiv");
    var queryString = $div.data("querystr");
    console.log(queryString)
    //alert($div.data("url-page"))
    $("#page-content").load($div.data("url-page") + (queryString?("?"+queryString):""));
}
// 添加/编辑
$(document).on("click", ".myTableDiv .editBtn", function(){

    var id = $(this).data("id");
    var $div = $(this).closest("div.myTableDiv");
    var url = $div.data("url-au");
    if((id > 0))url = url.split("?")[0] + "?id="+id;
    loadModal(url, $(this).data("width"));
});
// 删除
$(document).on("click", ".myTableDiv .delBtn", function(){

    var id = $(this).data("id");
    var $div = $(this).closest("div.myTableDiv");
    bootbox.confirm("确定删除该记录吗？", function (result) {
        if (result) {
            $.post($div.data("url-del"), {id: id}, function (ret) {
                if (ret.success) {
                    page_reload();
                    toastr.success('操作成功。', '成功');
                }
            });
        }
    });
});
// 调序
$(document).on("click", ".myTableDiv .changeOrderBtn", function(){

    var id = $(this).data("id");
    var direction = parseInt($(this).data("direction"));
    var step = $(this).closest("td").find("input").val();
    var addNum = (parseInt(step)||1)*direction;
    var $div = $(this).closest(".myTableDiv");
    //console.log($div.data("url-co"))
    $.post($div.data("url-co"),{id:id, addNum:addNum},function(ret){
        if(ret.success) {
            page_reload();
            toastr.success('操作成功。', '成功');
        }
    });
});
// 搜索
$(document).on("click", ".myTableDiv .searchBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    _tunePage(1, "", $div.data("url-page"), "#page-content", "", "&" + $("div.myTableDiv #searchForm").serialize());
});
// 重置
$(document).on("click", ".myTableDiv .resetBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    var querystr = $(this).data("querystr");
    _tunePage(1, "", $div.data("url-page"), "#page-content", "", "&" + querystr);
});
// 排序
$(document).on("click",".myTableDiv .table th.sortable", function(){

    var $this = $(this);
    //alert($this.hasClass("asc"))
    var order = $this.hasClass("asc")?"desc":"asc";
    var $div = $(this).closest(".myTableDiv");

    $(".myTableDiv #searchForm input[name=sort]").val($this.data("field"));
    $(".myTableDiv #searchForm input[name=order]").val(order);
    //alert($("div.myTableDiv #searchForm").serialize())
    _tunePage(1, "", $div.data("url-page"), "#page-content", "", "&" + $("div.myTableDiv #searchForm").serialize());
});
// 导出
$(document).on("click", ".myTableDiv .exportBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    location.href = $div.data("url-page") +"?export=1&" + $("div.myTableDiv #searchForm").serialize();
});
// 批量删除
$(document).on("click", ".myTableDiv .batchDelBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    var ids = $.map($(".myTableDiv .table td :checkbox:checked"),function(item, index){
        return $(item).val();
    });
    if(ids.length==0){
        toastr.warning("请选择行", "提示");
        return ;
    }
    bootbox.confirm("确定删除这"+ids.length+"条数据？",function(result){
        if(result){
            $.post($div.data("url-bd"),{ids:ids},function(ret){
                if(ret.success) {
                    page_reload();
                    toastr.success('操作成功。', '成功');
                }
            });
        }
    });
});
// 弹出框提交表单
$(document).on("click", "#modal input[type=submit]", function(){$("#modal form").submit();return false;});
//↑↑↑↑↑↑↑↑↑列表操作↑↑↑↑↑↑↑↑↑

// ↓↓↓↓↓↓弹出框列表操作↓↓↓↓↓↓
function pop_reload() {

    var $div = $(".popTableDiv");
    $("#modal .modal-content").load($div.data("url-page"));
}

// 删除
$(document).on("click", ".popTableDiv .delBtn", function(){

    var id = $(this).data("id");
    var $div = $(this).closest(".popTableDiv");
    bootbox.confirm("确定删除该记录吗？", function (result) {
        if (result) {
            $.post($div.data("url-del"), {id: id}, function (ret) {
                if (ret.success) {
                    pop_reload();
                    toastr.success('操作成功。', '成功');
                }
            });
        }
    });
});
// 调序
$(document).on("click", ".popTableDiv .changeOrderBtn", function(){

    var id = $(this).data("id");
    var direction = parseInt($(this).data("direction"));
    var step = $(this).closest("td").find("input").val();
    var addNum = (parseInt(step)||1)*direction;
    var $div = $(this).closest(".popTableDiv");
    //console.log($div.data("url-co"))
    $.post($div.data("url-co"),{id:id, addNum:addNum},function(ret){
        if(ret.success) {
            pop_reload();
            toastr.success('操作成功。', '成功');
        }
    });
});
//↑↑↑↑↑↑↑↑↑弹出框列表操作↑↑↑↑↑↑↑↑↑