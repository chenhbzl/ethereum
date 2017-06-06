angular.module('ethExplorer')
    .controller('ballotCtrl', function ($rootScope, $scope, $location, $routeParams, $q) {
    	var accounts;
    	var myBallotInstance;
    	var Ballot;
    	
        $scope.init=function(){
        	//加载Ballot.json文件内容
        	$.ajax({
        	   type: "get",
        	   url: "../../contracts/Ballot.json",
        	   async: false,
        	   success: function(msg){
        	     Ballot = TruffleContract(msg);
        	   }
        	});
        	Ballot.setProvider(provider);
        	
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
        	    initializeBallot();
        	  });	
        	  
        	  //修改默认票数
        	  $scope.addProposals = function(){
        		  var val = $("#proposals").val();
        		  //val = web3.fromUtf8(val, 32);
        		  myBallotInstance.setProposal(val,{from:accounts[0]}).then(
				  	  function(result){
				  		  var msgResult;
	          		      if(result){
	          		      	  msgResult = "添加投票项成功";
	          		      }else {
	          				  msgResult = "添加投票项失败";
		  				  }
		  				  $("#proposalsResult").html(msgResult);
				  	  }
				  	);
        	  };
        	  
        	  //添加可投票人员
        	  $scope.giveRightToVote = function(){
        		  var val = $("#voterAddress").val();
        		  myBallotInstance.giveRightToVote(val,{from:accounts[0]}).then(
				  	  function(result){
				  		  var msgResult;
	          		      if(result){
	          		      	  msgResult = "添加投票人员成功";
	          		      }else {
	          				  msgResult = "添加投票人员失败";
		  				  }
		  				  $("#voterAddressResult").html(msgResult);
				  	  }
				  	);
        	  };
        	  
        	  //委托
        	  $scope.delegate = function(){
        		  var coverDelegate = $("#coverDelegate").val();
        		  var delegate = $("#delegate").val();
        		  myBallotInstance.delegate(delegate,{from:coverDelegate}).then(
				  	  function(result){
				  		  var msgResult;
	          		      if(result){
	          		      	  msgResult = "委托成功";
	          		      }else {
	          				  msgResult = "委托失败";
		  				  }
		  				  $("#delegateResult").html(msgResult);
				  	  }
				  	);
        	  };
        	  
        	  //添加可投票人员
        	  $scope.vote = function(){
        		  var proposalAddress = $("#proposalAddress").val();
        		  var proposal = $("#proposal").val();
        		  myBallotInstance.vote(proposal,{from:proposalAddress}).then(
				  	  function(result){
				  		  var msgResult;
	          		      if(result){
	          		      	  msgResult = "投票成功";
	          		      }else {
	          				  msgResult = "投票失败";
		  				  }
		  				  $("#voteResult").html(msgResult);
				  	  }
				  	);
        	  };
        	  
        	  //获取投票信息
        	  $scope.winningProposal = function(){
        		  var proposalAddress = $("#proposalAddress").val();
        		  var proposal = $("#proposal").val();
        		  myBallotInstance.winningProposal().then(
				  	  function(result){
		  				$("#winningProposal").html(result[0]+"");
		  				$("#winningVoteCount").html(result[1]+"");
		  				console.log(web3.toUtf8(result[2]));
		  				$("#name").html(web3.toUtf8(result[2]));
//		  				var str2 = web3.fromUtf8('投票', 32);
//		  				console.log(str2);
//		  				console.log(web3.toUtf8(str2));
				  	  }
				  	);
        	  };
        	  
        	  $("#proposals").focus(function(){
        		  $("#proposals").val("");
                  $("#proposalsResult").html("");
              });
        	  
        	  $("#voterAddress").focus(function(){
        		  $("#voterAddress").val("");
                  $("#voterAddressResult").html("");
              });
        	  
        	  $("#proposalAddress").focus(function(){
        		  $("#proposalAddress").val("");
        		  $("#proposal").val("");
                  $("#voteResult").html("");
              });
        	  
        	  $("#coverDelegate").focus(function(){
        		  $("#coverDelegate").val("");
        		  $("#delegate").val("");
                  $("#delegateResult").html("");
              });
        };

        $scope.init();
        
        //初始化
        function initializeBallot(){
        	var contract_address="0xdd504135312091b6b6779b84a48d8d724fa0ce15";//合约地址
        	Ballot.at(contract_address).then(function(instance) {
        		myBallotInstance = instance;
        		$("#confAddress").html(myBallotInstance.address);//部署成功后，智能合约在区块链的地址
        	})
        };
    });

