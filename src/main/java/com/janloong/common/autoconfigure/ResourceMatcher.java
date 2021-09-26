package com.janloong.common.autoconfigure;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 路径匹配器
 *
 * @author <a href ="https://blog.janloong.com">Janloong Doo</a>
 * @version 1.0.0
 * @since 2021-09-27 0:34
 */
@Configuration
@Data
@ConditionalOnBean({OAuth2ResourceServerConfiguration.class})
@ConfigurationProperties(prefix = "doo.matcher")
@RefreshScope
public class ResourceMatcher {

    private List<AntMatcher> permit;

   public class AntMatcher {
        private String method;
        private String pattern;

        @Override
        public String toString() {
            return "AntMatcher{" +
                    "method='" + method + '\'' +
                    ", pattern='" + pattern + '\'' +
                    '}';
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }
}
