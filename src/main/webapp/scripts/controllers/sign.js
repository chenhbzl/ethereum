angular.module('ethExplorer')
    .controller('signCtrl', function ($rootScope, $scope, $location, $routeParams, $q,$http) {
    	var account = "";
    	var sha3Msg = "";
    	var signedData = "";
    	var decode = "";
        $scope.init=function(){

        	account = web3.eth.accounts[0]; 
        	sha3Msg = web3.sha3("abc"); 
        	//sha3Msg = keccak256("\x19Ethereum Signed Message:\n" + len("abc") + "abc");
        	signedData = web3.eth.sign(account,sha3Msg); 
        	
        	$("#account").html(account);
        	$("#sha3Msg").html(sha3Msg);
        	$("#signedData").html(signedData);
        	

        	$.ajax({
        	   type: "get",
        	   url: "../../contracts/Decode.json",
        	   async: false,
        	   success: function(msg){
        	     Decode = TruffleContract(msg);
        	   }
        	});
        	Decode.setProvider(provider);
        	initializeDecode();
        };

        $scope.init();
        
        function initializeDecode(){
        	var contract_address="0xadddcc7e2967f177bdff43b83d79b16e32da1d51";//合约地址
        	Decode.at(contract_address).then(function(instance) {
        		decode = instance;
        		$("#confAddress").html(decode.address);
 	    	    console.log(decode);
        	});
        };
        
        
    	
        $scope.GetDecode = function(){
          	decode.decode(sha3Msg,signedData,{from:account}).then(
          	  function(){
          	  	return decode.decodeAddress.call();
          	  }
          	).then(
          	  function(decodeAddress){
          	    $("#decodeAddress").val(decodeAddress);
          	  }
          	);
        };
        
        $scope.GetSha3 = function(){
        	var message = $("#message").val();
        	$http.post('http://127.0.0.1:8080/ecrecover/getSha3?message='+message).success(function(data){
        		$("#sha3Result").html(data.web3Sha3);
            });
        };
        
        $scope.GetSignedData = function(){
        	var address = $("#address").val();
        	var sha3Result = $("#sha3Result").html();
        	$http.post('http://127.0.0.1:8080/ecrecover/getSignedData?address='+address+"&sha3Result="+sha3Result).success(function(data){
        		$("#signedDataResult").html(data.signature);
            });
        };
        
        $scope.GetResult = function(){
        	var sha3Result = $("#sha3Result").html();
        	var signedDataResult = $("#signedDataResult").html();
        	var address = $("#address").val();
        	$http.post('http://127.0.0.1:8080/ecrecover/getResult?sha3Result='+sha3Result+"&signedDataResult="+signedDataResult).success(function(data){
        		$("#addressResult").html(data.personalEcRecover);
        		if(data.personalEcRecover==address){
        			$("#result").html("解密成功");
        		}else{
        			$("#result").html("解密失败");
        		};
            });
        };
    });

