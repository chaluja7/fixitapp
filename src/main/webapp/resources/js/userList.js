$(document).ready(function() {

    var table = $("#dataTableList").DataTable({
        "sAjaxSource": "../api/v1/users/list",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "name"},
            {"mData": "username"},
            {"mData": "region"},
            {"mData": "role"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                document.location.href = "user-edit.xhtml?id=" + table.row(this).data().id;
            });
        }
    });

    setUpDataTablesInlineFinding(table);

} );