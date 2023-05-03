package com.sms.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SMSInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> smsMap = new HashMap<String, String>();
        smsMap.put("App Name", "SMS");
        smsMap.put("App Description", "School Management System is a  Web Application for Students and Admin");
        smsMap.put("App Version", "1.0.0");
        smsMap.put("Contact Email", "info@sms.com");
        smsMap.put("Contact Mobile", "+212 0612345678");
        builder.withDetail("sms-info", smsMap);
    }

}
