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
    var languages = ["spanish", "english", "portuguese", "italian", "russian", "galician"];

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

 function selectLanguage(id)
 {
    var languages = ["spanish", "english", "portuguese", "italian", "russian", "galician"];

    var searchInput = document.getElementById("languages");
    var list = document.getElementById("languages-list");
    var selectedLanguages = document.getElementById("selected-languages-list");
    var selectedLanguagesHidden = document.getElementById("selected-languages");

    var index = id.substring(8, id.length);

    searchInput.value = "";
    list.parentNode.removeChild(list);
    
    if(!selectedLanguagesHidden.value.includes(languages[index]))
    {
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

    if(selectedLanguagesHidden.value=="")
    {
        selectedLanguagesHidden.value += languages[index];
    }
    else
    {
        selectedLanguagesHidden.value += ","+languages[index];
    }
    }
 }

 function deleteLanguage(id)
 {
    var languages = ["spanish", "english", "portuguese", "italian", "russian", "galician"];

    var language = document.getElementById(id);
    var selectedLanguagesHidden = document.getElementById("selected-languages");
    var languageStr = id.substring(17, id.length);

    var index = Number(languageStr);

    if(selectedLanguagesHidden.value.substring(0, languages[index].length)==languages[index])
    {
        selectedLanguagesHidden.value = selectedLanguagesHidden.value.replace(languages[index],"");
        if(selectedLanguagesHidden.value!="")
        {
            selectedLanguagesHidden.value = selectedLanguagesHidden.value.replace(",", "");
        }
    }
    else
    {
        selectedLanguagesHidden.value = selectedLanguagesHidden.value.replace(","+languages[index], "");
    }

    language.parentNode.removeChild(language);
 }

 function selectDatatype(id)
 {
     var datatype = document.getElementById(id);
     var selectedDatatypesHidden = document.getElementById("selected-datatypes");

     if(datatype.checked)
     {
         if(selectedDatatypesHidden.value == "")
         {
             selectedDatatypesHidden.value = id;
         }
         else
         {
             selectedDatatypesHidden.value = selectedDatatypesHidden.value+","+id;
         }
     }
     else
     {
         if(selectedDatatypesHidden.value.substring(0, id.length)==id)
         {
             selectedDatatypesHidden.value = selectedDatatypesHidden.value.replace(id, "");
             if(selectedDatatypesHidden.value!="")
             {
                 selectedDatatypesHidden.value = selectedDatatypesHidden.value.replace(",", "");
             }
         }
         else
         {
             selectedDatatypesHidden.value = selectedDatatypesHidden.value.replace(","+id,"");
         }
     }
 }

 
 
