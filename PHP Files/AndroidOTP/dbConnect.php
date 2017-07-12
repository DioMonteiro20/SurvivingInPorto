<?php
        define('HOST', 'pdb6.awardspace.net');
	define('USER', '2124939_survport');
        define('PASS', 'survporto16');
        define('DB', '2124939_survport');
 
	
//Connecting to database
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');

?>