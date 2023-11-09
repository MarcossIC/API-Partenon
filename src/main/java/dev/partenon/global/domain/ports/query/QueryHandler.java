package dev.partenon.global.domain.ports.query;

/**
 * QueryHandler Generico
 */
@FunctionalInterface
public interface QueryHandler<Q extends Query, R extends Response> {

    R handle(Q query) throws Exception;
}
