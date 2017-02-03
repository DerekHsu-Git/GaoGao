package com.arbo.lib.base.mvp;



public interface BasePresenter {
	/**
	 * 初始化时调用
	 */
	void start();

	/**
	 * 当activity或者fragment退出时调用
	 */
	void stop();


	void unsubcrible();

	void resume();

}
