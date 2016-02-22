package com.yichang.kaku.view;

/**
 * ���ֶ����Զ���
 * 
 * @author zengtao 2015��7��17�� ����11:48:27 
 *
 */
public interface RiseNumberBase {
	public void start();

	public RiseNumberTextView withNumber(float number);

	public RiseNumberTextView withNumber(float number, boolean flag);

	public RiseNumberTextView withNumber(int number);

	public RiseNumberTextView setDuration(long duration);

	public void setOnEnd(RiseNumberTextView.EndListener callback);
}
