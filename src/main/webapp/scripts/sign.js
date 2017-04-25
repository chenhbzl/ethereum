var Web3 = require('web3');
var web3 = new Web3();
var provider = new web3.providers.HttpProvider('http://192.168.9.147:8545'); 
web3.setProvider(provider);

var account = web3.eth.accounts[0]; 
var sha3Msg = web3.sha3("abc"); 
var signedData = web3.eth.sign(account,sha3Msg); 
var decode;

$.ajax({
   type: "get",
   url: "../contracts/Decode.json",
   async: false,
   success: function(msg){
     Decode = TruffleContract(msg);
   }
});

Decode.setProvider(provider);
console.log("account="+account);
console.log("sha3Msg="+sha3Msg);
console.log("signedData="+signedData);



function initializeDecode(){
  	Decode.new({from:account,gas:3141592}).then(
  	  function(conf){
  	    	decode = conf;
  	    	$("#confAddress").html(decode.address);
  	  }
  	)
//	decode = web3.eth.contract(Decode.abi).at('0x471bba49c11624f3c2d1715b3048b43e495f3d6b');
}

function GetDecode(){
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
window.onload = function(){
	$("#account").html(account);
	$("#sha3Msg").html(sha3Msg);
	$("#signedData").html(signedData);
	
	initializeDecode();
	
	$("#GetDecode").click(function(){
  	  GetDecode();
  	}
  );
}
