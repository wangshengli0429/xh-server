package com.xinghuo.ams.common.idcard.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class IDValidator {
	private Map<String, String> gb2260 = GB2260.getInstance();
	private static Map<String, IDCodeInfo> cache = new HashMap<String, IDCodeInfo>();

	/**
	 * 身份证是否有效
	 * 
	 * @param id
	 *            {@link String}
	 * @return {@link Boolean}
	 */
	public boolean isValid(String id) {
		IDCodeInfo code = IDValidatorUtils.checkArg(id);
		if (code == null) {
			return false;
		}
		// 查询cache
		if (cache.containsKey(id)) {
			return cache.get(id).isValid();
		}

		IDValidatorUtils.parseCode(code);

		if (!(IDValidatorUtils.checkAddr(code.getAddrCode())
				&& IDValidatorUtils.checkBirth(code.getBirthCode()) && IDValidatorUtils
					.checkOrder(code.getOrder()))) {
			code.setValid(false);
			cache.put(id, code);
			return false;
		}

		// 15位不含校验码，到此已结束
		if (code.getType() == 15) {
			code.setValid(true);
			cache.put(id, code);
			return true;
		}

		/* 校验位部分 */
		// 位置加权
		int[] posWeight = new int[17];
		for (int i = 18; i > 1; i--) {
			int wei = IDValidatorUtils.weight(i);
			posWeight[18 - i] = wei;
		}

		// 累加body部分与位置加权的积
		int bodySum = 0;
		char[] bodyArr = code.getBody().toCharArray();
		for (int j = 0; j < bodyArr.length; j++) {
			bodySum += (Integer.valueOf(new Character(bodyArr[j]).toString(), 10) * posWeight[j]);
		}

		// 得出校验码
		int tempCheckBit = 12 - (bodySum % 11);
		String checkBit = String.valueOf(tempCheckBit);
		if (tempCheckBit == 10) {
			checkBit = "X";
		} else if (tempCheckBit > 10) {
			checkBit = String.valueOf(tempCheckBit % 11);
		}

		// 检查校验码
		if (!checkBit.equals(code.getCheckBit())) {
			code.setValid(false);
			cache.put(id, code);
			return false;
		} else {
			code.setValid(true);
			cache.put(id, code);
			return true;
		}
	}

	/**
	 * 分析详细信息
	 * 
	 * @param id
	 *            {@link String} 身份证号
	 * @return {@link IDCodeInfo}
	 */
	public IDCodeInfo getInfo(String id) {
		// 号码必须有效
		if (this.isValid(id) == false) {
			return null;
		}
		// 复用此部分
		IDCodeInfo code = IDValidatorUtils.checkArg(id);

		// 查询cache
		// 到此时通过isValid已经有了cache记录
		if (cache.containsKey(id)) {
			return cache.get(id);
		}

		IDValidatorUtils.parseCode(code);

		// 记录cache
		cache.put(id, code);

		return code;
	}

	/**
	 * 仿造一个号
	 * 
	 * @param isFifteen
	 *            是否生成15位数
	 * @return
	 */
	public String makeID(boolean isFifteen) {
		// 地址码
		String addr = "";
		if (gb2260 != null) {
			int loopCnt = 0;
			while (StringUtils.isBlank(addr)) {
				// 防止死循环
				if (loopCnt > 10) {
					addr = "110101";
					break;
				}
				String prov = IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(50, 1)),
						2, '0', false);
				String city = IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(60, 1)),
						2, '0', false);
				String area = IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(20, 1)),
						2, '0', false);
				String addrTest = prov + city + area;
				if (gb2260.containsKey(addrTest)) {
					addr = addrTest;
					break;
				}
				loopCnt++;
			}
		} else {
			addr = "110101";
		}

		// 出生年
		String year = IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(99, 50)), 2, '0',
				false);
		String month = IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(12, 1)), 2, '0',
				false);
		String day = IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(28, 1)), 2, '0',
				false);
		if (isFifteen) {
			return addr
					+ year
					+ month
					+ day
					+ IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(999, 1)), 3, '1',
							false);
		}

		year = "19" + year;
		String body = addr
				+ year
				+ month
				+ day
				+ IDValidatorUtils.strPad(String.valueOf(IDValidatorUtils.rand(999, 1)), 3, '1',
						false);

		// 位置加权
		int[] posWeight = new int[17];
		for (int i = 18; i > 1; i--) {
			int wei = IDValidatorUtils.weight(i);
			posWeight[18 - i] = wei;
		}

		// 累加body部分与位置加权的积
		int bodySum = 0;
		char[] bodyArr = body.toCharArray();
		for (int j = 0; j < bodyArr.length; j++) {
			bodySum += (Integer.valueOf(new Character(bodyArr[j]).toString(), 10) * posWeight[j]);
		}

		// 得出校验码
		int tempCheckBit = 12 - (bodySum % 11);
		String checkBit = String.valueOf(tempCheckBit);
		if (tempCheckBit == 10) {
			checkBit = "X";
		} else if (tempCheckBit > 10) {
			checkBit = String.valueOf(tempCheckBit % 11);
		}

		return (body + checkBit);
	}
}
