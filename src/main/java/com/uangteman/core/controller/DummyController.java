package com.uangteman.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uangteman.common.utils.rest.RestResponse;
import com.uangteman.core.model.BankModel;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class DummyController {

    public String getAgeRange(String jsonString, String ServiceUrl){
        ObjectMapper mapper = new ObjectMapper();
        RestResponse value = null;
        try {
            value = mapper.readValue(jsonString, RestResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object counter = value.getData();
        String url = ServiceUrl + "/master-service/bank/search/";

        for(int i = 0; i < counter; i++) {
            BankModel bankDetail = getDetail(url,value.getData().get(i).getId());
            value.getData().get(i).setBankDetail(bankDetail);
        }

        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonInString;
    }

    private static BankModel getDetail(String url, long id){

        RestTemplate restTemplate = new RestTemplate();
        String newjson = restTemplate.getForObject(url + id, String.class);

        ObjectMapper mapper = new ObjectMapper();
        RestResponse newvalue = null;
        try {
            newvalue = mapper.readValue(newjson, RestResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BankModel bankDetail = newvalue.getData();

        return bankDetail;
    }

}
