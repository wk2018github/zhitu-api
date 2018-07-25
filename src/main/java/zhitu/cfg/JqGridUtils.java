package zhitu.cfg;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JqGridUtils {
  
  /**
   * 解析jqGrid参数
   * @param request
   * @return
   */
  public static JqGridParams send(HttpServletRequest request) {
    JqGridParams params = new JqGridParams();
    
    String page = request.getParameter("page");
    if(StringUtils.isNotEmpty(page)) {
      params.setPage(page.matches("[1-9]+[0-9]*") ? Integer.parseInt(page) : 0);
    }
    
    String rows = request.getParameter("rows");
    if(StringUtils.isNotEmpty(rows)) {
      params.setRows(Integer.parseInt(rows) > 500 ? 500 : Integer.parseInt(rows));
    }
    params.setSidx(request.getParameter("sidx"));
    params.setSord(request.getParameter("sord"));

    return params;
  }

  /**
   * 返回给客户端的数据
   * @param map
   * @param returnedData
   */
  public static void returnData(Map<String, Object> map,
      JqGridReturnedData returnedData) {
    map.put("page", returnedData.getPage());
    map.put("records", returnedData.getRecords());
    map.put("rows", returnedData.getRows());
    map.put("total", returnedData.getTotal());
    map.put("userdata", returnedData.getUserdata());
  }
}
