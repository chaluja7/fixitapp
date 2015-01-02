$(document).ready(function() {

    var table = $("#dataTableList").DataTable({
        "sAjaxSource": "../api/v1/regions/list",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "name"},
            {"mData": "admin"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                document.location.href = "region.xhtml?id=" + table.row(this).data().id;
            });
        }
    });

    setUpDataTablesInlineFinding(table);

} );