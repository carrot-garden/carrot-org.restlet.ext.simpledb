package org.restlet.ext.simpledb.util;

/** amazon query */

// http://docs.amazonwebservices.com/AmazonSimpleDB/2009-04-15/DeveloperGuide/index.html?QuotingRulesSelect.html

public class AQ {

	private String text = "";

	private AQ() {
	}

	private AQ(String value) {
		this.text = value;
	}

	public static AQ init() {
		return new AQ();
	}

	public static AQ where() {
		return new AQ(" where ");
	}

	public AQ attr(String name) {
		text += " " + name + " ";
		return this;
	}

	public AQ eq(String value) {
		text += " = '" + value + "' ";
		return this;
	}

	public AQ ne(String value) {
		text += " != '" + value + "' ";
		return this;
	}

	public AQ gt(String value) {
		text += " > '" + value + "' ";
		return this;
	}

	public AQ ge(String value) {
		text += " >= '" + value + "' ";
		return this;
	}

	public AQ lt(String value) {
		text += " > '" + value + "' ";
		return this;
	}

	public AQ le(String value) {
		text += " >= '" + value + "' ";
		return this;
	}

	public AQ and() {
		text += " and ";
		return this;
	}

	public AQ or() {
		text += " or ";
		return this;
	}

	public AQ not() {
		text += " not ";
		return this;
	}

	public AQ item() {
		text += " itemName() ";
		return this;
	}

	public AQ like() {
		text += " like ";
		return this;
	}

	public AQ limit(int size) {
		text += " limit " + size + " ";
		return this;
	}

	public String end() {
		return text;
	}

}
