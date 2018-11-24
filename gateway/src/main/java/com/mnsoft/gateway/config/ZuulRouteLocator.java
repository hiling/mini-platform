//package com.mnsoft.gateway.config;
//
//import com.ctrip.framework.apollo.Config;
//import com.ctrip.framework.apollo.ConfigService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
//import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
//import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
//import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
//import org.springframework.util.StringUtils;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Set;
//
//
///**
// * Author by hiling, Email admin@mn-soft.com, Date on 11/25/2018.
// */
//public class ZuulRouteLocator  extends SimpleRouteLocator implements RefreshableRouteLocator {
//
//    private static final Logger logger = LoggerFactory.getLogger(ZuulRouteLocator.class);
//    private ZuulProperties properties;
//
//    public ZuulRouteLocator(String servletPath, ZuulProperties properties) {
//        super(servletPath, properties);
//        this.properties = properties;
//        logger.info("servletPath:{}", servletPath);
//    }
//
//    /**
//     * @description 刷新路由配置
//     * @author fuwei.deng
//     * @date 2017年7月3日 下午6:04:42
//     * @version 1.0.0
//     * @return
//     */
//    @Override
//    public void refresh() {
//        doRefresh();
//    }
//
//    @Override
//    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
//        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
//        // 从application.properties中加载路由信息
//        // routesMap.putAll(super.locateRoutes());
//        // 加载路由配置
//        routesMap.putAll(loadLocateRoute());
//        // 优化一下配置
//        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
//
//        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
//            String path = entry.getKey();
//            // Prepend with slash if not already present.
//            if (!path.startsWith("/")) {
//                path = "/" + path;
//            }
//            if (StringUtils.hasText(this.properties.getPrefix())) {
//                path = this.properties.getPrefix() + path;
//                if (!path.startsWith("/")) {
//                    path = "/" + path;
//                }
//            }
//            values.put(path, entry.getValue());
//        }
//        return values;
//    }
//
//    /**
//     * @description 加载路由配置，由子类去实现
//     * @author fuwei.deng
//     * @date 2017年7月3日 下午6:04:42
//     * @version 1.0.0
//     * @return
//     */
//    public Map<String, ZuulRoute> loadLocateRoute(){
//
//        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
//        Config config = ConfigService.getAppConfig();
//
//        Set<String> names = config.getPropertyNames();
//        //TODO
//        StringBuilder sb = new StringBuilder();
//        for (String name : names) {
//            sb.append(name + "/");
//            String key = name;
//            String value = config.getProperty(key,null);
//        }
//
//        return routes;
//    }
//
//    /**
//     * @description 路由定位器的优先级
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//
//}
