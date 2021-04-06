package com.selenium.demo.configuration;


import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SeleniumConfiguration {
    @PostConstruct
    void postConstruct () {
        System.setProperty("webdriver.chrome.driver", "/Users/janes.m/OneDrive - Procter and Gamble/Desktop/chromedriver_win32/chromedriver.exe");
    }
    @Bean
    public ChromeDriver driver() {
        return new ChromeDriver() ;
    }
}
