<html>
	<head>
		<title>Connect to BonCard</title>
	</head>
	<body>
		<?
			// ������� �������� ��� ������� ����� � ������ �� ����� 		
			if(isset($_REQUEST["edit_password"])&&isset($_REQUEST["edit_login"])){
// #region ������� ������ �� ������ � ��������� ����������� ������������
		$userLogin=$_REQUEST["edit_login"];
		$userPassword=$_REQUEST["edit_password"];
		
		$wsdl = "http://localhost:8080/TempXFire/services/register_user?wsdl";
		$client = new SoapClient($wsdl);
		print_r($client);

		// $wrapper->team->name
		$userData->user =new SoapVar($userLogin, XSD_STRING);
		$userData->password =new SoapVar($userPassword, XSD_STRING);

		// call the reverseString method
		// $response = $client->__soapCall("reverseString", array($wrapper) );
		$response = $client->__soapCall("registerUser", array($userData) );
		// debug("response was recieved");
		print_r($response);
	
				?>
		<div align="center">
			<table border="1">
				<tr>
					<td>Login:</td>
					<td><?echo $_REQUEST["edit_login"]?></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><?echo $_REQUEST["edit_password"]?></td>
				</tr>
			</table>
		</div>
				<?
// #endregion ������� ������ �� ������ � ��������� ����������� ������������
			}else{
// #region ���� ������ � ������ 
				?>
		<form method="POST">
			<table>
				<tr>
					<td>Login</td>
					<td>
						<input type="text" value="user" name="edit_login" />
					</td>
				</tr>
				<tr>
					<td>Password</td>
					<td>
						<input type="text" value="user_password" name="edit_password" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="OK" style="width:100%;" />
					</td>
				</tr>
			</table>
		</form>
				<?
// #endregion ���� ������ � ������ 
			}
		?>
	</body>
</html>