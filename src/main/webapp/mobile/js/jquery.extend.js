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
        loadModal: function (url, width, dragTarget) { // dragTarget：拖拽位置
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

            $('#modal .modal-content').load(url, function (data) {
                if (!data.startWith("{")) $("#modal").modal('show');
            });
        }
    });
    /*$.fn.mask = function (options) {
        //console.log(options)
        options = options || {};
        if(options.hide!=undefined && options.hide>0) {
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
    };
    $.fn.unmask = function(){
        var $this = $(this);
        return $this.hideLoading();
    }*/
})(jQuery);

try {
    bootbox.setDefaults({locale: 'zh_CN'});

} catch (e) {
    console.log(e)
}