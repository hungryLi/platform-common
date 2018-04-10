package platform.common.base.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 
 * @author Administrator
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseMongoModel implements Serializable {

    private static final long serialVersionUID = 1034671331121855153L;

    /**
     * 主键
     */
    public String id;

   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
 
}
