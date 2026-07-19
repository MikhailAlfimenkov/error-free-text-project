package com.example.errorfreetext.client;

import com.example.errorfreetext.dto.SpellResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "yandexSpellerClient", url = "https://speller.yandex.net/services/spellservice.json")
public interface YandexSpellerClient {

    @GetMapping("/checkText")
    List<SpellResult> checkText(@RequestParam("text") String text, @RequestParam("lang")String lang);

}
