/**
 * Copyright (C) 2018 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.datatags.service.utils;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DataTagsHelper {
//TODO: As plugin, post shoulbe return response
    private static final Logger LOG = LoggerFactory.getLogger(DataTagsHelper.class);
    private int responseCode = -1;
    private String responseMessage;
    public DataTagsHelper(String url, String key, String json) {
        sendTag(url, key, json);
    }

    public void sendTag(String url, String key, String json) {

        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build()){
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity( new StringEntity(json));
            httpPost.setHeader("X-Dataverse-key",key);
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = httpClient.execute(httpPost);

            responseCode = response.getStatusLine().getStatusCode();
            responseMessage = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
