function retrieveDatasetInfo(id) {
    var url = "/dataset/modal?id="+id;
    console.log(url);
    $("#dataset-info").load(url);
    $("#datasetModal").modal('show');
}