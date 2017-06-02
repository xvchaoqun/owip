(function ($) {
    $.extend({
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
        hashchange: function (querystr, path) {

            //alert($(".nav.nav-pills li").length)
            var oldHash = window.location.hash;
            if($.trim(querystr) == '' && $.trim(path) == ''){

                $("window").trigger("hashchange");
            }else if ($.trim(querystr) == '') {

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
        loadPage:function(url){
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

            if(isBody==undefined || typeof isBody == 'function') {
                fn = isBody;
                isBody = true;
            }
            // 关闭modal
            $("#modal").removeClass("fade").modal('hide').addClass("fade");

            var $container = isBody?$("#body-content"):$("#item-content");

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
                if ($.trim(pageUrl)!='') {
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
        }
    })
})(jQuery);