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

toastr.options = {
    "closeButton": true,
    "debug": false,
    "positionClass": "toast-bottom-full-width",
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "0",
    "extendedTimeOut": "0",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}
var SysMsg = {};
SysMsg.error = function(msg, title){
    $("body").css('padding-right','0px');
    bootbox.alert(msg);
    //toastr.error(msg, title);
}
SysMsg.warning = function(msg, title){
    $("body").css('padding-right','0px');
    //toastr.warning(msg, title);
    bootbox.alert(msg);
}
SysMsg.success = function(msg, title, callback){
    $("body").css('padding-right','0px');
    //toastr.success(msg, title);
    bootbox.alert(msg, callback);
}
SysMsg.info = function(msg, title, callback){
    $("body").css('padding-right','0px');
    //toastr.info(msg, title);
    bootbox.alert(msg, callback);
}

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
            SysMsg.success('操作成功。', '成功');
        }
    });
});
// 搜索
$(document).on("click", " .searchBtn", function(){

    //var $div = $(".myTableDiv");
    var $div = $(this).closest(".myTableDiv");
    var $target = ($div.data("target"))? ($($div.data("target")) || $("#page-content")):$("#page-content");
    //alert($target)
    _tunePage(1, "", $div.data("url-page"), $target, "", "&" + $("div.myTableDiv #searchForm").serialize());
    //_tunePage(1, "", $div.data("url-page"), $target, "", "&" + $("#searchForm").serialize());
});
// 重置
$(document).on("click", " .resetBtn", function(){

    //var $div = $(".myTableDiv");
    var $div = $(this).closest(".myTableDiv");
    var querystr = $(this).data("querystr");

    var $target = ($div.data("target"))? ($($div.data("target")) || $("#page-content")):$("#page-content");
    _tunePage(1, "", $div.data("url-page"), $target, "", "&" + querystr);
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
// 批量作废
$(document).on("click", ".myTableDiv .batchAbolishBtn", function(){

    var $div = $(this).closest(".myTableDiv");
    var ids = $.map($(".myTableDiv .table td :checkbox:checked"),function(item, index){
        return $(item).val();
    });
    if(ids.length==0){
        SysMsg.warning("请选择行", "提示");
        return ;
    }
    bootbox.confirm("确定作废这"+ids.length+"条数据？",function(result){
        if(result){
            $.post($div.data("url-ba"),{ids:ids},function(ret){
                if(ret.success) {
                    page_reload();
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
$(document).on("click", "#view-box .nav-tabs li a", function(){
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
            $("#view-box .nav-tabs li").removeClass("active");
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
    var $container = $("#item-content");
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
$(document).on("click", "#item-content .closeView", function(){
    var $this = $(this);
    $("#item-content").fadeOut("fast",function(){
        if($this.hasClass("reload"))
            page_reload(function(){$("#body-content").show()});
        else
            $("#body-content").show();
    });
});

// 分党委、党支部select2联动
function register_party_branch_select($container, branchDivId, mt_direct_branch_id,
                                      init_party_id, init_party_class, partyId, branchId){

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

        var $party_class = $(this).select2("data")[0]['class'] || init_party_class;
        //alert("${party.id}")
        if($(this).val()!=init_party_id)
            $('select[name='+branchId+']', $container).val(null).trigger("change");
        if($(this).val()>0 && $party_class != mt_direct_branch_id){
            $("#"+branchDivId, $container).show();
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
                                            mt_direct_branch_id, init_party_id, classId, partyId, branchId){

    classId = classId || "classId";
    partyId = partyId || "partyId";
    branchId = branchId || "branchId";

    $('select[name='+classId+']', $container).select2({width:200}).on("change", function () {
        $('select[name='+partyId+']', $container).val(null).trigger("change");
        $('select[name='+branchId+']', $container).val(null).trigger("change");
        if($(this).val()>0){
            $("#"+partyDivId, $container).show();
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

        if($(this).val()!=init_party_id)
            $('select[name='+branchId+']', $container).val(null).trigger("change");

        if($(this).val()>0 && $('select[name='+classId+']', $container).val()!=mt_direct_branch_id){
            $("#"+branchDivId, $container).show();
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
// 选择账号
function register_user_select($select){
    $select.select2({
        templateResult: formatState,
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
function register_date($date){
    $date.datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true,
        clearBtn:true
    })
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
function register_cadre_select($select, $name){
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
    });;
}
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

    if(detectIE()) {
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