<?php
require "conn.php";

$row_ID = $_POST['row_ID'];

$mysql_qry = "DELETE FROM photos WHERE row_id = '$row_ID'";
$upload_path = "uploaded_images/$row_ID.jpg";

if($conn->query($mysql_qry) === TRUE){
	@unlink($upload_path);
	echo "Delete image completed!";
}
else{
	echo "Delete image failed!";
}


$conn->close();
?>