/*
 * Copyright (C) 2016 user
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.api.synchro;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.telegram.api.Response;

/**
 *
 * @author user
 */
public class BotApiProxy implements InvocationHandler {

    private String token;
    private ObjectMapper mapper;

    public BotApiProxy(String botToken) {
        token = botToken;
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] os) throws Throwable {
        ArrayList<NameValuePair> nvps = new ArrayList<>(); 
        for (int i = 0; i < method.getParameterCount(); i++) {
            Parameter parameter = method.getParameters()[i];
            nvps.add(new BasicNameValuePair(parameter.getName(), os[i].toString()));
        }
        return callApiMethod(method.getName(), nvps, method.getReturnType());
    }

    private Object callApiMethod(String methodName, List<NameValuePair> nvps, Class apiResponse) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.telegram.org")
                    .setPath("/bot" + token + "/" + methodName)
                    .setParameters(nvps)
                    .build();

            HttpGet httpget = new HttpGet(uri);

            CloseableHttpResponse response;
            response = httpclient.execute(httpget);
            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, methodName + ": " + response.getStatusLine().getReasonPhrase());
                    return null;
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    long len = entity.getContentLength();
                    if (len != -1 && len < 2048) {
                        String json = EntityUtils.toString(entity);
                        return mapper.readValue(json, apiResponse);
                    } else {
                        // Stream content out
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, methodName + ": No stream returned");
                    }
                }
            } finally {
                try {
                    response.close();
                    httpclient.close();
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }


}
