<?php
require "conn.php";

if(mysqli_connect_error($conn)){
	echo "Failed to connect to DB".mysqli_connect_error();
}

$query=mysqli_query($conn,"SELECT * FROM reports");

if($query){
	
	while($row=mysqli_fetch_array($query)){
		$flag[]=$row;
	}
	print(json_encode($flag));
}

mysqli_close($conn);
?>