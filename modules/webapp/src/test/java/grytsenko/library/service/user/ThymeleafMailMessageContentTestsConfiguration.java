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

import grytsenko.library.repository.MailMessageTemplateRepository;
import grytsenko.library.repository.thymeleaf.ThymeleafMailMessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
@Configuration
@Import(MailMessagesContentTestsConfiguration.class)
public class ThymeleafMailMessageContentTestsConfiguration {

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail/");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    @Bean
    public MailMessageTemplateRepository mailMessageTemplateRepository() {
        return new ThymeleafMailMessageRepository();
    }
}
