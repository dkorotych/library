package grytsenko.library.test;

import static org.mockito.Mockito.doAnswer;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

/**
 * Utilities for simplifying work with Mockito.
 */
public final class MockitoUtils {

    /**
     * Returns the first argument of method as is.
     * 
     * @return object to select a method for stubbing.
     */
    public static Stubber doReturnFirstArgument() {
        return doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }

        });
    }

    private MockitoUtils() {
    }

}
