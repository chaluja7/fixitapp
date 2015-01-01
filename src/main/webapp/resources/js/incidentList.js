$(document).ready(function() {

    var table = $("#dataTableList").DataTable({
        "sAjaxSource": "../api/v1/incidents/list",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "title"},
            {"mData": "state"},
            {"mData": "address"},
            {"mData": "timeOfCreation"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                document.location.href = "incident.xhtml?id=" + table.row(this).data().id;
            });
        }
    });

    setUpDataTablesInlineFinding(table);

} );