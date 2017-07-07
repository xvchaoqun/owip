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

try {
    bootbox.setDefaults({locale: 'zh_CN'});

} catch (e) {
    console.log(e)
}