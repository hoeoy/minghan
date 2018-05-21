$(function() {
    pushHistory();
});
function pushHistory() {
    window.addEventListener("popstate", function(e) {
      //alert("后退");
        self.location.reload();
    }, false);
    var url = window.location.href;
    var state = {
        title : "",
        url : url
    };
    //alert(url);
    window.history.replaceState(state, "", url);
};