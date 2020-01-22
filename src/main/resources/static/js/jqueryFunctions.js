function retrieveDatasetInfo(id) {
    var url = "/dataset/modal?id=" + id;

    $("#dataset-info").load(url, function (response, status, xhr) {
        if (status == "error") {
            location.reload();
        }
    });
    $("#datasetModal").modal('show');
}

function updateDatasetsList() {
    var licenses = $('#licenses').find("option:selected");//document.getElementsByName("licenses");
    var languages = $('#languages').find("option:selected");//$('input[name="language"]:checked');//document.getElementsByName("language");
    var datatypes = $('#datatypes').find("option:selected");//$('input[name="datatype"]:checked');//document.getElementsByName("datatype");
    var dateValue1 = document.getElementById("date1").value;
    var dateValue2 = document.getElementById("date2").value;

    var firstParam = true;

    var url = "/dataset/createlist?";
    if (dateValue1 && dateValue2) {
        url += ("date1=" + dateValue1 + "&date2=" + dateValue2);
        firstParam = false;
    }

    var vlic = [];
    for (var i = 0; i < licenses.length; i++) {
        if (licenses[i].value != "") {
            vlic.push(licenses[i].value)
        }
    }
    if (vlic.length > 0) {
        url += encodeURI((firstParam ? "license=" : "&license=") + vlic);
        firstParam = false;
    }

    var vlang = [];
    for (var i = 0; i < languages.length; i++) {
        if (languages[i].value != "") {
            vlang.push(languages[i].value);
        }
    }
    if (vlang.length > 0) {
        url += encodeURI((firstParam ? "language=" : "&language=") + vlang);
        firstParam = false;
    }

    var vdatat = [];
    for (var i = 0; i < datatypes.length; i++) {
        if (datatypes[i].value != "") {
            vdatat.push(datatypes[i].value);
        }
    }
    if (vdatat.length > 0) {
        url += encodeURI((firstParam ? "datatype=" : "&datatype=") + vdatat);
        firstParam = false;
    }

    $("#datasets-list").load(url, function (response, status, xhr) {
        if (status == "error") {
             alert("#update-datasets-list error")
            location.reload();
        }
    });
}

function validateCitationRequest() {
    if (!$("#citationRequest").attr("readonly")) {
        var license = $("#selectLicense").val();
        var url = "";
        if (location.href.lastIndexOf("create") != -1) {
            url = encodeURI("/dataset/validateCitationRequestOnCreate?license=" + license);
        } else {
            url = encodeURI("/dataset/validateCitationRequest?license=" + license);
        }

        $("#citation-request").load(url, function (response, status, xhr) {
            if (status == "error") {
                 alert("#validate-citation-request error")
                location.reload();
            }
        });
    }
}

function checkLicenses() {
    var datasetsList = $("input[name=datasets]");
    var selectedDatasets = $("input[name=datasets]:checked");
    var vCheckedDatasets = [];
    var vDatasets = [];
    if (selectedDatasets.length > 0) {
        for (var i = 0; i < selectedDatasets.length; i++) {
            if (selectedDatasets[i].value != "") {
                vCheckedDatasets.push(selectedDatasets[i].value);
            }
        }
        for (var i = 0; i < datasetsList.length; i++) {
            if (datasetsList[i].value != "") {
                vDatasets.push(datasetsList[i].value);
            }
        }

        var params = "";
        var firstElement = true;
        if (vCheckedDatasets.length > 0) {
            params += encodeURI((firstElement ? "checkedDatasets=" : "&checkedDatasets=") + vCheckedDatasets);
            firstElement = false;
        }

        if (vDatasets.length > 0) {
            params += encodeURI(("&datasets=") + vDatasets);
        }

        var urlCheckLicenses = "/dataset/filterDatasetsByLicense?" + params;
        $("#datasets-list").load(urlCheckLicenses, function (response, status, xhr) {
            if (status == "error") {
                alert("#datasets-list error")
                location.reload();
            } else {
                var urlComposeCitationRequest = "/dataset/composeCitationRequest?" + params;
                $("#citation-request").load(urlComposeCitationRequest, function (response, status, xhr) {
                    if (status == "error") {
                        alert("#citation-request error")
                        location.reload();
                    }
                });

                var urlCheckLicenses = "/dataset/checkLicenses?" + params;
                $("#check-licenses").load(urlCheckLicenses, function (response, status, xhr) {
                    if (status == "error") {
                        alert("#check-licenses error")
                        location.reload();
                    }
                });

                var urlCheckAccess = "/dataset/checkAccess?" + params;
                $("#check-access").load(urlCheckAccess, function (response, status, xhr) {
                    updateTable();
                    if (status == "error") {
                        alert("#check-access error")
                        location.reload();
                    }
                });
            }
        });
    } 
}

function updateTable(id) {
    var datasetNames = document.getElementsByName("datasets");
    if (id == "buttonCheckDatatypes") {
        var inputFileNumber = document.getElementById("fileNumber");
        var datasets = document.getElementsByName("datasets");

        var inputFileNumberValue = checkNullValues(inputFileNumber);
        var inputSpamEml = document.getElementById("inputSpam.eml");
        var inputHamEml = document.getElementById("inputHam.eml");
        var inputSpamTsms = document.getElementById("inputSpam.tsms");
        var inputHamTsms = document.getElementById("inputHam.tsms");
        var inputSpamTytb = document.getElementById("inputSpam.ytbid");
        var inputHamTytb = document.getElementById("inputHam.ytbid");
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

        for (var i = 0; i < datasets.length; i++) {
            if (datasets[i].checked) {
                url += encodeURI("&" + datasets[i].name + "=" + datasets[i].value);
            }
        }

        url += "&inputSpamEml=" + inputSpamEmlValue + "&inputHamEml=" + inputHamEmlValue + "&inputSpamTsms=" + inputSpamTsmsValue + "&inputHamTsms=" + inputHamTsmsValue +
                "&inputSpamYtbid=" + inputSpamTytbValue + "&inputHamYtbid=" + inputHamTytbValue + "&inputSpamTwtid=" + inputSpamTwtidValue + "&inputHamTwtid=" + inputHamTwtidValue +
                "&inputSpamWarc=" + inputSpamWarcValue + "&inputHamWarc=" + inputHamWarcValue;
        checkSelectedDatasets();
        $("#datatypes-table").load(url, function (response, status, xhr) {
            if (status == "error") {
                location.reload();
            } else {
                 $("input[name^='inputHam']").attr("disabled", false);
                 $("input[name^='inputSpam']").attr("disabled", false);
            }
        });
    } else {
        var url = "/dataset/updateDatatypesTable";
        for (var i = 0; i < datasetNames.length; i++) {
            if (datasetNames[i].checked) {
                if (url == "/dataset/updateDatatypesTable")
                    url += encodeURI("?" + datasetNames[i].name + "=" + datasetNames[i].value);
                else
                    url += encodeURI("&" + datasetNames[i].name + "=" + datasetNames[i].value);
            }
        }

        checkSelectedDatasets();
        $("#datatypes-table").load(url, function (response, status, xhr) {
            if (status == "error") {
                location.reload();
            }
        });
    }
}

function checkIfPosibleSpam() {
    var inputSpam = document.getElementById("inputSpamPercentage");
    var inputFileNumber = document.getElementById("fileNumber");
    var datasets = document.getElementsByName("datasets");


    var url = "/dataset/checkPosibleSpam?inputSpam=" + inputSpam.value + "&fileNumber=" + inputFileNumber.value;

    for (var i = 0; i < datasets.length; i++) {
        if (datasets[i].checked) {
            url += encodeURI("&" + datasets[i].name + "=" + datasets[i].value);
        }
    }

    $("#info-spam").load(url, function (response, status, xhr) {
        if (status == "error") {
            location.reload();
        }
    });
}

function about() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
}

function checkNullValues(input) {
    if (input != null) {
        return input.value;
    } else {
        return 0;
    }
}

$('#deleteDatasetModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var href = button.attr('href');
    var buttonConfirm = $('#deleteConfirmation');
    buttonConfirm.attr("href", href);
});


function changeLocale(id) {
    var url = location.href;
    var index = url.lastIndexOf("?");
    if (index == -1) {
        url += "?lang=" + id;
    } else {
        index = url.search("lang");
        if (index == -1) {
            url += "&lang=" + id;
        } else {
            var suburl = url.substr(0, index - 1);
            index = url.lastIndexOf("?lang");
            if (index == -1) {
                url = suburl + "&lang=" + id;
            } else {
                url = suburl + "?lang=" + id;
            }
        }
    }
    location.replace(url);
}

function deniedRequest() {

    document.getElementById('permissionForm').action = '/permission/reject';
    document.getElementById('permissionForm').submit();
}

// Tune dataset: Needed to hold disabled inputs in not selected option
$('#datasetSelectedError').hide();

function checkSelectedDatasets() {
    var checkedOptionValue = $("input[name=mode]:checked").val();
    if (checkedOptionValue == "spam") {
        $('#datasetSelectedError').hide();
        $("#datatypespercentage input").attr("disabled", true);
        $("#buttonCheckDatatypes").attr("disabled", true);
        $("#spampercentage input").attr("disabled", false);
    } else {
        if ($('#checkboxUpdateTable:checked').length == 0) {
            $('#datasetSelectedError').show();
        } else {
            $('#datasetSelectedError').hide();
            $("#spampercentage input").attr("disabled", true);
            $("#datatypespercentage input").attr("disabled", false);
            $("#buttonCheckDatatypes").attr("disabled", false);
        }
    }
}

function back(url) {

    if (url === "") {
        window.location = document.referrer;
    } else {
        window.location = url;
    }
}

$(document).ready(function () {

    $('#datasetSelectedError').hide();
    $("#back").click(function () {
        window.location = document.referrer;
    });

    // It's been done like this to make it work properly in Chrome
    document.getElementById('locales').addEventListener('change', function () {
        changeLocale($('#locales').val());
    }, false);

    // Needed to update properly the dropbox
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

    // create_preprocessing_task. Needed to disabled the unselect option.
    $("#selectTask").change(function () {
        //$('#errorLabelName').remove();
        var taskId = $("#selectTask option:selected").val()
        if (taskId !== "") {
            
            $("#dataset-file").attr("disabled", true);
            $("#createPreprocessingTaskF").attr("action", "/task/preprocess/reuse");
            
            // Fill name and description 
            var url = encodeURI("/task/fillFields?id=" + taskId);

            $("#task-data").load(url, function (response, status, xhr) {
          //      $('#errorLabelName').remove();
                if (status === "error") {
                    alert("error");
                    location.reload();
                } else {
                    $("#name").attr("readonly", true);
                    $("#description").attr("readonly", true);
                }
            });

        } else {
            $("#name").attr("readonly", false);
            $("#name").val("");
            $("#description").attr("readonly", false);
            $("#description").val("");
            $("#dataset-file").attr("disabled", false);
            $('.error-label').attr("disabled", false);
            $("#createPreprocessingTaskF").attr("action", "/task/preprocess/create")
        }
    });
});
