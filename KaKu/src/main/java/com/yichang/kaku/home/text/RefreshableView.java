package com.yichang.kaku.home.text;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yichang.kaku.R;

/**
 * �ɽ�������ˢ�µ��Զ���ؼ���
 * 
 * 
 */
public class RefreshableView extends LinearLayout implements OnTouchListener {

	Context context = getContext();
	/**
	 * ����״̬
	 */
	public static final int STATUS_PULL_TO_REFRESH = 0;

	/**
	 * �ͷ�����ˢ��״̬
	 */
	public static final int STATUS_RELEASE_TO_REFRESH = 1;

	/**
	 * ����ˢ��״̬
	 */
	public static final int STATUS_REFRESHING = 2;

	/**
	 * ˢ����ɻ�δˢ��״̬
	 */
	public static final int STATUS_REFRESH_FINISHED = 3;

	/**
	 * ����ͷ���ع����ٶ�
	 */
	public static final int SCROLL_SPEED = -20;

	/**
	 * һ���ӵĺ���ֵ�������ж��ϴεĸ���ʱ��
	 */
	public static final long ONE_MINUTE = 60 * 1000;

	/**
	 * һСʱ�ĺ���ֵ�������ж��ϴεĸ���ʱ��
	 */
	public static final long ONE_HOUR = 60 * ONE_MINUTE;

	/**
	 * һ��ĺ���ֵ�������ж��ϴεĸ���ʱ��
	 */
	public static final long ONE_DAY = 24 * ONE_HOUR;

	/**
	 * һ�µĺ���ֵ�������ж��ϴεĸ���ʱ��
	 */
	public static final long ONE_MONTH = 30 * ONE_DAY;

	/**
	 * һ��ĺ���ֵ�������ж��ϴεĸ���ʱ��
	 */
	public static final long ONE_YEAR = 12 * ONE_MONTH;

	/**
	 * �����ListView��ͻ��
	 */
	public static boolean longFlag = true;

	/**
	 * �ж��Ƿ�Ϊ�ݸ����н�ȥ��
	 */
	public static boolean noFromEditOrNew = true;

	/**
	 * �жϽ�β�Ƿ�ΪCF����CM
	 */
	public static boolean noCFOrCM = true;

	/**
	 * �ϴθ���ʱ����ַ���������������ΪSharedPreferences�ļ�ֵ
	 */
	private String UPDATED_AT = "updated_at";

	public void setUPDATED_AT(String uPDATED_AT) {
		UPDATED_AT = uPDATED_AT;
	}

	/**
	 * ����ˢ�µĻص��ӿ�
	 */
	private PullToRefreshListener mListener;

	/**
	 * ���ڴ洢�ϴθ���ʱ��
	 */
	private SharedPreferences preferences;

	/**
	 * ����ͷ��View
	 */
	private View header;

	/**
	 * ��Ҫȥ����ˢ�µ�ListView
	 */
	private ScrollView scrollView;

	/**
	 * ˢ��ʱ��ʾ�Ľ�����
	 */

	/**
	 * ָʾ�������ͷŵļ�ͷ
	 */

	/**
	 * ָʾ�������ͷŵ���������
	 */

	/**
	 * �ϴθ���ʱ�����������
	 */

	/**
	 * ����ͷ�Ĳ��ֲ���
	 */
	private MarginLayoutParams headerLayoutParams;

	/**
	 * �ϴθ���ʱ��ĺ���ֵ
	 */
	private long lastUpdateTime;

	/**
	 * Ϊ�˷�ֹ��ͬ���������ˢ�����ϴθ���ʱ���ϻ����г�ͻ��ʹ��id��������
	 */
	private int mId = -1;

	/**
	 * ����ͷ�ĸ߶�
	 */
	private int hideHeaderHeight;

	/**
	 * ��ǰ����ʲô״̬����ѡֵ��STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
	 * STATUS_REFRESHING �� STATUS_REFRESH_FINISHED
	 */
	private int currentStatus = STATUS_REFRESH_FINISHED;;

	/**
	 * ��¼��һ�ε�״̬��ʲô����������ظ�����
	 */
	private int lastStatus = currentStatus;

	/**
	 * ��ָ����ʱ����Ļ������
	 */
	private float yDown;

	/**
	 * �ڱ��ж�Ϊ����֮ǰ�û���ָ�����ƶ������ֵ��
	 */
	private int touchSlop;

	/**
	 * �Ƿ��Ѽ��ع�һ��layout������onLayout�еĳ�ʼ��ֻ�����һ��
	 */
	private boolean loadOnce;

	/**
	 * ��ǰ�Ƿ����������ֻ��ListView������ͷ��ʱ�����������
	 */
	private boolean ableToPull;

	/**
	 * ����ˢ�¿ؼ��Ĺ��캯������������ʱ��̬���һ������ͷ�Ĳ��֡�
	 * 
	 * @param context
	 * @param attrs
	 */
	public RefreshableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh,
				null, true);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		refreshUpdatedAtValue();
		setOrientation(VERTICAL);
		addView(header, 0);
	}

	/**
	 * ����һЩ�ؼ��Եĳ�ʼ�����������磺������ͷ����ƫ�ƽ������أ���ListViewע��touch�¼���
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			hideHeaderHeight = -header.getHeight();
			headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
			headerLayoutParams.topMargin = hideHeaderHeight;
			scrollView = (ScrollView) getChildAt(1);
			scrollView.setOnTouchListener(this);
			loadOnce = true;
		}
	}

	/**
	 * ��ListView������ʱ���ã����д����˸�������ˢ�µľ����߼���
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (noFromEditOrNew) {

			if (noCFOrCM) {
				setIsAbleToPull(event);
				if (ableToPull) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						yDown = event.getRawY();
						break;
					case MotionEvent.ACTION_MOVE:
						float yMove = event.getRawY();
						int distance = (int) (yMove - yDown);
						if (distance > 360) {
							distance=360;
						}
						//Toast.makeText(context, distance+"", Toast.LENGTH_SHORT).show();
						// �����ָ���»�״̬����������ͷ����ȫ���صģ������������¼�
						if (distance > 10) {
							longFlag = false;
						}
						if (distance <= 0
								&& headerLayoutParams.topMargin <= hideHeaderHeight) {
							return false;
						}
						if (distance < touchSlop) {
							return false;
						}
						if (currentStatus != STATUS_REFRESHING) {
							if (headerLayoutParams.topMargin > 0) {
								currentStatus = STATUS_RELEASE_TO_REFRESH;
							} else {
								currentStatus = STATUS_PULL_TO_REFRESH;
							}
							// ͨ��ƫ������ͷ��topMarginֵ����ʵ������Ч��
							headerLayoutParams.topMargin = (distance / 2)
									+ hideHeaderHeight;
							header.setLayoutParams(headerLayoutParams);
						}
						break;
					case MotionEvent.ACTION_UP:
						if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
							// ����ʱ������ͷ�����ˢ��״̬����ȥ��������ˢ�µ�����
							new RefreshingTask().execute();
						} else if (currentStatus == STATUS_PULL_TO_REFRESH) {
							// ����ʱ���������״̬����ȥ������������ͷ������
							new HideHeaderTask().execute();
						} else {
							longFlag = true;
						}
						break;
					default:
						break;
					}
					// ʱ�̼ǵø�������ͷ�е���Ϣ
					if (currentStatus == STATUS_PULL_TO_REFRESH
							|| currentStatus == STATUS_RELEASE_TO_REFRESH) {
						// ��ǰ�������������ͷ�״̬��Ҫ��ListViewʧȥ���㣬���򱻵������һ���һֱ����ѡ��״̬
						scrollView.setPressed(false);
						scrollView.setFocusable(false);
						scrollView.setFocusableInTouchMode(true);
						lastStatus = currentStatus;
						// ��ǰ�������������ͷ�״̬��ͨ������true���ε�ListView�Ĺ����¼�
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * ������ˢ�¿ؼ�ע��һ����������
	 * 
	 * @param listener
	 *            ��������ʵ�֡�
	 * @param id
	 *            Ϊ�˷�ֹ��ͬ���������ˢ�����ϴθ���ʱ���ϻ����г�ͻ�� �벻ͬ������ע������ˢ�¼�����ʱһ��Ҫ���벻ͬ��id��
	 */
	public void setOnRefreshListener(PullToRefreshListener listener, int id) {
		mListener = listener;
		mId = id;
	}

	/**
	 * �����е�ˢ���߼���ɺ󣬼�¼����һ�£��������ListView��һֱ��������ˢ��״̬��
	 */
	public void finishRefreshing() {
		currentStatus = STATUS_REFRESH_FINISHED;
		preferences.edit()
				.putLong(UPDATED_AT + mId, System.currentTimeMillis()).commit();
		new HideHeaderTask().execute();
	}

	/**
	 * ���ݵ�ǰListView�Ĺ���״̬���趨 {@link #ableToPull}
	 * ��ֵ��ÿ�ζ���Ҫ��onTouch�е�һ��ִ�У����������жϳ���ǰӦ���ǹ���ListView������Ӧ�ý���������
	 * 
	 * @param event
	 */
	private void setIsAbleToPull(MotionEvent event) {
		View firstChild = scrollView.getChildAt(0);
		if (firstChild != null) {
			int firstVisiblePos = scrollView.getScrollY();
			if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
				if (!ableToPull) {
					yDown = event.getRawY();
				}
				// ����׸�Ԫ�ص��ϱ�Ե�����븸����ֵΪ0����˵��ListView���������������ʱӦ����������ˢ��
				ableToPull = true;
			} else {
				if (headerLayoutParams.topMargin != hideHeaderHeight) {
					headerLayoutParams.topMargin = hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
				ableToPull = false;
			}
		} else {
			// ���ListView��û��Ԫ�أ�ҲӦ����������ˢ��
			ableToPull = true;
		}
	}

	/**
	 * ��������ͷ�е���Ϣ��
	 */

	/**
	 * ���ݵ�ǰ��״̬����ת��ͷ��
	 */

	/**
	 * ˢ������ͷ���ϴθ���ʱ�������������
	 */
	private void refreshUpdatedAtValue() {
		lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
		long currentTime = System.currentTimeMillis();
		long timePassed = currentTime - lastUpdateTime;
		long timeIntoFormat;
		String updateAtValue;
		if (lastUpdateTime == -1) {
			updateAtValue = "��δ���¹�";
		} else if (timePassed < 0) {
			updateAtValue = "ʱ��������";
		} else if (timePassed < ONE_MINUTE) {
			updateAtValue = "�ոո���";
		} else if (timePassed < ONE_HOUR) {
			timeIntoFormat = timePassed / ONE_MINUTE;
			String value = timeIntoFormat + "����";
			updateAtValue = String.format("�ϴθ�����%1$sǰ", value);
		} else if (timePassed < ONE_DAY) {
			timeIntoFormat = timePassed / ONE_HOUR;
			String value = timeIntoFormat + "Сʱ";
			updateAtValue = String.format("�ϴθ�����%1$sǰ", value);
		} else if (timePassed < ONE_MONTH) {
			timeIntoFormat = timePassed / ONE_DAY;
			String value = timeIntoFormat + "��";
			updateAtValue = String.format("�ϴθ�����%1$sǰ", value);
		} else if (timePassed < ONE_YEAR) {
			timeIntoFormat = timePassed / ONE_MONTH;
			String value = timeIntoFormat + "����";
			updateAtValue = String.format("�ϴθ�����%1$sǰ", value);
		} else {
			timeIntoFormat = timePassed / ONE_YEAR;
			String value = timeIntoFormat + "��";
			updateAtValue = String.format("�ϴθ�����%1$sǰ", value);
		}
	}

	/**
	 * ����ˢ�µ������ڴ������л�ȥ�ص�ע�����������ˢ�¼�������
	 * 
	 * @author
	 */
	class RefreshingTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= 0) {
					topMargin = 0;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			currentStatus = STATUS_REFRESHING;
			publishProgress(0);
			if (mListener != null) {
				mListener.onRefresh();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			headerLayoutParams.topMargin = topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}

	}

	/**
	 * ��������ͷ�����񣬵�δ��������ˢ�»�����ˢ����ɺ󣬴����񽫻�ʹ����ͷ�������ء�
	 * 
	 * @author
	 */
	class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= hideHeaderHeight) {
					topMargin = hideHeaderHeight;
					break;
				}

				publishProgress(topMargin);
				sleep(10);
			}
			return topMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			headerLayoutParams.topMargin = topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}

		@Override
		protected void onPostExecute(Integer topMargin) {
			longFlag = true;
			headerLayoutParams.topMargin = topMargin;
			header.setLayoutParams(headerLayoutParams);
			currentStatus = STATUS_REFRESH_FINISHED;
		}
	}

	/**
	 * ʹ��ǰ�߳�˯��ָ���ĺ�������
	 * 
	 * @param time
	 *            ָ����ǰ�߳�˯�߶�ã��Ժ���Ϊ��λ
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ˢ�µļ�������ʹ������ˢ�µĵط�Ӧ��ע��˼���������ȡˢ�»ص���
	 * 
	 * @author
	 */
	public interface PullToRefreshListener {

		/**
		 * ˢ��ʱ��ȥ�ص��˷������ڷ����ڱ�д�����ˢ���߼���ע��˷����������߳��е��õģ� ����Բ������߳������к�ʱ������
		 */
		void onRefresh();

	}

}
