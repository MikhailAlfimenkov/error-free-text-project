package com.example.errorfreetext.client;

import com.example.errorfreetext.cofiguration.FeignConfig;
import com.example.errorfreetext.dto.CheckTextRequest;
import com.example.errorfreetext.dto.SpellResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "yandexSpellerClient",
        url = "https://speller.yandex.net/services/spellservice.json",
        configuration = FeignConfig.class
)
public interface YandexSpellerClient {

    @PostMapping(value ="/checkTexts", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    List<List<SpellResult>> checkTexts(@RequestBody CheckTextRequest request);

}
