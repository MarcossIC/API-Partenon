package dev.partenon.global.domain.ports;

@FunctionalInterface
public interface Mapper<M, R> {
    R map(M model);
}
