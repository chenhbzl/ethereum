package com.blackbrother.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blackbrother.model.EthereumAtranction;
import com.blackbrother.service.EthereumServe;
import com.blackbrother.util.BaseController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class EtfController extends BaseController{
	@Autowired
	private EthereumServe ethereumServe;

	@RequestMapping(value = "etf/getTransactions", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTransactions(HttpServletResponse response) {
		List<EthereumAtranction> list = ethereumServe.getTransactions();
		// 获取全部存在交易账号并去重
		String[] addressArr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			addressArr[i] = list.get(i).getAddress();
		}
		List<String> addresslist = new ArrayList<String>();
		for (int i = 0; i < addressArr.length; i++) {
			if (!addresslist.contains(addressArr[i])) {
				addresslist.add(addressArr[i]);
			}
		}
		// 将每个账号下对应的交易信息组装
		List<Object> objlist = new ArrayList<Object>();
		for (String str : addresslist) {
			EthereumAtranction atb = new EthereumAtranction();
			atb.setAddress(str);
			StringBuffer sb = new StringBuffer();
			for (EthereumAtranction etb : list) {
				if (etb.getAddress().equals(str)) {
					sb.append(etb.getTransactionarr() + ",");
				}
			}
			atb.setTransactionList(sb.toString().split(","));
			objlist.add(atb);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("allAddress", objlist);
		return map;
	}

	@RequestMapping(value = "etf/getBlockCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> getBlockCount(HttpServletResponse response) {
		int number = ethereumServe.getBlockNumber();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("blockNumber", number);
		return map;
	}

	@RequestMapping(value = "etf/addTransactionCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addTransactionCount(HttpServletRequest request) {
		String jsonobj = request.getParameter("transactionsstr");
		System.out.println("addTransaction=====" + jsonobj);
		JSONArray jsonArr = JSONArray.fromObject(jsonobj);
		for (int i = 0; i < jsonArr.size(); i++) {
			EthereumAtranction ethereum = new EthereumAtranction();
			JSONObject obj = jsonArr.getJSONObject(i);
			ethereum.setBlockid(obj.getInt("blockId"));
			ethereum.setAddress(obj.getString("address"));
			ethereum.setTransactionarr(obj.getString("transactionArr"));
			int count = ethereumServe.getTransactionCount(ethereum.getTransactionarr());
			if (count == 0) {
				ethereumServe.addTransactionCount(ethereum);
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}

	@RequestMapping(value = "etf/addAddress", method = RequestMethod.POST)
	@ResponseBody
	public void addAddress(HttpServletRequest request) {
		String[] address_data = request.getParameter("address_data").split(",");
		String jsonobj = request.getParameter("transactionsstr");
		System.out.println("addAddress=====" + jsonobj);
		System.out.println("addAddress=====" + address_data.toString());
		if (jsonobj != null || "".equals(jsonobj)) {
			JSONArray jsonArr = JSONArray.fromObject(jsonobj);
			for (int i = 0; i < jsonArr.size(); i++) {
				EthereumAtranction ethereum = new EthereumAtranction();
				JSONObject obj = jsonArr.getJSONObject(i);
				ethereum.setBlockid(obj.getInt("blockId"));
				ethereum.setAddress(obj.getString("address"));
				ethereum.setTransactionarr(obj.getString("transactionArr"));
				int count = ethereumServe.getTransactionCount(ethereum.getTransactionarr());
				if (count == 0) {
					ethereumServe.addTransactionCount(ethereum);
				}
			}
		}
		int number = Integer.parseInt(request.getParameter("blockend"));
		ethereumServe.updateBlockNumber(number);
		savefile(address_data);
	}

	private void savefile(String[] json) {
		for (int i = 0; i < json.length; i++) {
			String addressid = json[i];
			int count = ethereumServe.getAllAddress(addressid);
			if(count==0){
				ethereumServe.addAddress(addressid);
			}
		}
	}
}
