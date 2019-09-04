// 恢复重新加载之前滚动位置及选中的行状态
var jgrid_sid, jgrid_left, jgrid_top;
function saveJqgridSelected(jqGridId) {

    jgrid_sid = jgrid_sid || {};
    // console.log($(jqGridId).getGridParam("selarrrow"))
    jgrid_sid[jqGridId] = $(jqGridId).getGridParam("selarrrow");
    //console.log(jgrid_sid[jqGridId])
}
function loadJqgridSelected(jqGridId) {

    jgrid_sid = jgrid_sid || {};
    if (jgrid_sid[jqGridId]) {
        //console.log(jqGridId + ":" + jgrid_sid[jqGridId])
        var num = jgrid_sid[jqGridId].length;
        //console.log(num)
        $(jqGridId).resetSelection();
        var ids = [];
        for (var i = 0; i < num; i++) {
            //console.log(jqGridId+"====="+i+"======"+jgrid_sid[jqGridId][i])
            ids.push(jgrid_sid[jqGridId][i])
        }

        ids.forEach(function (item, i) {
            $(jqGridId).jqGrid("setSelection", item);
        });
    }
}
function clearJqgridSelected() {
    // 清空jqGrid选择
    jgrid_sid = undefined;
}

$.jgrid.defaults.onSelectRow = function (id, status) {

    saveJqgridSelected("#" + this.id);
    //console.log(jgrid_sid)
};
$.jgrid.defaults.onSelectAll = function (aRowids, status) {
    //console.log(aRowids)
    saveJqgridSelected("#" + this.id);
}
$.jgrid.defaults.gridComplete = function () {
    // 自定义初始化方法
    $(this).trigger('initGrid');

    //console.log(jgrid_sid)
    loadJqgridSelected("#" + this.id);

    //console.log("加载完成：left:{0}, top:{1}".format(_left, _top))
    if (jgrid_left != undefined) {
        $(this).closest(".ui-jqgrid-bdiv").scrollLeft(0).scrollLeft(jgrid_left);
        jgrid_left = undefined;
    }
    if (jgrid_top != undefined) {
        $(this).closest(".ui-jqgrid-bdiv").scrollTop(0).scrollTop(jgrid_top);
        jgrid_top = undefined;
    }
    _adjustFrozenDivHeight($(this))
};

// 防止冻结列时，挡住部分滚动条
function _adjustFrozenDivHeight($jqGrid){
    setTimeout(function(){
        //console.log("---------")
        var $frozenBdiv = $jqGrid.closest(".ui-jqgrid").find(".frozen-bdiv");
        $frozenBdiv.height($frozenBdiv.height()-1);
    }, 400)
}

// 适用于页面只有一个jqGrid的情况
$(window).on('resize.jqGrid', function () {
    if ($("#body-content").is(":hidden")) {
        return;
    }
    var gridWidth = $(window).width() - 60;
    var widthReduce = $(".jqGrid").data("width-reduce");
    if (widthReduce != undefined) {
        gridWidth = gridWidth - parseInt(widthReduce);
    }

    if ($("#menu-toggler").is(":hidden")) { // 手机屏幕
        gridWidth -= $(".nav-list").width()
    }
    $(".jqGrid").jqGrid('setGridWidth', gridWidth);
    var height = 0;
    $("#body-content .jqgrid-vertical-offset").each(function () {
        height += $(this).height();
    });
    //console.log($("#navbar").height() + " " + $("#breadcrumbs").height() + " " +$(".nav-tabs").height())
    var navHeight = $(".nav.nav-tabs").height();
    navHeight = navHeight > 0 ? (navHeight + 10) : navHeight;
    if (navHeight == null) navHeight = 0;

    //console.log("document.body.scrollHeight=" + document.body.scrollHeight + " $(window).height()=" + $(window).height())
    //console.log("height=" + height + " navHeight=" + navHeight)
    var minusHeight = 320;
    if ($(".jqGrid").getGridParam("pager") == false) {
        minusHeight -= 45;
    }

    var gridHeight = $(window).height() - minusHeight - height - navHeight;
    var heightReduce = $(".jqGrid").data("height-reduce");
    if (heightReduce != undefined) {
        gridHeight = gridHeight - parseInt(heightReduce);
    }

    $(".jqGrid").setGridHeight(gridHeight)
        .trigger("reloadGrid")        // 以下两行防止jqgrid内部高度变化，导致前后高度显示不一致
        .closest(".ui-jqgrid-bdiv").scrollTop(0).scrollLeft(0);

    $(".jqGrid").each(function(){
        _adjustFrozenDivHeight($(this))
    })
});

// 适用于子页面多个jqGrid的情况
$(window).on('resize.jqGrid2', function () {
    /*if( $("#body-content-view").is(":hidden")){
     return;
     }*/
    var gridWidth = $(window).width() - 70;
    if ($("#menu-toggler").is(":hidden")) { // 手机屏幕
        gridWidth -= $(".nav-list").width()
    }
    $(".jqGrid2").each(function () {
        var $jqgrid = $(this);
        var widthReduce = $jqgrid.data("width-reduce");
        if (widthReduce != undefined) {
            gridWidth = gridWidth - parseInt(widthReduce);
        }

        $jqgrid.jqGrid('setGridWidth', gridWidth);
        var height = 0, navHeight=0;
        var _thisGrid = this;
        var $container = $("#body-content-view, #body-content-view2");
        if(!$container.is(":visible")){
            $container = $("#page-content");
        }
        $container.each(function(){
            if($(this).find(_thisGrid).length>0) {
                $(".jqgrid-vertical-offset", this).each(function () {
                    height += $(this).height();
                    //console.log(this.id + "---" + height)
                });

                navHeight = $(".nav.nav-tabs", this).height();
                navHeight = navHeight > 0 ? (navHeight + 10) : navHeight;
                if (navHeight == null) navHeight = 0;
                //console.log(this.id + "--navHeight---" + navHeight)
            }
        });

        var gridHeight = $(window).height() - 330 - height - navHeight;

        var heightReduce = $jqgrid.data("height-reduce");
        if (heightReduce != undefined) {
            gridHeight = gridHeight - parseInt(heightReduce);
        }
        //console.log(" gridWidth=" + gridWidth + "gridHeight=" + gridHeight)
        $jqgrid.setGridHeight(gridHeight);

        _adjustFrozenDivHeight($jqgrid)
    })

});
// 不改变宽度
$(window).on('resize.jqGrid3', function () {
    var height = 0;
    $("#body-content .jqgrid-vertical-offset").each(function () {
        height += $(this).height();
        //alert(height)
    });
    //alert(height)
    $(".jqGrid3").setGridHeight($(window).height() - 400 - height);
});
// 不改变高度
$(window).on('resize.jqGrid4', function () {

    var gridWidth = $(window).width() - 70;
    if ($("#menu-toggler").is(":hidden")) { // 手机屏幕
        gridWidth -= $(".nav-list").width()
    }

    $(".jqGrid4").each(function () {
        var _gridWidth = gridWidth;
        var widthReduce = $(this).data("width-reduce");
        if (widthReduce != undefined && Math.abs(widthReduce) > 0) {
            _gridWidth = _gridWidth - widthReduce;
        }
        $(this).jqGrid('setGridWidth', _gridWidth);
        // console.log(" gridWidth=" + gridWidth)
    });
});
//resize on sidebar collapse/expand
$(document).on('settings.ace.jqGrid', function (ev, event_name, collapsed) {
    if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
        //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        setTimeout(function () {
            $(window).triggerHandler('resize.jqGrid');
            $(window).triggerHandler('resize.jqGrid2');
        }, 0);
    }
});
$(document).on('shown.ace.widget hidden.ace.widget', function (ev) {
    $(window).triggerHandler('resize.jqGrid');
    $(window).triggerHandler('resize.jqGrid2');
});

$(document).on("click", ".widget-header", function () {
    $("a[data-action=collapse]", this).click()
});
$(document).on("click", ".panel-heading", function () {
    $("a[data-toggle=collapse]", this).click()
});
$(document).on("click", ".widget-header a[data-action=collapse],.panel-heading .select2,.panel-heading a,.panel-heading button, .panel-heading input, .panel-heading a[data-toggle=collapse]", function (e) {
    e.stopPropagation();
});

$(document).on("select2:select", "[data-rel=select2],[data-rel=select2-ajax]", function () {
    //alert(0)
    try {
        $(this).valid();
    } catch (e) {
    }
});
$(document).on("change", ".date-picker", function () {
    //alert(0)
    try {
        $(this).valid();
    } catch (e) {
    }
});
$(document).on("click", "button[type=reset],input[type=reset]", function (event) {
    var $form = $(this).closest('form')[0] || $(this).closest("#modal").find("form");
    $("select", $form).val(null).trigger("change");
    if ($form)$form.reset;
    //validator.resetForm();
    event.stopPropagation();
});

var console = window.console || {
        log: function () {
            return false;
        },
        dir: function () {
            return false;
        }
    };

$(document).on("click", ".table-actived tr>td", function () {

    var $tr = $(this).closest("tr");
    $(this).closest(".table-actived").find("tr").not($tr).removeClass("active");

    $tr.addClass("active");
});

$.ajaxSetup({
    cache: false,
    dataFilter: function (data, type) {
        var ret;

        try {
            ret = JSON.parse(data)
        } catch (e) {
            //console.log(data)
            return data;
        }
        //console.log(ret)
        if (ret.success==undefined || ret.success) return data;

        if (ret.msg == "login") {

            if(_hasLoginPage) {
                SysMsg.info("登录超时或账号已在别处登录，请您重新登录", "登录超时", function () {
                    location.reload();
                });
            }else{
                location.reload();
            }

            try {
                $(".loading-indicator-overlay, .loading-indicator").hide();
                NProgress.done();
            }catch(e){}

            throw  new Error("login");
            //$(".modal").width(300);
        } else if (ret.msg == "filemax") {

            SysMsg.warning('文件大小超过10M。', '文件大小限制');
        } else if (ret.msg == "wrong") {

        } else if (ret.msg == "duplicate") {

            SysMsg.warning('该记录已经存在，请不要重复添加。', '重复');
        } else
            SysMsg.error(ret.msg, '操作失败');

        return data;
    }, error: function (jqXHR, textStatus, errorMsg) {

        if (jqXHR.status == 401) {
            alert("您没有权限进行此项操作。");
        }
    }
});

//select/deselect all rows according to table header checkbox
var active_class = 'active';
$(document).on('click', '.table > thead > tr > th input[type=checkbox]', function () {

    $(this).closest('.sticky-wrap').find('table.sticky-enabled').find('thead > tr > th input[type=checkbox]').click();
    var th_checked = this.checked;//checkbox inside "TH" table header
    $(this).closest('table').find('tbody > tr').each(function () {
        var row = this;
        if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
        else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
    });
});
//select/deselect a row when the checkbox is checked/unchecked
$(document).on('click', '.table td input[type=checkbox]', function () {
    var $row = $(this).closest('tr');
    if (this.checked) $row.addClass(active_class);
    else $row.removeClass(active_class);
});

//↓↓↓↓↓↓页面列表操作↓↓↓↓↓↓
function page_reload(fn) {

    $("#modal").modal('hide');
    var $div = $(".myTableDiv");
    var queryString = $div.data("querystr");
    //console.log(queryString)
    //alert($div.data("url-page"))
    var $target = ($div.data("target")) ? ($($div.data("target")) || $("#page-content")) : $("#page-content");
    //console.log(fn)
    $target.load($div.data("url-page") + (queryString ? ("?" + queryString) : ""), function () {
        if (typeof fn == 'function') fn();
    });
}

// 添加/编辑
$(document).on("click", ".myTableDiv .editBtn", function () {

    var id = $(this).data("id");
    var $div = $(this).closest(".myTableDiv");
    var url = $div.data("url-au");
    if(id>0) url += (url.indexOf("?") > 0 ? "&" : "?") + "id=" + id;

    $.loadModal(url, $(this).data("width"));
});

// 调序
$(document).on("click", ".myTableDiv .changeOrderBtn", function () {

    var $this = $(this);
    var id = $this.data("id");
    var direction = parseInt($this.data("direction"));
    var step = $this.closest("td").find("input").val();
    var addNum = (parseInt(step) || 1) * direction;
    var $div = $this.closest(".myTableDiv");

    var fn = $this.data("callback");
    var url = $this.data("url") || $div.data("url-co");
    //console.log($div.data("url-co"))
    $.post(url, {id: id, addNum: addNum}, function (ret) {
        if (ret.success) {

            if (fn) {
                window[fn]($this);
            } else {
                //SysMsg.success('操作成功。', '成功',function(){
                if ($this.hasClass("pageReload")) {
                    page_reload();
                } else
                    $("#jqGrid").trigger("reloadGrid");
                //});
            }
        }
    });
});

// 下载
$(document).on("click", ".downloadBtn", function () {

    var $this = $(this);
    var url = $this.data("url");
    var type = $this.data("type") || 'export';

    $this.download(url, type);
    return false;
});

// 删除
$(document).on("click", ".myTableDiv .delBtn", function () {

    var id = $(this).data("id");
    var $div = $(this).closest(".myTableDiv");
    bootbox.confirm("确定删除该记录吗？", function (result) {
        if (result) {
            $.post($div.data("url-del"), {id: id}, function (ret) {
                if (ret.success) {
                    page_reload();
                    //SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});
// 批量删除
$(document).on("click", ".myTableDiv .batchDelBtn", function () {

    var $div = $(this).closest(".myTableDiv");
    var ids = $.map($(".table td :checkbox:checked", $div), function (item, index) {
        return $(item).val();
    });
    if (ids.length == 0) {
        SysMsg.warning("请选择行", "提示");
        return;
    }
    bootbox.confirm("确定删除这" + ids.length + "条数据？", function (result) {
        if (result) {
            $.post($div.data("url-bd"), {ids: ids}, function (ret) {
                if (ret.success) {
                    page_reload();
                    //SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});

// hashchange
$(document).on("click", ".hashchange", function () {

    var $this = $(this);
    $.hashchange($this.data("querystr"), $this.data("url"))
});

// load page
$(document).on("click", ".loadPage", function () {

    var $this = $(this);

    var queryString = $this.data("querystr");
    var url = $this.data("url") + (queryString ? ("?" + queryString) : "");
    var loadEl = $this.data("load-el") || "#page-content";
    var maskEl = $this.data("mask-el") || loadEl || "#page-content";
    var fn = $.trim($this.data("callback"));
    //console.log("maskEl="+maskEl)
    $.loadPage({
        url: url, loadEl: loadEl, maskEl: maskEl, callback: function () {
            $("#modal").modal('hide');
            clearJqgridSelected();
            if (fn != '') {
                 //console.log(fn)
                 //console.log($this)
                if(typeof window[fn] == "function") {
                    window[fn]($this);
                }else{
                    var evalFn = eval(fn);
                    if (typeof evalFn != "function"){
                        console.log(fn + " is not a function");
                        return false;
                    }
                    evalFn($this);
                }
            }
        }
    })
});
// 显示内页
$(document).on("click", ".openView", function () {
    var $this = $(this);
    $this.attr("disabled", "disabled");

    var hideEl = $(this).data("hide-el");
    var loadEl = $(this).data("load-el");
    if($.trim(hideEl)=='#') hideEl=undefined;
    if($.trim(loadEl)=='#') loadEl=undefined;

    var $hide = $(hideEl || "#body-content");
    var $show = $(loadEl || "#body-content-view");

    $.openView({url:$this.data("url"), $hide:$hide, $show:$show, callback:function () {
        $this.removeAttr("disabled");
    }});
});
// 隐藏内页
$(document).on("click", ".hideView", function () {

    $.hideView({url:$(this).data("url"),
        hideEl:$(this).data("hide-el"),
        loadEl:$(this).data("load-el")})
});

// 打开弹出框modal
$(document).on("click", ".popupBtn", function (e) {

    e.stopPropagation();
    var $this = $(this);
    var querystr = $.trim($this.data("querystr"));
    var url = $this.data("url");
    if($.trim(querystr)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + querystr;

    $.loadModal(url, $(this).data("width"));
});
// 子窗口打开连接
$(document).on("click", ".openUrl", function (e) {

    e.stopPropagation();
    e.preventDefault();
    var width = $(this).data("width") || 720;
    var height = $(this).data("height") || 820;
    $.openWindow($(this).data("url"), '', width, height)
});

$(document).on("click", ".confirm", function (e) {

    e.stopPropagation();
    $.confirm(this);
});

// 执行操作按钮
$(document).on("click", ".runBtn", function (e) {

    e.stopPropagation();
    var $this = $(this);
    var url = $this.data("url");
    $this.data("loading-text", $this.data("loading-text") || '<i class="fa fa-spinner fa-spin"></i> 操作中')

    var fn = $.trim($this.data("callback"));
    var $btn = $this.button('loading');
    $.post(url,function(ret){
        if(ret.success) {
            if($this.data("tip")!='no') {
                var $tip = $.tip({
                    $target: $this,
                    at: 'top center', my: 'bottom center', type: 'success',
                    msg: $this.data("success-text") || "操作成功"
                });
                setTimeout(function () {
                    $tip.qtip('destroy', true);
                    //console.log($tip)
                    $this.closest(".btn-group").removeClass("open");
                }, 3000)
            }

            if (fn != '') {
                 //console.log(fn)
                 //console.log($this)
                if(typeof window[fn] == "function") {
                    window[fn]($this);
                }else{
                    var evalFn = eval(fn);
                    if (typeof evalFn != "function"){
                        //console.log(fn + " is not a function");
                        return false;
                    }
                    evalFn($this);
                }
            }
        }
        $btn.button('reset');
    })
});

// 按钮打开连接
$(document).on("click", ".linkBtn", function (e) {

    e.stopPropagation();
    var $this = $(this);
    var url = $this.data("url");
    var target = $this.data("target");
    window.open(url, target || '_self', '');
});
// 打印地址
$(document).on("click", ".printBtn", function (e) {

    e.stopPropagation();
    $.print($(this).data("url"));
});

// 重置
$(document).on("click", " .reloadBtn", function () {

    var $this = $(this);
    var querystr = $.trim($this.data("querystr"));
    var $div = $this.closest(".myTableDiv");
    //var $target = ($div.data("target")) ? ($($div.data("target")) || $("#page-content")) : $("#page-content");
    var $target = $($this.data("target") || $div.data("target") || "#page-content");
    var url = $this.data("url") || $div.data("url-page");

    $target.renderUrl({
        url: url,
        op:$this.data("op"),
        params: querystr==''?null:("&" + querystr)
    });
});

/**
 * 编辑 for jqgrid
 *
 * 默认需要选择一条记录，弹出框显示
  */
$(document).on("click", ".jqEditBtn", function () {

    var $this = $(this);
    var openBy = $this.data("open-by");
    var needId = $(this).data("need-id");
    if (needId == undefined) needId = true;
    var idName = $this.data("id-name") || 'id';
    var grid = $("#jqGrid");
    var id = grid.getGridParam("selrow");
    var ids = grid.getGridParam("selarrrow");
    if (needId && (!id || ids.length > 1)) {
        SysMsg.warning("请选择一行", "提示");
        return;
    }

    if (needId) saveJqgridSelected("#jqGrid");

    var url = $(this).data("url") ||  $this.closest(".myTableDiv").data("url-au");
    var queryString = $(this).data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;
    if(id>0) url += (url.indexOf("?") > 0 ? "&" : "?") + idName + "=" + id;

    if (openBy == 'page') {
        var $container = $("#body-content");
        //$container.mask()
        $.get(url, {}, function (html) {
            //$container.unmask().hide();
            $container.hide();
            if (!html.startWith("{")) {
                $("#body-content-view").hide().html(html).fadeIn("slow");
            }
        })
    } else {
        $.loadModal(url, $this.data("width"));
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
$(document).on("click", ".jqOpenViewBtn", function (e) {

    e.stopPropagation();
    var openBy = $(this).data("open-by");
    var needId = $(this).data("need-id");
    if (needId == undefined) needId = true;
    var idName = $(this).data("id-name") || 'id';
    var gridId = $(this).data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var id = grid.getGridParam("selrow");
    var ids = grid.getGridParam("selarrrow");
    if (needId && (!id || ids.length > 1)) {
        SysMsg.warning("请选择一行", "提示");
        return;
    }
    if (needId) saveJqgridSelected(gridId);

    var url = $(this).data("url");
    var queryString = $(this).data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;
    if(id>0) url += (url.indexOf("?") > 0 ? "&" : "?") + idName + "=" + id;

    if (openBy == 'page') {
        var hideEl = $(this).data("hide-el");
        var loadEl = $(this).data("load-el");
        var $hide = $(hideEl || loadEl || "#body-content");// hideEl不传，loadEl传时，在当前级别加载页面
        var $show = $(loadEl || "#body-content-view");

        $.openView({url:url, $hide:$hide, $show:$show});
    } else {
        $.loadModal(url, $(this).data("width"));
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
$(document).on("click", ".jqOpenViewBatchBtn", function () {

    var openBy = $(this).data("open-by");
    var needId = $(this).data("need-id");
    if (needId == undefined) needId = true;
    var gridId = $(this).data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var idsName = $(this).data("ids-name") || 'ids[]';
    var ids = grid.getGridParam("selarrrow");
    if (needId && ids.length == 0) {
        SysMsg.warning("请选择行", "提示");
        return;
    }

    var url = $(this).data("url");
    var queryString = $(this).data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;
    url += (url.indexOf("?") > 0 ? "&" : "?") + encodeURI(idsName) + "=" + ids;

    if (openBy == 'page') {
        var $container = $("#body-content");
        //$container.mask()
        $.get(url, {}, function (html) {
           // $container.unmask().hide();
           $container.hide();
            $("#body-content-view").hide().html(html).fadeIn("slow");
        })
    } else {
        $.loadModal(url, $(this).data("width"));
    }
});

$(document).on("click", ".jqLinkItemBtn", function (e) {

    //e.stopPropagation();

    var needId = $(this).data("need-id");
    if (needId == undefined) needId = true;
    var idName = $(this).data("id-name") || 'id';
    var gridId = $(this).data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var id = grid.getGridParam("selrow");
    var ids = grid.getGridParam("selarrrow");
    if (needId && (!id || ids.length > 1)) {
        SysMsg.warning("请选择一行", "提示");
        return;
    }

    var url = $(this).data("url");
    var queryString = $(this).data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;
    if(id>0) url += (url.indexOf("?") > 0 ? "&" : "?") + idName + "=" + id;

    var target = $(this).data("target");
    window.open(url, target || '_self', '');
});

$(document).on("click", ".jqLinkBtn", function (e) {

    //e.stopPropagation();

    var needId = $(this).data("need-id");
    if (needId == undefined) needId = true;
    var gridId = $(this).data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var idsName = $(this).data("ids-name") || 'ids[]';
    var ids = grid.getGridParam("selarrrow");
    if (needId && ids.length == 0) {
        SysMsg.warning("请选择行", "提示");
        return;
    }

    var url = $(this).data("url");
    var queryString = $(this).data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;
    url += (url.indexOf("?") > 0 ? "&" : "?") + encodeURI(idsName) + "=" + ids;

    var target = $(this).data("target");
    window.open(url, target || '_self', '');
});

// 调序 for jqgird
$(document).on("click", ".jqOrderBtn", function () {

    //alert($(this).data("grid-id"))
    var gridId = $(this).data("grid-id") || "#jqGrid";
    //alert(gridId)
    var grid = $(gridId);

    jgrid_left = grid.closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = grid.closest(".ui-jqgrid-bdiv").scrollTop();
    var id = $(this).data("id");
    var direction = parseInt($(this).data("direction"));
    var step = $(this).closest("td").find("input").val();
    var addNum = (parseInt(step) || 1) * direction;

    var url = $(this).data("url") || $(this).closest(".myTableDiv").data("url-co");
    //console.log($div.data("url-co"))
    $.post(url, {id: id, addNum: addNum}, function (ret) {
        if (ret.success) {
            grid.trigger("reloadGrid");
            //SysMsg.success('操作成功。', '成功');
        }
    });
});

// 搜索 for jqgrid
$(document).on("click", " .jqSearchBtn", function () {

    var $this = $(this);
    var $div = $this.closest(".myTableDiv");
    var url = $this.data("url") || $div.data("url-page");
    //var $target = ($div.data("target")) ? ($($div.data("target")) || $("#page-content")) : $("#page-content");
    var $target = $($this.data("target") || $div.data("target") || "#page-content");
    //var $form = $($this.data("form") || "div.myTableDiv #searchForm");
    var $form = $($this.data("form") || "#searchForm");

    $target.renderUrl({
        url: url,
        params: $form.serialize()
    });
});

// 导出 for jqgrid
$(document).on("click", ".jqExportBtn", function (e) {

    var $this = $(this);

    var gridId = $this.data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var ids = grid.getGridParam("selarrrow");
    var idsName = $(this).data("ids-name") || 'ids[]';
    var _export = $(this).data("export") || '1';
    var type = $(this).data("type") || 'export';

    var url = $this.data("url") || $(this).closest(".myTableDiv").data("url-export");
    var queryString = $this.data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;

    //var searchFormId = $this.data("search-form-id") || "div.myTableDiv #searchForm";
    var searchFormId = $this.data("search-form-id") || "#searchForm";

    url = url + (url.indexOf("?") > 0 ? "&" : "?") + "export="+ _export +"&"+
        encodeURI(idsName)+"=" + ids + "&" + $(searchFormId).serialize();

    $this.download(url, type);

    e.stopPropagation();
    return false;
});

$(document).on("click", ".jqExportItemBtn", function () {

    var $this = $(this);

    var gridId = $this.data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var id = grid.getGridParam("selrow");
    var ids = grid.getGridParam("selarrrow");
    if (!id || ids.length > 1) {
        SysMsg.warning("请选择一行", "提示");
        return;
    }
    var type = $(this).data("type") || 'export';
    var idName = $this.data("id-name") || 'id';
    var url = $this.data("url");
    var queryString = $this.data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;
    if(id>0) url += (url.indexOf("?") > 0 ? "&" : "?") + idName + "=" + id;

    $this.download(url, type);
    return false;
});

// 批量操作 for jqgrid
$(document).on("click", ".jqBatchBtn", function (e) {

    e.stopPropagation();
    var $this = $(this);
    var url = $this.data("url");
    var queryString = $this.data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;

    var title = $this.data("title");
    var msg = $this.data("msg");
    var gridId = $this.data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var ids = grid.getGridParam("selarrrow");
    var callback = $.trim($this.data("callback"));

    if (ids.length == 0) {
        SysMsg.warning("请选择行", "提示");
        return;
    }
    clearJqgridSelected();

    //title = '<h3 class="label label-success" style="font-size: 20px; height: 30px;">{0}</h3>'.format(title);
    //msg = '<p style="padding:10px;font-size:20px;text-indent: 2em; ">' + msg + '</p>';
    SysMsg.confirm(msg.format(ids.length), title, function () {
        $.post(url, {ids: ids}, function (ret) {
            if (ret.success) {
                if (callback) {
                    // console.log($this)
                    window[callback]($this, ret);
                } else {
                    grid.trigger("reloadGrid");
                }
            }
        });
    });
});

$(document).on("click", ".jqRunBtn", function (e) {

    e.stopPropagation();
     var $this = $(this);
    var url = $this.data("url");
    var queryString = $this.data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;

    var title = $this.data("title");
    var msg = $this.data("msg");
    var gridId = $this.data("grid-id") || "#jqGrid";
    var grid = $(gridId);
    var ids = grid.getGridParam("selarrrow");
    var callback = $.trim($this.data("callback"));

    if (ids.length == 0) {
        SysMsg.warning("请选择行", "提示");
        return;
    }

    $this.data("loading-text", $this.data("loading-text") || '<i class="fa fa-spinner fa-spin"></i> 操作中')

    var callback = $.trim($this.data("callback"));
    SysMsg.confirm(msg.format(ids.length), title, function () {

        var $btn = $this.button('loading');
        $.post(url, {ids: ids}, function (ret) {
            if (ret.success) {
                if($this.data("tip")!='no') {
                    var $tip = $.tip({
                        $target: $this,
                        at: 'top center', my: 'bottom center', type: 'success',
                        msg: $this.data("success-text") || "操作成功"
                    });
                    setTimeout(function () {
                        $tip.qtip('destroy', true);
                        //console.log($tip)
                        $this.closest(".btn-group").removeClass("open");
                    }, 3000)
                }
                if (callback) {
                    // console.log($this)
                    window[callback]($this, ret);
                } else {
                    grid.trigger("reloadGrid");
                }
            }
            $btn.button('reset');
        });
    });
});

// 操作for jqgrid
$(document).on("click", ".jqItemBtn", function () {

    var $this = $(this);

    var needId = $(this).data("need-id");
    if (needId == undefined) needId = true;

    var id;
    if(needId) {
        var gridId = $this.data("grid-id") || "#jqGrid";
        var grid = $(gridId);

        id = grid.getGridParam("selrow");
        var ids = grid.getGridParam("selarrrow");
        if (!id || ids.length > 1) {
            SysMsg.warning("请选择一行", "提示");
            return;
        }
    }

    var callback = $.trim($this.data("callback"));
    var idName = $(this).data("id-name") || 'id';
    var url = $(this).data("url");
    var queryString = $(this).data("querystr");
    if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;
    if(id>0) url += (url.indexOf("?") > 0 ? "&" : "?") + idName + "=" + id;

    var title = $(this).data("title");
    var msg = $(this).data("msg");
    var grid = $("#jqGrid");

    SysMsg.confirm(msg, title, function () {

        var $btn = $this.button('loading');
        var text = $this.data("loading-text");
        if($.trim(text)==''){
            $this.data("loading-text", '<i class="fa fa-spinner fa-spin"></i> 操作中')
        }

        $.post(url, function (ret) {
            if (ret.success) {
                if (callback) {
                    // console.log($this)
                    window[callback]($this, ret);
                } else {
                    //grid.trigger("reloadGrid");
                    $(window).resize(); // 解决jqgrid显示的问题
                }
            }
            $btn.button('reset');
        });
    });
});

function _refreshMenu(url) {

    // 处理左侧菜单
    $("#sidebar .nav-list li").removeClass("active").removeClass("open")
    $("#sidebar .nav-list li ul").removeClass("nav-show").css("display", "none")
    while ($("#sidebar .nav-list a[data-url='" + url + "']").length == 0) {
        var idx = url.lastIndexOf("&");
        if (idx == -1) {
            idx = url.indexOf("?");
            if (idx == -1) break;
        }
        url = url.substr(0, idx);
        //console.log(url)
    }
    //console.log(url + "  " + $(".nav-list a[href='"+url+"']").length)
    $("#sidebar .nav-list a[data-url='" + url + "']").parents("li").addClass("active open").children("ul").addClass("nav-show").css("display", "block");
    $("#sidebar .nav-list a[data-url='" + url + "']").closest("li").removeClass("open");

    // 处理顶部菜单
    //url = hash.substr(1);
    while ($(".navbar-header .nav-pills a[data-url='" + url + "']").length == 0) {
        var idx = url.lastIndexOf("&");
        if (idx == -1) {
            idx = url.indexOf("?");
            if (idx == -1) break;
        }
        url = url.substr(0, idx);
        //console.log(url)
    }
    //console.log(url + "  " + $(".nav-pills a[href='"+url+"']").length)
    // 清除顶部水平菜单状态
    $(".navbar-header .nav-pills li").removeClass("active");
    $(".navbar-header .nav-pills a[data-url='" + url + "']").closest("li").addClass("active");

    $(window).resize();

    NProgress.done();
}
$(window).bind("hashchange", function () {

    NProgress.start();

    var hash = $.trim(location.hash);
    //console.log("hash=" + hash)
    if (hash == '' || hash == '#' || hash == '#/') {
        $("#page-content").renderUrl({
            url: ctx + '/index', fn: function () {
                _refreshMenu('#');
                $("#breadcrumbs").hide();
            }
        });
        return;
    }
    var url = hash.substr(1);
    if (url == '') return;

    //console.log(hash.substr(1))
    $.getJSON(ctx + "/menu_breadcrumbs?url=" + $.base64.encode(url)).done(function (topMenus) {

        var breadcrumbs = _.template($("#breadcrumbs_tpl").html())({data: topMenus});
        $.ajax({
            url: url,
            cache: false
        }).done(function (html) {
            $("#page-content").empty().append(html);
            if (topMenus.parents.length >= 1) {
                $("#breadcrumbs").html(breadcrumbs).show();
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            } else {
                //$("#breadcrumbs").hide();
            }
            $("#modal").modal('hide');
            clearJqgridSelected();

            _refreshMenu(url);

        }).fail(function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == 401) {
                SysMsg.info("您没有权限访问。", "没有权限", function () {
                    location.href = ctx + "/";
                });
            }
            if (jqXHR.status == 404) {
                SysMsg.info("页面不存在。", function () {
                    history.back();
                    //location.href = ctx + "/";
                });
            } else {
                //SysMsg.info("系统错误，请稍后再试。");
                if(_hasLoginPage) {
                    SysMsg.info("系统错误({0})，请稍后再试。".format(jqXHR.status));
                }else{
                    location.reload();
                }
            }

            NProgress.done();
        });
    })
});

// 弹出框提交表单
$(document).on("click", "#modal input[type=submit]", function () {
    $("#modal form").submit();
    return false;
});
//↑↑↑↑↑↑↑↑↑列表操作↑↑↑↑↑↑↑↑↑

// ↓↓↓↓↓↓弹出框列表操作↓↓↓↓↓↓
function pop_reload(fn) {

    var $div = $(".popTableDiv");
    var queryString = $div.data("querystr");
    //console.log(queryString)
    //alert($div.data("url-page"))
    var $target = $("#modal .modal-content");
    $target.load($div.data("url-page") + (queryString ? ("?" + queryString) : ""), function () {
        if (typeof fn === "function") fn();
    });
}

// 删除
$(document).on("click", ".popTableDiv .delBtn", function () {

    var $this = $(this);
    var id = $this.data("id");
    var $div = $this.closest(".popTableDiv");
    var fn = $this.data("callback");
    bootbox.confirm("确定删除该记录吗？", function (result) {
        if (result) {
            $.post($div.data("url-del"), {id: id}, function (ret) {
                if (ret.success) {
                    pop_reload(function(){
                        if (fn) {
                            window[fn]($this);
                        }
                    });
                    //SysMsg.success('操作成功。', '成功');
                }
            });
        }
    });
});

// 编辑
$(document).on("click", ".popTableDiv .editBtn", function () {

    var id = $(this).data("id");
    var $div = $(this).closest(".popTableDiv");

    var idName = $(this).data("id-name") || 'id';

    var url = $div.data("url-au");
    if(id>0) url += (url.indexOf("?") > 0 ? "&" : "?") + idName + "=" + id;

    $.loadModal(url, $(this).data("width"));
});
// 调序
$(document).on("click", ".popTableDiv .changeOrderBtn", function () {

    var $this = $(this);
    var id = $this.data("id");
    var direction = parseInt($this.data("direction"));
    var step = $this.closest("td").find("input").val();
    var addNum = (parseInt(step) || 1) * direction;
    var $div = $this.closest(".popTableDiv");
    var fn = $this.data("callback");
    //console.log($div.data("url-co"))
    $.post($div.data("url-co"), {id: id, addNum: addNum}, function (ret) {
        if (ret.success) {
           pop_reload(function(){
                if (fn) {
                    window[fn]($this);
                }
            });
            //SysMsg.success('操作成功。', '成功');
        }
    });
});
//↑↑↑↑↑↑↑↑↑弹出框列表操作↑↑↑↑↑↑↑↑↑

// 内页标签
$(document).on("click", "#view-box .widget-toolbar .nav-tabs li a", function () {

    var $this = $(this);
    var url = $this.data("url");
    if(url!=undefined) {
        if (url != '') {

            //if (url == '-1' || $this.closest("li").hasClass("active")) return; // 不响应

            $.loadPage({
                url: url,
                maskEl: "#view-box .tab-content",
                loadEl: "#view-box .tab-content",
                callback: function () {
                    $("#view-box .widget-toolbar .nav-tabs li").removeClass("active");
                    $this.closest("li").addClass("active");

                    clearJqgridSelected();
                }
            })
        } else {
            SysMsg.warning("暂缓开通该功能");
        }
    }
});

// 数字输入框
$(document).on("blur keyup keydown paste change", "input.num, input.float", function (event) {

    if(event.keyCode==8) return; // backspace
    if(event.keyCode < 35 || event.keyCode > 40) { // 不是方向键
        var val = $(this).val();
        //console.log(val)
        //console.log(event.keyCode + " " + val.indexOf("\."));
        //if(val.indexOf("\.")>=0 && event.keyCode==110) return false;
        if ($(this).hasClass("num"))
            $(this).val(val.replace(/[^\d]/g, ''));
        else {
            val = val.replace(/[^\d.]/g, '');
            $(this).val(val.replace(/\.\d{2,}$/g, val.substr(val.indexOf('.'), 3)));
        }
    }
});

// 禁止textarea回车换行
$(document).on('keydown input propertychange', 'textarea.noEnter, #modalForm textarea:not(.canEnter)',function(event){
    //console.log("type=" + event.type + " which =" +event.which )
    if (event.which == 13) {
        if (window.event) {
            window.event.returnValue = false;
        } else {
            event.preventDefault(); //for firefox
        }
    }
    // 去掉回车换行
    $(this).val($(this).val().replace(/[\r\n]/g, ""));
})

// 搜索框响应回车事件
$(document).on("select2:close", '#searchForm [data-rel="select2"], #searchForm [data-rel="select2-ajax"]', function () {
    $(':focus').blur();
});
$(document).on("keydown keyup","#searchForm input[type=text]", function(e){
    //alert(0)
    if(e.keyCode==13){
        e.preventDefault();
        $(':focus').blur();
    }
    e.stopPropagation();
})
$(document).on("keyup",function(e){
    //alert(0)
    if(e.keyCode==13){
        e.preventDefault();
       if($("#searchForm").is(":visible")){
           $(".jqSearchBtn ", "#searchForm").click();
       }
    }
    e.stopPropagation();
})

/** 统计页面 infobox **/
$(document).on("click", ".infobox", function () {
    var _count = $.trim($(".count", this).html());
    var _url = $.trim($(this).data("url"));
    if (parseInt(_count) > 0 && _url.length > 0)
        location.hash = _url;
});
$(document).on("click", ".infobox [data-url]", function () {
    var _count = $.trim($(".count", this).html());
    var _url = $.trim($(this).data("url"));
    if (parseInt(_count) > 0 && _url.length > 0)
        location.hash = _url;
});

/*
 { label:'排序',width: 50, name:'sortOrder',frozen:true, formatter:function(cellvalue, options, rowObject){
 return '<a title="点击修改排序" class="change-order" data-url="${ctx}/party_changeOrder" data-id="{0}" data-init="{1}" data-title="{2}">{1}</a>'
 .format(rowObject.id, rowObject.sortOrder, rowObject.code);
 }},
 */
$(document).on("click", "a.change-order", function () {
    $this = $(this);
    var url = $this.data("url");
    var id = $this.data("id");
    var init = $this.data("init");
    var title = $.trim($this.data("title"));
    var grid = $($this.data("grid-id") || "#jqGrid");
    bootbox.prompt({
        size: 'small',
        title: "修改排序，序号大的在前" + (title != '' ? "[" + title + "]" : ""),
        value: init,
        inputType: 'number',
        callback: function (result) {
            if (result != null) {
                $.post(url, {id: id, sortOrder: result}, function (ret) {
                    if (ret.success) {
                        grid.trigger("reloadGrid");
                    }
                });
            }
        }
    }).draggable({handle: ".modal-header"});
});

$(document).on("mouseover", ".prompt", function () {

    var settings = {
        animation:'pop',
        width:$(this).data("width")||300,
        title:$(this).data("title")||'提示',
        trigger:'hover',
        delay:{show:null, hide:300},
        content:'<div class="alert alert-success">' + $(this).data("prompt") + '</div>'
    };
    var popoverId = $(this).data("target");
    //console.log("target=" + $(this).data("target") + " visible=" + $("#"+popoverId).is(":visible"))
    //console.log($(".webui-popover").is(":visible"))
    if(popoverId==undefined || !$("#"+popoverId).is(":visible")) {
        $(this).webuiPopover(settings).webuiPopover('show');
    }
    //$(elem).qtip({content:$(elem).data("msg"),show: true});
    event.stopPropagation();
});