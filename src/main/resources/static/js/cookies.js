function GetCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
            return "1";
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0)
            break;
    }

    return null;
}

function aceptar_cookies() {
    var expire = new Date();
    expire = new Date(expire.getTime() + 7776000000);
    document.cookie = "cookies_surestao=aceptada; expires=" + expire + "; path=/;";
    $('#overbox3').addClass("hide");
}

jQuery(function () {
    var visit = GetCookie("cookies_surestao");
    if (visit == 1) {
       $('#overbox3').addClass("hide");
    } else {
        $('#overbox3').removeClass("hide");
    } 

});



