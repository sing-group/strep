/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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



