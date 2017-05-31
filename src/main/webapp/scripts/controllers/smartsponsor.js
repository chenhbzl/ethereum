angular.module('ethExplorer')
    .controller('smartsponsorCtrl', function ($rootScope, $scope, $location, $routeParams, $q) {
    	var accounts, account,account1,account2;
    	var mySmartSponsorInstance;
    	var SmartSponsor;
    	
        $scope.init=function(){
        	//加载SmartSponsor.json文件内容
        	$.ajax({
        	   type: "get",
        	   url: "../../contracts/SmartSponsor.json",
        	   async: false,
        	   success: function(msg){
        		   SmartSponsor = TruffleContract(msg);
        	   }
        	});
        	SmartSponsor.setProvider(provider);
        	
        	web3.eth.getAccounts(function(err,accs){
        	  	if(err != null){
        	  		alert("There was an error fetching your accounts.");
        	  		return;	
        	  	}
        	  	console.log(accs);
        	  	if(accs.length == 0){
        	  		alert("Couldn't get any accounts! Make sure your Ethereum client is configured correctly.");
        	  		return;
        	  	}
        	  	accounts = accs;
        	    account = accounts[0];
        	    account1 = accounts[1];
        	    account2 = accounts[2];
        	    initializeSmartSponsor();
        	  });	
        	  
        	  //修改默认票数
	      	  $scope.setBenefactor = function(){
	      		  var val = $("#benefactor").val();
	      		  mySmartSponsorInstance.setBenefactor(val,{from:accounts[0]}).then(
				  	  function(){
				  	    return 	mySmartSponsorInstance.benefactor.call();
				  	  }
				  ).then(
				  	  function(benefactor){
				  	  	var msgResult;
				  	    if(benefactor == val){
				  	        msgResult= "Change successful";
				  	    }else{
				  	    	msgResult = "Change failed";
				  	    }
				  	    $("#changeBenefactor").html(msgResult);
				  	  }
				  	);
	      	  };
        	  //募捐
        	  $scope.pledge = function(){
        		  var val = $("#ticketPrice").val();
          	  	  var buyerAddress = $("#buyerAddress").val();
          		  mySmartSponsorInstance.pledge(0,{from:buyerAddress,value:web3.toWei(val), gas:2000000}).then(
          		    function(){
          		    return 	mySmartSponsorInstance.numPledges.call();
          		  }).then(
          		    function(num){
          		      $("#numPledges").html(num.toNumber());
          		      mySmartSponsorInstance.getPot().then(
              			function(balance){
              				$("#balance").html(balance.toNumber());
              			}
    	              );
          		    }
          		  );
        	  };
        	  
        	  //转给受益人
        	  $scope.drawdown= function(){
        		  mySmartSponsorInstance.drawdown({from:accounts[0]}).then(
        			function(flag){
        				var msgResult;
        				if(flag){
				  	        msgResult= "转给受益人成功";
				  	    }else{
				  	    	msgResult = "转给受益人失败";
				  	    }
				  	    $("#drawdownResult").html(msgResult);
        			}
  	              );
        	  };
        	  
        	  //退还给募捐者
        	  $scope.refund= function(){
        		  mySmartSponsorInstance.refund({from:accounts[0]}).then(
        			function(flag){
        				var msgResult;
        				if(flag){
				  	        msgResult= "退还给募捐者成功";
				  	    }else{
				  	    	msgResult = "退还给募捐者失败";
				  	    }
				  	    $("#refundResult").html(msgResult);
        			}
  	              );
        	  };
        	  
        	  //查询受益者金额
        	  $scope.benefactorBalance= function(){
        		  mySmartSponsorInstance.getBalance().then(
        			function(benefactorBalance){
				  	    $("#benefactorBalanceResult").html(benefactorBalance.toNumber());
        			}
  	              );
        	  };
        };

        $scope.init();
        
        //初始化
        function initializeSmartSponsor(){
        	var contract_address="0xcffac5b2ef3bfc10fe8c584ad4ec7c8626fa589b";//合约地址
        	SmartSponsor.at(contract_address).then(function(instance) {
        		mySmartSponsorInstance = instance;
        		$("#confAddress").html(mySmartSponsorInstance.address);//部署成功后，智能合约在区块链的地址
     	    	checkValues();
        	})
        };
        //检查初始化的数据
        function checkValues(){
          	mySmartSponsorInstance.numPledges.call().then(
          	  function(numPledges){
          	    $("#numPledges").html(numPledges.toNumber());//获取募捐数量
          	    return 	mySmartSponsorInstance.owner.call();
          	  }).then(
        	    function(owner){
          	    	$("input#confOrganizer").val(owner);//智能合约在组织者
          	    	return 	mySmartSponsorInstance.benefactor.call();
          	    }
          	  ).then(
          	    function(benefactor){
          	    	if("0x0000000000000000000000000000000000000000"!=benefactor){
          	    		$("input#benefactor").val(benefactor);//募捐受益者
          	    	}
          	    }
          	  );
          	mySmartSponsorInstance.getPot().then(
      			function(balance){//募捐金额
      				$("#balance").html(balance.toNumber());
      			}
          	);
        };
    });

