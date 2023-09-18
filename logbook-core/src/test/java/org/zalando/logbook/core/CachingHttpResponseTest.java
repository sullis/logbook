package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.attributes.HttpAttributes;
import org.zalando.logbook.test.MockHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

final class CachingHttpResponseTest {

    @Test
    void shouldDelegate() {
        final CachingHttpResponse unit = new CachingHttpResponse(MockHttpResponse.create());
        assertThat(unit.getHeaders()).isEmpty();
    }

    @Test
    void shouldCache() {
        final HttpResponse delegate = mock(HttpResponse.class);
        when(delegate.getHeaders()).thenReturn(HttpHeaders.empty());

        final CachingHttpResponse unit = new CachingHttpResponse(delegate);

        unit.getHeaders();
        unit.getHeaders();

        verify(delegate, atMost(1)).getHeaders();
    }

    @Test
    void shouldGetAttributes() {
        final HttpResponse delegate = mock(HttpResponse.class);
        final CachingHttpResponse unit1 = new CachingHttpResponse(delegate);

        assertThat(unit1.getAttributes()).isEqualTo(HttpAttributes.EMPTY);

        final HttpAttributes attributes = HttpAttributes.of("key", "val");
        final CachingHttpResponse unit2 = new CachingHttpResponse(delegate, attributes);

        assertThat(unit2.getAttributes()).isEqualTo(attributes);
    }

}
