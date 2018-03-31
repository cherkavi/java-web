<html>
	<body>
		<b> index.php </b>
		<?php
// Curl module must present into php.ini file 
// add library
include_once 'HessianClient.php';

class Proxy_TestWS{
	private $proxy;

	public function __construct($pUrl){
		$this->proxy = new HessianClient($pUrl);
	}

	public function getDate(){
		return $this->proxy->getDate();
	}

	public function getDateString(){
		return $this->proxy->getDateString();
	}

	public function getBean(){
		return $this->proxy->getBean();
	}

	public function getListOfBeans(){
		return $this->proxy->getListOfBeans();
	}

	public function getArrayOfBeans(){
		return $this->proxy->getArrayOfBeans();
	}

};

// path to service
$testurl = 'http://localhost:8080/WebServiceHessian/TestWS';

// proxy object
$proxy = new Proxy_TestWS($testurl);
try{
    print("getDate:");
    print_r($proxy->getDate()); 
    print("<br />");
    print("<br />");

    print("getDate:");
    print_r($proxy->getDateString()); 
    print("<br />");
    print("<br />");

    print("getBean:");
    print_r($proxy->getBean()); 
    print("<br />");
    print("<br />");

    print("getListOfBean:");
    print_r($proxy->getListOfBeans()); 
    print("<br />");
    print("<br />");

    print("getArrayOfBean:");
    print_r($proxy->getArrayOfBeans()); 
    print("<br />");
    print("<br />");

} catch (Exception $ex){
    echo $ex;
}


		?>
	</body>
</html>