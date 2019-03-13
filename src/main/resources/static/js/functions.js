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

function enableInput(id)
{
    var length = id.length;
    var idInput = id.substring(0, length-8);

    var input = document.getElementById(idInput);
    var inputStatus = input.getAttribute("disabled");

    if(inputStatus)
    {
        input.removeAttribute("disabled");
    }
    else
    {
        input.setAttribute("disabled", "disabled");
    }
}

function showLanguageOptions(id)
{
    var languages = ["spanish", "english", "portuguese", "italian", "russian"];

    var idInput = document.getElementById(id);

    var idLanguageList = document.getElementById("show-language-list");

    var text = idInput.value.toLowerCase();

    if(idLanguageList.childElementCount!=0)
    {
        idLanguageList.removeChild(document.getElementById("languages-list"));
    }
    
    if(text.length!=0)
    {
    var element = document.createElement('ul');
    element.className = "list-group";
    element.id = "languages-list";

    for(i=0; i<languages.length;i++)
    {
        if(text == languages[i].substring(0, text.length))
        {
            var listItem = document.createElement('a');
            listItem.className = "list-group-item list-group-item-action";
            listItem.id = languages[i];
            listItem.innerHTML = languages[i];
            listItem.onclick = function() {selectLanguage(listItem.id)};

            listItem.id = "language"+i;
            idLanguageList.appendChild(element);
            element.appendChild(listItem);
        }
    }
 }
}

function showDataTypeOptions(id)
 {
    var dataTypes = [".eml", ".tytb", ".tsms", ".twtid", ".warc"];

    var idInput = document.getElementById(id);

    var idDataTypeList = document.getElementById("show-data-types-list");

    var text = "."+idInput.value.toLowerCase();

    if(idDataTypeList.childElementCount!=0)
    {
        idDataTypeList.removeChild(document.getElementById("data-types-list"));
    }
    if(text.length!=1)
    {
        var element = document.createElement('ul');
        element.className = "list-group"
        element.id = "data-types-list"
    for(i=0; i<dataTypes.length;i++)
    {
        if(text == dataTypes[i].substring(0, text.length))
        {
            var listItem = document.createElement('a');
            listItem.className = "list-group-item list-group-item-action";
            listItem.id = "dataType"+i;
            listItem.innerHTML = dataTypes[i];
            listItem.onclick = function() {selectDataType(listItem.id)};

            idDataTypeList.appendChild(element);
            element.appendChild(listItem);
        }
    }
 }
 }

 function enableDateInput()
 {
     var date1 = document.getElementsByName("date")[0];
     var date2 = document.getElementsByName("date")[1];

     if(date1.getAttribute("disabled"))
     {
         date1.removeAttribute("disabled");
         date2.removeAttribute("disabled");
     }
     else
     {
         date1.setAttribute("disabled", "disabled");
         date2.setAttribute("disabled", "disabled");
     }
 }

 function selectLanguage(id)
 {
    var languages = ["spanish", "english", "portuguese", "italian", "russian"];

    var searchInput = document.getElementById("languages");
    var list = document.getElementById("languages-list");
    var selectedLanguages = document.getElementById("selected-languages-list");

    var index = id.substring(8, id.length);

    searchInput.value = "";
    list.parentNode.removeChild(list);

    var language = document.createElement('ul');
    language.className = "list-group-item list-group-item-action text-center";
    language.setAttribute("data-type", "selected-language-item");
    language.innerText = languages[index];
    language.id = "Selected-" + id;
    language.onclick = function() {deleteLanguage(language.id)};

    var removeButton = document.createElement('a');
    removeButton.className = "list-group-item-button";

    var removeButtonIcon = document.createElement('i');
    removeButtonIcon.className = "fas fa-trash";
    removeButton.append(removeButtonIcon);
    language.append(removeButton);

    language.append(removeButton);
    selectedLanguages.append(language);
 }

 function deleteLanguage(id)
 {
    var language = document.getElementById(id);

    language.parentNode.removeChild(language);
 }

 

 function selectDataType(id)
 {
    var dataTypes = [".eml", ".tytb", ".tsms", ".twtid", ".warc"];

    var searchInput = document.getElementById("dataTypes");
    var list = document.getElementById("data-types-list");
    var selectedDataTypes = document.getElementById("selected-data-types-list");

    var index = id.substring(8, id.length);

    console.log(index);

    searchInput.value = "";
    list.parentNode.removeChild(list);

    var dataType = document.createElement('ul');
    dataType.className = "list-group-item list-group-item-action text-center";
    dataType.setAttribute("data-type", "selected-data-type-item");
    dataType.innerText = dataTypes[index];
    dataType.id = "Selected-" + id;
    dataType.onclick = function() {deleteDataType(dataType.id)};

    var removeButton = document.createElement('a');
    removeButton.className = "list-group-item-button";

    var removeButtonIcon = document.createElement('i');
    removeButtonIcon.className = "fas fa-trash";
    removeButton.append(removeButtonIcon);
    dataType.append(removeButton);

    dataType.append(removeButton);
    selectedDataTypes.append(dataType);
 }

 
