package com.example.demo.entity;

import lombok.Data;

@Data
public class FontFlagText {

	/**
	 * 默认0 - 英文；1 - 中文
	 */
	private int font_flag;

	/**
	 * 角色名
	 */
	private String text;

}