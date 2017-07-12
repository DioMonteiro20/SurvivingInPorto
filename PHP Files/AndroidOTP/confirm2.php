<?php  

 require "dbConnect.php";  

 $username = (isset($_POST['username']) ? $_POST['username'] : null); 
 $password = (isset($_POST['password']) ? $_POST['password'] : null); 
 
 $sql_query = "select id, username from androidotp where username like '$username' and password like '$password' and verified=1;";  
 
 $result = mysqli_query($con,$sql_query);  
 
         if(mysqli_num_rows($result) >0 )  
         {  
                 $row = mysqli_fetch_assoc($result);  

                 $name =$row["nome"];  
 
                 echo "Login success".$name;  
         }  
         else  
         {   
                 echo "Login Failed.......Try Again..";  
         }  
 
 ?>  