$.ajaxSetup({
    traditional:true,
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
        if (ret.success) return data;

        if (ret.msg == "login") {
            bootbox.dialog({
                message: "登陆超时，请您重新登陆",
                closeButton: false,
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
                            if (window.opener) {
                                //window.open('','_self','');
                                window.close();
                            }
                            location.href = ctx + "/login";
                        }
                    }
                }
            });
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
        //alert( '发送AJAX请求到"' + this.url + '"时出错[' + jqXHR.status + ']：' + errorMsg );
        //SysMsg.error('系统异常，请稍后再试。', '系统异常');
    }
});

$.register.m_click(".hashchange", function () {
    location.href = $(this).data("url");
})

$.register.m_click(".ahref", function () {
    location.href = $(this).data("url");
})
$.register.m_click(".popupBtn", function () {
    var url = $(this).data("url");
    $.loadModal(url, $(this).data("width"), $(this).data("direction"));
})
$.register.m_click(".linkBtn", function () {
    //e.stopPropagation();
    var $this = $(this);
    var url = $this.data("url");
    var target = $this.data("target") || '_self';
    if(target=='_self') {
        $this.data("loading-text", $this.data("loading-text") || '<i class="fa fa-spinner fa-spin"></i> 操作中')
        $this.button("loading");
    }

    window.open(url, target, '');
})
$.register.m_click(".popPdfView", function () {
    $.loadPdfModal($(this).data("path"), $(this).data("pages"),
        $(this).data("width"), $(this).data("direction"));
})
$.register.m_click(".confirm", function () {
    $.confirm(this);
})
// 内页展示
$.register.m_click(".openView", function () {
    var $this = $(this);
    var openBy = $this.data("open-by");
    var url = $this.data("url");
    if (openBy == 'page') {
        var callback = $this.data("callback");
        if(callback!='') callback=eval(callback)
        $.openView({url:url, callback:callback});
    } else {
        $.loadModal(url, $this.data("width"));
    }
});

$.register.m_click("#body-content-view .hideView", function () {
    //var $this = $(this);
    $.hideView({mobile:true})
});

// 左div float时，保证左div高度不小于右div高度
$(document).on('shown.bs.tab', 'a[data-toggle="tab"]', function (e) {
    $.adjustLeftFloatDivHeight($(".profile-info-name.td"))
});