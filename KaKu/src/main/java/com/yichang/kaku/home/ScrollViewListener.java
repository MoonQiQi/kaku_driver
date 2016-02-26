package com.yichang.kaku.home;

import com.yichang.kaku.home.text.ObservableScrollView;

public interface ScrollViewListener {
	
	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
	
}
