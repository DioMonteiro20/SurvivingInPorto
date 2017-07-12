 <?php  
 $db_name = "2124939_survport";  
 $mysql_user = "2124939_survport";  
 $mysql_pass = "survporto16";  
 $server_name = "pdb6.awardspace.net";  
 $con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);  
 

if(!$con)
{
	echo "Erro".mysqli_connect_error();
}
else 
{
	echo "Sucesso";
}

 ?>  