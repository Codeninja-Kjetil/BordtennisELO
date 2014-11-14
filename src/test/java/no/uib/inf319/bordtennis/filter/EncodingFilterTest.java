package no.uib.inf319.bordtennis.filter;

import static org.mockito.Mockito.verify;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class EncodingFilterTest {

    private EncodingFilter encodingFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    @Before
    public void setUp() throws Exception {
        encodingFilter = new EncodingFilter();
    }

    @Test
    public void doFilterShouldSetRequestEncodingToUTF8() throws Exception {
        encodingFilter.doFilter(request, response, chain);

        verify(request).setCharacterEncoding("UTF-8");
    }

    @Test
    public void doFilterShouldPassRequestAlongFilterChain() throws Exception {
        encodingFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
