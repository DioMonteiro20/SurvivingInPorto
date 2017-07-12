<?php 
	
//If a post request is detected 
if($_SERVER['REQUEST_METHOD']=='POST'){

	//Getting the username and otp 
	$username2 = $_POST['username2'];

        $codconf1 = $_POST['codconf1'];

    //Importing the dbConnect script 
	require_once('dbConnect.php');
	
	//Creating an SQL to fetch the otp from the table 
	$sql = "SELECT otp FROM androidotp WHERE username = '$username2'";
	
	//Getting the result array from database 
	$result = mysqli_fetch_array(mysqli_query($con,$sql));
	
	//Getting the otp from the array 
	$realotp = $result['codconf1'];
	
	//If the otp given is equal to otp fetched from database 
	if($otp == $realotp){
		//Creating an sql query to update the column verified to 1 for the specified user 
		$sql = "UPDATE androidotp SET  verified =  '1' WHERE username ='$username2'";
		
		//If the table is updated 
		if(mysqli_query($con,$sql)){
			//displaying success 
			echo 'success';
		}else{
			//displaying failure 
			echo 'failure';
		}
	}else{
		//displaying failure if otp given is not equal to the otp fetched from database  
		echo 'failure';
	}
	
	//Closing the database 
	mysqli_close($con);
}
?>