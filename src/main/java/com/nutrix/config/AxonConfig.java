package com.nutrix.config;

import com.nutrix.command.domain.RecipeA;
import com.nutrix.command.domain.RecommendationA;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    @Bean
    public Repository<RecipeA> eventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(RecipeA.class)
                .eventStore(eventStore)
                .build();
    }
    @Bean
    public Repository<RecommendationA> eventSourcingRepository2(EventStore eventStore) {
        return EventSourcingRepository.builder(RecommendationA.class)
                .eventStore(eventStore)
                .build();
    }

}

//    @Bean
//    XStream xstream(){
//        XStream xstream = new XStream();
//        // clear out existing permissions and set own ones
//        xstream.addPermission(NoTypePermission.NONE);
//        // allow any type from the same package
//        xstream.allowTypesByWildcard(new String[] {
//                "com.nutrix.**",
//                "org.axonframework.**",
//                "java.**",
//                "com.thoughtworks.xstream.**"
//        });
//
//        return xstream;
//    }
//
//    @Bean
//    @Primary
//    public Serializer serializer(XStream xStream) {
//        return XStreamSerializer.builder().xStream(xStream).build();
//    }