
/**
 * toPageNo 显示第几页内容
 * pageNo 表示指定页码的变量名称，默认为"pageNo"
 * uri 请求列表的url地址
 * selector 放置列表代码的容器，jquery选择器
 * op 对装载的内容操作 html 放入到容器中，replace将容器替换，prepend 放入容器中的前面，append 放入容器中的后面,before放入容器外前面,after放入容器外后面 默认为html
 * searchStr是用来记录搜索时的条件字符串的，生产格式如下：“&name=abc&id=1&pid=8”;
 */
function _tunePage(toPageNo, pageNo, uri, selector, op, searchStr, method) {
	var topage = 1;
	if(typeof toPageNo == "string"){
		try{toPageNo = parseInt(toPageNo);}catch(_e){}
	}
    if (typeof(toPageNo) != "number" || toPageNo < 1) topage = 1;
    else topage = toPageNo;
    if(!pageNo){
    	pageNo = "pageNo";
    }
    try {
    	var para = pageNo+"=" + topage ;
        if (searchStr && searchStr != "null" && searchStr.length>0) {
            //  alert("add_searchStr_pathname=" + window.location.pathname +"?pageNo="+ toPageNo + searchStr);
            //window.location = window.location.pathname + "?pageNo=" + toPageNo + searchStr;
        	//_renderUrl(uri + "?"+pageNo+"=" + topage + searchStr, selector, replace);
        	para += searchStr;
        }
        else {
          //  _renderUrl(uri + "?"+pageNo+"=" + topage, selector, replace);
        }

        $(selector).renderUrl({
    		url : uri,
    		op : op,
			params : para,
			method:method
    	});
    }
    catch(e) {
       // window.location = window.location.pathname + window.location.search;
       // _renderUrl(uri + "?"+pageNo+"=1", selector, replace);
    	$.error("分页出错");
    }
}

/*========jquery.validate.extend=====start===*/
if (jQuery.validator) {
    jQuery.extend(jQuery.validator.messages, {
        required: "必填字段",
        remote: "请修正该字段",
        email: "请输入正确格式的电子邮件",
        url: "请输入合法的网址",
        date: "请输入合法的日期",
        dateISO: "请输入合法的日期 (ISO).",
        number: "请输入合法的数字",
        digits: "只能输入非负整数",
        creditcard: "请输入合法的信用卡号",
        equalTo: "请再次输入相同的值",
        accept: "请输入拥有合法后缀名的字符串",
        maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
        minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
        rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
        range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
        max: jQuery.validator.format("请输入一个最大为{0} 的值"),
        min: jQuery.validator.format("请输入一个最小为{0} 的值")
    });
    jQuery.validator.setDefaults({
        /* errorElement: 'span',
         errorClass: 'help-block',*/
        focusInvalid: false,
        ignore: '',
        invalidHandler: function (event, validator) { //display error alert on form submit
        },
        highlight: function (e) {
        },
        success: function (errorElement) {
        },
        errorPlacement: function (error, element) {
            if (error.html() != '') {
                //$("label:first", element.closest(".form-group")).css("color", "red")
                //console.log(element.data("container"))
                $.tip({
                    $form: element.closest("form"), $container: $(element.data("container")),
                    field: element.attr("name"), msg: error.text()
                })
            }
        }
    })

//中文字两个字节
    jQuery.validator.addMethod("byteRangeLength", function (value, element, param) {
        var length = value.length;
        for (var i = 0; i < value.length; i++) {
            if (value.charCodeAt(i) > 127) {
                length++;
            }
        }
        return this.optional(element) || (length >= param[0] && length <= param[1]);
    }, $.validator.format("请确保输入的值在{0}-{1}个字符之间(一个中文字算2个字符)"));

//中文字两个字节
    jQuery.validator.addMethod("byteMaxLength", function (value, element, param) {
        var length = value.length;
        for (var i = 0; i < value.length; i++) {
            if (value.charCodeAt(i) > 127) {
                length++;
            }
        }

        return this.optional(element) || (length <= param);
    }, $.validator.format("输入长度最多是{0}的字符串(汉字算2个字符)"));

// 邮政编码验证
    jQuery.validator.addMethod("isZipCode", function (value, element) {
        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的邮政编码");

// 手机号码验证
    jQuery.validator.addMethod("mobile", function (value, element) {
        var length = value.length;
        //console.log("_mobileRegex="+_mobileRegex)
        var mobile = new RegExp(_mobileRegex);
        return this.optional(element) || (mobile.test(value));
    }, "请正确填写手机号码");

    // 课时
    jQuery.validator.addMethod("period", function (value, element) {
        var period = /^\d*(\.(5|0))?$/;
        //console.log(value + ":" + period.test(value))
        return this.optional(element) || (period.test(value));
    }, "请输入正确的课时（最小单位0.5小时）");

    //自定义validate验证输入的数字小数点位数不能大于两位
    jQuery.validator.addMethod("minNumber", function (value, element) {
        var returnVal = true;
        inputZ = value;
        var ArrMen = inputZ.split(".");    //截取字符串
        if (ArrMen.length == 2) {
            if (ArrMen[1].length > 2) {    //判断小数点后面的字符串长度
                returnVal = false;
                return false;
            }
        }
        return returnVal;
    }, "小数点后最多为两位");
}
/*========jquery.validate.extend=====end===*/
if (typeof _ !== 'undefined') {
    _.templateSettings = {
        evaluate: /\{\{([\s\S]+?)\}\}/g,
        interpolate: /\{\{=([\s\S]+?)\}\}/g,
        escape: /\{\{-([\s\S]+?)\}\}/g
    };
}
var SysMsg = {};
SysMsg.error = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.dialog({
        title: '<div class="msg title text-danger"><i class="fa fa-exclamation-triangle"></i> ' + (title || "提示") + '</div>',
        message: '<div class="msg info">' + msg + '</div>',
        closeButton: false,
        buttons: {
            close: {
                label: '<i class="fa fa-hand-o-right"> 关闭',
                className: "btn-default",
                callback: callback || function () {
                }
            }
        }
    }).draggable({handle: ".modal-header"});
}
SysMsg.warning = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.warning(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.dialog({
        title: '<div class="msg title text-info"><i class="fa fa-exclamation-triangle"></i> ' + (title || "提示") + '</div>',
        message: '<div class="msg info">' + msg + '</div>',
        closeButton: false,
        buttons: {
            close: {
                label: '<i class="fa fa-hand-o-right"> 确定',
                className: "btn-warning",
                callback: callback || function () {
                }
            }
        }
    }).draggable({handle: ".modal-header"});
}
SysMsg.success = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.success(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.dialog({
        title: '<div class="msg title text-success"><i class="fa fa-check-square-o"></i> ' + (title || "操作成功") + '</div>',
        message: '<div class="msg info">' + msg + '</div>',
        closeButton: false,
        buttons: {
            close: {
                label: '<i class="fa fa-hand-o-right"> 确定',
                className: "btn-success",
                callback: callback || function () {
                }
            }
        }
    }).draggable({handle: ".modal-header"});
}
SysMsg.info = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.info(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.dialog({
        title: '<div class="msg title text-info"><i class="fa fa-info-circle"></i> ' + (title || "提示") + '</div>',
        message: '<div class="msg info">' + msg + '</div>',
        closeButton: false,
        buttons: {
            close: {
                label: '<i class="fa fa-hand-o-right"> 确定',
                className: "btn-primary",
                callback: callback || function () {
                }
            }
        }
    }).draggable({handle: ".modal-header"});
}
SysMsg.confirm = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.success(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.confirm({
        title: '<div class="msg title text-primary"><i class="fa fa-info-circle"></i> ' + (title || "信息确认") + '</div>',
        message: '<div class="msg info text-danger">' + msg + '</div>',
        callback: function (result) {
            if (result) {
                if (callback) callback();
            }
        }
        /*,
         closeButton:false
         */
    }).draggable({handle: ".modal-header"});
};

$.fn.extend({
    renderUrl: function (options) {
        var defaults = {
            op: "html",
            params: {},
            url: "",
            //method:"POST",
            fn: function () {

            }
        };
        if (typeof options == "string") {
            options = {url: options};
        }
        options = $.extend(defaults, options);

        //alert(JSON.stringify(defaults))
        this.each(function () {
            if (options.url) {
                var thisContainer = $(this);
                /*thisContainer.showLoading({'afterShow':
                    function() {
                        setTimeout( function(){
                            thisContainer.hideLoading();
                        }, 5000 );
                    }});*/
                $.ajax({
                    url: options.url,
                    type: options.method,
                    data: options.params,
                    cache: false,
                    success: function (html) {
                        //_img_load_div.remove();
                        if (options.op == "replace") {
                            $(thisContainer).replaceWith(html);
                        } else if (options.op == "append") {
                            $(thisContainer).append(html);
                        } else if (options.op == "prepend") {
                            $(thisContainer).prepend(html);
                        } else if (options.op == "before") {
                            $(thisContainer).before(html);
                        } else if (options.op == "after") {
                            $(thisContainer).after(html);
                        } else {
                            //alert(html)
                            $(thisContainer).empty().append(html);
                        }
                        if (options.fn) {
                            options.fn(html);
                        }
                        thisContainer.hideLoading();
                    },
                    error: function () {
                        $.error("页面出错");
                        thisContainer.html("页面出错");
                    }
                });
            }
        });
        return this;
    },
    fixedTable: function (option) {
        var fixedCell = option.fixedCell || 0;
        var fixedType = option.fixedType || 'left';
        var $table = $(this).clone(true);

        var fixedCellTheadList = getFixedCellThead($(this), fixedCell, fixedType);
        var fixedCellTbodyList = getFixedCellTbody($(this), fixedCell, fixedType);
        //console.log(fixedCellTheadList)
        //console.log(fixedCellTbodyList)
        setFixedCell($table, fixedCellTheadList.fixedCellList, fixedCellTbodyList);
        $(this).parent().css({
            "width": Number($(this).parent().width()) - Number(fixedCellTheadList.fixedCellWidth) + "px",
            "position": "relative",
            "top": "0"
        });
        $(this).parent().parent().css("position", "relative");
        $table.attr("id", $table.attr("id") + "_fixed");
        $table.css({
            "width": fixedCellTheadList.fixedCellWidth + "px",
            "position": "absolute",
            "top": 0
        })
        if (fixedType == 'left') {
            $table.css("left", "0");
            $(this).parent().css("left", fixedCellTheadList.fixedCellWidth);
        } else {
            $table.css("right", "0");
            $(this).parent().css("left", "0");
        }
        $(this).parent().parent().append($table);

        function setFixedCell($ele, fixedCellTheadList, fixedCellTbodyList) {
            $ele.find("thead").html(fixedCellTheadList);
            $ele.find("tbody").html(fixedCellTbodyList.join(""));
        }

        function getFixedCellThead($ele, fixedCell, fixedType) {
            var ret = {};
            var needArr = [];
            var width = 0;
            var needLength = $ele.find("th").length;
            if (fixedType == "left") {
                var removeTh = [];
                for (var i = 0; i < fixedCell; i++) {
                    needArr.push($ele.find("th").eq(i).clone(true)[0].outerHTML);
                    width += $ele.find("th").eq(i).width() +
                        parseInt($ele.find("th").eq(i).css("padding-left")) +
                        parseInt($ele.find("th").eq(i).css("padding-right")) +
                        parseInt($ele.find("th").eq(i).css("border-left")) +
                        parseInt($ele.find("th").eq(i).css("border-right"));
                    //console.log("needArr="+ needArr)
                    removeTh.push($ele.find("th").eq(i));
                }
                $.each(removeTh, function (i, th) {
                    th.remove();
                })
            } else {
                for (var i = needLength - fixedCell; i < needLength; i++) {
                    needArr.push($ele.find("th").eq(i).clone(true)[0].outerHTML);
                    width += $ele.find("th").eq(i).width();
                    $ele.find("th").eq(i).remove();
                }
            }

            ret.fixedCellList = "<tr>" + needArr.join("") + "</tr>";
            ret.fixedCellWidth = width;
            return ret;
        }

        function getFixedCellTbody($ele, fixedCell, fixedType) {
            var needArr = [];
            var needLength = $ele.find("th").length + fixedCell;
            var needTr = $ele.find("tbody").children("tr");
            var needTrLength = needTr.length;

            for (var k = 0; k < needTrLength; k++) {
                var $needTdArr = $(needTr[k]).children("td");
                if (fixedType == "left") {
                    var nowTr = [];
                    for (var i = 0; i < fixedCell; i++) {
                        nowTr.push($needTdArr.eq(i).clone(true)[0].outerHTML);
                        $needTdArr.eq(i).remove();
                    }
                    needArr.push("<tr>" + nowTr.join("") + "</tr>");
                } else {
                    var nowTr = [];
                    for (var i = needLength - fixedCell; i < needLength; i++) {
                        nowTr.push($needTdArr.eq(i).clone(true)[0].outerHTML);
                        $needTdArr.eq(i).remove();
                    }
                    needArr.push("<tr>" + nowTr.join("") + "</tr>");
                }
            }
            return needArr;
        }
    }
});

var _modal_width;
(function ($) {
    $.extend({
        openWindow: function (url, name, iWidth, iHeight) {
            /* var url; //转向网页的地址;
            var name; //网页名称，可为空;
            var iWidth; //弹出窗口的宽度;
            var iHeight; //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置; */
            //win = window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=yes,location=no,status=no');

            iWidth = iWidth || (screen.availWidth - 10);
            iHeight = iHeight || (screen.availHeight - 30);
            win = window.open(url, name, 'width=' + iWidth + ',height=' + iHeight + 'fullscreen=yes,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no,status=no');
            /*win.resizeTo(screen.width,screen.height);
            win.moveTo(0,0);
            win.focus();*/

            return win;
        },
        trimHtml: function (html) {
            return $.trim($('<div/>').html(html).text());
        },
        reloadMetaData: function (fn) {
            $.getJSON(ctx + "/cache/flush_metadata_JSON", function (ret) {
                //alert(typeof fn)
                if (ret.success && $.isJson(ret.metadata)) {
                    _cMap = ret.metadata;
                }
                if (typeof fn == 'function') fn();
            })
        },
        // 左div float时，保证左div高度不小于右div高度
        adjustLeftFloatDivHeight: function ($leftFloatDiv) {
            $.each($leftFloatDiv, function (i, e) {
                var thisHeight = $(this).height();
                //console.log($(e).attr("class"))
                //console.log($(e).parent().attr("class"))
                //console.log($(e).next().attr("class"))
                var nextHeight = $(e).next().height();
                if (nextHeight > thisHeight) {
                    $(this).height(nextHeight).css("line-height", nextHeight + "px");
                }
            });
        },
        menu: {
            liSelected: function ($aHref) {
                //console.log($aHref)
                var $nav = $aHref.closest(".nav");
                $("li", $nav).removeClass("active");
                $aHref.closest("li").addClass("active");
            }
        },
        /**
         * detect IE
         * returns version of IE or false, if browser is not Internet Explorer
         */
        isIE: function () {
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
        },
        loadModal: function (url, width, direction, dragTarget) { // 加载url内容，dragTarget：拖拽位置
            //$("#modal").modal('hide');
            //console.log("width="+width + " _modal_width=" + _modal_width);
            if (width > 0) {
                $('#modal .modal-dialog').removeClass("width" + _modal_width).addClass("width" + width);
                _modal_width = width;
            } else {
                $('#modal .modal-dialog').removeClass("width" + _modal_width);
                _modal_width = undefined;
            }
            dragTarget = dragTarget || ".modal-header,.modal-footer";
            if (direction) {
                $("#modal").addClass(direction);
            }
            $('#modal .modal-content').load(url, function (data) {
                if (!data.startWith("{")) $("#modal").modal('show').draggable({handle: dragTarget});
            });
        },
        loadPdfModal: function (url, width, direction, dragTarget) { // 加载url内容，dragTarget：拖拽位置
            //$("#modal").modal('hide');
            //console.log("width="+width + " _modal_width=" + _modal_width);
            if (width > 0) {
                $('#modal .modal-dialog').removeClass("width" + _modal_width).addClass("width" + width);
                _modal_width = width;
            } else {
                $('#modal .modal-dialog').removeClass("width" + _modal_width);
                _modal_width = undefined;
            }
            dragTarget = dragTarget || ".modal-header";
            if (direction) {
                $("#modal").addClass(direction);
            }
            //     position: sticky;right: 20px;top: 20px;z-index: 2222;
            var close = '<button type="button" style="padding: 20px; position: absolute;right: 0;top: -10px;z-index: 2222"'
                // var close = '<button type="button" style="position: sticky;padding: 20px;top: 0px;z-index: 2222;"'
                + 'data-dismiss="modal" aria-hidden="true" class="close">&times;</button>'
            $('#modal .modal-content').html(close + "<div id='preview'><img src='" + url + "'></div>")
            $("#preview img").width(window.screen.availWidth)
            $(".modal-dialog").css("margin", "0")
            $("#modal").modal('show').draggable({handle: dragTarget});
        },
        showModal: function (content, width, dragTarget) { // 直接显示content内容，dragTarget：拖拽位置
            //$("#modal").modal('hide');
            //console.log("width="+width + " _modal_width=" + _modal_width);
            if (width > 0) {
                $('#modal .modal-dialog').removeClass("width" + _modal_width).addClass("width" + width);
                _modal_width = width;
            } else {
                $('#modal .modal-dialog').removeClass("width" + _modal_width);
                _modal_width = undefined;
            }
            dragTarget = dragTarget || ".modal-header";

            $('#modal .modal-content').html(content);
            $("#modal").modal('show').draggable({handle: dragTarget});
        },
        tip: function (params) {

            var $target = params.$target;
            var $container = params.$container;
            if ($container != undefined && $container.is(":hidden")) return;
            var msg = params.msg;
            var my = params.my;
            var at = params.at;
            var type = params.type || "info";
            var inactive = params.inactive || 1000;

            if ($target == undefined) {

                var $form = params.$form;
                var field = params.field;
                $target = $("[name=" + field + "]", $form);
                if ($target.closest(".input-group").length > 0) {
                    $target = $target.closest(".input-group")
                } else if ($target.is('[data-rel="select2"]')
                    || $target.is('[data-rel="select2-ajax"]')
                    || $target.hasClass("select2-hidden-accessible")) {
                    $target = $target.closest("div,td").find(".select2-container");
                } else if ($target.closest(".ace-file-input").length > 0) {
                    $target = $target.closest(".ace-file-input")
                } else if ($target.is(":radio")) {
                    $target = $target.closest("div,td")
                }
                $container = $form;
                if ($form.closest("#modal").length > 0) {
                    $container = $form.closest("#modal");
                }
            }
            var label = '<i class="fa fa-warning red"></i> ';
            if (type == "success") {
                label = '<i class="fa fa-check-circle green"></i> ';
            }
            //console.log($target)
            //console.log($container)
            return $target.qtip({
                content: label + msg,
                show: true, hide: {
                    event: 'unfocus',
                    //inactive: inactive
                }, position: {
                    container: $container || $('#page-content'),
                    my: my || $target.data("my") || (isMobile?'bottom center':'left center'),
                    at: at || $target.data("at") || (isMobile?'top center':'right center')
                }
            });
        },
        getEvent: function () {
            return window.event || arguments.callee.caller.arguments[0];
        },
        party: function (partyId, branchId) { // 显示组织名称

            var party = _cMap.partyMap[partyId];
            var _partyView = null;
            if (party != undefined) {
                _partyView = party.name;
                if ($.inArray("party:list", _permissions) >= 0 || $.inArray("party:*", _permissions) >= 0)
                    _partyView = '<a href="javascript:;" class="openView" data-url="{2}/party_view?id={0}">{1}</a>'
                        .format(party.id, party.name, ctx);
            }

            var branch = (branchId == undefined) ? undefined : _cMap.branchMap[branchId];
            var _branchView = null;
            if (branch != undefined) {
                var _branchView = branch.name;
                if ($.inArray("branch:list", _permissions) >= 0 || $.inArray("branch:*", _permissions) >= 0)
                    _branchView = '<a href="javascript:;" class="openView" data-url="{2}/branch_view?id={0}">{1}</a>'
                        .format(branch.id, branch.name, ctx);
            }

            if (_partyView != null && _branchView != null) {
                return '<span class="{0}">{1}</span><span class="{2}">{3}</span>'
                    .format(party.isDeleted ? "delete" : "", _partyView, branch.isDeleted ? "delete" : "", " - " + _branchView);
            } else if (_partyView != null) {
                return '<span class="{0}">{1}</span>'.format(party.isDeleted ? "delete" : "", _partyView);
            } else if (_branchView != null) { // 仅显示党支部
                return '<span class="{0}">{1}</span>'
                    .format(branch.isDeleted ? "delete" : "", _branchView);
            }
            return '';
        },
        del: function (str, del) { // 显示名称，del：是否带删除线

            if ($.trim(str) == '') return str;
            return '<span class="{0}">{1}</span>'
                .format(del ? "delete" : "", str);
        },
        cadre: function (cadreId, realname, params) {

            if (cadreId == undefined || cadreId <= 0) {
                console.log("illegal cadreId:" + cadreId);
                return $.trim(realname);
            }
            //console.log("==="+$.inArray("cadre:archive", _permissions))
            if ($.trim(realname) != '' && $.inArray("cadre:archive", _permissions) >= 0) {

                if (params == '_blank') {
                    return ('<a href="{2}/#{2}/cadre_view?cadreId={0}&hideBack=1" target="_blank">{1}</a>')
                        .format(cadreId, realname, ctx);
                } else if ($.isJson(params) && params.hideId != undefined && params.loadId != undefined) {

                    return ('<a href="javascript:;" class="openView" data-hide-el="#{3}"  data-load-el="#{4}" ' +
                        'data-url="{2}/cadre_view?cadreId={0}&hideEl={4}&loadEl={3}">{1}</a>')
                        .format(cadreId, realname, ctx, params.hideId, params.loadId);

                } else {
                    return '<a href="javascript:;" class="openView" data-url="{2}/cadre_view?cadreId={0}">{1}</a>'
                        .format(cadreId, realname, ctx);
                }
            }

            return $.trim(realname);
        },
        member: function (userId, realname) {

            if (userId > 0 && $.trim(realname) != '')
                return '<a href="javascript:;" class="openView" data-url="{2}/member_view?userId={0}">{1}</a>'
                    .format(userId, realname, ctx);

            return $.trim(realname);
        },
        user: function (userId, label) {

            if (userId > 0 && $.trim(label) != ''
                && ($.inArray("sysUser:view", _permissions) >= 0
                    || $.inArray("sysUser:*", _permissions) >= 0)) {

                return '<a href="javascript:;" class="openView" data-url="{2}/sysUser_view?userId={0}">{1}</a>'
                    .format(userId, label, ctx);
            }

            return $.trim(label);
        },
        isIntNum: function (val) {
            var regPos = /^\d+$/; // 非负整数
            var regNeg = /^\-[1-9][0-9]*$/; // 负整数
            if (regPos.test(val) || regNeg.test(val)) {
                return true;
            } else {
                return false;
            }
        },
        date: function (str, format) {

            if ($.trim(str) == '') return '';
            var date = null;
            //console.log(str + " " + isNaN(str) + " " + $.isIntNum(str))
            if ($.isIntNum(str)) {
                date = new Date(str);
            } else {
                date = new Date(Date.parse($.trim(str).replace(/-/g, "/")));
            }
            return date.format(format);
        },
        //计算天数差的函数，通用
        dayDiff: function (sDate1, sDate2) {    //sDate1和sDate2是2006-12-18格式
            var aDate, oDate1, oDate2, iDays
            sDate1 = sDate1.substr(0, 10)
            sDate2 = sDate2.substr(0, 10)
            oDate1 = new Date(sDate1.replaceAll("-", "/")).getTime();   //转换为2006/12/18格式，兼容IE
            oDate2 = new Date(sDate2.replaceAll("-", "/")).getTime();
            //console.log(oDate1)
            //console.log(oDate2)
            iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24)    //把相差的毫秒数转换为天数
            return iDays + 1; // 第一天和最后一天都算
        },
        //计算月份差
        monthDiff: function (date1, date2) {
            //默认格式为"2003-03-03",根据自己需要改格式和方法
            // console.log("date1=" + date1)
            var year1 = date1.substr(0, 4);
            var year2 = date2.substr(0, 4);
            var month1 = date1.substr(5, 2);
            var month2 = date2.substr(5, 2);
            var day1 = date1.substr(8, 2);
            var day2 = date2.substr(8, 2);

            var len = (year2 - year1) * 12 + (month2 - month1);

            if (len > 0 && month1 == month2 && day2 < day1) // 月份相同，日在后面，则这个月不能算
                len--;

            return len;

        },
        monthOffNow: function (date) {// 距离现在多少月，date格式：yyyy-MM-dd
            return $.monthDiff(date, new Date().format("yyyy-MM-dd"));
        },
        yearOffNow: function (date) {// 距离现在多少年，date格式：yyyy-MM-dd
            return Math.floor($.monthOffNow(date) / 12);
        },
        calAge: function (date) {// 计算年龄，date格式：yyyy-MM-dd
            var year = $.yearOffNow(date);
            if (year == 0) {
                var month = $.monthOffNow(date);
                if (month == 0) return "未满月";
                return month + "个月";
            }
            return year + "岁";
        },
        isJson: function (obj) {
            var isjson = typeof (obj) == "object" && Object.prototype.toString.call(obj).toLowerCase()
                == "[object object]" && !obj.length;
            return isjson;
        },
        initNavGrid: function (gridId, pagerId) {
            $("#" + gridId).navGrid('#' + pagerId, {
                refresh: true,
                refreshstate: 'current',
                refreshtitle: '获取最新数据',
                edit: false,
                add: false,
                del: false,
                search: false
            });
        },
        serializeRequestParams: function (paramArray) {
            var temp = [];
            for (i in paramArray) {
                if (typeof paramArray[i] != 'function')
                    temp.push(i + "=" + paramArray[i]);
            }

            return temp.join("&");
        },
        unserializeRequestParams: function (querystr) {

            var paramArray = [];
            $.each(querystr.split('&'), function (idx, item) {
                item = item.split('=');
                var name = item[0],
                    val = item[1];
                paramArray[name] = val;
            })
            return paramArray;
        },
        confirm: function (btn) {

            var _this = btn;
            var url = $(btn).data("url");
            var loading = $(btn).data("loading");
            var title = $(btn).data("title");
            var callback = $.trim($(btn).data("callback"));
            var $loading = $(loading || "#main-container");

            bootbox.confirm({
                title: '<div class="msg title text-primary"><i class="fa fa-info-circle"></i> ' + (title || "信息确认") + '</div>',
                message: '<div class="msg info text-danger">' + $(btn).data("msg") + '</div>',
                callback: function (result) {
                    if (result) {
                        $loading.mask({hide: 10000})
                        $.post(url, {}, function (ret) {
                            if (ret.success) {
                                if (callback) {
                                    // console.log(_this)
                                    window[callback](_this, ret);
                                }
                            }
                            $loading.unmask();
                        });
                    }
                }
            }).draggable({handle: ".modal-header"});
        },
        /**
         * 1、传入的querystr和path均为空时，是触发window.hashchange事件
         * 2、传入的querystr为空，path不为空时，是给location.hash赋值
         * 3、都不为空时，是重新计算hash，并触发window.hashchange事件
         */
        hashchange: function (querystr, path) {

            //alert($(".nav.nav-pills li").length)
            var oldHash = window.location.hash;
            if ($.trim(querystr) == '' && $.trim(path) == '') {

                $("window").trigger("hashchange");
            } else if ($.trim(querystr) == '') {

                location.hash = path;
            } else {
                var params = [];
                var newHash = oldHash;
                var idx = oldHash.indexOf("?");
                if (idx > 0) {
                    newHash = oldHash.substr(0, idx);
                    params = $.unserializeRequestParams(oldHash.substr(idx + 1));
                    //console.log(params)
                }
                // 新参数值覆盖原值
                var newParams = $.unserializeRequestParams(querystr);
                for (i in newParams) {
                    if (typeof newParams[i] != 'function')
                        params[i] = newParams[i];
                }

                location.hash = (path || newHash) + "?" + $.serializeRequestParams(params);
            }
            if (location.hash == oldHash) $(window).trigger("hashchange");
        },
        // 加载主区域
        loadPage: function (options) {
            //console.log(options)
            options = options || {};
            var url = options.url;
            var $maskEl = options.$maskEl || $(options.maskEl || "#page-content");
            var $loadEl = options.$loadEl || $(options.loadEl || "#page-content");
            var callback = options.callback;

            NProgress.start();

            // 关闭modal
            $("#modal").removeClass("fade").modal('hide').addClass("fade");

            //console.log("$maskEl.attr(\"id\")="+$maskEl.attr("id"))
            /*if($maskEl.attr("id")=="page-content"){
                $maskEl = $maskEl.closest(".page-content")
            }*/
            //$maskEl.mask();
            $.ajax({
                url: url, data: {}, cache: false, success: function (html) {
                    //$maskEl.unmask();
                    $loadEl.html(html);
                    if (typeof callback == 'function') callback();
                    NProgress.done();
                }, error: function () {
                    $.error("页面出错");
                    $loadEl.html("页面出错");
                    NProgress.done();
                }
            })
        },
        // 载入副区域, params是 url地址 或 {url：url, $mask: $mask, $show:$show, $hide:$hide, callback: callback} 对象
        loadView: function (params) {

            if (typeof NProgress != 'undefined') NProgress.start();

            var _params = {};
            if (!$.isJson(params)) {
                _params.url = params;
            } else {
                _params = $.extend({}, params);
            }
            var url = _params.url;
            var $mask = _params.$mask || $("#page-content");
            var callback = _params.callback;
            var $hide = _params.$hide || $("#body-content");
            var $show = _params.$show || $("#body-content-view");
            //console.log("_params.modal=" + _params.modal)
            var modal = _params.modal || 'hide';
            //console.log("modal=" + modal)
            // 关闭modal
            if (modal == 'hide') {
                $("#modal").removeClass("fade").modal('hide').addClass("fade");
            }
            //console.log("$mask.attr(\"id\")="+$mask.attr("id"))
            /*if($mask.attr("id")=="page-content"){
                $mask = $mask.closest(".page-content")
            }*/
            //$mask.mask();
            $.get(url, {}, function (html) {
                //$mask.unmask();
                $hide.hide();
                $show.hide().fadeIn("slow").html(html);
                if (typeof callback == 'function') callback();
                $("#btn-scroll-up").click();// 移动端
                if (typeof NProgress != 'undefined') NProgress.done();
            })
        },
        // 关闭副区域，如果传入了url，则刷新主区域
        hideView: function (params) {
            var _params = {};
            if (!$.isJson(params)) {
                _params.url = params;
            } else {
                _params = $.extend({}, params);
            }
            if ($.trim(_params.hideEl) == '#') _params.hideEl = undefined;
            if ($.trim(_params.loadEl) == '#') _params.loadEl = undefined;

            //console.log("_params=" + JSON.stringify(_params))
            var $hide = $(_params.hideEl || "#body-content-view");
            var $show = $(_params.loadEl || "#body-content");

            if(isMobile){
                $hide.fadeOut("fast", function () {
                    $show.show();
                });
            }else {
                $hide.fadeOut("fast", function () {
                    if ($.trim(_params.url) != '') {
                        $.hashchange('', _params.url);
                    } else {
                        $show.show(0, function () {
                            $(window).resize(); // 解决jqgrid不显示的问题
                        });
                    }
                });
            }
        },
        // 获取url参数
        getQueryString: function (name) {

            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURI(r[2]);
            return null;
        },
        print: function (url) {
            var isFirefox = navigator.userAgent.toUpperCase().indexOf("FIREFOX") ? true : false;
            //alert($.isIE())
            if ($.isIE() || isFirefox) {
                var win = window.open(url);
                win.focus();
                win.print();
            } else {
                var iframe = document.createElement('IFRAME');
                iframe.style.display = "none";
                iframe.src = url;
                document.body.appendChild(iframe);
                iframe.focus();
                iframe.onload = function () {
                    iframe.contentWindow.print();
                }
            }
        },
        fileInput: function ($files, params) {

            params = params || {};
            var maxSize = params.maxSize || _uploadMaxSize;
            $files.each(function () {
                var $file = $(this);
                $file.ace_file_input($.extend({
                    no_file: '请选择文件 ...',
                    btn_choose: '选择',
                    btn_change: '更改',
                    droppable: false,
                    onchange: null,
                    maxSize: maxSize,
                    thumbnail: false //| true | large
                    //whitelist:'gif|png|jpg|jpeg'
                    //blacklist:'exe|php'
                    //onchange:''
                    //
                }, params)).off('file.error.ace').on("file.error.ace", function (e, info) {
                    var size = info.error_list['size'];
                    if (size != undefined) {
                        $.tip({
                            $target: $file.closest(".ace-file-input"), $container: $("#pageContent") || $("body"),
                            msg: "文件<span class='text-danger'>[{0}]</span>超过{1}M大小".format(size, _uploadMaxSize / (1024 * 1024)),
                            my: 'left center', at: 'right center'
                        });
                        return;
                    }
                    var ext = info.error_count['ext'];
                    var mime = info.error_count['mime'];
                    //console.log(info.error_count)
                    if (ext > 0 || mime > 0) {
                        $.tip({
                            $target: $file.closest(".ace-file-input"), $container: $("#pageContent") || $("body"),
                            msg: "文件格式有误，请上传{0}格式的文件".format(params.allowExt),
                            my: 'left center', at: 'right center'
                        });
                        return;
                    }
                    e.preventDefault();
                });
                // 初始图片
                if (params.value != undefined) {
                    $file.ace_file_input('show_file_list', [{
                        type: 'image',
                        name: params.value
                    }]);
                }
            })
        },
        swfPreview: function (filepath, filename, hrefLabel, plainText, type) {
            filepath = $.trim(filepath);
            filename = $.trim(filename);
            hrefLabel = $.trim(hrefLabel)
            var cls = "";
            if (type == 'url') {
                cls = "openUrl";
            } else {
                cls = "popupBtn"
            }
            if (filepath != '' && filename != '') {
                hrefLabel = hrefLabel || filename;
                return '<a href="javascript:void(0)" class="{4}" data-url="{3}/swf/preview?path={0}&filename={1}&type={5}">{2}</a>'
                    .format(encodeURI(filepath), encodeURI(filename), hrefLabel, ctx, cls, type || '');
            } else
                return $.trim(plainText);
        },
        button: {
            confirm: function (params) {
                params = $.extend({
                    style: 'btn-default',
                    title: '',
                    msg: '',
                    callback: "",
                    url: '',
                    icon: '',
                    label: ''
                }, params);
                return ('<button class="confirm btn {0} btn-xs" ' +
                    'data-title="{1}" data-msg="{2}" data-callback="{3}" ' +
                    'data-url="{4}" ><i class="fa {5}"></i> {6}</button>')
                    .format(params.style, params.title, params.msg,
                        params.callback, params.url, params.icon, params.label)
            },
            modal: function (params) {
                params = $.extend({
                    style: 'btn-default',
                    url: '',
                    icon: '',
                    label: ''
                }, params);
                return ('<button class="popupBtn btn {0} btn-xs" ' +
                    'data-url="{1}" ><i class="fa {2}"></i> {3}</button>')
                    .format(params.style, params.url, params.icon, params.label)
            }
        }
    });

    $.fn.extend({
        loadPage: function (options) {
            var _options;
            if (typeof options == "string") {
                _options = {};
                _options.url = options;
                _options.$maskEl = $(this);
            } else {
                _options = options || {}
            }

            _options.$loadEl = $(this);
            $.loadPage(_options);
        },
        mask: function (options) {
            //console.log(options)
            options = options || {};
            if (options.hide != undefined && options.hide > 0) {
                options = $.extend(options, {
                    'afterShow': function () {
                        setTimeout(function () {
                            $this.hideLoading();
                        }, options.hide);
                    }
                })
            }
            var $this = $(this);
            return $this.showLoading(options);
        },
        unmask: function () {
            var $this = $(this);
            return $this.hideLoading();
        },
        download: function (url, type) { // type：export/download, default：export

            var $this = $(this);
            var $btn = $this.button('loading');
            var loadText = $this.data("load-text") || (type == "download" ? "下载中" : "正在导出");
            var successText = $this.data("success-text") || (type == "download" ? "下载完成" : "导出成功");
            var failedText = $this.data("failed-text") || (type == "download" ? "下载失败，请稍后重试" : "导出失败，请稍后重试");

            $this.data("loading-text", '<i class="fa fa-spinner fa-spin"></i> ' + loadText)

            // 清除cookie
            var settings = {
                cookieName: "fileDownload",
                cookiePath: "/",
                cookieDomain: null,
            };
            var cookieData = settings.cookieName + "=; path=" + settings.cookiePath + "; expires=" + new Date(0).toUTCString() + ";";
            if (settings.cookieDomain) cookieData += " domain=" + settings.cookieDomain + ";";
            document.cookie = cookieData;

            $.fileDownload(url, {
                prepareCallback: function (url) {
                },
                successCallback: function (url) {
                    $('[data-rel="tooltip"]').tooltip('hide');
                    var $tip = $.tip({
                        $target: $this,
                        at: 'top center', my: 'bottom center', type: 'success',
                        msg: successText
                    });
                    setTimeout(function () {
                        $tip.qtip('destroy', true);
                        //console.log($tip)
                        $this.closest(".btn-group").removeClass("open");
                    }, 3000)
                    $btn.button('reset');
                },
                failCallback: function (responseHtml, url, error) {
                    //console.log("responseHtml="+responseHtml)
                    //console.log("url="+url)
                    //console.log("error="+error)
                    $('[data-rel="tooltip"]').tooltip('hide');
                    var $tip = $.tip({
                        $target: $this,
                        at: 'top center', my: 'bottom center',
                        msg: failedText
                    });
                    setTimeout(function () {
                        $tip.qtip('destroy', true);
                        //console.log($tip)
                    }, 3000)
                    $btn.button('reset');
                }
            });
        }
    })

})(jQuery);

// base64默认编码
if ($.base64) {
    $.base64.utf8encode = true;
}

if (typeof toastr != 'undefined') {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        //"positionClass": "toast-bottom-full-width",
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "3000",
        "extendedTimeOut": "0",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
}
bootbox.setDefaults({locale: 'zh_CN'});
if ($.fn.select2) {
    $.fn.select2.defaults.set("language", "zh-CN");
    $.fn.select2.defaults.set("theme", "classic");
    $.fn.select2.defaults.set("allowClear", true);
    $.fn.select2.defaults.set("width", "200px");
}
// 解决IE8下select2在modal里不能搜索的bug
$.fn.modal.Constructor.prototype.enforceFocus = function () {
};
if ($.fn.bootstrapSwitch) {
    $.extend($.fn.bootstrapSwitch.defaults, {
        onText: "是",
        offText: "否",
        onColor: "success",
        offColor: "danger"
    });
}
if (typeof $.ui !== 'undefined' && typeof $.ui.dynatree !== 'undefined' && $.ui.dynatree) {
    $.extend($.ui.dynatree.prototype.options, {
        checkbox: true,
        selectMode: 3,
        cookieId: "dynatree-Cb3",
        idPrefix: "dynatree-Cb3-",
        debugLevel: 0
    });
}
if ($.jgrid) {
    $.extend($.jgrid.defaults, {
        responsive: true,
        styleUI: "Bootstrap",
        prmNames: {page: "pageNo", rows: "pageSize", sort: "sort", order: "order"},
        rowList: [20, 50, 100, 200, 500],
        //width:$(window).width()-$(".nav-list").width()-50,
        //height:$(window).height()-390,
        viewrecords: true,
        shrinkToFit: false,
        rowNum: 20,
        multiselect: true,
        multiboxonly: true,
        mtype: "GET",
        datatype: "jsonp",
        //loadui:"disabled",
        loadtext: "",
        pager: "#jqGridPager",
        //pagerpos:"right",
        cmTemplate: {
            sortable: false, align: 'center', width: 100,
            formatter: function (cellvalue, options, rowObject) {
                //console.log(options.colModel.hidden)
                //console.log(cellvalue.length)
                // if(options.colModel.hidden) console.log(cellvalue==undefined)
                return (!options.colModel.hidden && (cellvalue == undefined || cellvalue.length == 0)) ? '--' : $.trim(cellvalue)
            }
        },
        sortorder: "desc",
        ondblClickRow: function (rowid, iRow, iCol, e) {
            $(".jqEditBtn").click();
        },
        onPaging: function () {
            $(this).closest(".ui-jqgrid-bdiv").scrollTop(0).scrollLeft(0);
        }
    });

    // 格式化jqGrid字段
    $.jgrid.formatter = {};
    $.extend($.jgrid.formatter, {
        date: function (cellvalue, options, rowdata, action) {
            return (cellvalue == undefined || $.trim(cellvalue).length == 0) ? '--' :
                $.fn.fmatter.call(this, "date", cellvalue, options, rowdata, action);
        },
        MAP: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '--';

            var op = {map: null}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }
            if (op.map == undefined || op.map == null) return '--'

            return op.map[cellvalue];
        },
        defaultString: function (cellvalue, options, rowObject) {
            var op = {def: '--'}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }
            if ($.trim(cellvalue) == '') return op.def;
            return cellvalue;
        },
        TRUEFALSE: function (cellvalue, options, rowObject) {

            if (cellvalue == undefined) return '--';
            var op = {on: '是', off: '否'}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }

            return cellvalue ? op.on : op.off;
        },
        NoMultiSpace: function (cellvalue, options, rowObject) {
            if ($.trim(cellvalue) == '') return '--'
            // console.log(cellvalue)
            return $('<p>' + cellvalue.NoMultiSpace() + '</p>').text()
            //return cellvalue.NoMultiSpace();
        },
        htmlencodeWithNoSpace: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '--'
            return cellvalue.htmlencode().NoSpace();
        },
        GENDER: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '--'
            return _cMap.GENDER_MAP[cellvalue];
        },
        unit: function (cellvalue, options, rowObject) {
            //return cellvalue;
            if (cellvalue == undefined) return '--'
            var name = null;
            var unit = _cMap.unitMap[cellvalue];
            if (unit != undefined) name = unit.name;

            if ($.trim(name) == '') return '--'

            if ($.inArray("unit:view", _permissions) >= 0 || $.inArray("unit:*", _permissions) >= 0) {
                return ('<a href="javascript:;" class="openView" data-url="{3}/unit_view?id={0}"><span class="{1}">{2}</span></a>'
                    .format(unit.id, unit.status == 2 ? 'delete' : '', name, ctx));
            }

            return ('<span class="{0}">{1}</span>'
                .format(unit.status == 2 ? 'delete' : '', name));
        },
        AGE: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '';
            return $.yearOffNow(cellvalue);
        },
        MetaType: function (cellvalue, options, rowObject) {

            var op = {def: '--'}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }

            //console.log(cellvalue)
            if ($.trim(cellvalue) == '') return op.def;

            if (cellvalue == undefined || _cMap.metaTypeMap[cellvalue] == undefined) return op.def
            return _cMap.metaTypeMap[cellvalue].name
        },
        cadreParty: function (cellvalue, options, rowObject) {

            var op = {useCadre: false}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }

            if (op.useCadre && rowObject.cadre != undefined) {
                op.isOw = rowObject.cadre.isOw;
                op.dpTypeId = rowObject.cadre.dpTypeId;
            } else {
                op.isOw = rowObject.isOw;
                op.dpTypeId = rowObject.dpTypeId;
            }
            var parties = [];
            if (op.isOw) {
                parties.push("中共党员");
            }
            if (op.dpTypeId > 0 && _cMap.metaTypeMap[op.dpTypeId] != undefined) {
                parties.push(_cMap.metaTypeMap[op.dpTypeId].name);
            }
            if (parties.length > 0) return parties.join(",");

            return "--"
        },
        growTime: function (cellvalue, options, rowObject) {

            var op = {useCadre: false}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }
            if (op.useCadre && rowObject.cadre != undefined) {
                op.isOw = rowObject.cadre.isOw;
                op.owGrowTime = rowObject.cadre.owGrowTime;
                op.dpTypeId = rowObject.cadre.dpTypeId;
                op.dpGrowTime = rowObject.cadre.dpGrowTime;
            } else {
                op.isOw = rowObject.isOw;
                op.owGrowTime = rowObject.owGrowTime;
                op.dpTypeId = rowObject.dpTypeId;
                op.dpGrowTime = rowObject.dpGrowTime;
            }
            var growTimes = [];
            if (op.isOw) {
                growTimes.push(op.owGrowTime == undefined ? "-" : op.owGrowTime.substr(0, 10));
            }
            if (op.dpTypeId > 0) {
                growTimes.push(op.dpGrowTime == undefined ? "-" : op.dpGrowTime.substr(0, 10));
            }
            if (growTimes.length > 0) return growTimes.join(",");

            return "--"
        },
        growAge: function (cellvalue, options, rowObject) {

            var op = {useCadre: false}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }

            if (op.useCadre && rowObject.cadre != undefined) {
                op.isOw = rowObject.cadre.isOw;
                op.owGrowTime = rowObject.cadre.owGrowTime;
                op.dpTypeId = rowObject.cadre.dpTypeId;
                op.dpGrowTime = rowObject.cadre.dpGrowTime;
            } else {
                op.isOw = rowObject.isOw;
                op.owGrowTime = rowObject.owGrowTime;
                op.dpTypeId = rowObject.dpTypeId;
                op.dpGrowTime = rowObject.dpGrowTime;
            }
            var growAges = [];
            if (op.isOw) {
                growAges.push(op.owGrowTime == undefined ? "-" : $.yearOffNow(op.owGrowTime));
            }
            if (op.dpTypeId > 0) {
                growAges.push(op.dpGrowTime == undefined ? "-" : $.yearOffNow(op.dpGrowTime));
            }
            if (growAges.length > 0) return growAges.join(",");

            return "-"
        },
        sortOrder: function (cellvalue, options, rowObject) {

            var op = {grid: ''}
            if (options != undefined && options.colModel != undefined) {
                op = $.extend(op, options.colModel.formatoptions);
            }

            return _.template($("#common_sort_tpl").html().NoMultiSpace())({
                grid: op.grid,
                id: rowObject.id,
                url: op.url
            })
        }
    });
}
// 初始化表单控件
$.register = {};
$.extend($.register, {
    // 移动端点击注册
    m_click: function (selector, fn) {
        var _touch = false;
        $(document).on("touchend click", selector, function () {
            event.preventDefault();
            if (_touch) {
                //console.log(this)
                fn.call(this);
            }
        });

        $(document).on("touchstart", selector, function () {
            _touch = true;
        });

        $(document).on("touchmove", selector, function () {
            _touch = false;
        });
    },
    formatState: function (state) {
        if (!state.id) {
            return state.text;
        }
        var $state = $.trim(state.text);
        if ($.trim(state.code) != '')
            $state += ($state != '' ? '-' : '') + state.code;
        if ($.trim(state.unit) != '') {
            $state += '-' + state.unit;
        }
        //console.log($state)

        return '<span class="{0}">{1}</span>'.format(state.del || state.title == 'true' ? "delete" : "", $state);
    },
    templateSelection: function (state) {
        var $state = state.text;
        if ($.trim(state.code) != '')
            $state += ($state != '' ? '-' : '') + state.code;

        return '<span class="{0}">{1}</span>'.format(state.del || state.title == 'true' ? "delete" : "", $state);
    },
    defaultTemplateResult: function (state) {

        var $state = state.text;
        if ($.trim(state.type) != '')
            $state += ($state != '' ? '-' : '') + state.type;
        // 反转义
        $state = $('<div/>').html($state).text();
        return '<span class="{0}">{1}</span>'.format(state.del || state.title == 'true' ? "delete" : "", $state);
    },
    // 下拉多选
    multiselect: function ($select, selected, params) {
        var $select = $select.multiselect($.extend({
            enableFiltering: true,
            /*enableHTML: true,*/
            buttonClass: 'btn btn-default',
            filterPlaceholder: '查找',
            nonSelectedText: '请选择',
            nSelectedText: '已选择',
            includeSelectAllOption: true,
            collapseOptGroupsByDefault: true,
            selectAllText: '全选/取消全选',
            allSelectedText: '全部已选择',
            maxHeight: 300,
            buttonWidth: '200px'
            /* templates: {
             button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"><span class="multiselect-selected-text"></span> &nbsp;<b class="fa fa-caret-down"></b></button>',
             ul: '<ul class="multiselect-container dropdown-menu"></ul>',
             filter: '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="fa fa-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
             filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default btn-white btn-grey multiselect-clear-filter" type="button"><i class="fa fa-times-circle red2"></i></button></span>',
             li: '<li><a tabindex="0"><label></label></a></li>',
             divider: '<li class="multiselect-item divider"></li>',
             liGroup: '<li class="multiselect-item multiselect-group"><label></label></li>'
             }*/
        }, params));

        if (selected != undefined && selected instanceof Array && selected.length > 0) {
            //console.log("selected=" + selected)
            $select.multiselect('select', selected);
        }
        return $select;
    },
    // 图片
    fancybox: function (afterLoad) {

        $(".various").fancybox({
            live: true,
            tpl: {error: '<p class="fancybox-error">该文件不是有效的图片格式，请下载后查看。</p>'},
            maxWidth: 800,
            maxHeight: 600,
            fitToView: false,
            width: '70%',
            height: '70%',
            autoSize: false,
            closeClick: false,
            openEffect: 'none',
            closeEffect: 'none',
            loop: false,

            arrows: false,
            prevEffect: 'none',
            nextEffect: 'none',
            closeBtn: true,
            helpers: {
                overlay: {
                    closeClick: false,
                    locked: false
                },
                title: {type: 'inside'},
                buttons: {}
            },
            beforeShow: function () {
                this.wrap.draggable();
            },
            afterLoad: afterLoad || function () {
            }
        });
    },
    // 根据单位状态 - 选择单位
    unit_select: function ($unitTypeSelect, $unitSelect, $unitType) {
        $unitTypeSelect.on("change", function () {
            $unitSelect.val(null).trigger("change");
        });
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
        }).on("change", function () {
            if ($(this).select2("data").length > 0) {
                var unitType = $(this).select2("data")[0]['type'] || '';
                $unitType.val(unitType);
            }
        });
    },
    // 日历
    date: function ($date, params) {
        $date.parent().css('z-index', '1030');
        $date.parent().parent().css('z-index', '10');
        var endDate = new Date();
        endDate.setFullYear(endDate.getFullYear() + 50); // 最多允许选择50年以内的年份
        return $date.datepicker($.extend({
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true,
            clearBtn: true,
            endDate: endDate,
        }, params)).attr("autocomplete", "off").attr("disableautocomplete", "")
    },
    // 日历时间
    datetime: function ($date, params) {
        $date.datetimepicker($.extend({
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true,
            //todayBtn: true,
            clearBtn: true
        }, params)).attr("autocomplete", "off").attr("disableautocomplete", "")
    },

    // 选择发文类型
    dispatchType_select: function ($select, $year) {
        $year.on("change", function () {
            $select.val(null).trigger("change");
        });
        var t = $select.select2({
            language: {
                noResults: function (term) {
                    return "请先选择年份";
                }
            },
            templateResult: $.register.formatState,
            escapeMarkup: function (markup) {
                return markup;
            },
            ajax: {
                dataType: 'json',
                delay: 300,
                data: function (params) {
                    return {
                        year: $year.val() || -1,
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
    },
    // 年份-发文类型-发文， 选择任免文件
    dispatch_select: function ($dispatchTypeSelect, $year, $dispatchSelect) {
        $.register.dispatchType_select($dispatchTypeSelect, $year);
        $year.on("change", function () {
            $dispatchTypeSelect.val(null).trigger("change");
            $dispatchSelect.val(null).trigger("change");
        });
        $dispatchTypeSelect.on("change", function () {
            $dispatchSelect.val(null).trigger("change");
        });
        return $dispatchSelect.select2({
            templateResult: $.register.formatState,
            escapeMarkup: function (markup) {
                return markup;
            },
            ajax: {
                dataType: 'json',
                delay: 300,
                data: function (params) {
                    return {
                        dispatchTypeId: $dispatchTypeSelect.val() || '',
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
        })
    },
    // 分党委、党支部select2联动
    party_branch_select: function ($container, branchDivId, mt_direct_branch_id,
                                   init_party_id, init_party_class, partyId, branchId, branchIsNotEmpty) {

        //var $container = $("#modalForm");
        partyId = partyId || "partyId";
        branchId = branchId || "branchId";
        $('select[name=' + partyId + '], select[name=' + branchId + ']', $container).select2({
            templateResult: function (state) {

                return '<span class="{0}">{1}</span>'.format(state.del || state.title == 'true' ? "delete" : "", state.text);
            },
            templateSelection: function (state) {
                return '<span class="{0}">{1}</span>'.format(state.del || state.title == 'true' ? "delete" : "", state.text);
            },
            escapeMarkup: function (markup) {
                return markup;
            },
            ajax: {
                dataType: 'json',
                delay: 300,
                data: function (params) {
                    return {
                        searchStr: params.term,
                        pageSize: 10,
                        pageNo: params.page,
                        partyId: $('select[name=' + partyId + ']', $container).val()
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
        $('select[name=' + partyId + ']', $container).on("change", function () {

            $("#" + branchDivId + " select", $container).removeAttr("required");

            var partyOption = $(this).select2("data")[0];
            var $party_class = (partyOption?$(this).select2("data")[0]['class']:null) || init_party_class;
            //alert("${party.id}")
            if ($(this).val() != init_party_id)
                $('select[name=' + branchId + ']', $container).val(null).trigger("change");
            if ($(this).val() > 0 && $party_class != mt_direct_branch_id) {
                $("#" + branchDivId, $container).show();
                if (branchIsNotEmpty != undefined && branchIsNotEmpty)
                    $("#" + branchDivId + " select", $container).attr("required", "required");
            } else {
                $('select[name=' + branchId + ']', $container).val(null).trigger("change");
                $("#" + branchDivId, $container).hide();
            }
        }).change();
        $('select[name=' + partyId + ']', $container).on("select2:unselect", function () {
            $('select[name=' + branchId + ']', $container).val(null).trigger("change");
            $("#" + branchDivId, $container).hide();
        })
    },
    // 类型、分党委、党支部 3级联动
    class_party_branch_select: function ($container, partyDivId, branchDivId,
                                         mt_direct_branch_id, init_party_id, classId, partyId, branchId, isNotEmpty) {

        classId = classId || "classId";
        partyId = partyId || "partyId";
        branchId = branchId || "branchId";

        $('select[name=' + classId + ']', $container).select2({width: 200}).on("change", function () {

            $("#" + partyDivId + " select").removeAttr("required");

            $('select[name=' + partyId + ']', $container).val(null).trigger("change");
            $('select[name=' + branchId + ']', $container).val(null).trigger("change");
            if ($(this).val() > 0) {
                $("#" + partyDivId, $container).show();
                if (isNotEmpty != undefined && isNotEmpty)
                    $("#" + partyDivId + " select").attr("required", "required");
            } else {

                $("#" + partyDivId + ", #" + branchDivId, $container).hide();
            }
        });

        $('select[name=' + partyId + '], select[name=' + branchId + ']', $container).select2({
            width: 400,
            ajax: {
                dataType: 'json',
                delay: 300,
                data: function (params) {
                    return {
                        searchStr: params.term,
                        pageSize: 10,
                        pageNo: params.page,
                        classId: $('select[name=' + classId + ']', $container).val(),
                        partyId: $('select[name=' + partyId + ']', $container).val()
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

        $('select[name=' + partyId + ']', $container).on("change", function () {

            $("#" + branchDivId + " select").removeAttr("required");

            if ($(this).val() != init_party_id)
                $('select[name=' + branchId + ']', $container).val(null).trigger("change");

            if ($(this).val() > 0 && $('select[name=' + classId + ']', $container).val() != mt_direct_branch_id) {
                $("#" + branchDivId, $container).show();
                if (isNotEmpty != undefined && isNotEmpty)
                    $("#" + branchDivId + " select").attr("required", "required");
            } else {
                $('select[name=' + branchId + ']', $container).val(null).trigger("change");
                $("#" + branchDivId, $container).hide();
            }
        });
    },
    ajax_select: function ($select, params) {
        var _params = {};
        if (!$.isJson(params)) {
            _params.templateResult = params;
            _params.templateSelection = params;
        } else {
            _params = $.extend({}, params);
        }
        //console.log(_params)
        return $($select).select2($.extend({
                templateResult: _params.templateResult || $.register.defaultTemplateResult,
                templateSelection: _params.templateSelection || $.register.defaultTemplateResult,
                escapeMarkup: function (markup) {
                    return markup;
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
                        return {
                            results: data.options, pagination: {
                                more: (params.page * 10) < data.totalCount
                            }
                        };
                    },
                    cache: true
                }
            }, _params)
        );
    },
    // 选择账号
    user_select: function ($select, params) {

        var _params = {};
        if (!$.isJson(params)) {
            _params.templateSelection = params;
        } else {
            _params = $.extend({}, params);
        }

        return $select.select2($.extend({
            templateResult: $.register.formatState,
            templateSelection: _params.templateSelection || $.register.templateSelection,
            escapeMarkup: function (markup) {
                return markup;
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
                    return {
                        results: data.options, pagination: {
                            more: (params.page * 10) < data.totalCount
                        }
                    };
                },
                cache: true
            }
        }, _params));
    },
    // select2-ajax选择框，option包含属性del=true时，带删除线
    del_select: function ($select, params) {

        var _params = {};
        if (!$.isJson(params)) {
            _params.width = params;
        } else {
            _params = $.extend({}, params);
        }
        return $select.select2($.extend({
            templateResult: _params.templateResult || function (state) {

                return '<span class="{0}">{1}</span>'.format(state.del || state.title == 'true' ? "delete" : "", state.text);
            },
            templateSelection: _params.templateSelection || function (state) {
                return '<span class="{0}">{1}</span>'.format(state.del || state.title == 'true' ? "delete" : "", state.text);
            },
            escapeMarkup: function (markup) {
                return markup;
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
                    return {
                        results: data.options, pagination: {
                            more: (params.page * 10) < data.totalCount
                        }
                    };
                },
                cache: true
            }
        }, _params));
    }
});
