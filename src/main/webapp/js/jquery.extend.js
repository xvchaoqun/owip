/**
 * 仅网页端调用
 *
 * */
/*========jquery.validate.extend=====start===*/
jQuery.extend(jQuery.validator.messages, {
    required: "必填字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
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
    focusInvalid: true,
    ignore: '',
    invalidHandler: function (event, validator) { //display error alert on form submit
    },
    highlight: function (e) {
    },
    success: function (errorElement) {
        /* var id = errorElement.attr("id");
         var fieldName = id.substr(0, id.length-6);
         console.log(fieldName)
         var $field = $("[name="+fieldName+"]", $("#modalForm"))
         $("label:first", $field.closest(".form-group")).css("color", "")*/
    },
    /*success: function (e) {
     //console.log(e)
     //console.log(e.closest('.uploader').find(".help-block"))
     $(e).closest('div.form-group').removeClass('has-error').addClass('has-success')

     // 文件上传特殊处理
     e.closest('.uploader').find(".help-block").remove();

     $(e).removeClass('has-error').addClass('has-success')
     $(e).parent().removeClass('has-error').addClass('has-success')
     },*/
    errorPlacement: function (error, element) {
        if (error.html() != '') {
            //$("label:first", element.closest(".form-group")).css("color", "red")
            $.tip({$form:element.closest("form"), field:element.attr("name"), msg:error.text()})
        }
    }
    /*
     errorPlacement: function (error, element) {

     $(element).closest('div.form-group').removeClass('has-success')

     if(error.html()!='') {
     $(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
     }

     // 文件上传特殊处理
     if(element.is(':file')){

     //console.log(element)
     var $uploaderdiv = element.closest('.uploader');
     $uploaderdiv.removeClass('has-success');
     error.appendTo($uploaderdiv)
     return;
     }

     /!*if(element.parent().is('.input-group')){

     error.insertAfter(element.parent().parent())
     return;
     }*!/

     //console.log($(element).hasClass("date-picker")+"==================1111")
     if(element.is(":checkbox")){
     $(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
     error.insertAfter(element.closest('div'));
     }else if(element.is(":radio") ){
     var parent = element.closest(".radio").parent();
     parent.removeClass('has-success').addClass('has-error')
     error.insertAfter(parent);
     }else if(element.is("select") ){
     //console.log("==================")
     $(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
     error.appendTo($(element).parent());
     }else if($(element).hasClass("date-picker")){
     $(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
     error.insertAfter($(element).closest("div.input-group"));
     }else {
     //element.parent().remove('.has-success').addClass('has-error')
     //error.insertAfter(element.parent());
     $(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
     error.insertAfter(element);
     }
     }*/
})

//中文字两个字节
jQuery.validator.addMethod("byteRangeLength", function (value, element, param) {
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }
    return this.optional(element) || ( length >= param[0] && length <= param[1] );
}, $.validator.format("请确保输入的值在{0}-{1}个字符之间(一个中文字算2个字符)"));

//中文字两个字节
jQuery.validator.addMethod("byteMaxLength", function (value, element, param) {
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }

    return this.optional(element) || ( length <= param);
}, $.validator.format("输入长度最多是{0}的字符串(汉字算2个字符)"));

// 邮政编码验证
jQuery.validator.addMethod("isZipCode", function (value, element) {
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");

/*========jquery.validate.extend=====end===*/

_.templateSettings = {
    evaluate: /\{\{([\s\S]+?)\}\}/g,
    interpolate: /\{\{=([\s\S]+?)\}\}/g,
    escape: /\{\{-([\s\S]+?)\}\}/g
};

var SysMsg = {};
SysMsg.error = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.alert({
        message: msg,
        callback: callback,
        title: title
    });
    //toastr.error(msg, title);
}
SysMsg.warning = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.warning(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.alert({
        message: msg,
        callback: callback,
        title: title
    });
}
SysMsg.success = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.success(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.alert({
        message: msg,
        callback: callback,
        title: title
    });
}
SysMsg.info = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.info(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.dialog({
        title: title,
        message: msg,
        closeButton: false,
        buttons: {
            close: {
                label: "确定",
                className: "btn-info",
                callback: callback || function () {
                }
            }
        }
    });
}
SysMsg.confirm = function (msg, title, callback) {
    $("body").css('padding-right', '0px');
    //toastr.success(msg, title);
    if (typeof title == 'function') {
        callback = title;
        title = '';
    }
    bootbox.confirm({
        message: msg,
        callback: callback,
        title: title/*,
         closeButton:false*/
    });
};

var _modal_width;
(function ($) {
    $.extend({
        loadModal:function(url, width, dragTarget) { // dragTarget：拖拽位置
            //$("#modal").modal('hide');
            if (width > 0) {
                _modal_width = width;
                $('#modal .modal-dialog').addClass("width" + width);
            } else {
                $('#modal .modal-dialog').removeClass("width" + _modal_width);
            }
            dragTarget = dragTarget || ".modal-header";

            $('#modal .modal-content').load(url, function (data) {
                if (!data.startWith("{")) $("#modal").modal('show').draggable({handle: dragTarget});
            });
        },
        tip: function (params) {

            var $target = params.$target;
            var $container = params.$container;
            var msg = params.msg;
            var my = params.my;
            var at = params.at;

            if($target==undefined) {

                var $form = params.$form;
                var field = params.field;
                $target = $("[name=" + field + "]", $form);
                if ($target.closest(".input-group").length > 0) {
                    $target = $target.closest(".input-group")
                } else if ($target.is('[data-rel="select2"]')
                    || $target.is('[data-rel="select2-ajax"]')
                    || $target.hasClass("select2-hidden-accessible")) {
                    $target = $target.closest("div").find(".select2-container");
                } else if ($target.closest(".ace-file-input").length > 0) {
                    $target = $target.closest(".ace-file-input")
                } else if ($target.is(":radio")) {
                    $target = $target.closest("div")
                }
                $container = $form;
                if ($form.closest("#modal").length > 0) {
                    $container = $form.closest("#modal");
                }
            }
            $target.qtip({
                content: msg,
                show: true, hide: {
                    event: 'unfocus',
                    inactive: 1000
                }, position: {
                    container: $container,
                    my: my || $target.data("my") || 'left center',
                    at: at || $target.data("at") || 'right center'
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
                if ($.inArray("party:list", _permissions) >= 0)
                    _partyView = '<a href="javascript:;" class="openView" data-url="{2}/party_view?id={0}">{1}</a>'
                        .format(party.id, party.name, ctx);
            }

            var branch = (branchId == undefined) ? undefined : _cMap.branchMap[branchId];
            var _branchView = null;
            if (branch != undefined) {
                var _branchView = branch.name;
                if ($.inArray("branch:list", _permissions) >= 0)
                    _branchView = '<a href="javascript:;" class="openView" data-url="{2}/branch_view?id={0}">{1}</a>'
                        .format(branch.id, branch.name, ctx);
            }

            if (_partyView != null && _branchView != null) {
                return '<span class="{0}">{1}</span><span class="{2}">{3}</span>'
                    .format(party.isDeleted ? "delete" : "", _partyView, branch.isDeleted ? "delete" : "", " - " + _branchView);
            } else if (_partyView != null) {
                return '<span class="{0}">{1}</span>'.format(party.isDeleted ? "delete" : "", _partyView);
            }
            return '';
        },
        cadre: function (cadreId, realname) {

            if (cadreId > 0 && $.trim(realname) != '')
                return '<a href="javascript:;" class="openView" data-url="{2}/cadre_view?cadreId={0}">{1}</a>'
                    .format(cadreId, realname, ctx);

            return $.trim(realname);
        },
        member: function (userId, realname) {

            if (userId > 0 && $.trim(realname) != '')
                return '<a href="javascript:;" class="openView" data-url="{2}/member_view?userId={0}">{1}</a>'
                    .format(userId, realname, ctx);

            return $.trim(realname);
        },
        //计算天数差的函数，通用
        dayDiff: function (sDate1, sDate2) {    //sDate1和sDate2是2006-12-18格式
            var aDate, oDate1, oDate2, iDays
            sDate1 = sDate1.substr(0, 10)
            sDate2 = sDate2.substr(0, 10)
            aDate = sDate1.split("-")
            oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])    //转换为12-18-2006格式
            aDate = sDate2.split("-")
            oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
            iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24)    //把相差的毫秒数转换为天数
            return iDays + 1; // 第一天和最后一天都算
        },
        //计算月份差
        monthDiff: function (date1, date2) {
            //默认格式为"2003-03-03",根据自己需要改格式和方法
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
            var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase()
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
            var msg = $(btn).data("msg");
            var loading = $(btn).data("loading");
            var callback = $.trim($(btn).data("callback"));

            var $loading = $(loading || "#main-container");
            bootbox.confirm(msg, function (result) {
                if (result) {
                    $loading.showLoading({
                        'afterShow': function () {
                            setTimeout(function () {
                                $loading.hideLoading();
                            }, 10000);
                        }
                    });
                    $.post(url, {}, function (ret) {
                        if (ret.success) {
                            if (callback) {
                                // console.log(_this)
                                window[callback](_this);
                            }
                            $loading.hideLoading();
                        }
                    });
                }
            });
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
            options = options||{};
            var url = options.url;
            var $maskEl = options.$maskEl || $(options.maskEl||"#page-content");
            var $loadEl = options.$loadEl || $(options.loadEl||"#page-content");
            var callback = options.callback;

            NProgress.start();

            // 关闭modal
            $("#modal").removeClass("fade").modal('hide').addClass("fade");
            $maskEl.showLoading({
                'afterShow': function () {
                    setTimeout(function () {
                        $maskEl.hideLoading();
                        //if (typeof callback == 'function') callback();
                    }, 2000);
                }
            });
            $.ajax({url: url, data: {}, cache: false, success: function (html) {
                    $maskEl.hideLoading();
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
        // 载入副区域
        loadView: function (url, $maskEl, fn) {

            NProgress.start();
            if ($maskEl == undefined || typeof $maskEl == 'function') {
                fn = $maskEl;
                $maskEl = $("#page-content");
            }
            // 关闭modal
            $("#modal").removeClass("fade").modal('hide').addClass("fade");
            $maskEl.showLoading({
                'afterShow': function () {
                    setTimeout(function () {
                        $maskEl.hideLoading();
                        //if (typeof fn == 'function') fn();
                    }, 2000);
                }
            });
            $.get(url, {}, function (html) {
                $maskEl.hideLoading();
                $("#body-content").hide();
                $("#item-content").hide().html(html).fadeIn("slow");
                if (typeof fn == 'function') fn();
                NProgress.done();
            })
        },
        // 关闭副区域，如果传入了url，则刷新主区域
        hideView: function (pageUrl) {
            $("#item-content").fadeOut("fast", function () {
                if ($.trim(pageUrl) != '') {
                    $.hashchange('', pageUrl);
                } else {
                    $("#body-content").show(0, function () {
                        $(window).resize(); // 解决jqgrid不显示的问题
                    });
                }
            });
        },
        // 获取url参数
        getQueryString: function (name) {

            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        },
        print: function (url) {
            var isFirefox = navigator.userAgent.toUpperCase().indexOf("FIREFOX") ? true : false;
            //alert(isFirefox)
            if (detectIE() || isFirefox) {
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
            var maxSize = params.maxSize||_uploadMaxSize;
            $files.each(function() {
                var $file = $(this)
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
                    if (size != undefined){
                        $.tip({
                            $target: $file.closest(".ace-file-input"), $container: $("#pageContent")||$("body"),
                            msg: "文件<span class='text-danger'>[{0}]</span>超过{1}M大小".format(size, _uploadMaxSize / (1024 * 1024)),
                            my: 'left center', at: 'right center'
                        });
                        return;
                    }
                    var ext = info.error_count['ext'];
                    var mime = info.error_count['mime'];
                    console.log(info.error_count)
                    if (ext >0 || mime >0){
                        $.tip({
                            $target: $file.closest(".ace-file-input"), $container: $("#pageContent")||$("body"),
                            msg: "文件格式有误，请上传{0}格式的文件".format(params.allowExt),
                            my: 'left center', at: 'right center'
                        });
                        return;
                    }
                    e.preventDefault();
                });
            })
        },
        swfPreview: function (filepath, filename, hrefLabel, plainText) {
            filepath = $.trim(filepath);
            filename = $.trim(filename);
            hrefLabel = $.trim(hrefLabel)
            if (filepath != '' && filename != '') {
                hrefLabel = hrefLabel || filename;
                return '<a href="javascript:void(0)" class="popupBtn" data-url="{3}/swf/preview?path={0}&filename={1}">{2}</a>'
                    .format(encodeURI(filepath), encodeURI(filename), hrefLabel, ctx);
            } else
                return $.trim(plainText);
        }
    });
    $.fn.loadPage = function( options ) {
        var _options = {};
        if(typeof options=="string"){
            _options.url = options;
            _options.$maskEl = $(this);
        }

        _options.$loadEl = $(this);
        $.loadPage(_options);
    };
})(jQuery);
try {
    bootbox.setDefaults({locale: 'zh_CN'});
    $.fn.select2.defaults.set("language", "zh-CN");
    $.fn.select2.defaults.set("theme", "classic");
    $.fn.select2.defaults.set("allowClear", true);
    $.fn.select2.defaults.set("width", "200px");
// 解决IE8下select2在modal里不能搜索的bug
    $.fn.modal.Constructor.prototype.enforceFocus = function () {
    };

    $.extend($.fn.bootstrapSwitch.defaults, {
        onText: "是",
        offText: "否",
        onColor: "success",
        offColor: "danger"
    });
    $.extend($.ui.dynatree.prototype.options, {
        checkbox: true,
        selectMode: 3,
        cookieId: "dynatree-Cb3",
        idPrefix: "dynatree-Cb3-",
        debugLevel: 0
    });
    $.extend($.jgrid.defaults, {
        responsive: true,
        styleUI: "Bootstrap",
        prmNames: {page: "pageNo", rows: "pageSize", sort: "sort", order: "order"},
        //width:$(window).width()-$(".nav-list").width()-50,
        //height:$(window).height()-390,
        viewrecords: true,
        shrinkToFit: false,
        rowNum: 20,
        multiselect: true,
        multiboxonly: true,
        mtype: "GET",
        datatype: "jsonp",
        //loadui:"disable",
        loadtext: "数据加载中，请稍后...",
        pager: "#jqGridPager",
        //pagerpos:"right",
        cmTemplate: {sortable: false, align: 'center', width: 100},
        sortorder: "desc",
        ondblClickRow: function (rowid, iRow, iCol, e) {
            $(".jqEditBtn").click();
        },
        onPaging: function () {
            $(this).closest(".ui-jqgrid-bdiv").scrollTop(0).scrollLeft(0);
        }
    });

    $.jgrid.formatter = {};
    $.jgrid.formatter.TRUEFALSE = function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return '-';
        return cellvalue ? "是" : "否";
    };

    $.jgrid.formatter.NoMultiSpace = function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return ''
        //console.log(cellvalue)
        return cellvalue.NoMultiSpace();
    };
    $.jgrid.formatter.GENDER = function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return ''
        return _cMap.GENDER_MAP[cellvalue];
    };
    $.jgrid.formatter.AGE = function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return '';
        return $.yearOffNow(cellvalue);
    };
    $.jgrid.formatter.MetaType = function (cellvalue, options, rowObject) {
        if (cellvalue == undefined || _cMap.metaTypeMap[cellvalue] == undefined) return ''
        return _cMap.metaTypeMap[cellvalue].name
    };

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
    };
} catch (e) {
    console.log(e)
}