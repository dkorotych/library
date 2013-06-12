package grytsenko.library.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import grytsenko.library.model.BookFilter;
import grytsenko.library.service.BookService;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

/**
 * Test for {@link BooksController}.
 */
public class BooksControllerTests {

    BookService bookService;
    BooksController booksController;

    @Before
    public void prepare() {
        // Setup SUT.
        bookService = mock(BookService.class);

        booksController = new BooksController(null, bookService);
    }

    /**
     * User selects new filter.
     */
    @Test
    public void testFilter() throws Exception {
        // Setup data.
        String selectedFilter = "RELATED";
        Model model = mock(Model.class);

        // Setup behavior.

        // Execute.
        booksController.filter(model, selectedFilter);

        // Verify behavior.
        verify(model).addAttribute("filter", BookFilter.RELATED);
        verifyNoMoreInteractions(model);
    }

    /**
     * User selects filter, that is not supported.
     */
    @Test
    public void testFilterNotSupported() throws Exception {
        // Setup data.
        String selectedFilter = "UNSUPPORTED";
        Model model = mock(Model.class);

        // Setup behavior.

        // Execute.
        booksController.filter(model, selectedFilter);

        // Verify behavior.
        verify(model).addAttribute("filter", BookFilter.ALL);
        verifyNoMoreInteractions(model);
    }

}
