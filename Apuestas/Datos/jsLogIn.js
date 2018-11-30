function getUserData() {
    var user = document.getElementById('identificador');
    console.log(user);
    var fileUrl = "http://172.30.155.39:7777/user/"+user.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', fileUrl, true);
    xhttp.send();
    
    xhttp.onreadystatechange = processRequest;
    function processRequest(){
        if(xhttp.readyState == 4 && xhttp.status == 200){
            var re =xhttp.responseText;
            
            console.log(re);
            document.writeln(re);
        }
    }
 }
