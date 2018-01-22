<?php
require "conn.php";
$user_name = $_POST["username"];
$user_pass = $_POST["password"];
$mysql_qry = "select * from userinfo where username like '$user_name' and password like '$user_pass'";
$result = mysqli_query($conn ,$mysql_qry);
if(mysqli_num_rows($result) > 0){
	$row = mysqli_fetch_assoc($result);
	$name = $row["username"];
	echo "Welcome $name!";
}
else{
	echo "Login not success!";
}
$conn->close();
?>