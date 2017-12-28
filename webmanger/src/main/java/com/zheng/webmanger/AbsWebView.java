package com.zheng.webmanger;

/**
 * Created by 18513 on 2017/12/28.
 */

public abstract class AbsWebView {

    /**
     * 页面开始加载时开始启用，可以设定一个loading的页面，告诉用户程序在等待网络响应。
     *
     * @return
     */
    public void onPageStarted() {
    }

    /**
     * 页面加载结束时调用，可以关闭loading 条，切换程序动作。
     */
    public void onPageFinished() {
    }

    /**
     * 加载页面的服务器出现错误时调用;用于出错时展示给用户看的提示页面
     */
    public void onReceivedError() {
    }

    /**
     * 获取网页的title
     *
     * @param title 传递网页的title
     * @return 返回网页的title
     */
    public String onReceivedTitle(String title) {
        return title;
    }

    /**
     * 加载网页的进度
     *
     * @param newProgress 传递网页的进度
     * @return 返回网页的进度
     */
    public abstract void onProgressChanged(int newProgress);

}
