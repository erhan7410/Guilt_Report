<?php
 
$db_name = "report_list";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);
if($conn)
{
	$row_id = $_POST["row_id"];
	$image = $_POST["image"];
	$sql = "insert into photos(row_id,photo) values ('$row_id','$row_id')";
	$upload_path = "uploaded_images/$row_id.jpg";
	
	if(mysqli_query($conn,$sql))
	{	
		file_put_contents($upload_path,base64_decode($image));
		echo json_encode(array('response'=>'Image Uploaded Successfully'));
	}
	else
	{
		echo json_encode(array('response'=>'Image Upload Failed! $sql'));
	}
}
else
{
	echo json_encode(array('response'=>'Image Upload Failed'));
}

mysqli_close($conn);
?>