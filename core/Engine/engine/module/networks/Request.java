package engine.module.networks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import utils.factory.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.coder5560.game.enums.Constants;

public class Request {

	private static Request INSTANCE;

	public HttpRequest lastestHttpRequest;

	float timeout = 0;

	private Request() {
	}

	public static Request getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Request();
		}
		return INSTANCE;
	}

	/**
	 * Sử dụng HttpRequest và HttpResponse của Libgdx Method GET
	 */
	private void get(String cmd, String params,
			HttpResponseListener httpResponseListener) {
		// if (UserInfo.getInstance().userLogged()) {
		// params = ParamsBuilder.builder().parseParams(params)
		// .add(ExtParamsKey.USER, UserInfo.getInstance().getUserId())
		// .build();
		// }
		params = params.replace(" ", "%20");
		String serviceUrl = ConnectionConfig.MAIN_URL + "/"
				+ ConnectionConfig.serviceName + "/" + cmd + "?" + params;
		HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.GET);
		httpRequest.setHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=utf-8");
		httpRequest.setUrl(serviceUrl);
		lastestHttpRequest = httpRequest;
		Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
	}

	/**
	 * Sử dụng HttpRequest và HttpResponse của Libgdx Method POST
	 * 
	 * @throws IOException
	 */
	public void post(String cmd, String params,
			HttpResponseListener httpResponseListener) {
		String serviceUrl = ConnectionConfig.MAIN_URL + "/"
				+ ConnectionConfig.serviceName + "/" + cmd;
		HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.POST);
		httpRequest.setUrl(serviceUrl);
		httpRequest.setContent(params);
		httpRequest.setHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=utf-8");
		Log.d("url=" + serviceUrl);
		Log.d("parrams=" + params);
		lastestHttpRequest = httpRequest;
		Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);

	}

	public void getFoodDetail(int id, HttpResponseListener listener) {
		get(CommandRequest.FOOD_DETAIL,
				ParamsBuilder.builder().add(ExtParamsKey.ID, id).build(),
				listener);
	}

	public void requestLike(int id, HttpResponseListener listener) {
		get(CommandRequest.FOOD_LIKE,
				ParamsBuilder.builder().add(ExtParamsKey.ID, id)
						.add(ExtParamsKey.VALUE, 1).build(), listener);
	}

	public void getCategoryList(int categoryId, HttpResponseListener listener) {
		get(CommandRequest.FOOD_GET_CATEGORY,
				ParamsBuilder.builder()
						.add(ExtParamsKey.CATEGORY_ID, categoryId).build(),
				listener);
	}

	public void getAllCategory(HttpResponseListener listener) {
		get(CommandRequest.FOOD_GET_ALL_CATEGORY, ParamsBuilder.builder()
				.build(), listener);
	}

	public void getAllMaterial(HttpResponseListener listener) {
		get(CommandRequest.MATERIAL_GET_ALL, ParamsBuilder.builder().build(),
				listener);
	}

	public void loadConfig() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				String response = DefaultHttpConnection.get(
						ConnectionConfig.CONFIG, "");
				JsonValue params = (new JsonReader()).parse(response);
				if (params.has("server_primary_host")) {
					String host = params.getString("server_primary_host");
					if (!host.startsWith("http")) {
						host = "http://" + host;
					}
					ConnectionConfig.MAIN_URL = host;
				}
				if (params.has("zone")) {
					ConnectionConfig.serviceName = params.getString("zone");
				}
			}
		});
		thread.start();
	}

	public void requestProfile(HttpResponseListener listener) {
		get(CommandRequest.USER_GET_PROFILE, ParamsBuilder.builder().build(),
				listener);
	}

	public void login(String userName, String pass,
			HttpResponseListener listener) {
		post(CommandRequest.LOGIN,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, userName)
						.add(ExtParamsKey.PASSWORD, pass)
						.add(ExtParamsKey.SIGNATURE,
								hash(userName + pass
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}

	public void active(String phone, String activeCode,
			HttpResponseListener listener) {
		post(CommandRequest.ACTIVE,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, phone)
						.add(ExtParamsKey.ACTIVE_CODE, activeCode)
						.add(ExtParamsKey.SIGNATURE,
								hash(phone + activeCode
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}

	public void rejectActive(String phoneReject, String phoneAdmin,
			String passAdmin, HttpResponseListener listener) {
		post(CommandRequest.REJECT_AGENCY_ACTIVE,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, phoneReject)
						.add(ExtParamsKey.ADMIN_NAME, phoneAdmin)
						.add(ExtParamsKey.PASSWORD, passAdmin)
						.add(ExtParamsKey.SIGNATURE,
								hash(phoneReject + phoneAdmin + passAdmin
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}

	public void doneSendingActiveCode(String phoneReject, String phoneAdmin,
			String passAdmin, HttpResponseListener listener) {
		post(CommandRequest.ADMIN_SEND_ACTIVE_CODE,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, phoneReject)
						.add(ExtParamsKey.ADMIN_NAME, phoneAdmin)
						.add(ExtParamsKey.PASSWORD, passAdmin)
						.add(ExtParamsKey.SIGNATURE,
								hash(phoneReject + phoneAdmin + passAdmin
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}

	public void getListAgency(String phone, String pass, int state,
			HttpResponseListener listener) {
		post(CommandRequest.GET_AGENCY_LIST,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, phone)
						.add(ExtParamsKey.PASSWORD, pass)
						.add(ExtParamsKey.STATE, state)
						.add(ExtParamsKey.SIGNATURE,
								hash(phone + pass + ConnectionConfig.CLIENT_KEY))
						.build(), listener);
	}

	public void getActiveCode(String phoneAdmin, String pass, String phone,
			HttpResponseListener listener) {
		post(CommandRequest.GET_ACTIVE_CODE,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.ADMIN_NAME, phoneAdmin)
						.add(ExtParamsKey.PASSWORD, pass)
						.add(ExtParamsKey.AGENCY_NAME, phone)
						.add(ExtParamsKey.SIGNATURE,
								hash(phone + phoneAdmin + pass
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}

	public void register(String phoneNumber, String pass,
			String phoneIntroduce, String fullName, String address,
			String email, HttpResponseListener listener) {
		post(CommandRequest.REGISTER,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, phoneNumber)
						.add(ExtParamsKey.PASSWORD, pass)
						.add(ExtParamsKey.REF_CODE, phoneIntroduce)
						.add(ExtParamsKey.FULL_NAME, fullName)
						.add(ExtParamsKey.ADDRESS, address)
						.add(ExtParamsKey.EMAIL, email)
						.add(ExtParamsKey.DEVICE_ID, Constants.DEVICE_ID)
						.add(ExtParamsKey.DEVICE_NAME, Constants.DEVICE_NAME)
						.add(ExtParamsKey.SIGNATURE,
								hash(phoneNumber + pass + Constants.DEVICE_ID
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}

	public String hash(String key) {
		try {
			return MD5Good.hash(key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void getInfoDaily(String agencyName, String pass,
			HttpResponseListener listener) {
		post(CommandRequest.GET_INFO,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, agencyName)
						.add(ExtParamsKey.PASSWORD, pass)
						.add(ExtParamsKey.SIGNATURE,
								hash(agencyName + pass
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}

	public void chaneStateAdmin(String agencyName, String adminName,
			String pass, int state, HttpResponseListener listener) {
		post(CommandRequest.CHANGE_AGENCY_STATE,
				ParamsBuilder
						.builder()
						.add(ExtParamsKey.AGENCY_NAME, agencyName)
						.add(ExtParamsKey.ADMIN_NAME, adminName)
						.add(ExtParamsKey.PASSWORD, pass)
						.add(ExtParamsKey.STATE, state)
						.add(ExtParamsKey.SIGNATURE,
								hash(agencyName + adminName + pass
										+ ConnectionConfig.CLIENT_KEY)).build(),
				listener);
	}
}
