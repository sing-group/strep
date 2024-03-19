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
function dynamicUrl(val)
{
    var finalValue = "/dataset/public/detailed/" + val.toLowerCase();
    finalValue = finalValue.replace(/\s/g,"");
    var index = document.getElementById("url").value.indexOf("/dataset/public/detailed",0);
    if(index!=-1)
    {
    document.getElementById("url").value = document.getElementById("url").value.substring(0, index);
    }
    document.getElementById("url").value += finalValue;
}

function enableInput(id)
{

    var length = id.length;
    //var idInput = id.substring(0, length-8);
    var idInput = id;

    var checkbox = document.getElementById(id);
    var input = document.getElementById(idInput);
    if(checkbox.checked)
    {
        input.removeAttribute("disabled");
    }
    else
    {
        input.setAttribute("disabled", "disabled");
    }
}
