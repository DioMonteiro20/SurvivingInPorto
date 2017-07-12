<?php  

 require "dbConnect.php";  

 $username = (isset($_POST['username']) ? $_POST['username'] : null); 
 
 
 $sql_query = "delete from androidotp where username = '$username';";  

 
 mysqli_query($con,$sql_query);  
 
         if(mysqli_query($con,$sql_query))  
         {  
                 $row = mysqli_fetch_assoc($result);  

                 $name =$row["nome"];  
 
                 echo "Apagada".$name;  
         }  
         else  
         {   
                 echo "Falhou a apagar";  
         }  

	   	    ?>﻿