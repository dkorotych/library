package grytsenko.library.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Results of search.
 */
public class SearchResults<T> implements Serializable {

    private static final long serialVersionUID = 7153596408658236211L;

    public static <T> SearchResults<T> create(Page<T> page) {
        SearchResults<T> results = new SearchResults<>();
        results.setPageNum(page.getNumber());
        results.setPagesTotal(page.getTotalPages());
        results.setContent(page.getContent());
        return results;
    }

    private int pageNum;
    private int pagesTotal;

    private List<T> content;

    public SearchResults() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(int pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

}
