package zhitu.cfg;

public class JqGridReturnedData {
  /**
   * 当前第几页
   */
  private int page;

  /**
   * 数据总数
   */
  private int records;

  /**
   * 返回的list
   */
  private Object rows;

  /**
   * 总页数
   */
  private int total;

  /**
   * 返回的额外数据，在自定义footer时使用
   */
  private Object userdata;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getRecords() {
    return records;
  }

  public void setRecords(int records) {
    this.records = records;
  }

  public Object getRows() {
    return rows;
  }

  public void setRows(Object rows) {
    this.rows = rows;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public Object getUserdata() {
    return userdata;
  }

  public void setUserdata(Object userdata) {
    this.userdata = userdata;
  }
}
