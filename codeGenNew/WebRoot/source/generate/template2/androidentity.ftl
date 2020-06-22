package ${packageName};

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.whty.xmytktsm.cardbag.consts.NetWorkConst;
import com.whty.xmytktsm.cardbag.consts.OperateTypeConst;
import com.whty.xmytktsm.cardbag.consts.RegisterActivityConst;
import com.whty.xmytktsm.commons.Constants;
import com.whty.xmytktsm.commons.LogUtil;
import com.whty.xmytktsm.http.HttpsAsyncTask;
import com.whty.xmytktsm.http.TLSReqListener;


public class ${className}ActivityManager implements TLSReqListener {

	private final static String TAG = "${className}ActivityManager";
	private Context context;
	private Handler handler;

	public ${className}ActivityManager(Context context, Handler handler) {
		super();
		this.context = context;
		this.handler = handler;
	}
	
	public void ${className?uncap_first}(<#list params as param>String ${param},</#list>) {

		JSONObject job = new JSONObject();
		String params = "";
		try {
			<#list params as param>
			job.put("${param}", ${param});
			</#list>
			params = job.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new HttpsAsyncTask(this, OperateTypeConst.TYPE_REGIST).execute(params);

	}

	@Override
	public void onReqSucceeded(String response, String operType) {
		if(operType.equals(OperateTypeConst.TYPE_${className?upper_case})){
			try {
				JSONObject jsonObj = new JSONObject(response);
				String result = jsonObj.getString("result");
				String description = jsonObj.getString("description");
				if(NetWorkConst.RESULT_SUCCESS.equals(result)){
					handler.sendEmptyMessage(${className}ActivityConst.MSG_REGISTER_SUCCESS);
				}else{
					Message ${className?lower_case}FailMsg = new Message();
					regFailMsg.obj = description;
					regFailMsg.what = ${className}ActivityConst.MSG_${className?upper_case}_FAIL;
					handler.sendMessage(${className?lower_case}FailMsg);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Message ${className?lower_case}FailMsg = new Message();
				${className?lower_case}FailMsg.obj = e.getMessage();
				${className?lower_case}FailMsg.what = ${className}ActivityConst.MSG_REGISTER_FAIL;
				handler.sendMessage(${className?lower_case}FailMsg);
			}
		
		}
	}

	@Override
	public void onReqStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReqProcessing(int progress, String operType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReqCanceled(String operType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReqError(String errorMsg, String operType) {
		if(OperateTypeConst.TYPE_REGIST.equals(operType)){
			handler.sendEmptyMessage(Constants.MSG_NETWORK_ERROR);
		}

	}
}
