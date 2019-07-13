 var imagePath="images/ajax-loader-circle.gif" 
 var loadedobjects="";
 var rootdomain="http://"+window.location.hostname;
 var headScriptAdd=0;
 
  function ajaxpage(url, containerid){
   var page_request = false
   if (window.XMLHttpRequest) // if Mozilla, Safari etc
    page_request = new XMLHttpRequest()
   else if (window.ActiveXObject){ // if IE
    try {
     page_request = new ActiveXObject("Msxml2.XMLHTTP"); //new ActiveXObject('MSXML2.XMLHTTP.3.0');
    } catch (e){
     try{
      page_request = new ActiveXObject("Microsoft.XMLHTTP"); //new ActiveXObject('MSXML2.XMLHTTP.3.0');
     }catch (e){}
    }
   }else return false
   page_request.onreadystatechange=function(){
    loadpage(page_request, containerid)
   }
   /*
   document.getElementById(containerid).innerHTML=
	   "<table width=\"400px\"><tr><td width=\"100%\" valign=\"middle\" align=\"center\" " +
	   " height=\"400px\" > <img style=\"top: 50%;vertical-align: middle;\" src=\""+imagePath+
	   "\"  alt=\"load page\" /></td></tr></table>";
   */
   try {
	   document.getElementById('imgWait').style.visibility = "visible";
   } catch (err){
       
   }
   
   page_request.open('GET', url, true)
   page_request.send(null)
  }

  
  
  function loadpage(page_request, containerid){
   if (page_request.readyState == 4 && (page_request.status==200 || window.location.href.indexOf("http")==-1)){
    // place for remove from head
    var head = document.getElementsByTagName("head")[0];
    for(var counter=0;counter<headScriptAdd;counter++){
     head.removeChild(head.lastChild);}
    // место замены изображения 
    headScriptAdd=0;
    document.getElementById(containerid).innerHTML=getBody(page_request.responseText);
    getResponseTextSourceScripts(page_request.responseText);
    //document.getElementById(containerid).innerHTML=page_request.responseText;
   }
  }

  function getBody(value){
   var html_text=new String(value);
   var positionBodyBegin=getPositionBodyBegin(html_text,0);
   var positionBodyEnd=getPositionBodyEnd(html_text,positionBodyBegin);
   if((positionBodyBegin>0)&&(positionBodyEnd>0)){
    var tempValue=new String(html_text);
    //alert(html_text.substring(positionBodyBegin+1, positionBodyEnd-1));
    return html_text.substring(positionBodyBegin+1, positionBodyEnd-1);
   }else{
    return html_text;
   }
   
  }

  function getPositionBodyBegin(value, position_begin){
   var position=value.indexOf("<body",position_begin);
   if(position>0){
    return value.indexOf(">",position);
   }else{
    return -1;
   }
  }

  function getPositionBodyEnd(value, position_begin){
   return value.indexOf(escape("/")+"body>",position_begin);
  }
  
  
  function getPositionBegin(value, position_begin){
   var position=value.indexOf("<script",position_begin);
   if(position>0){
    return value.indexOf(">",position);
   }else{
    return -1;
   }
  }

  function getPositionEnd(value, position_begin){
   return value.indexOf(escape("/")+"script>",position_begin);
  }
  
  
  function getResponseTextSourceScripts(responseText){
   var head = document.getElementsByTagName("head")[0];
   var value=new String(responseText);
   var position_begin=0;
   var position_end=0;
   position_begin=getPositionBegin(value,0);
   position_end=0;
   while(position_begin>=0){
    // получить начало и конец скрипта 
    position_end=getPositionEnd(value,position_begin);
    if(position_end<position_begin){
     break;
    }
    // создать элемент скрипт и добавить его в Header
    var script = document.createElement("script");
    script.type = "text/javascript";
    var scriptText=value.substring(position_begin+1, position_end-1);
    //eval('"'+scriptText+'"');
    //alert(scriptText);
    script.appendChild(document.createTextNode(scriptText));
    head.appendChild(script);
    headScriptAdd++;
    // вырезать скрипт из исходного кода 
    position_begin=getPositionBegin(value, position_end);
   };
   
  }
