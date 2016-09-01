/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astech.lexredupbot;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author irene
 */
public class Demo implements Runnable {

    public static final String BOT_NAME = "lexredupbot";

    public Integer botID = 0;
    public String botToken = "";
    long last_id = 465487294 + 1;
    long old_last_id = 0;

    public Demo() throws Exception {
        Properties bot_config = new Properties();
        try {
            bot_config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(BOT_NAME + ".properties"));
            botToken = bot_config.getProperty("token");
            botID = Integer.valueOf(botToken.split(":")[0]);
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    
    
    @Override
    public void run() {
        try {
            //System.out.println("Scheduling: " + System.nanoTime());
            if (old_last_id != last_id) {
                System.out.println("Requesting from id: " + last_id);
                old_last_id = last_id;
            }
            JSONObject r = getUpdates(last_id, 1, 0);
            if (r != null) {
                //System.out.println(r.toString()); 
                try {
                    JSONArray results = r.getJSONArray("result");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        int update_id = result.getInt("update_id");
                        System.out.println("update_id = " + update_id);

                        JSONObject message = result.getJSONObject("message");
                        if (message != null) {
                            String answer = null;
                            String text = null;
                            String user = null;
                            BigInteger chat_id = null;
                            try {
                                chat_id = message.getJSONObject("chat").getBigInteger("id");
                                user = message.getJSONObject("from").getString("first_name");
                                text = message.getString("text");
                            } catch (JSONException e) {
                                System.out.println(e.getMessage());
                            }
                            System.out.println("message from " + chat_id + " " + user + ". text = " + text);
                            if (text != null) {
                                if (text.startsWith("/")) {
                                    String cmd = text.substring(1);
                                    if ("help".equalsIgnoreCase(cmd)) {
                                        answer = "Hi, " + user + "!\n"
                                                + "I'm a simple lexical reduplicator\n"
                                                + "All I can is to reply you with your exact text ;)\n"
                                                + "Type /help for this text\n"
                                                + "Type /start to start me\n";
                                    } else if ("start".equalsIgnoreCase(cmd)) {
                                        answer = "Started";
                                    } else if (cmd.length() == 0) {
                                        System.out.println("Empty command: " + message.toString());
                                        if (message.has("reply_to_message")) {
                                            JSONObject reply_message = message.getJSONObject("reply_to_message");
                                            if (reply_message.has("text")) {
                                                String relply_text = reply_message.getString("text");
                                                if (relply_text.startsWith("/")) {
                                                    relply_text = relply_text.substring(1);
                                                }
                                                answer = processText(relply_text);
                                            }
                                        }
                                    } else {
                                        answer = processText(cmd);
                                    }
                                } else {
                                    answer = processText(text);
                                }
                            } else {
                                /// text is null   
                                System.out.println(message.toString());
                                if (message.has("new_chat_member")) {
                                    JSONObject cm = message.getJSONObject("new_chat_member");
                                    if (botID.equals(cm.getInt("id"))) {
                                        answer = "Спасибо, что позвали!";
                                    } else {
                                        answer = "LexredupBot приветствует тебя, " + cm.getString("first_name") + "!";
                                    }
                                } else if (message.has("left_chat_member")) {
                                    JSONObject cm = message.getJSONObject("left_chat_member");
                                    answer = "Пока, " + cm.getString("first_name") + "! Буду скучать!";
                                } else if (message.has("new_chat_title")) {
                                    String s = message.getString("new_chat_title");
                                    answer = processText(s);
                                } else if (message.has("sticker")) {
                                    sendSticker(chat_id, "BQADAgAD2QADWfR0AAEoBUkSdNeWIQI");
                                } else {
                                    //System.out.println(message.toString());
                                    answer = null;//"Text messages allowed only";
                                }

                            }

                            if (answer != null) {
                                System.out.println(chat_id + "  " + answer);
                                sendMessage(chat_id, answer);
                            }
                        }
                        if (update_id >= last_id) {
                            last_id = update_id + 1;
                        }
                    }

                } catch (Exception ex) {

                    System.out.println(r);
                    Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //null
                last_id++;
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void startPooler() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleWithFixedDelay(this, 0, 2, TimeUnit.SECONDS);
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    private JSONObject getMe() {
        try {
            CloseableHttpClient httpclient = getHttpClient();

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.telegram.org")
                    .setPath("/bot" + botToken + "/getMe")
                    //.setParameter("q", "httpclient")
                    .build();

            HttpGet httpget = new HttpGet(uri);
            //System.out.println(httpget.getURI());

            CloseableHttpResponse response;
            response = httpclient.execute(httpget);
            try {
                //System.out.println(response.getProtocolVersion());
                //System.out.println(response.getStatusLine().getStatusCode());
                //System.out.println(response.getStatusLine().getReasonPhrase());
                System.out.println(response.getStatusLine().toString());

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    long len = entity.getContentLength();
                    if (len != -1 && len < 2048) {
                        //System.out.println(EntityUtils.toString(entity));
                        return new JSONObject(EntityUtils.toString(entity));
                    } else {
                        // Stream content out
                    }
                }
            } finally {
                try {
                    response.close();
                    httpclient.close();

                } catch (IOException ex) {
                    Logger.getLogger(Demo.class
                            .getName()).log(Level.SEVERE, null, ex);

                }
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private JSONObject getUpdates(long offset, int limit, int timeout) {
        try {
            CloseableHttpClient httpclient = getHttpClient();

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.telegram.org")
                    .setPath("/bot" + botToken + "/getUpdates")
                    .setParameter("offset", String.valueOf(offset))
                    .setParameter("limit", String.valueOf(limit))
                    .setParameter("timeout", String.valueOf(timeout))
                    .build();

            HttpGet httpget = new HttpGet(uri);
            //System.out.println(httpget.getURI());

            CloseableHttpResponse response;
            response = httpclient.execute(httpget);

            try {
                //System.out.println(response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() != 200) {
                    Logger.getLogger(Demo.class
                            .getName()).log(Level.SEVERE, null, "getUpdates: " + response.getStatusLine().getReasonPhrase());
                    return null;
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    long len = entity.getContentLength();
                    if (len != -1 && len < 2048) {
//                        System.out.println(EntityUtils.toString(entity));
                        return new JSONObject(EntityUtils.toString(entity));
                    } else {
                        // Stream content out
                    }
                }
            } finally {
                try {
                    response.close();
                    httpclient.close();

                } catch (IOException ex) {
                    Logger.getLogger(Demo.class
                            .getName()).log(Level.SEVERE, null, ex);

                }
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void sendMessage(BigInteger chat_id, String text) {
        sendMessage(chat_id, text, "Markdown");
    }

    private void sendMessage(BigInteger chat_id, String text, String parse_mode) {
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient();

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.telegram.org")
                    .setPath("/bot" + botToken + "/sendMessage")
                    .setParameter("chat_id", chat_id.toString())
                    .setParameter("text", text)
                    .setParameter("parse_mode", parse_mode)
                    .build();

            HttpGet httpget = new HttpGet(uri);
            //System.out.println(httpget.getURI());

            CloseableHttpResponse response;
            response = httpclient.execute(httpget);

        } catch (URISyntaxException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();

                }
            } catch (IOException ex) {
                Logger.getLogger(Demo.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void sendSticker(BigInteger chat_id, String stickerId) {
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient();

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.telegram.org")
                    .setPath("/bot" + botToken + "/sendSticker")
                    .setParameter("chat_id", chat_id.toString())
                    .setParameter("sticker", stickerId)
                    .build();

            HttpGet httpget = new HttpGet(uri);
            //System.out.println(httpget.getURI());

            CloseableHttpResponse response;
            response = httpclient.execute(httpget);

        } catch (URISyntaxException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Demo.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();

                }
            } catch (IOException ex) {
                Logger.getLogger(Demo.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    protected int detectLang(String word) {
        Pattern pat = Pattern.compile("[А-Яё]");
        if (pat.matcher(word).matches()) {
            return 0;
        }
        return 1;
    }

    static private final List<Character> vowels_ru = Arrays.asList('А', 'О', 'У', 'Я', 'Ё', 'Ю', 'Е', 'Э', 'И', 'Ы');
    static private final List<Character> vowels_en = Arrays.asList('E', 'U', 'I', 'O', 'A');

    static private final List<String> stop_words = Arrays.asList("И", "НА", "ЗА", "У", "ПРО", "ИЗ", "БЕЗ", "А", "НЕ", "НИ");

    protected boolean briefWordScan(String word, List<Integer> vowels) {
        //if (detectLang(word)!=0)
        //    return false;
        word = word.toUpperCase();
        for (int i = 0; i < word.length(); i++) {
            if (vowels_ru.contains(word.charAt(i))) {
                vowels.add(i);
            }
            if (vowels_en.contains(word.charAt(i))) {
                vowels.add(i);
            }
        }
//        System.out.println(vowels);
        return (vowels.size() > 0);
    }

    private String redup(String word) {
        List<Integer> vowels = new ArrayList<>();
        if (!briefWordScan(word, vowels)) {
            return word;
        }
        boolean startsUpper = word.substring(0, 1).equals(word.substring(0, 1).toUpperCase());

        int firstw = vowels.get(0);

        String newword = word.substring(firstw);
        int v = vowels_ru.indexOf(newword.toUpperCase().charAt(0));
        if (v >= 0) {
            // russian
            if (firstw >= 3) {
                newword = (startsUpper ? "Х" : "х") + "уй" + word.substring(firstw - 1);
            } else {
                if (v < 3) {
                    // hard vowel -> the soft analog
                    newword = vowels_ru.get(v + 3).toString().toLowerCase() + newword.substring(1);
                }
                if (startsUpper) {
                    newword = "Ху" + newword.toLowerCase();
                } else {
                    newword = "ху" + newword;
                }
            }
        } else {
            // english
            v = vowels_en.indexOf(newword.toUpperCase().charAt(0));
            if (word.substring(0, 1).equals(word.substring(0, 1).toUpperCase())) {
                newword = "Shm" + newword.toLowerCase();
            } else {
                newword = "shm" + newword;
            }
        }
        return newword;
    }

    public String processText(String text) {

        String[] words = text.split(" ");
        String answer = "";
        for (int i = 0; i < words.length; i++) {
            if (words.length > 1 && stop_words.contains(words[i].toUpperCase())) {
                answer += words[i] + " ";
            } else {
                answer += redup(words[i]) + " ";
            }
        }

        return answer;
    }

    public static void main(String args[]) {
        try {
            System.out.println("Here we go!");
            
            Demo demo = new Demo();
            
            JSONObject ret = demo.getMe();
            if (ret != null) {
                System.out.println(ret.toString());
            }
            
            //demo.getUpdates(465487011, 100, 0);
            //demo.startPooler();
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
