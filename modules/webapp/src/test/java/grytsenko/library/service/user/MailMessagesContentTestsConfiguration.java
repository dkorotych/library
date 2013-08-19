/*
 * Copyright 2013 Dmitry Korotych &lt;dkorotych at gmail dot com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grytsenko.library.service.user;

import java.io.IOException;
import java.util.Properties;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.MailSender;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
@Configuration
public class MailMessagesContentTestsConfiguration {

    @Bean
    public MailSender mailSender() {
        return Mockito.mock(MailSender.class);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        return source;
    }

    @Bean
    public Properties mailProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/mail.properties"));
        return properties;
    }

    @Bean
    public NotifyUsersService notifyUsersService() {
        return new NotifyUsersService();
    }
}
