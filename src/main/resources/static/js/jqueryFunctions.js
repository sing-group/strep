function retrieveDatasetInfo(id) {
    var url = "/dataset/modal?id=" + id;
    console.log(url);
    $("#dataset-info").load(url, function (response, status, xhr) {
        if (response.substr(0, 2) == "<!")
        {
            location.reload();
        }
    });
    $("#datasetModal").modal('show');
}

function updateDatasetsList()
{
    var licenses = document.getElementsByName("license");
    var languages = document.getElementsByName("language");
    var datatypes = document.getElementsByName("datatype");

    var dateValue1 = document.getElementById("date1").value;
    var dateValue2 = document.getElementById("date2").value;

    var url = "/dataset/createlist?date1=" + dateValue1 + "&date2=" + dateValue2;

    for (var i = 0; i < licenses.length; i++)
    {
        if (licenses[i].checked)
        {
            url += encodeURI("&" + licenses[i].name + "=" + licenses[i].value);
        }
    }

    for (var i = 0; i < languages.length; i++)
    {
        if (languages[i].checked)
        {
            url += encodeURI("&" + languages[i].name + "=" + languages[i].value);
        }
    }

    for (var i = 0; i < datatypes.length; i++)
    {
        if (datatypes[i].checked)
        {
            url += encodeURI("&" + datatypes[i].name + "=" + datatypes[i].value);
        }
    }

    console.log(url);

    $("#datasets-list").load(url, function (response, status, xhr) {
        if (response.substr(0, 2) == "<!")
        {
            location.reload();
        }
    });
}

function updateTable(id)
{
    var datasetNames = document.getElementsByName("datasets");

    if (id == "buttonCheckDatatypes")
    {
        var inputFileNumber = document.getElementById("fileNumber");
        var datasets = document.getElementsByName("datasets");

        var inputFileNumberValue = checkNullValues(inputFileNumber);
        var inputSpamEml = document.getElementById("inputSpam.eml");
        var inputHamEml = document.getElementById("inputHam.eml");
        var inputSpamTsms = document.getElementById("inputSpam.tsms");
        var inputHamTsms = document.getElementById("inputHam.tsms");
        var inputSpamTytb = document.getElementById("inputSpam.tytb");
        var inputHamTytb = document.getElementById("inputHam.tytb");
        var inputSpamTwtid = document.getElementById("inputSpam.twtid");
        var inputHamTwtid = document.getElementById("inputHam.twtid");
        var inputSpamWarc = document.getElementById("inputSpam.warc");
        var inputHamWarc = document.getElementById("inputHam.warc");

        var inputSpamEmlValue = checkNullValues(inputSpamEml);
        var inputHamEmlValue = checkNullValues(inputHamEml);
        var inputSpamTsmsValue = checkNullValues(inputSpamTsms);
        var inputHamTsmsValue = checkNullValues(inputHamTsms);
        var inputSpamTytbValue = checkNullValues(inputSpamTytb);
        var inputHamTytbValue = checkNullValues(inputHamTytb);
        var inputSpamTwtidValue = checkNullValues(inputSpamTwtid);
        var inputHamTwtidValue = checkNullValues(inputHamTwtid);
        var inputSpamWarcValue = checkNullValues(inputSpamWarc);
        var inputHamWarcValue = checkNullValues(inputHamWarc);

        var url = "/dataset/checkPosibleDatatypes?inputFileNumber=" + inputFileNumberValue;

        for (var i = 0; i < datasets.length; i++)
        {
            if (datasets[i].checked)
            {
                url += encodeURI("&" + datasets[i].name + "=" + datasets[i].value);
            }
        }

        url += "&inputSpamEml=" + inputSpamEmlValue + "&inputHamEml=" + inputHamEmlValue + "&inputSpamTsms=" + inputSpamTsmsValue + "&inputHamTsms=" + inputHamTsmsValue +
                "&inputSpamTytb=" + inputSpamTytbValue + "&inputHamTytb=" + inputHamTytbValue + "&inputSpamTwtid=" + inputSpamTwtidValue + "&inputHamTwtid=" + inputHamTwtidValue +
                "&inputSpamWarc=" + inputSpamWarcValue + "&inputHamWarc=" + inputHamWarcValue;

        $("#datatypes-table").load(url, function (response, status, xhr) {
            if (response.substr(0, 2) == "<!")
            {
                location.reload();
            }
        });
    } else
    {
        var url = "/dataset/updateDatatypesTable";
        for (var i = 0; i < datasetNames.length; i++)
        {
            if (datasetNames[i].checked)
            {
                if (url == "/dataset/updateDatatypesTable")
                    url += encodeURI("?" + datasetNames[i].name + "=" + datasetNames[i].value);
                else
                    url += encodeURI("&" + datasetNames[i].name + "=" + datasetNames[i].value);
            }
        }
        console.log(url);
        $("#datatypes-table").load(url, function (response, status, xhr) {
            if (response.substr(0, 2) == "<!")
            {
                location.reload();
            }
        });
    }
}

function checkIfPosibleSpam()
{
    var inputSpam = document.getElementById("inputSpamPercentage");
    var inputFileNumber = document.getElementById("fileNumber");
    var datasets = document.getElementsByName("datasets");


    var url = "/dataset/checkPosibleSpam?inputSpam=" + inputSpam.value + "&fileNumber=" + inputFileNumber.value;

    for (var i = 0; i < datasets.length; i++)
    {
        if (datasets[i].checked)
        {
            url += encodeURI("&" + datasets[i].name + "=" + datasets[i].value);
        }
    }
    console.log(url);
    $("#info-spam").load(url, function (response, status, xhr) {
        if (response.substr(0, 2) == "<!")
        {
            location.reload();
        }
    });
}

function checkNullValues(input)
{
    if (input != null)
    {
        return input.value;
    } else
    {
        return 0;
    }
}

$('#deleteDatasetModal').on('show.bs.modal', function (event)
{
    var button = $(event.relatedTarget);
    var href = button.attr('href');
    var buttonConfirm = $('#deleteConfirmation');
    buttonConfirm.attr("href", href);
});

/*
$(function () {
    $('[data-toggle="popover"]').popover()
})*/

function changeLocale(id)
{
    var url = location.href;
    var index = url.lastIndexOf("?");
    if (index == -1)
    {
        url += "?lang=" + id;
    } else
    {
        index = url.search("lang");
        if (index == -1)
        {
            url += "&lang=" + id;
        } else
        {
            var suburl = url.substr(0, index - 1);
            index = url.lastIndexOf("?lang");
            if (index == -1)
            {
                url = suburl + "&lang=" + id;
            } else
            {
                url = suburl + "?lang=" + id;
            }
        }
    }
    location.replace(url);
}

function deniedRequest(){

  document.getElementById('permissionForm').action='/permission/reject';
  alert(document.getElementById('permissionForm').action)
  document.getElementById('permissionForm').submit();
}

$(document).ready(function () {

    $("#back").click(function() {
      window.location= document.referrer;
    });

  /*  document.getElementById('back').addEventListener('click', function () {
      alert(document.referrer);
      window.location= document.referrer;
    }, false);
*/
    // It's been done like this to make it work properly in Chrome
    document.getElementById('locales').addEventListener('change', function () {
        changeLocale($('#locales').val());
    }, false);

    //Needed to update properly the dropbox
    let params = new URLSearchParams(location.search);
    var lang = params.get('lang');
    if (lang != null) {
        $('#locales').val(lang);
    }

    // Needed to mark active links
    var pathname = $(location).attr('pathname').replace(/[/]/gi, "");
    $('#navbarColor01 a.active').removeClass('active');

    if (pathname === "") {
        $("#home").addClass("active");
    } else {
        $("#" + pathname).addClass("active");
    }

});
