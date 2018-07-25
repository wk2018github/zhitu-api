package zhitu.cfg;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SQApiResponse <T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 成功
	 */
	public static final String SUCCESS = "000000";
	
	/**
	 * 业务异常
	 */
	public static final String EXCEPTION_BUSINESS = "000001";
	
	/**
	 * 失败
	 */
	public static final String ERROR = "100000";

	@JsonProperty(value = "code")
	private String code;
	
	@JsonProperty(value = "message")
	private String message;
	
	@JsonProperty(value = "data")
	private T data;

	protected SQApiResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 创建请求成功时返回结果对象。
	 * 
	 * @return
	 */
	public static <T> SQApiResponse<T> success(T data) {
		return new SQApiResponse<T>(SUCCESS, "", data);
	}
	/**
	 * 返回数据  data 没有值 msg 起提示作用
	 * @param msg
	 * @param data
	 * @return
	 */
	public static <T> SQApiResponse<T> success(String msg,T data) {
		return new SQApiResponse<T>(SUCCESS, msg, data);
	}

	/**
	 * 处理指定异常的返回结果。
	 * 
	 * @param e
	 * @return
	 */
	public static <T> SQApiResponse<T> error(Throwable e) {
		// 处理业务异常
		if (e instanceof BusinessException) {
			String message = ((BusinessException) e).getMessage();
			return response(EXCEPTION_BUSINESS, message);
		}
		return error(e.toString());
	}

	/**
	 * 处理错误消息的返回结果
	 * 
	 * @param message
	 * @return
	 */
	public static <T> SQApiResponse<T> error(String message) {
		return error(ERROR, message, null);
	}

	/**
	 * 错误消息及具体的数据
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return ApiResponse<T>
	 */
	public static <T> SQApiResponse<T> error(String code, String message, T data) {
		return new SQApiResponse<T>(code, message, data);
	}
	
	/**
	 * 返回消息及具体的数据
	 * 
	 * @param code
	 * @param message
	 * @return ApiResponse<T>
	 */
	public static <T> SQApiResponse<T> response(String code, String message) {
		return response(code, message, null);
	}

	/**
	 * 返回消息及具体的数据
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return ApiResponse<T>
	 */
	public static <T> SQApiResponse<T> response(String code, String message, T data) {
		return new SQApiResponse<T>(code, message, data);
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
