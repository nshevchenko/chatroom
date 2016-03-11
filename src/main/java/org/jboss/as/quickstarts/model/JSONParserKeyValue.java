/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.persistence.Table;



import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParsingException;

import java.io.IOException;
import java.io.StringReader;
import java.io.BufferedReader;
import java.util.*;
/**
 * Definition of the chat Message entity
 * Contains:
 - ID
 - username (author)
 - message (content)
 */

public class JSONParserKeyValue{

    private Map<String, String> keyValues;

    public JSONParserKeyValue(HttpServletRequest req){
        String jsonStr = getJsonDataFromRequest(req);
        final JsonParser parser = Json.createParser(new StringReader(jsonStr));
        keyValues = new HashMap<String, String>();
        // parse string to hashmap key->values
        String key = null;
        String value = null;
        // System.out.println("data " + jsonStr);
        try {
            while (parser.hasNext()) {
                final Event event = parser.next();
                switch (event) {
                case KEY_NAME:
                    key = parser.getString();
                    if(!keyValues.containsKey(key)){
                        keyValues.put(key, null);
                    }
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    if(keyValues.get(key) == null && value != null){
                        keyValues.put(key, value);
                    }
                    break;
                }
            }
        } catch(JsonParsingException e){
            System.out.println(e);
        }

        Iterator it = keyValues.entrySet().iterator();
        while (it.hasNext())
            Map.Entry pair = (Map.Entry)it.next();
    }

    public String getValueByKey(String key){
        return keyValues.get(key);
    }

    public String[] getKeys(){
        return keyValues.keySet().toArray(new String[0]);
    }

    private String getJsonDataFromRequest(HttpServletRequest req){
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader reader = (BufferedReader)req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e){}
        return buffer.toString();
    }
}
