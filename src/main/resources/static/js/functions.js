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
    var idInput = id.substring(0, length-8);
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



 
 
