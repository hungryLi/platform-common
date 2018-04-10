package platform.common.base.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Administrator
 *
 * @param <T>
 */

public class PageVo<T extends BaseMongoModel> implements Serializable {

    private static final long serialVersionUID = 3144038956308932204L;

    private static final int DEFAULT__SIZE = 10;
    private static final int DEFAULT__PAGE = 1;
    public static final int PAGING_YES = 1;
    public static final int PAGING_NO = 0;

    /**
     * 分页时的第几页
     */
    private Integer page = DEFAULT__PAGE;
    /**
     * 每页数目，默认为 DEFAULT__SIZE
     */
    private Integer size = DEFAULT__SIZE;
    /**
     * 查询结果总数
     */
    private Long sum;
    /**
     * 是否分页，默认是
     */
    @JsonIgnore
    private Integer isPaging = PAGING_YES;
    /**
     * 查询参数
     */
    @JsonIgnore
    private T entity;

    private Object list;
    
    private Map<String, Object> parMap; 

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page > 0 ? page : DEFAULT__PAGE;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Integer getIsPaging() {
        return isPaging;
    }

    public void setIsPaging(Integer isPaging) {
        this.isPaging = isPaging;
    }

    @JsonIgnore
    public Long getFrom() {
        return size.longValue() * (page - 1);
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

	public Map<String, Object> getParMap() {
		return parMap;
	}

	public void setParMap(Map<String, Object> parMap) {
		this.parMap = parMap;
	}
    
}
