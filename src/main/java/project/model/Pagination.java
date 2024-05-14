package project.model;

import lombok.Data;

/**
 * Used for pagination.
 */
@Data
public class Pagination {
    private Integer page;
    private Integer limit;
    private String order;
    private String type;
}
