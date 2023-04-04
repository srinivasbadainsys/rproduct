package com.rp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.rp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.rp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.rp.domain.User.class.getName());
            createCache(cm, com.rp.domain.Authority.class.getName());
            createCache(cm, com.rp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.rp.domain.Ruser.class.getName());
            createCache(cm, com.rp.domain.Workspace.class.getName());
            createCache(cm, com.rp.domain.Workspace.class.getName() + ".workspaceUsers");
            createCache(cm, com.rp.domain.Workspace.class.getName() + ".workspaceLocations");
            createCache(cm, com.rp.domain.Workspace.class.getName() + ".teams");
            createCache(cm, com.rp.domain.WorkspaceLocation.class.getName());
            createCache(cm, com.rp.domain.BusinessUnit.class.getName());
            createCache(cm, com.rp.domain.WorkspaceUser.class.getName());
            createCache(cm, com.rp.domain.TeamMember.class.getName());
            createCache(cm, com.rp.domain.Team.class.getName());
            createCache(cm, com.rp.domain.Team.class.getName() + ".teamMembers");
            createCache(cm, com.rp.domain.Job.class.getName());
            createCache(cm, com.rp.domain.Job.class.getName() + ".jobLocations");
            createCache(cm, com.rp.domain.Job.class.getName() + ".jobDocuments");
            createCache(cm, com.rp.domain.Job.class.getName() + ".jobCustomAttributes");
            createCache(cm, com.rp.domain.JobLocation.class.getName());
            createCache(cm, com.rp.domain.JobDocument.class.getName());
            createCache(cm, com.rp.domain.JobWork.class.getName());
            createCache(cm, com.rp.domain.UserWork.class.getName());
            createCache(cm, com.rp.domain.TeamWork.class.getName());
            createCache(cm, com.rp.domain.JobBoard.class.getName());
            createCache(cm, com.rp.domain.JobBoardSharedTo.class.getName());
            createCache(cm, com.rp.domain.JobBoardPost.class.getName());
            createCache(cm, com.rp.domain.JobCustomAttribute.class.getName());
            createCache(cm, com.rp.domain.Client.class.getName());
            createCache(cm, com.rp.domain.Client.class.getName() + ".clientAccounts");
            createCache(cm, com.rp.domain.Client.class.getName() + ".clientNotes");
            createCache(cm, com.rp.domain.Client.class.getName() + ".clientDocuments");
            createCache(cm, com.rp.domain.Client.class.getName() + ".contacts");
            createCache(cm, com.rp.domain.Client.class.getName() + ".clientGuidelineSubmissionDocuments");
            createCache(cm, com.rp.domain.ClientAccount.class.getName());
            createCache(cm, com.rp.domain.ClientNote.class.getName());
            createCache(cm, com.rp.domain.ClientDocument.class.getName());
            createCache(cm, com.rp.domain.ClientGuidelineSubmissionDocument.class.getName());
            createCache(cm, com.rp.domain.Contact.class.getName());
            createCache(cm, com.rp.domain.Catalogue.class.getName());
            createCache(cm, com.rp.domain.CatalogueValue.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
