function retrieveDatasetInfo(id) {
    var url = "/dataset/modal?id="+id;
    console.log(url);
    $("#dataset-info").load(url);
    $("#datasetModal").modal('show');
}

function updateDatasetsList()
{
    var selectedLanguages = document.getElementById("selected-languages");
    var selectedDatatypes = document.getElementById("selected-datatypes");
    var date1 = document.getElementById("date1");
    var date2 = document.getElementById("date2");

    var url = "/dataset/createlist?selectedLanguages="+selectedLanguages.value+"&selectedDatatypes="+selectedDatatypes.value+"&date1="+date1.value+"&date2="+date2.value;

    $("#datasets-list").load(url);
}

function resetFilters()
{
    var selectedLanguages = document.getElementById("selected-languages");
    var selectedDatatypes = document.getElementById("selected-datatypes");
    var date1 = document.getElementById("date1");
    var date2 = document.getElementById("date2");

    var checkEml = document.getElementById(".eml");
    var checkTwid = document.getElementById(".twtid");
    var checkTytb = document.getElementById(".tytb");
    var checkWarc = document.getElementById(".warc");
    var checkTsms = document.getElementById(".tsms");

    checkEml.checked = false;
    checkTwid.checked = false;
    checkTytb.checked = false;
    checkWarc.checked = false;
    checkTsms.checked = false;

    var selectedLanguagesList = document.getElementById("selected-languages-list");

    while(selectedLanguagesList.childElementCount!=0)
    {
        selectedLanguagesList.removeChild(selectedLanguagesList.firstChild);
    }

    selectedLanguages.value = "";
    selectedDatatypes.value = "";
    date1.value = "";
    date2.value = "";

    var url = "/dataset/createlist?selectedLanguages="+selectedLanguages.value+"&selectedDatatypes="+selectedDatatypes.value+"&date1="+date1.value+"&date2="+date2.value;
    $("#datasets-list").load(url);
}

$('#deleteDatasetModal').on('show.bs.modal', function(event)
{
 var button = $(event.relatedTarget);
 var href = button.attr('href');
 var buttonConfirm = $('#deleteConfirmation');
 buttonConfirm.attr("href", href);
});

