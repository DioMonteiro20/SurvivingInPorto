<?php

include_once("init.php");

$sqlString = "select * from habitacoes order by id ";
$rs=mysql_query($sqlString);

if($rs)
{
	while($objRs = mysql_fetch_assoc($rs))
	{
		$output[] = $objRs;
	}
	
	echo json_encode($output);
	
}

mysql_close();

?>