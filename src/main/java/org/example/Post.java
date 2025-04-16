package org.example;


public class Post {
	private String Rate;
	private String Ccy;

	public Post() {
	}

	public Post(String ccy, String rate) {
		Ccy = ccy;
		Rate = rate;
	}

	public String getCcy() {
		return Ccy;
	}

	public void setCcy(String ccy) {
		Ccy = ccy;
	}

	public String getRate() {
		return Rate;
	}

	public void setRate(String rate) {
		Rate = rate;
	}
}
