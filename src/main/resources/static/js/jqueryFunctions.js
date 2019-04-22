function retrieveDatasetInfo(id) {
    var url = "/dataset/modal?id="+id;
    console.log(url);
    $("#dataset-info").load(url);
    $("#datasetModal").modal('show');
}

function updateDatasetsList()
{
    var licenses = document.getElementsByName("license");
    var languages = document.getElementsByName("language");
    var datatypes = document.getElementsByName("datatype");

    var dateValue1 = document.getElementById("date1").value;
    var dateValue2 = document.getElementById("date2").value;

    var url = "/dataset/createlist?date1="+dateValue1+"&date2="+dateValue2;

    for(var i = 0;i<licenses.length;i++)
    {
        if(licenses[i].checked)
        {
            url+=encodeURI("&"+licenses[i].name+"="+licenses[i].value);
        }
    }

    for(var i = 0;i<languages.length;i++)
    {
        if(languages[i].checked)
        {
            url+=encodeURI("&"+languages[i].name+"="+languages[i].value);
        }
    }

    for(var i = 0;i<datatypes.length;i++)
    {
        if(datatypes[i].checked)
        {
            url+=encodeURI("&"+datatypes[i].name+"="+datatypes[i].value);
        }
    }

    console.log(url);

    $("#datasets-list").load(url);
}

$('#deleteDatasetModal').on('show.bs.modal', function(event)
{
 var button = $(event.relatedTarget);
 var href = button.attr('href');
 var buttonConfirm = $('#deleteConfirmation');
 buttonConfirm.attr("href", href);
});

