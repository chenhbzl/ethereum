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
          	Decode.new({from:web3.eth.accounts[0],gas:3141592}).then(
          	  function(conf){
          	    	decode = conf;
          	    	$("#confAddress").html(decode.address);
          	    	console.log(decode);
          	  }
          	)
//          	console.log(web3.eth);
//        	decode = web3.eth.contract(Decode.abi).at('0x1a8667f3d04862d58e618966b843c6cdadd56d88');
//          	console.log(decode);
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

