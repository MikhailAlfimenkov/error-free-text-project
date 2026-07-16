package com.example.errorfreetext.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "https://speller.yandex.net/services/spellservice.json")
public interface UserClient {

}
