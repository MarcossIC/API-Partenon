package dev.partenon.global.domain.abstractcomponents.query;

/**
 * QueryBus generico
 */
@FunctionalInterface
public interface QueryBus {
    <T> T ask(Query query) throws Exception;

}
