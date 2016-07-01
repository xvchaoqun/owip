bootbox.setDefaults({locale:'zh_CN'});
$.fn.select2.defaults.set("language", "zh-CN");
$.fn.select2.defaults.set("theme", "classic");
$.fn.select2.defaults.set("allowClear", true);
$.fn.select2.defaults.set("width", "200px");
// 解决IE8下select2在modal里不能搜索的bug
$.fn.modal.Constructor.prototype.enforceFocus = function () { };

$.fn.bootstrapSwitch.defaults.onText = "是";
$.fn.bootstrapSwitch.defaults.offText= "否";
$.fn.bootstrapSwitch.defaults.onColor= "success";
$.fn.bootstrapSwitch.defaults.offColor= "danger";


//$.jgrid.defaults.width = '100%';
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';
$.jgrid.defaults.prmNames={page:"pageNo",rows:"pageSize", sort:"sort",order:"order"};
//$.jgrid.defaults.width=$(window).width()-$(".nav-list").width()-50;
//$.jgrid.defaults.height=$(window).height()-390;
$.jgrid.defaults.viewrecords = true;
$.jgrid.defaults.shrinkToFit = false;
$.jgrid.defaults.rowNum = 20;
$.jgrid.defaults.multiselect = true;
$.jgrid.defaults.multiboxonly = true;
$.jgrid.defaults.mtype = "GET";
$.jgrid.defaults.datatype = "jsonp";
$.jgrid.defaults.loadui = "disable";
$.jgrid.defaults.pager = "#jqGridPager";
$.jgrid.defaults.cmTemplate = {sortable:false,
    align:'center', width:100};
$.jgrid.defaults.sortorder = "desc";
$.jgrid.defaults.ondblClickRow = function(rowid,iRow,iCol,e){
    $(".jqEditBtn").click();
}
$.jgrid.defaults.onPaging= function(){
    $(this).closest(".ui-jqgrid-bdiv").scrollTop(0).scrollLeft(0);
}

/*$.jgrid.defaults.onSelectRow = function(ids) {
    sid = ids;
};*/
// 恢复重新加载之前滚动位置及选中的行状态
var jgrid_sid,jgrid_left, jgrid_top;
$.jgrid.defaults.onSelectRow = function(id, status) {
    if(status) jgrid_sid = id;
    else jgrid_sid = null;
}
$.jgrid.defaults.gridComplete = function(){

    // 自定义初始化方法
    $(this).trigger('initGrid');
    //alert(jgrid_sid)
    if(jgrid_sid){
        $(this).jqGrid("setSelection",jgrid_sid);
    }
    //console.log("加载完成：left:{0}, top:{1}".format(_left, _top))
    if(jgrid_left!=undefined) {
        $(this).closest(".ui-jqgrid-bdiv").scrollLeft(0).scrollLeft(jgrid_left);
        jgrid_left = undefined;
    }
    if(jgrid_top!=undefined) {
        $(this).closest(".ui-jqgrid-bdiv").scrollTop(0).scrollTop(jgrid_top);
        jgrid_top = undefined;
    }
}

$(window).on('resize.jqGrid0', function () {

    $(".jqGrid0").jqGrid( 'setGridWidth', $(window).width()-$(".nav-list").width()-60-180 );
    var height = 0;
    $("#body-content .jqgrid-vertical-offset").each(function(){
        height += $(this).height();
    });
    //console.log($("#navbar").height() + " " + $("#breadcrumbs").height() + " " +$(".nav-tabs").height())
    var navHeight = $(".nav.nav-tabs").height();
    navHeight = navHeight>0?(navHeight+10):navHeight;
    if(navHeight==null) navHeight=0;

    $(".jqGrid0").setGridHeight($(window).height()-390-height-navHeight - 30)
        .trigger("reloadGrid")        // 以下两行防止jqgrid内部高度变化，导致前后高度显示不一致
        .closest(".ui-jqgrid-bdiv").scrollTop(0).scrollLeft(0);
})

$(window).on('resize.jqGrid', function () {
    if( $("#body-content").is(":hidden")){
        return;
    }
    var gridWidth = $(window).width()-60;
    if($("#menu-toggler").is(":hidden")){ // 手机屏幕
        gridWidth -= $(".nav-list").width()
    }
    $(".jqGrid").jqGrid( 'setGridWidth', gridWidth );
    var height = 0;
    $("#body-content .jqgrid-vertical-offset").each(function(){
        height += $(this).height();
    });
    //console.log($("#navbar").height() + " " + $("#breadcrumbs").height() + " " +$(".nav-tabs").height())
    var navHeight = $(".nav.nav-tabs").height();
    navHeight = navHeight>0?(navHeight+10):navHeight;
    if(navHeight==null) navHeight=0;

    $(".jqGrid").setGridHeight($(window).height()-320-height-navHeight)
        .trigger("reloadGrid")        // 以下两行防止jqgrid内部高度变化，导致前后高度显示不一致
        .closest(".ui-jqgrid-bdiv").scrollTop(0).scrollLeft(0);
})
$(window).on('resize.jqGrid2', function () {
    if( $("#item-content").is(":hidden")){
        return;
    }
    var gridWidth = $(window).width()-70;
    if($("#menu-toggler").is(":hidden")){ // 手机屏幕
        gridWidth -= $(".nav-list").width()
    }
    $(".jqGrid2").jqGrid( 'setGridWidth', gridWidth );
    var height = 0;
    $("#item-content .jqgrid-vertical-offset").each(function(){
        height += $(this).height();
        //alert(height)
    });
    $(".jqGrid2").setGridHeight($(window).height()-390-height);
})
// 不改变宽度
$(window).on('resize.jqGrid3', function () {
    var height = 0;
    $("#body-content .jqgrid-vertical-offset").each(function(){
        height += $(this).height();
        //alert(height)
    });
    //alert(height)
    $(".jqGrid3").setGridHeight($(window).height()-400-height);
})
// 不改变高度
$(window).on('resize.jqGrid4', function () {

    var gridWidth = $(window).width()-70;
    if($("#menu-toggler").is(":hidden")){ // 手机屏幕
        gridWidth -= $(".nav-list").width()
    }

    $(".jqGrid4").each(function(){
        var _gridWidth = gridWidth;
        var widthReduce = $(this).data("width-reduce");
        if(widthReduce!=undefined && Math.abs(widthReduce)>0) {
            _gridWidth = _gridWidth - widthReduce;
        }
        $(this).jqGrid( 'setGridWidth', _gridWidth );
    });
})
//resize on sidebar collapse/expand
$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
    if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
        //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        setTimeout(function() {
            $(window).triggerHandler('resize.jqGrid0');
            $(window).triggerHandler('resize.jqGrid');
            $(window).triggerHandler('resize.jqGrid2');
        }, 0);
    }
})
$(document).on('shown.ace.widget hidden.ace.widget', function(ev) {
    $(window).triggerHandler('resize.jqGrid0');
    $(window).triggerHandler('resize.jqGrid');
    $(window).triggerHandler('resize.jqGrid2');
});

toastr.options = {
    "closeButton": true,
    "debug": false,
    "positionClass": "toast-top-full-width",
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "3000",
    "extendedTimeOut": "0",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

$(document).on("click", ".myTableDiv .widget-header", function(){
    $("a[data-action=collapse]", this).click()
})
$(document).on("click", ".myTableDiv .widget-header a[data-action=collapse]",function(e){
    e.stopPropagation();
})

$(document).on("select2:select","[data-rel=select2],[data-rel=select2-ajax]",function(){
    //alert(0)
    try{$(this).valid();}catch (e){}
});
$(document).on("change",".date-picker",function(){
    //alert(0)
    try{$(this).valid();}catch (e){}
});
$(document).on("click","button[type=reset],input[type=reset]",function(event){
    var $form = $(this).closest('form')[0] || $(this).closest("#modal").find("form");
    $("select", $form).val(null).trigger("change");
    if($form)$form.reset;
    //validator.resetForm();
    event.stopPropagation();
});
$.fn.inputlimiter.defaults = $.extend({}, $.fn.inputlimiter.defaults, {
    remText: '还可以输入%n个字符...',
    limitText: '最多能输入%n个字符。'
})

var console = console || {
    log : function(){
        return false;
    }
}

var isJson = function(obj){
    var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
    return isjson;
}

$(document).on("click", ".table-actived tr>td",function(){

    var $tr = $(this).closest("tr");
    $(this).closest(".table-actived").find("tr").not($tr).removeClass("active");

     $tr.addClass("active");
})

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
            throw  new Error("login");
            //$(".modal").width(300);
        }else if(ret.msg=="filemax"){

            SysMsg.warning('文件大小超过10M。', '文件大小限制');
        }else if(ret.msg=="wrong"){

        }else if(ret.msg=="duplicate"){

            SysMsg.warning('该记录已经存在，请不要重复添加。', '重复');
        }else
            SysMsg.error(ret.msg, '操作失败');

         return data;
    },error:function(jqXHR, textStatus, errorMsg){
        //alert( '发送AJAX请求到"' + this.url + '"时出错[' + jqXHR.status + ']：' + errorMsg );
        //SysMsg.error('系统异常，请稍后再试。', '系统异常');
    }
});
var _width;
function loadModal(url, width, dragTarget){ // dragTarget：拖拽位置
    if(width>0){
        _width = width;
        $('#modal .modal-dialog').addClass("modal-width"+width);
    }else{
        $('#modal .modal-dialog').removeClass("modal-width"+_width);
    }
    dragTarget = dragTarget||".modal-header";

    $('#modal .modal-content').load(url,function(data){
        if(!data.startWith("{")) $("#modal").modal('show').draggable({handle :dragTarget});
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
$(document).on('click', '.table > thead > tr > th input[type=checkbox]', function(){

    $(this).closest('.sticky-wrap').find('table.sticky-enabled').find('thead > tr > th input[type=checkbox]').click();
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
function page_reload(fn) {

    $("#modal").modal('hide');
    var $div = $(".myTableDiv");
    var queryString = $div.data("querystr");
    //console.log(queryString)
    //alert($div.data("url-page"))
    var $target = ($div.data("target"))? ($($div.data("target")) || $("#page-content")):$("#page-content");
    $target.load($div.data("url-page") + (queryString?("?"+queryString):""), function(){if(fn) fn();});
}
// 添加/编辑
$(document).on("click", ".myTableDiv .editBtn", function(){

    var id = $(this).data("id");
    var $div = $(this).closest("div.myTableDiv");
    var url = $div.data("url-au");
    if((id > 0))url = url.split("?")[0] + "?id="+id;
    loadModal(url, $(this).data("width"));
});

// 打开弹出框modal
$(document).on("click", ".popupBtn", function(){

    loadModal($(this).data("url"), $(this).data("width"));
});

// 编辑 for jqgrid
$(document).on("click", ".myTableDiv .jqEditBtn", function(){

    var openBy = $(this).data("open-by");
    var idName = $(this).data("id-name") || 'id';
    var grid = $("#jqGrid");
    var id  = grid.getGridParam("selrow");
    var ids  = grid.getGridParam("selarrrow")
    if(!id || ids.length>1){
        SysMsg.warning("请选择一行", "提示");
        return ;
    }
    jgrid_sid = id;

    var $div = $(this).closest("div.myTableDiv");
    var url = $div.data("url-au");
    if((id > 0))url = url.split("?")[0] + "?"+ idName +"="+id;
    if(openBy=='page'){
        var $container = $("#body-content");
        $container.showLoading({'afterShow':
            function() {
                setTimeout( function(){
                    $container.hideLoading();
                }, 2000 );
            }})
        $.get(url,{},function(html){
            if(!html.startWith("{")) {
                $container.hideLoading().hide();
                $("#item-content").hide().html(html).fadeIn("slow");
            }else{
                $container.hideLoading().hide();
            }
        })
    }else{
        loadModal(url, $(this).data("width"));
    }
});

/**
 * 打开窗口或页面 for jqgrid（单条数据）
 *
 * data-open-by 打开方式 page,modal(默认)
 * data-id-name 传入的id参数名称，默认值id
 * data-url 请求地址
 * data-querystr 其他参数字符串&param=value
 * data-width 打开方式为modal时的宽度
 */
$(document).on("click", ".jqOpenViewBtn", function(){

    var openBy = $(this).data("open-by");
    var needId = $(this).data("need-id");
    if(needId==undefined) needId = true;
    var idName = $(this).data("id-name") || 'id';
    var gridId = $(this).data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var id  = grid.getGridParam("selrow");
    var ids  = grid.getGridParam("selarrrow")
    if(needId && (!id || ids.length>1)){
        SysMsg.warning("请选择一行", "提示");
        return ;
    }
    if(needId) jgrid_sid = id;

    var url = $(this).data("url");
    var querystr = $(this).data("querystr");
    if((id > 0)){
        url = url.split("?")[0] + "?"+ idName +"="+id + ((querystr!=undefined)?(querystr):"");
    }else{
        if(querystr!=undefined)
            url = url.split("?")[0] + "?" + querystr;
    }
    //url += (querystr&&querystr!='')?(querystr):"";
    if(openBy=='page'){
        var $container = $("#body-content");
        $container.showLoading({'afterShow':
            function() {
                setTimeout( function(){
                    $container.hideLoading();
                }, 2000 );
            }})
        $.get(url,{},function(html){
            $container.hideLoading().hide();
            $("#item-content").hide().html(html).fadeIn("slow");
        })
    }else{
        loadModal(url, $(this).data("width"));
    }
});

/**
 * 打开窗口或页面 for jqgrid（多条数据）
 *
 * data-open-by 打开方式 page,modal(默认)
 * data-url 请求地址
 * data-querystr 其他参数字符串&param=value
 * data-width 打开方式为modal时的宽度
 */
$(document).on("click", ".jqOpenViewBatchBtn", function(){

    var openBy = $(this).data("open-by");
    var grid = $("#jqGrid");
    var ids  = grid.getGridParam("selarrrow")
    if(ids.length==0){
        SysMsg.warning("请选择行", "提示");
        return ;
    }

    var url = $(this).data("url");
    var querystr = $(this).data("querystr");
    url = url.split("?")[0] + "?ids[]="+ids + ((querystr!=undefined)?(querystr):"");

    if(openBy=='page'){
        var $container = $("#body-content");
        $container.showLoading({'afterShow':
            function() {
                setTimeout( function(){
                    $container.hideLoading();
                }, 2000 );
            }});
        $.get(url,{},function(html){
            $container.hideLoading().hide();
            $("#item-content").hide().html(html).fadeIn("slow");
        })
    }else{
        loadModal(url, $(this).data("width"));
    }
});

$(document).on("click", ".confirm", function(){

    var _this = this;
    var url = $(this).data("url");
    var msg = $(this).data("msg");
    var callback = $.trim($(this).data("callback"));

    bootbox.confirm(msg, function (result) {
        if (result) {
            $.post(url, {}, function (ret) {
                if (ret.success) {
                     if(callback){
                        // console.log(_this)
                        window[callback](_this);
                     }
                }
            });
        }
    });
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
                    SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});
// 删除
$(document).on("click", ".myTableDiv .jqItemDelBtn", function(){

    var grid = $("#jqGrid");
    var id  = grid.getGridParam("selrow");
    if(!id){
        SysMsg.warning("请选择行", "提示");
        return ;
    }

    var $div = $(this).closest("div.myTableDiv");
    bootbox.confirm("确定删除该记录吗？", function (result) {
        if (result) {
            $.post($div.data("url-del"), {id: id}, function (ret) {
                if (ret.success) {
                    page_reload();
                    SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});
// 调序
$(document).on("click", ".myTableDiv .changeOrderBtn", function(){

    var $this = $(this);
    var id = $this.data("id");
    var direction = parseInt($this.data("direction"));
    var step = $this.closest("td").find("input").val();
    var addNum = (parseInt(step)||1)*direction;
    var $div = $this.closest(".myTableDiv");
    //console.log($div.data("url-co"))
    $.post($div.data("url-co"),{id:id, addNum:addNum},function(ret){
        if(ret.success) {
            SysMsg.success('操作成功。', '成功',function(){
                if($this.hasClass("pageReload")){
                    page_reload();
                }else
                    $("#jqGrid").trigger("reloadGrid");
            });
        }
    });
});
// 调序 for jqgird
$(document).on("click", ".myTableDiv .jqOrderBtn", function(){

    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    var id = $(this).data("id");
    var direction = parseInt($(this).data("direction"));
    var step = $(this).closest("td").find("input").val();
    var addNum = (parseInt(step)||1)*direction;
    var $div = $(this).closest(".myTableDiv");
    //console.log($div.data("url-co"))
    $.post($div.data("url-co"),{id:id, addNum:addNum},function(ret){
        if(ret.success) {
            $("#jqGrid").trigger("reloadGrid");
            SysMsg.success('操作成功。', '成功');
        }
    });
});
// 搜索
$(document).on("click", " .searchBtn", function(){

    //alert($("div.myTableDiv #searchForm").serialize())
    //var $div = $(".myTableDiv");
    var $div = $(this).closest(".myTableDiv");
    //alert($div.data("url-page"))
    var $target = ($div.data("target"))? ($($div.data("target")) || $("#page-content")):$("#page-content");

    //alert($target)
    _tunePage(1, "", $div.data("url-page"), $target, "", "&" + $("div.myTableDiv #searchForm").serialize());
    //_tunePage(1, "", $div.data("url-page"), $target, "", "&" + $("#searchForm").serialize());
});
// 搜索 for jqgrid
$(document).on("click", " .jqSearchBtn", function(){
    var $div = $(this).closest(".myTableDiv");
    var $target = ($div.data("target"))? ($($div.data("target")) || $("#page-content")):$("#page-content");
    $target.renderUrl({
        url : $div.data("url-page"),
        params : $("div.myTableDiv #searchForm").serialize()
    });
});

// 重置
$(document).on("click", " .resetBtn", function(){

    //var $div = $(".myTableDiv");
    var $div = $(this).closest(".myTableDiv");
    var querystr = $(this).data("querystr");

    var $target = ($div.data("target"))? ($($div.data("target")) || $("#page-content")):$("#page-content");
   // _tunePage(1, "", $div.data("url-page"), $target, "", "&" + querystr);
    $target.renderUrl({
        url : $div.data("url-page"),
        params : "&" + querystr
    });
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

    var $target = ($div.data("target"))? ($($div.data("target")) || $("#page-content")):$("#page-content");
    _tunePage(1, "", $div.data("url-page"), $target, "", "&" + $("div.myTableDiv #searchForm").serialize());
});
// 导出
$(document).on("click", ".myTableDiv .exportBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    location.href = $div.data("url-page") +"?export=1&" + $("div.myTableDiv #searchForm").serialize();
});
// 导出 for jqgrid
$(document).on("click", ".myTableDiv .jqExportBtn", function(){

    var grid = $("#jqGrid");
    var ids  = grid.getGridParam("selarrrow")
    var $div = $(this).closest(".myTableDiv");
    location.href = $div.data("url-export") +"?export=1&ids[]="+ids +"&" + $("div.myTableDiv #searchForm").serialize();
});

// 批量操作 for jqgrid
$(document).on("click", ".jqBatchBtn", function(){

    var _this = $(this);
    var queryString = _this.data("querystr");
    var url = _this.data("url") + (queryString?("?"+queryString):"");

    var title = _this.data("title");
    var msg = _this.data("msg");
    var gridId = _this.data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var ids  = grid.getGridParam("selarrrow");
    var callback = $.trim(_this.data("callback"));

    if(ids.length==0){
        SysMsg.warning("请选择行", "提示");
        return ;
    }
    jgrid_sid = null;

    title = '<h3 class="label label-success" style="font-size: 20px; height: 30px;">{0}</h3>'.format(title);
    msg = '<p style="padding:10px;font-size:20px;text-indent: 2em; ">'+msg+'</p>';
    SysMsg.confirm(msg.format(ids.length),title,function(result){
        if(result){
            $.post(url,{ids:ids},function(ret){
                if(ret.success) {
                    if(callback){
                        // console.log(_this)
                        window[callback](_this);
                    }else {
                        grid.trigger("reloadGrid");
                    }
                }
            });
        }
    });
});

// 操作for jqgrid
$(document).on("click", ".myTableDiv .jqItemBtn", function(){

    var grid = $("#jqGrid");
    var id  = grid.getGridParam("selrow");
    var ids  = grid.getGridParam("selarrrow")
    if(!id || ids.length>1){
        SysMsg.warning("请选择一行", "提示");
        return ;
    }

    var queryString = $(this).data("querystr");
    var idName = $(this).data("id-name") || 'id';
    var url = $(this).data("url").split("?")[0] + "?"+ idName +"="+id + (queryString?("&"+queryString):"");

    var title = $(this).data("title");
    var msg = $(this).data("msg");
    var grid = $("#jqGrid");

    SysMsg.confirm(msg, title, function (result) {
        if (result) {
            $.post(url,function (ret) {
                if (ret.success) {
                    grid.trigger("reloadGrid");
                    SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});

// 批量删除
$(document).on("click", ".myTableDiv .batchDelBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    var ids = $.map($(".myTableDiv .table td :checkbox:checked"),function(item, index){
        return $(item).val();
    });
    if(ids.length==0){
        SysMsg.warning("请选择行", "提示");
        return ;
    }
    bootbox.confirm("确定删除这"+ids.length+"条数据？",function(result){
        if(result){
            $.post($div.data("url-bd"),{ids:ids},function(ret){
                if(ret.success) {
                    page_reload();
                    SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});

// 批量删除 for jqgrid
$(document).on("click", ".myTableDiv .jqDelBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    var grid = $("#jqGrid");
    var ids  = grid.getGridParam("selarrrow");

    if(ids.length==0){
        SysMsg.warning("请选择行", "提示");
        return ;
    }
    bootbox.confirm("确定删除这<span  class='label label-lg label-danger arrowed-in arrowed-in-right'>"+ids.length+"条</span>数据？",function(result){
        if(result){
            $.post($div.data("url-bd"),{ids:ids},function(ret){
                if(ret.success) {
                    grid.trigger("reloadGrid");
                    SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});
// 弹出框提交表单
$(document).on("click", "#modal input[type=submit]", function(){$("#modal form").submit();return false;});
//↑↑↑↑↑↑↑↑↑列表操作↑↑↑↑↑↑↑↑↑

// ↓↓↓↓↓↓弹出框列表操作↓↓↓↓↓↓
function pop_reload(fn) {

    var $div = $(".popTableDiv");
    var queryString = $div.data("querystr");
    //console.log(queryString)
    //alert($div.data("url-page"))
    var $target = $("#modal .modal-content");
    $target.load($div.data("url-page") + (queryString?("?"+queryString):""), function(){if(fn) fn();});

    /*var $div = $(".popTableDiv");
    $("#modal .modal-content").load($div.data("url-page"));*/
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
                    SysMsg.success('操作成功。', '成功');
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
            SysMsg.success('操作成功。', '成功');
        }
    });
});
//↑↑↑↑↑↑↑↑↑弹出框列表操作↑↑↑↑↑↑↑↑↑

// 内页标签
$(document).on("click", "#view-box .widget-toolbar .nav-tabs li a", function(){
    var $this = $(this);
    var $container = $("#view-box .tab-content");
    $container.showLoading({'afterShow':
        function() {
            setTimeout( function(){
                $container.hideLoading();
            }, 2000 );
        }}) ;
    if($(this).data("url")!='') {
        $container.load($(this).data("url"), function () {
            $container.hideLoading();
            $("#view-box .widget-toolbar .nav-tabs li").removeClass("active");
            $this.closest("li").addClass("active");
        });
    }else{
        $container.hideLoading();
        SysMsg.warning("暂缓开通该功能");
    }
});

// 内页展示
$(document).on("click", "#body-content .openView", function(){
    var $container = $("#body-content");
    $container.showLoading({'afterShow':
        function() {
            setTimeout( function(){
                $container.hideLoading();
            }, 2000 );
        }})
    $.get($(this).data("url"),{},function(html){
        $container.hideLoading().hide();
        $("#item-content").hide().html(html).fadeIn("slow");
    })
});
$(document).on("click", "#item-content .openView", function(){
    /*var $container = $("#item-content");
    $container.showLoading({'afterShow':
        function() {
            setTimeout( function(){
                $container.hideLoading();
            }, 2000 );
        }})
    $.get($(this).data("url"),{},function(html){
        $container.hideLoading().hide();
        $("#item-content").hide().html(html).show();
    })*/
    $(this).attr("disabled", "disabled")
    $.get($(this).data("url"),{},function(html){
        $("#item-content").html(html);
        $(this).removeAttr("disabled");
    })
});
$(document).on("click", "#item-content .closeView", function(){
    var $this = $(this);
    $("#item-content").fadeOut("fast",function(){

        if($this.hasClass("reload")) {
            page_reload(function () {
                $("#body-content").show()
            });
        } else
            $("#body-content").show(0,function(){
                $(window).resize(); // 解决jqgrid不显示的问题
            });
    });
});

// 分党委、党支部select2联动
function register_party_branch_select($container, branchDivId, mt_direct_branch_id,
                                      init_party_id, init_party_class, partyId, branchId, branchIsNotEmpty){

    //var $container = $("#modalForm");
    partyId = partyId || "partyId";
    branchId = branchId || "branchId";
    $('select[name='+partyId+'], select[name='+branchId+']', $container).select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page,
                    partyId: $('select[name='+partyId+']', $container).val()
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
    $('select[name='+partyId+']', $container).on("change", function () {

        $("#" + branchDivId + " select").removeAttr("required");

        var $party_class = $(this).select2("data")[0]['class'] || init_party_class;
        //alert("${party.id}")
        if($(this).val()!=init_party_id)
            $('select[name='+branchId+']', $container).val(null).trigger("change");
        if($(this).val()>0 && $party_class != mt_direct_branch_id){
            $("#"+branchDivId, $container).show();
            if(branchIsNotEmpty!=undefined && branchIsNotEmpty)
                $("#" + branchDivId + " select").attr("required", "required");
        }else{
            $('select[name='+branchId+']', $container).val(null).trigger("change");
            $("#"+branchDivId, $container).hide();
        }
    }).change();
    $('select[name='+partyId+']', $container).on("select2:unselect",function(){
        $('select[name='+branchId+']', $container).val(null).trigger("change");
        $("#"+branchDivId, $container).hide();
    })
}

// 类型、分党委、党支部 3级联动
function register_class_party_branch_select($container, partyDivId, branchDivId,
                                            mt_direct_branch_id, init_party_id, classId, partyId, branchId, isNotEmpty){

    classId = classId || "classId";
    partyId = partyId || "partyId";
    branchId = branchId || "branchId";

    $('select[name='+classId+']', $container).select2({width:200}).on("change", function () {

        $("#" + partyDivId + " select").removeAttr("required");

        $('select[name='+partyId+']', $container).val(null).trigger("change");
        $('select[name='+branchId+']', $container).val(null).trigger("change");
        if($(this).val()>0){
            $("#"+partyDivId, $container).show();
            if(isNotEmpty!=undefined && isNotEmpty)
                $("#" + partyDivId + " select").attr("required", "required");
        }else{

            $("#"+partyDivId+", #"+branchDivId, $container).hide();
        }
    });

    $('select[name='+partyId+'], select[name='+branchId+']', $container).select2({
        width:400,
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page,
                    classId: $('select[name='+classId+']', $container).val(),
                    partyId: $('select[name='+partyId+']', $container).val()
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

    $('select[name='+partyId+']', $container).on("change", function () {

        $("#" + branchDivId + " select").removeAttr("required");

        if($(this).val()!=init_party_id)
            $('select[name='+branchId+']', $container).val(null).trigger("change");

        if($(this).val()>0 && $('select[name='+classId+']', $container).val()!=mt_direct_branch_id){
            $("#"+branchDivId, $container).show();
            if(isNotEmpty!=undefined && isNotEmpty)
                $("#" + branchDivId + " select").attr("required", "required");
        }else{
            $('select[name='+branchId+']', $container).val(null).trigger("change");
            $("#"+branchDivId, $container).hide();
        }
    });
}
function formatState (state) {
    if (!state.id) { return state.text; }
    var $state = state.text;
    if(state.code!=undefined && state.code.length>0)
        $state += '-' + state.code;
    if(state.unit!=undefined && state.unit.length>0){
        $state += '-' + state.unit;
    }
    //console.log($state)
    return $state;
};
function templateSelection(state){
    var $state = state.text;
    if(state.code!=undefined && state.code.length>0)
        $state += '-' + state.code;
    return $state;
}

function register_ajax_select($select){

    $($select).select2({
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
}
// 选择账号
function register_user_select($select, ts){
    return $select.select2({
        templateResult: formatState,
        templateSelection:ts||templateSelection,
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
}
// 选择分党委
function register_party_select($select, ts){
    return $select.select2({
        width:350,
        templateResult: formatState,
        templateSelection:ts||templateSelection,
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
}

// 日历
function register_date($date, params){
    $date.datepicker($.extend({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true,
        clearBtn:true
    }, params))
}

// 选择发文类型
function register_dispatchType_select($select, $year){
    $year.on("change",function(){
        $select.val(null).trigger("change");
    });
    $select.select2({
        templateResult: formatState,
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    year: $year.val()||-1,
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
}
// 年份-发文类型-发文， 选择任免文件
function register_dispatch_select($dispatchTypeSelect, $year, $dispatchSelect){
    register_dispatchType_select($dispatchTypeSelect, $year);
    $year.on("change",function(){
        $dispatchTypeSelect.val(null).trigger("change");
        $dispatchSelect.val(null).trigger("change");
    });
    $dispatchTypeSelect.on("change",function(){
        $dispatchSelect.val(null).trigger("change");
    });
    $dispatchSelect.select2({
        templateResult: formatState,
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    dispatchTypeId: $dispatchTypeSelect.val()||'',
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
    }).on("change",function(){
        var id = $(this).val();
        if(id>0) {
            $("#item-content").load(ctx+"/dispatch_cadres?dispatchId="+id);
            /*$("#dispatch-file-view").load(ctx + "/swf_preview?way=3&id=" + id + "&type=file");
            $("#dispatch-cadres-view").load(ctx + "/dispatch_cadres_admin?dispatchId=" + id);

            var dispatchType = $(this).select2("data")[0]['type']||'';
            var year = $(this).select2("data")[0]['year']||'';
            $dispatchTypeSelect.val(dispatchType);
            $year.val(year);*/
        }
    });
}

// 根据单位状态 - 选择单位
function register_unit_select($unitTypeSelect, $unitSelect, $unitType) {
    $unitTypeSelect.on("change", function () {
        $unitSelect.val(null).trigger("change");
    })
    $unitSelect.select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    status: $unitTypeSelect.val() | '',
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
    }).on("change",function(){
        var unitType = $(this).select2("data")[0]['type']||'';
        $unitType.val(unitType);
    });
}

// 选择干部（显示工作证号）
/*function register_cadre_select($select, $name){
    $select.select2({
        templateResult: formatState,
        templateSelection:function(state){ var $state = state.text;
            if(state.code!=undefined && state.code.length>0)
                $state = state.code;
            return $state;
        },
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
    }).on("change",function(){
        var name = $(this).select2("data")[0]['text']||'';
        $name.val(name);
    });
}*/
// 预览发文
function swf_preview(id, type){
    loadModal(ctx + "/swf_preview?id="+id + "&type=" + type);
}

/**
 * detect IE
 * returns version of IE or false, if browser is not Internet Explorer
 */
function detectIE() {
    var ua = window.navigator.userAgent;

    var msie = ua.indexOf('MSIE ');
    if (msie > 0) {
        // IE 10 or older => return version number
        return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
    }

    var trident = ua.indexOf('Trident/');
    if (trident > 0) {
        // IE 11 => return version number
        var rv = ua.indexOf('rv:');
        return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
    }

    var edge = ua.indexOf('Edge/');
    if (edge > 0) {
        // Edge (IE 12+) => return version number
        return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
    }

    // other browser
    return false;
}

function printWindow(url){
    var isFirefox=navigator.userAgent.toUpperCase().indexOf("FIREFOX")?true:false;
    //alert(isFirefox)
    if(detectIE() || isFirefox) {
        var win=window.open(url);
        win.focus();
        win.print();
    }else{
        var iframe = document.createElement('IFRAME');
        iframe.style.display="none";
        iframe.src=url;
        document.body.appendChild(iframe);
        iframe.focus();
        iframe.onload = function() {
            iframe.contentWindow.print();
        }
    }
}