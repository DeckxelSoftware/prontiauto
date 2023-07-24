package com.ec.prontiauto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ec.prontiauto.repositoryImpl.CuotaRepositoryImpl;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = ("com.ec.prontiauto.utils"))
public class ScheduleConfig {

    @Autowired
    private CuotaRepositoryImpl cuotaRepositoryImpl;

    @Scheduled(cron = "0 0 2 * * *")
    public void cuotasEnMora() {
        this.cuotaRepositoryImpl.cuotasEnMora();
    }
}