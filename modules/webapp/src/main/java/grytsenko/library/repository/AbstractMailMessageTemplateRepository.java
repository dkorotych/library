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
package grytsenko.library.repository;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.Validate;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
public abstract class AbstractMailMessageTemplateRepository<Type extends MailMessageTemplateRepository.MailMessageTemplate> implements MailMessageTemplateRepository<Type> {

    private LoadingCache<String, Type> repository;
    private final Function<String, Type> templateCreationFunction;

    public AbstractMailMessageTemplateRepository(Function<String, Type> templateCreationFunction) {
        Validate.notNull(templateCreationFunction, "The \"templateCreationFunction\" is null");
        this.templateCreationFunction = templateCreationFunction;
    }

    @PostConstruct
    public void prepareTemplates() {
        repository = CacheBuilder.newBuilder()
                .maximumSize(5)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(CacheLoader.from(templateCreationFunction));
    }

    @Override
    public Type getBookAvailable() {
        return get("available");
    }

    @Override
    public Type getBookReserved() {
        return get("reserved");
    }

    @Override
    public Type getBookReleased() {
        return get("released");
    }

    @Override
    public Type getBookBorrowed() {
        return get("borrowed");
    }

    @Override
    public Type getBookReturned() {
        return get("returned");
    }

    protected Type get(String name) {
        try {
            return repository.get(name);
        } catch (ExecutionException exception) {
            throw new RuntimeException(exception);
        }
    }
}
