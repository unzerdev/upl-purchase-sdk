package com.unzer.paylater.communication;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ResponseHeaderTest {

    @Test
    public void test() {
        ResponseHeader accessTokenHeader = new ResponseHeader("access_token", "12345");
        List<ResponseHeader> headers = Collections.singletonList(accessTokenHeader);

        ResponseHeader foundHeader = ResponseHeader.getHeader(headers, accessTokenHeader.getName());
        Assert.assertEquals(accessTokenHeader, foundHeader);

        String foundHeaderValue = ResponseHeader.getHeaderValue(headers, accessTokenHeader.getName());
        Assert.assertEquals(accessTokenHeader.getValue(), foundHeaderValue);
    }
}
