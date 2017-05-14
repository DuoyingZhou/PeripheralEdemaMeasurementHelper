<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 $file_name = $_FILES['myFile']['name'];
 $file_size = $_FILES['myFile']['size'];
 $file_type = $_FILES['myFile']['type'];
 $temp_name = $_FILES['myFile']['tmp_name'];
 
 $location = "uploads/";
 echo $file_size;
 echo "            ";
 echo $file_type;
 echo "            ";
 echo $temp_name;
 echo "            ";
 echo $file_name;
 echo "            ";
 move_uploaded_file($temp_name, $location.$file_name);
 echo "http://**/AndroidFileUpload/uploads/".$file_name;
 }else{
 echo "Error";

 }
 ?>