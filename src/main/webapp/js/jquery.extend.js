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

(function ($) {
    $.extend({
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
            return iDays
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
        // 重新加载内容区域
        loadPage: function (url) {
            var $container = $("#body-content");
            $container.showLoading({
                'afterShow': function () {
                    setTimeout(function () {
                        $container.hideLoading();
                    }, 2000);
                }
            });
            $.get(url, {}, function (html) {
                $container.hideLoading().hide();
                $("#page-content").hide().html(html).fadeIn("slow");
            })
        },
        /*loadBody: function (url) {
         var $container = $("#body-content");
         $container.showLoading({
         'afterShow': function () {
         setTimeout(function () {
         $container.hideLoading();
         }, 2000);
         }
         });
         $.get(url, {}, function (html) {
         $container.hideLoading().hide();
         $("#item-content").hide();
         $("#body-content").html(html).fadeIn("slow");
         })
         },*/
        // 重新加载副区域
        loadView: function (url, isBody, fn) {

            if (isBody == undefined || typeof isBody == 'function') {
                fn = isBody;
                isBody = true;
            }
            // 关闭modal
            $("#modal").removeClass("fade").modal('hide').addClass("fade");

            var $container = isBody ? $("#body-content") : $("#item-content");

            $container.showLoading({
                'afterShow': function () {
                    setTimeout(function () {
                        $container.hideLoading();
                        if (typeof fn == 'function') fn();
                    }, 2000);
                }
            });
            $.get(url, {}, function (html) {
                $container.hideLoading().hide();
                $("#item-content").hide().html(html).fadeIn("slow");
            })
        },
        // 关闭副窗口，如果传入了url，则刷新主区域
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
        fileInput: function($input,params){
              $input.ace_file_input($.extend({
                    no_file:'请选择文件 ...',
                    btn_choose:'选择',
                    btn_change:'更改',
                    droppable:false,
                    onchange:null,
                    thumbnail:false,
                    maxSize:_uploadMaxSize,
                      //whitelist:'gif|png|jpg|jpeg'
                      //blacklist:'exe|php'
                      //onchange:''
                }, params)).off('file.error.ace').on("file.error.ace",function(e, info){

                var size = info.error_list['size'];
                if(size!=undefined) SysMsg.error("文件{0}超过{1}M大小".format(size, _uploadMaxSize/(1024*1024)));
                var ext = info.error_count['ext'];
                var mime = info.error_count['mime'];
                if(ext!=undefined||mime!=undefined) SysMsg.error("文件格式有误");
                e.preventDefault();
            });
        },
        swfPreview: function(filepath, filename, hrefLabel, plainText){
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
    })
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
        console.log(cellvalue)
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