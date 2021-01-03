package com.github.hiling.admin.config;

import com.alibaba.fastjson.JSONObject;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.MapUtils;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties("notifier.webhook")
public class DingTalkNotifier extends AbstractStatusChangeNotifier {

    private RestTemplate restTemplate = new RestTemplate();
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    private List<String> webhookUrl;

    private String adminServerUrl;

    /**
     * 消息模板
     */
    private static final String template = "%s \n\n 【服务名】: %s \n\n 【状态】: %s (%s) \n\n 【服务IP】: %s \n\n 【详情】: %s \n\n %s";

    private String titleAlarm = "系统告警";
    private String titleNotice = "系统通知";
    private String[] ignoreChanges = new String[]{"UNKNOWN:UP", "DOWN:UP", "OFFLINE:UP"};

    public DingTalkNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected boolean shouldNotify(InstanceEvent event, Instance instance) {
        if (!(event instanceof InstanceStatusChangedEvent)) {
            return false;
        } else {
            InstanceStatusChangedEvent statusChange = (InstanceStatusChangedEvent) event;
            String from = this.getLastStatus(event.getInstance());
            String to = statusChange.getStatusInfo().getStatus();
            return Arrays.binarySearch(this.ignoreChanges, from + ":" + to) < 0 && Arrays.binarySearch(this.ignoreChanges, "*:" + to) < 0 && Arrays.binarySearch(this.ignoreChanges, from + ":*") < 0;
        }
    }

    private <T> String post(String url, T t) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> requestEntity = new HttpEntity<>(t, headers);
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(url, requestEntity, String.class);
            return result.getBody();
        } catch (Exception ex) {
            log.error("发送通知失败！ url=" + url, ex);
            return "";
        }
    }

    private void sendMessage(Map<String, Object> message) {
        if (this.webhookUrl == null) {
            log.error("'webhookUrl' must not be null.");
            throw new IllegalStateException("'webhookUrl' must not be null.");
        } else {
            log.debug("webhookUrl={}  message={}", webhookUrl, message);
            Iterator iterator = this.webhookUrl.iterator();
            while (iterator.hasNext()) {
                String url = (String) iterator.next();
                this.executorService.execute(() -> {
                    post(url, message);
                });
            }
        }
    }


    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {

        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {

                Map<String, Object> parameters = new HashMap();
                Map<String, Object> req = new HashMap();

                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                String statusText;
                String statusTitle;
                switch (status) {
                    // 健康检查没通过
                    case "DOWN":
                        statusText = "健康检查没通过";
                        statusTitle = titleAlarm;
                        break;
                    // 服务离线
                    case "OFFLINE":
                        statusText = "服务离线";
                        statusTitle = titleAlarm;
                        break;
                    //服务上线
                    case "UP":
                        statusText = "服务上线";
                        statusTitle = titleNotice;
                        break;
                    // 服务未知异常
                    case "UNKNOWN":
                        statusText = "服务未知异常";
                        statusTitle = titleAlarm;
                        break;
                    default:
                        statusText = "未知状态";
                        statusTitle = titleNotice;
                        break;
                }

                Map<String, Object> detailsMap = instance.getStatusInfo().getDetails();
                String messageText = String.format(
                        template,
                        statusTitle,
                        instance.getRegistration().getName(),
                        status,
                        statusText,
                        instance.getRegistration().getServiceUrl(),
                        MapUtils.isEmpty(detailsMap) ? "无" : JSONObject.toJSONString(detailsMap),
                        "[查看详情](" + adminServerUrl + "/#/applications/" + instance.getId() + "/details)"
                );

                parameters.put("title", statusTitle);
                parameters.put("text", messageText);

                req.put("msgtype", "markdown");
                req.put("markdown", parameters);

                sendMessage(req);

                log.info("Instance {} ({}) is {} ({})", instance.getRegistration().getName(), event.getInstance(), status, statusText);
            } else {
                log.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(), event.getType());
            }
        });
    }
}