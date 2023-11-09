package dev.partenon.global.domain.ports.query;

/**
 * QueryBus generico
 */
@FunctionalInterface
public interface QueryBus {
    <T> T ask(Query query) throws Exception;

}
