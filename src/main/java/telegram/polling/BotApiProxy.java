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
package telegram.polling;

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
import telegram.api.data.response.Response;
import telegram.api.annotation.Param;

/**
 *
 * @author user
 */
public class BotApiProxy implements InvocationHandler {

    private static final Logger LOG = Logger.getLogger(BotApiProxy.class.getName());
    private static final long MAX_MESSAGE_SIZE = 8192L;

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
            if (os[i] != null) {
                String name;
                if (parameter.isAnnotationPresent(Param.class)) {
                    name = parameter.getDeclaredAnnotation(Param.class).name();
                } else {
                    name = parameter.getName();
                }
                nvps.add(new BasicNameValuePair(name, os[i].toString()));
            }
        }
        return callApiMethod(method.getName(), nvps, method.getReturnType());
    }

    private Object callApiMethod(String methodName, List<NameValuePair> nvps, Class apiResponse)
            throws BotException {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.telegram.org")
                    .setPath("/bot" + token + "/" + methodName)
                    .setParameters(nvps)
                    .build();

            //LOG.log(Level.SEVERE, "call " + uri.toString());
            HttpGet httpget = new HttpGet(uri);
            CloseableHttpResponse response;
            response = httpclient.execute(httpget);
            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    LOG.log(Level.SEVERE, methodName + ": " + response.getStatusLine().getReasonPhrase());
                    throw new BotException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    long len = entity.getContentLength();
                    if (len != -1 && len < MAX_MESSAGE_SIZE) {
                        String json = EntityUtils.toString(entity);
                        return mapper.readValue(json, apiResponse);
                    } else {
                        // Stream content out of bounds
                        String error = (len == -1 ? ": No stream returned" : "Stream size is over the upper limit");
                        LOG.log(Level.SEVERE, methodName, error);
                        throw new BotException(error);
                    }
                }
            } finally {
                try {
                    response.close();
                    httpclient.close();
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                    throw new BotException(ex.getMessage(), ex);
                }
            }
        } catch (URISyntaxException | IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BotException(ex.getMessage(), ex);
        }
        return null;
    }

}
