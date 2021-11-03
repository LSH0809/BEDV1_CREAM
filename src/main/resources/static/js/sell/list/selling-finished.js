$(function () {

  $("#send").click(function () {
    let userId = $('#search-input').val();
    let status = $('#status option:selected').val();
    status = encodeURI(status);

    if ((userId != localStorage.getItem('userId')) && (userId != '')) {
      localStorage.setItem('userId', userId);
    }

    userId = localStorage.getItem('userId');
    let url = '/users/' + userId + '/selling/finished';
    url = status === '' ? url : url + '?status=' + status;
    $.ajax({
      url: url,
      type: "get",
      dataType: "json",
      success: function (data) {
        console.log(data);
        let html = '';
        $("#table tr:gt(0)").remove();
        $.each(data.data, function (index, item) {
          for (key in item) {
            html += '<tr>';
            html += '<td padding = 150px >' + '<img src=' + item[key].image
                + ' width = "100" height = "100" >'
                + '</td>';
            html += '<td>' + item[key].name + '</td>';
            html += '<td>' + item[key].size + '</td>';
            html += '<td>' + item[key].price + '</td>';
            html += '<td>' + item[key].dealDate + '</td>';
            html += '</tr>';
          }
        })
        $("#dynamicTbody").empty();
        $("#dynamicTbody").append(html);
      },
      error: function (err) {
        alert("ajax 에러 발생");
      }
    });
  })
})
