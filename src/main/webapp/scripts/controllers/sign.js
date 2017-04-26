angular.module('ethExplorer')
    .controller('signCtrl', function ($rootScope, $scope, $location, $routeParams, $q) {
    	var account = "";
    	var sha3Msg = "";
    	var signedData = "";
    	var decode;
        $scope.init=function(){

        	account = web3.eth.accounts[0]; 
        	sha3Msg = web3.sha3("abc"); 
        	signedData = web3.eth.sign(account,sha3Msg); 
        	
        	$("#account").html(account);
        	$("#sha3Msg").html(sha3Msg);
        	$("#signedData").html(signedData);
        	
        	var decode;

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
        	var contract_address="0xbbcd44a1ab61c4e2bdcc34e2cfc08ad1337b3931";//合约地址
        	Decode.at(contract_address).then(function(instance) {
        		decode = instance;
        		$("#confAddress").html(decode.address);
 	    	    console.log(decode);
        	})
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
        }

    });
