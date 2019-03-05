function dynamicUrl(val)
{
    var finalValue = "/dataset/detailed/" + val.toLowerCase();
    finalValue = finalValue.replace(/\s/g,"");
    var index = document.getElementById("url").value.indexOf("/dataset/detailed",0);
    if(index!=-1)
    {
    document.getElementById("url").value = document.getElementById("url").value.substring(0, index);
    }
    document.getElementById("url").value += finalValue;
}